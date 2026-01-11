package clinic;

import util.List;
import util.CircularLinkedList;
import util.Sort;
import util.Date;
import util.Visit;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * User interface class to process command lines in the terminal
 *
 * @author Jasmine Saffold
 */
public class ClinicManager {

    public static List<Provider> providerList = new List<Provider>();
    public static CircularLinkedList<Provider> technicianList = new CircularLinkedList<Provider>();
    public static List<Appointment> appList = new List<Appointment>();
    public static List<Person> patientList = new List<>();
    private static boolean CODE_BREAK = false;

    /**
     * Creates a timeslot object if the time is valid
     *
     * @param slotNum - from terminal input
     * @return time
     */
    public static Timeslot createTimeslot(String slotNum) {

        if(!onlyDigits(slotNum)) {
            System.out.println(slotNum + " is not a valid time slot.");
            throw new IllegalArgumentException();
        }
        int number = Integer.parseInt(slotNum);
        Timeslot time = null;

        switch (number) {
            case 1:
                time = new Timeslot(9, 0);
                break;
            case 2:
                time = new Timeslot(9, 30);
                break;
            case 3:
                time = new Timeslot(10, 0);
                break;
            case 4:
                time = new Timeslot(10, 30);
                break;
            case 5:
                time = new Timeslot(11, 0);
                break;
            case 6:
                time = new Timeslot(11, 30);
                break;
            case 7:
                time = new Timeslot(14, 0);
                break;
            case 8:
                time = new Timeslot(14, 30);
                break;
            case 9:
                time = new Timeslot(15, 0);
                break;
            case 10:
                time = new Timeslot(15, 30);
                break;
            case 11:
                time = new Timeslot(16, 0);
                break;
            case 12:
                time = new Timeslot(16, 30);
                break;
            default:
                System.out.println(slotNum + " is not a valid time slot.");
                throw new IllegalArgumentException();
        }
        return time;
    }

    /**
     * Reads providers.txt and populates technician and provider lists
     *
     * @param filename - providers.txt
     */
    public static void loadProviders(String filename) throws FileNotFoundException {
        File f = new File(filename);
        Scanner providerFile = new Scanner(f);

        while (providerFile.hasNextLine()) {

            // reads a line and splits the line by the space character, then splits the DOB
            String line = providerFile.nextLine();
            String[] tokens = line.split("  ");
            String[] birthday = tokens[3].split("/");

            // Make Date object for DOB, make Profile for provider, Find location of provider
            Date dob = new Date(Integer.parseInt(birthday[2]), Integer.parseInt(birthday[0]), Integer.parseInt(birthday[1]));
            Profile provProfile = new Profile(tokens[1], tokens[2], dob);
            Location provLocate = Location.valueOf(tokens[4].toUpperCase());

            if(tokens[0].equals("T")){ // separate technicians in order to add them to the technician list

                Provider technician = new Technician(provLocate, provProfile, Integer.parseInt(tokens[5]));
                technicianList.add(technician);
                providerList.add(technician);

            } else{ // If they are a doctor

                Specialty docSpecial = Specialty.valueOf(tokens[5]);
                Provider doctor = new Doctor(provLocate, provProfile, docSpecial, tokens[6]);
                providerList.add(doctor);

            }
        }
        providerFile.close();
        Sort.provider(providerList);
    }

    /**
     * Checks to see if a string only contains digits
     *
     * @param str - string to check
     * @return true if the string is only digits, false otherwise
     */
    private static boolean onlyDigits(String str) {
        return str != null && str.matches("\\d+");
    }

    /**
     * Checks to make sure the commands are formatted correctly
     * Number of commands must have specific length
     *
     * @param length - length of command input
     * @param command - command input from terminal
     * @return true if number of commands is correct, false otherwise
     */
    public static boolean lengthSanitizer(int length, String[] command){

        if(command.length > length){ // if there are too many commands
            System.out.println("Missing data tokens.");
            return false;
        }else if (command.length < length){ // if there are too little commands
            System.out.println("Missing data tokens.");
            return false;
        }
        return true;

    }

