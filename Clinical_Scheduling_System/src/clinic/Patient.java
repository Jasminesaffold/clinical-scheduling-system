package clinic;

import util.Date;
import util.Visit;

/**
 * This class represents a patient with a profile and a linked list of visits.
 * It includes methods to calculate total charges, compare patients, and return a formatted string.
 *
 * @author Jasmine Saffold
 */
public class Patient extends Person {
    private Profile profile;   // Profile of the patient
    private Visit visits;      // A linked list of visits (completed appointments)

    /**
     * Constructor to create a patient with a given profile and visit history.
     *
     * @param profile the patient's profile
     * @param visits  the linked list of the patient's completed visits
     */
    public Patient(Profile profile, Visit visits) {
        super(profile);
        this.profile = profile;
        this.visits = visits;
    }

    /**
     * Constructor if no visits list
     *
     * @param profile - profile of patient
     */
    public Patient(Profile profile) {
        super(profile);
        this.profile = profile;
    }

    /**
     * Sets visits to patient object
     *
     * @param visit - visit node
     */
    public void setVisits(Visit visit) { this.visits = visit; }
    /**
     * Gives the visits linked list
     *
     * @return visits
     */
    public Visit getVisits() { return visits; }

    /**
     * Removes a visit from list of visits
     *
     * @param appointment the appointment to be removed
     */
    public void removeVisit(Appointment appointment) {
        if (visits == null) {
            return;
        }
        if (visits.getAppointment().equals(appointment)) {
            visits = visits.getNext();
            return;
        }
        Visit previous = visits;
        Visit current = visits.getNext();

        while (current != null) {
            if (current.getAppointment().equals(appointment)) {
                previous.setNext(current.getNext());
                return;
            }
            previous = current;
            current = current.getNext();
        }
    }

    /**
     * Traverses the linked list of visits and calculates the total charge for the patient.
     *
     * @return the total charge for all visits
     */
    public int charge() {
        int totalCharge = 0;
        Visit currentVisit = visits;

        while (currentVisit != null) {
            totalCharge += currentVisit.getCharge();
            currentVisit = currentVisit.getNext(); // Move to the next visit in the list
        }

        return totalCharge;
    }

    /**
     * Returns a string representation of the patient, including their profile.
     *
     * @return the string representation of the patient
     */
    @Override
    public String toString() {
        return profile.toString();
    }
}