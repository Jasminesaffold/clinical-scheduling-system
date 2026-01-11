package clinic;

import util.Date;
/**
 * Appointment subclass
 * Extends behaviors of the appointment class
 *
 * @author Jasmine Saffold
 */
public class Imaging extends Appointment {
    private Radiology room;

    /**
     * Constructor to create an appointment with a specified date, timeslot, patient, and provider.
     *
     * @param date     the date of the appointment
     * @param timeslot the timeslot for the appointment
     * @param patient  the patient's profile for the appointment
     * @param provider the provider assigned to the appointment
     */
    public Imaging(Date date, Timeslot timeslot, Person patient, Person provider, Radiology room) {
        super(date, timeslot, patient, provider);
        this.room = room;
    }

    /**
     * Gives the imaging room
     *
     * @return room
     */
    public Radiology getRoom() { return room; }

    /**
     * Returns a string representation of the appointment
     * Including the date, timeslot, patient, and provider details
     *
     * @return a formatted string of the appointment details
     */
    @Override
    public String toString() {

        if (provider instanceof Technician) {

            return String.format("%s %s %s [%s %s %s, %s][%s][%s]",
                    date.toString(),                                    // The date of the appointment
                    timeslot.toString(),                                // The timeslot
                    patient.toString(),                                 // The patient profile
                    provider.profile.getFname(),                        // Provider's first name
                    provider.profile.getLname(),                        // Provider's last name
                    provider.profile.getDob(),                          // Provider's dob
                    ((Technician) provider).getLocation().toString(),   // Doctor's location
                    ((Technician) provider).toString(),                 // Doctor's information
                    room.name()                                         // Room type
            );

        }else{
            return "This is not a technician";
        }
    }
}