    /**
     * Checks to make sure the date commands are formatted correctly
     *
     * @param type - dob or appointment date
     * @param command - command input from terminal
     * @return appDate
     */
    public static Date dateSanitizer(String type, String[] command){
        Date appDate = null;
        if (type.equals("app")) {
            String[] appDateList = command[1].split("/");
            Date appDateGetter = new Date(Integer.parseInt(appDateList[2]), Integer.parseInt(appDateList[0]), Integer.parseInt(appDateList[1]));

            if (appDateGetter.isValidAppTime()){ appDate = appDateGetter;
            } else { throw new IllegalArgumentException(); }

        }else{
            String[] dobList = command[5].split("/");
            Date dobDateGetter = new Date(Integer.parseInt(dobList[2]), Integer.parseInt(dobList[0]), Integer.parseInt(dobList[1]));

            if(dobDateGetter.isValidBirthday()){
                appDate = dobDateGetter;
            }else{ throw new IllegalArgumentException(); }
        }
        return appDate;
    }

    /**
     * Private helper to match the doctor's NPI to the doctor's info
     *
     * @param fname - first name of doctor
     * @param lname - last name of doctor
     * @param dob - date of birth of doctor
     * @param npi - NPI of doctor
     * @return doctor
     */
    private static Person findDoctor(String fname, String lname, Date dob, String npi) {
        Person doctor = null;

        boolean codeBreak = true;
        for (int i = 0; i < providerList.size(); i++){
            if (providerList.get(i) instanceof Doctor){
                Doctor doc = (Doctor) providerList.get(i);
                if (doc.getNpi().equals(npi)){
                    doctor = doc;
                    codeBreak = false;
                    break;
                }
            }
        }
        if (codeBreak){
            System.out.println(npi + " - provider doesn't exist");
            CODE_BREAK = true;
        }
        return doctor;
    }

    /**
     * Checks provider availability at the given timeslot
     *
     * @param doctor - Person object
     * @param command - string array of commands
     * @param appDate - date of appointment
     * @param appTimeslot - timeslot of the appointment
     * @param patientProf - patient's profile
     * @param patient - Patient object
     * @return currAppoint
     */
    private static Appointment availability(Person doctor, String[] command, Date appDate, Timeslot appTimeslot, Profile patientProf, Patient patient) {

        Appointment currAppoint = new Appointment(appDate, appTimeslot, patient, doctor);
        for (int i = 0; i < appList.size(); i++) {
            if (appList.get(i).getProvider() instanceof Doctor) {
                Timeslot newAppTimeSlot = appList.get(i).getTimeslot();
                Doctor prov = (Doctor) appList.get(i).getProvider();
                Date apdate = appList.get(i).getDate();
                if (appList.get(i).compareTo(currAppoint) == 0) { System.out.println(patientProf.toString()
                        + " has an existing appointment at the same time slot.");
                    CODE_BREAK = true;
                } else if (prov.equals(doctor) && newAppTimeSlot.equals(appTimeslot) && apdate.equals(appDate)) {
                    System.out.println(String.format("[%s %s %s, %s][%s] is not available at slot %s",
                            doctor.getProfile().getFname(),                    // Provider's first name
                            doctor.getProfile().getLname(),                    // Provider's last name
                            doctor.getProfile().getDob(),
                            prov.getLocation().toString(),
                            doctor.toString(),
                            command[2].trim()));
                    CODE_BREAK = true;
                }
            }
        }
        return currAppoint;
    }

    /**
     * Helper method for scheduleAppointment
     * Adds appointment to appointment list
     *
     * @param fname - first name of profile
     * @param lname - last name of profile
     * @param dob - date of birth of profile
     * @param npi - NPI of doctor
     * @param appDate - sanitized date
     * @param appTimeslot - sanitized timeslot
     */
    public static void appScheduler(String fname, String lname, Date dob, String npi, Date appDate, Timeslot appTimeslot, String[] command){
        Profile patientProf = new Profile(fname, lname, dob);
        Patient patient = new Patient(patientProf);
        Person doctor = findDoctor(fname, lname, dob, npi);

        if(CODE_BREAK) {
            return;
        }
        Appointment currAppoint = availability(doctor, command, appDate, appTimeslot, patientProf, patient);

        if(CODE_BREAK) {
            return;
        }
        boolean found = false;
        for(int i = 0; i < patientList.size(); i++) {
            if(patientList.get(i).getProfile().equals(patientProf)) {
                found = true;
                break;
            }
        }
        if(!found) {
            patientList.add(patient);
        }
        if(patient instanceof Patient) {
            Visit newVisit = new Visit(currAppoint);
            if(((Patient) patient).getVisits() == null) {
                ((Patient) patient).setVisits(newVisit);
            } else {
                ((Patient) patient).getVisits().setNext(newVisit);
            }
        }
        appList.add(currAppoint);

        System.out.println(currAppoint.toString() + " booked.");
    }

