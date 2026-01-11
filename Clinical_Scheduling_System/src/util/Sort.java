package util;

import clinic.*;

import java.util.Comparator;
/**
 * Handles the sorting for classes that need it
 * Static methods only
 *
 * @author Jasmine Saffold
 */
public class Sort {

    /**
     * Helps with dividing imaging appointments from office appointments
     *
     * @param comparator - the comparator chain
     * @param list - appointment list
     * @param type - type of appointment
     */
    public static List<Appointment>  appHelp(Comparator<Appointment> comparator, List<Appointment> list, String type){
        comparator = Comparator.comparing((Appointment a) -> a.getLocation().getCounty())
                .thenComparing(Appointment::getDate)
                .thenComparing(Appointment::getTimeslot);
        List<Appointment> appointments = new List<>();
        if (type.equals("i")) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) instanceof Imaging) {
                    appointments.add(list.get(i));
                }
            }
        }else{
            for (int i = 0; i < list.size(); i++) {
                if (!(list.get(i) instanceof Imaging)) {
                    appointments.add(list.get(i));
                }
            }
        }
        appointments.sort(comparator);
        return appointments;
    }
    /**
     * Static method to sort appointments
     *
     * @param list - appointment list
     * @param key - key to access type of sort
     */
    public static List<Appointment> appointment(List<Appointment> list, char key) {
        Comparator<Appointment> comparator = null;
        switch (key) {
            case 't': // Sort by timeslot first, then date, then patient last name
                comparator = Comparator.comparing(Appointment::getTimeslot).thenComparing(Appointment::getDate)
                        .thenComparing(a -> a.getPatient().getProfile().getLname());
                break;
            case 'd': // Sort by date first, then timeslot, then providers last name, then providers first name
                comparator = Comparator.comparing(Appointment::getDate).thenComparing(Appointment::getTimeslot)
                        .thenComparing(a -> a.getProvider().getProfile().getLname())
                        .thenComparing(a -> a.getProvider().getProfile().getFname());
                break;
            case 'p': // Sort by patient last name first, then date, then timeslot
                comparator = Comparator.comparing((Appointment a) -> a.getPatient().getProfile().getLname())
                        .thenComparing((Appointment a) -> a.getPatient().getProfile().getFname())
                        .thenComparing((Appointment a) -> a.getPatient().getProfile().getDob())
                        .thenComparing(Appointment::getDate).thenComparing(Appointment::getTimeslot);
                break;
            case 'r': // Sort by provider last name first, then date, then timeslot
                comparator = Comparator.comparing((Appointment a) -> a.getProvider().getProfile().getLname())
                        .thenComparing(Appointment::getDate).thenComparing(Appointment::getTimeslot);
                break;
            case 'l': // Sort by county, then appointment date, and then appointment time
                comparator = Comparator.comparing((Appointment a) -> a.getLocation().getCounty())
                        .thenComparing(Appointment::getDate).thenComparing(Appointment::getTimeslot);
                break;
            case 'o': // Sort office (non-imaging) appointments
                return  appHelp(comparator, list, "o");
            case 'i': // Sort imaging appointments
                return appHelp(comparator, list, "i");
            default:
                System.out.println("Invalid sort key.");
                return null;
        }
        list.sort(comparator);
        return list;
    }

    /**
     * Static method to sort providers
     *
     * @param list - provider list
     */
    public static void provider(List<Provider> list) {

        for(int i = 0; i < list.size() - 1; i++) {
            for(int j = 0; j < list.size() - i - 1; j++) {
                boolean willSwap = false;

                if (list.get(j).getProfile().getLname().compareTo(list.get(j + 1).getProfile().getLname()) > 0) {
                    willSwap = true; // sort by provider name
                }
                if(willSwap) {
                    Provider temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }
}