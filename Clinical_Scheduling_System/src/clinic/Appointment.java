package clinic;

import util.Date;

/**
 * This class represents an appointment with a provider at a specific date and time.
 * It includes methods to calculate the charge based on the provider's specialty,
 * compare appointments, and output a formatted string.
 *
 * @author Jasmine Saffold
 */
public class Appointment implements Comparable<Appointment> {
    protected Date date;            // The date of the appointment
    protected Timeslot timeslot;    // The time slot of the appointment
    protected Person patient;      // The patient's profile
    protected Person provider;    // The provider (doctor) for the appointment

    /**
     * Constructor to create an appointment with a specified date, timeslot, patient, and provider.
     *
     * @param date the date of the appointment
     * @param timeslot the timeslot for the appointment
     * @param patient the patient's profile for the appointment
     * @param provider the provider assigned to the appointment
     */
    public Appointment(Date date, Timeslot timeslot, Person patient, Person provider) {
        this.date = date;
        this.timeslot = timeslot;
        this.patient = patient;
        this.provider = provider;
    }

    /**
     * Sets a new timeslot for the appointment.
     *
     * @param timeslot the new timeslot to set
     */
    public void setTimeslot(Timeslot timeslot) {
        this.timeslot = timeslot;
    }

    /**
     * Gives the charge
     * Downcast from provider to doctor subclass
     *
     * @return rate of doctor or technician
     */
    public int getCharge() {
        if(provider instanceof Doctor) {
            return ((Doctor) provider).rate();
        } else if (provider instanceof Technician){
            return ((Technician) provider).rate();
        }
        return 0;
    }

    /**
     * Pulls the location from the provider
     *
     * @return location of appointment
     */
    public Location getLocation(){
        Location location = null;
        if(provider instanceof Doctor) {
            location = ((Doctor) provider).getLocation();
        } else if (provider instanceof Technician){
            location = ((Technician) provider).getLocation();
        }
        return location;
    }

    /**
     * Compares two appointments based on date, timeslot, and patient.
     *
     * @param other the other appointment to compare with
     * @return a negative integer, zero, or a positive integer as this appointment
     *         is less than, equal to, or greater than the specified appointment
     */
    @Override
    public int compareTo(Appointment other) {
        int dateComparison = this.date.compareTo(other.date);
        if (dateComparison != 0) return dateComparison;

        int timeslotComparison = this.timeslot.compareTo(other.timeslot);
        if (timeslotComparison != 0) return timeslotComparison;

        return this.patient.profile.compareTo(other.patient.profile);
    }

    /**
     * Returns a string representation of the appointment, including the date, timeslot, patient, and provider details.
     *
     * @return a formatted string of the appointment details
     */
    @Override
    public String toString() {

        if (provider instanceof Doctor) {

            return String.format("%s %s %s [%s %s %s, %s][%s]",
                    date.toString(),                                // The date of the appointment
                    timeslot.toString(),                            // The timeslot
                    patient.toString(),                             // The patient profile
                    provider.profile.getFname(),                    // Provider's first name
                    provider.profile.getLname(),                    // Provider's last name
                    provider.profile.getDob(),                      // Provider's dob
                    ((Doctor) provider).getLocation().toString(),   // Doctor's location
                    ((Doctor) provider).toString()                  // Doctor's information
            );

        }else{
            return "This is not a doctor";
        }
    }

    /**
     * Gets the date
     *
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Gets the timeslot
     *
     * @return timeslot
     */
    public Timeslot getTimeslot() {
        return timeslot;
    }

    /**
     * Gets the patient
     *
     * @return patient
     */
    public Person getPatient() {
        return patient;
    }

    /**
     * Gets the provider
     *
     * @return provider
     */
    public Person getProvider() {
        return provider;
    }
}