    /**
     * Case D, schedules an appointment
     *
     * @param command - command input from terminal
     */
    public static void scheduleAppointment(String[] command){
        Date appDate = null;
        Timeslot appTimeslot = null;
        String fname = null;
        String lname = null;
        Date dob = null;
        String npi = null;

        if(!lengthSanitizer(7, command)){ return;}

        try {
            appDate = dateSanitizer("app",command);

            String timeslot = command[2].trim();
            Timeslot appTime = createTimeslot(timeslot);
            appTimeslot = appTime;

            fname = command[3].trim();
            lname = command[4].trim();

            dob = dateSanitizer("dob", command);

            npi = command[6].trim();
            if (!onlyDigits(npi)){
                System.out.println(npi + " - provider doesn't exist");
                return;
            }
        } catch (Exception e) {
            return;
        }
        appScheduler(fname, lname, dob, npi, appDate,appTimeslot, command);
    }

    /**
     * Private helper method to find a technician and a free room
     *
     * @param appTimeslot - timeslot for appointment
     * @param roomtype - imaging room
     * @param command - command from terminal input
     * @return tech
     */
    private static Person findTech(Timeslot appTimeslot, Radiology roomtype , String[] command) {
        Person tech = null;
        boolean haveTech = false;

        for (int i = 0; i < technicianList.size(); i++) {
            Technician newTech = (Technician) technicianList.get(i).getData();
            boolean techBusy = false;
            boolean roomOccupied = false;

            for (int j = 0; j < appList.size(); j++) {
                if (appList.get(j) instanceof Imaging) {
                    Imaging appointment = (Imaging) appList.get(j);

                    if (appointment.getTimeslot().equals(appTimeslot) && appointment.getProvider().equals(newTech)) {
                        techBusy = true;
                        break;
                    }
                    if(appointment.getTimeslot().equals(appTimeslot)
                            && appointment.getRoom() == roomtype
                            && appointment.getProvider() instanceof Technician
                            && ((Technician) appointment.getProvider()).getLocation() == newTech.getLocation()) {
                        roomOccupied = true;
                        break;
                    }
                }
            }
            if (!techBusy && !roomOccupied) {
                tech = newTech;
                haveTech = true;
                break;
            }
        }
        if (!haveTech) {
            System.out.println("Cannot find an available technician for " + roomtype + " at slot " + command[2]);
            CODE_BREAK = true;
        }

        return tech;
    }

    /**
     * Helper method to scheduleImagingAppointment
     * Adds imaging appointments to appointment list
     *
     * @param fname - first name of profile
     * @param lname - last name of profile
     * @param dob - date of birth of profile
     * @param appDate - sanitized date
     * @param appTimeslot - sanitized timeslot
     * @param roomtype - type of radiology room
     */
    public static void imagingScheduler(String fname, String lname, Date dob, Date appDate, Timeslot appTimeslot, Radiology roomtype , String[] command){
        Profile patientProf = new Profile(fname, lname, dob);
        Person patient = new Patient(patientProf);
        Person tech = findTech(appTimeslot, roomtype, command);
        if(CODE_BREAK) {
            return;
        }
        Imaging currAppoint = new Imaging(appDate, appTimeslot, patient, tech, roomtype);

        for (int i = 0; i < appList.size(); i++) {
            if (appList.get(i).getProvider() instanceof Technician) {
                if (appList.get(i).compareTo(currAppoint) == 0) {
                    System.out.println(patientProf.toString()
                            + " has an existing appointment at the same time slot.");
                    return;
                }
            }
        }
        boolean found = false;
        for(int i = 0; i < patientList.size(); i++) {
            if(patientList.get(i).getProfile().equals(patientProf)) {
                found = true;
                break;
            }
        }
        if(!found) {
            patientList.add(patient);
        }
        if (patient instanceof Patient) {
            Visit newVisit = new Visit(currAppoint);
            if (((Patient) patient).getVisits() == null) {
                ((Patient) patient).setVisits(newVisit);
            } else {
                ((Patient) patient).getVisits().setNext(newVisit);
            }
        }

        appList.add(currAppoint);
        technicianList.rotate((Provider) tech);
        System.out.println(currAppoint.toString() + " booked.");
    }

    /**
     * Case T, schedules an imaging appointment with a rotating technician
     *
     * @param command - command from terminal input
     */
    public static void scheduleImagingAppointment(String[] command){
        Date appDate = null;
        Timeslot appTimeslot = null;
        String fname = null;
        String lname = null;
        Date dob = null;
        Radiology roomtype = null;

        if(!lengthSanitizer(7, command)){ return;}

        try {
            appDate = dateSanitizer("app",command);

            String timeslot = command[2].trim();
            Timeslot appTime = createTimeslot(timeslot);
            appTimeslot = appTime;

            fname = command[3].trim();
            lname = command[4].trim();

            dob = dateSanitizer("dob", command);

            String tempRoomType = command[6].trim().toUpperCase();
            try { roomtype = Radiology.valueOf(tempRoomType); }
            catch (IllegalArgumentException e) {
                System.out.println(tempRoomType.toLowerCase() + " - imaging service not provided.");
                return; }
        } catch (Exception e) {
            return;
        }
        // make a patient and an appointment and compare the appointment to other appointments
        imagingScheduler(fname,lname,dob,appDate,appTimeslot,roomtype,command);
    }

    /**
     * Case C, cancels an appointment and removes it from the list
     *
     * @param command - command from terminal input
     */
    public static void cancelAppointment(String[] command){ // Case C
        Date appDate = null;
        Timeslot appTimeslot = null;
        String fname = null;
        String lname = null;
        Date dob = null;
        if(!lengthSanitizer(6, command)){ return;}
        try {
            appDate = dateSanitizer("app",command);

            String timeslot = command[2].trim();
            Timeslot appTime = createTimeslot(timeslot);
            appTimeslot = appTime;

            fname = command[3].trim();
            lname = command[4].trim();

            dob = dateSanitizer("dob", command);
        } catch (Exception e) {
            return;
        }
        Appointment tempApp = null;
        for (int i = 0; i < appList.size(); i++) {
            if(appList.get(i).getDate().equals(appDate)
                    && appList.get(i).getTimeslot().equals(appTimeslot)
                    && appList.get(i).getPatient().getProfile().getFname().toUpperCase().equals(fname.toUpperCase())
                    && appList.get(i).getPatient().getProfile().getLname().toUpperCase().equals(lname.toUpperCase())
                    && appList.get(i).getPatient().getProfile().getDob().equals(dob)){
                tempApp = appList.get(i);
                break;
            }
        }
        if(tempApp == null){
            System.out.println(String.format("%s %s %s %s %s - appointment does not exist.",
                    appDate.toString(),                                // The date of the appointment
                    appTimeslot.toString(),                            // The timeslot
                    fname,                    // Provider's first name
                    lname,                    // Provider's last name
                    dob.toString()));

        }else {
            if(tempApp.getPatient() instanceof Patient) {
                ((Patient) tempApp.getPatient()).removeVisit(tempApp);
            }
            appList.remove(tempApp);
            System.out.println(String.format("%s %s %s %s %s - appointment has been canceled.",
                    appDate.toString(),                                // The date of the appointment
                    appTimeslot.toString(),                            // The timeslot
                    fname,                    // Provider's first name
                    lname,                    // Provider's last name
                    dob.toString()));
        }
    }

    /**
     * Helper method for rescheduleAppointment
     * Swaps in a new appointment object to change the time
     *
     * @param appDate - sanitized date
     * @param originalAppTime - original timeslot
     * @param fname - first name of profile
     * @param lname - last name of profile
     * @param dob - date of birth of profile
     * @param newAppTime - new timeslot
     * @param command - command from terminal input
     */
    public static void appRescheduler(Date appDate, Timeslot originalAppTime, String fname, String lname, Date dob, Timeslot newAppTime, String[] command) {
        Appointment tempApp = null;
        for (int i = 0; i < appList.size(); i++) {
            if (!(appList.get(i) instanceof Imaging)) {
                if (appList.get(i).getDate().equals(appDate)
                        && appList.get(i).getTimeslot().equals(originalAppTime)
                        && appList.get(i).getPatient().getProfile().getFname().equals(fname)
                        && appList.get(i).getPatient().getProfile().getLname().equals(lname)
                        && appList.get(i).getPatient().getProfile().getDob().equals(dob)) {
                    tempApp = appList.get(i);
                    break;
                }
            }
        }
        if (tempApp == null) {
            System.out.println(appDate.toString() + " " + originalAppTime.toString() + " " + fname + " " + lname +" " + dob.toString() + " does not exist.");
            return;
        }
        for (int i = 0; i < appList.size(); i++) {
            if (appList.get(i).getDate().equals(appDate) && appList.get(i).getTimeslot().equals(newAppTime)) {
                System.out.println(appList.get(i).getPatient().toString() + " has an existing appointment at " + appDate.toString() + " " + newAppTime.toString());
                return;
            }
        }
        appList.remove(tempApp);
        Appointment newApp = tempApp;
        newApp.setTimeslot(newAppTime);
        appList.add(newApp); // resecheduled
        System.out.println("Rescheduled to " + newApp.toString());
    }

    /**
     * Case R, reschedules an appointment
     *
     * @param command - command from terminal input
     */
    public static void rescheduleAppointment(String[] command){ // Case R
        Date appDate = null;
        Timeslot originalAppTime = null;
        String fname = null;
        String lname = null;
        Date dob = null;
        Timeslot newAppTime = null;
        if(!lengthSanitizer(7, command)){ return;}
        try {
            appDate = dateSanitizer("app", command);

            String timeslot = command[2].trim();
            Timeslot appTime = createTimeslot(timeslot);
            originalAppTime = appTime;

            fname = command[3].trim();
            lname = command[4].trim();

            dob = dateSanitizer("dob", command);

            timeslot = command[6].trim();
            appTime = createTimeslot(timeslot);
            newAppTime = appTime;
        } catch (Exception e) {
            return;
        }
        appRescheduler(appDate, originalAppTime,fname,lname,dob,newAppTime,command);
    }

    /**
     * Prints appointments sorted by key
     *
     * @param key - key for accessing specific sort type
     */
    public static void printAppointments(char key) {
        if(appList.isEmpty()) {
            System.out.println("Schedule calendar is empty.");
            return;
        }
        List<Appointment> newTempList = Sort.appointment(appList, key);
        if (key == 'o'){
            System.out.println("** List of office appointments ordered by county/date/time.");
        }else if (key == 'd'){
            System.out.println("** List of appointments, ordered by date/time/provider.");
        }else if(key == 'l'){
            System.out.println("** List of appointments ordered by county/date/time.");
        }else{
            System.out.println("** List of radiology appointments ordered by county/date/time.");
        }
        for(int i = 0; i < newTempList.size(); i++){
            System.out.println(newTempList.get(i).toString());
        }
        System.out.println("** end of list **");
    }
    /**
     * Case PS, Displays billing statements for all appointments.
     * The billing amount is based on the provider's specialty.
     */
    private static void printBillingStatements() {
        if(appList.isEmpty()) {
            System.out.println("Schedule calendar is empty.");
            return;
        }
        System.out.println("** Billing Statement ordered by patient. **");
        int count = 1;
        Sort.appointment(appList, 'p');
        int charge = 0;

        for(int i = 0; i < patientList.size(); i++) {
            Visit visits = null;
            if(patientList.get(i) instanceof Patient) {
                visits = ((Patient) patientList.get(i)).getVisits();
            }
            while(visits != null) {
                charge += visits.getCharge();
                appList.remove(visits.getAppointment());
                visits = visits.getNext();
            }
            String fname = appList.get(i).getPatient().getProfile().getFname();
            String lname = appList.get(i).getPatient().getProfile().getLname();
            String dob = appList.get(i).getPatient().getProfile().getDob().toString();
            System.out.println("(" + count + ") " + fname + " " + lname + " " + dob + " [due: $" + charge + ".00]");
            count ++;
        }
        System.out.println("** end of list **");
    }

    /**
     * Case PC, Displays credit statement per provider
     */
    private static void printCreditAmounts() {
        if(appList.isEmpty()) {
            System.out.println("Schedule calendar is empty.");
            return;
        }
        System.out.println("** Credit amount ordered by provider. **");
        int count = 1;
        Sort.provider(providerList);
        Sort.appointment(appList, 'r');

        for(int i = 0; i < providerList.size(); i++) {
            int numAppointments = 0;
            for(int j = 0; j < appList.size(); j++) {

                if(providerList.get(i).equals(appList.get(j).getProvider())) {
                    numAppointments ++;
                }
            }
            for(int k = 0; k < technicianList.size(); k++) {

                if(providerList.get(i).equals(technicianList.get(k))) {
                    numAppointments ++;
                }
            }
            int charge = providerList.get(i).rate() * numAppointments;
            String fname = providerList.get(i).getProfile().getFname();
            String lname = providerList.get(i).getProfile().getLname();
            String dob = providerList.get(i).getProfile().getDob().toString();

            System.out.println("(" + count + ") " + fname + " " + lname + " " + dob + " [credit amount: $" + charge + ".00]");
            count ++;
        }
        System.out.println("** end of list **");
    }

    /**
     * Helper method for commandSelector
     * Selects command based on key from user input
     *
     * @param command - command from terminal input
     * @return string
     */
    public static String commandSelectorHelper(String[] command){
        String action = command[0];
        switch (action) {
            case "PL": // like PA but with county then appointment date and time
                printAppointments('l');
                break;
            case "PS": // Display billing statements of all patients
                printBillingStatements();
                break;
            case "PO": // display list of office appointments, sorted by county, then date, then time
                printAppointments('o');
                break;
            case "PI": // same as PO but with imaging instead of appointments
                printAppointments('i');
                break;
            case "PC": // Display credit amounts for providers, sorted by provider profile
                printCreditAmounts();
                break;
            case "Q": // Quit the manager
                return "Q";
            default:
                System.out.println("Invalid command!");
        }
        return "looking good!";
    }

    /**
     * Helper method for run
     * Selects command based on key from user input
     *
     * @param command - command from terminal input
     * @return string
     */
    public static String commandSelector(String[] command){
        String action = command[0];
        switch (action) {
            case "":
                return "";
            case "D": // schedule office appointment with doctor
                scheduleAppointment(command);
                break;
            case "T": // schedule imaging appointment
                scheduleImagingAppointment(command);
                break;
            case "C": // cancel existing office or imaging appointment
                cancelAppointment(command);
                break;
            case "R": // reschedule office appointment, imaging appointments are not to be rescheduled
                rescheduleAppointment(command);
                break;
            case "PP": // to display the list of appointments, sorted by the patient (by last name, first name, date of birth, then appointment date and time).
                printAppointments('p');
                break;
            case "PA": // display list of appointments sorted by appointment date, time, providers name
                printAppointments('d');
                break;
            default:
                return commandSelectorHelper(command);
        }
        return "looking good!";
    }

    /**
     * Method to print a list of providers
     */
    public static void printProviders() {
        System.out.println("Providers loaded to the list.");

        for(int i = 0; i < providerList.size(); i++) {
            if(providerList.get(i) instanceof Doctor){
                System.out.println("[" + providerList.get(i).getProfile().getFname() + " " + providerList.get(i).getProfile().getLname() + " " +
                        providerList.get(i).getProfile().getDob().toString() + ", " + providerList.get(i).getLocation().toString() + "]" + "[" +
                        ((Doctor) providerList.get(i)).toString() + "]");

            } else if(providerList.get(i) instanceof Technician) {
                System.out.println("[" + providerList.get(i).getProfile().getFname() + " " + providerList.get(i).getProfile().getLname() + " " +
                        providerList.get(i).getProfile().getDob().toString() + ", " + providerList.get(i).getLocation().toString() + "]" + "[" +
                        ((Technician) providerList.get(i)).toString() + "]");
            }
        }
    }

    /**
     * Method to print a list of technicians
     */
    public static void printTechnicians() {
        System.out.println("\nRotation list for the technicians.");
        technicianList.reverse();
        String techString = "";

        for (int i = 0; i < technicianList.size(); i++){
            Technician newTech = (Technician) technicianList.get(i).getData();
            techString += newTech.getProfile().getFname() + " " + newTech.getProfile().getLname() + " (" + newTech.getLocation().name() + ")";

            if(!(i + 1 == technicianList.size())) {
                techString += " --> ";
            }
        }
        System.out.println(techString);
    }

    /**
     * Runs the program
     */
    public static void run() throws FileNotFoundException {
        Scanner commandReader = new Scanner(System.in);
        loadProviders("providers.txt");
        printProviders();
        printTechnicians();

        System.out.println("\nClinic Manager is running...");

        while(true) {
            CODE_BREAK = false;
            String command = commandReader.nextLine(); // Check for command to be inputted
            String[] tokens = command.split(","); // Splits command by commas

            String finalCommand = commandSelector(tokens);

            if (finalCommand.equals("Q")){  // If command is Q, terminate program
                break;
            }
        }
        System.out.println("Clinic Manager terminated");
    }

    /**
     * Main class to call run()
     *
     * @param args
     */
    public static void main(String[] args) throws FileNotFoundException {
        run();
    }
}