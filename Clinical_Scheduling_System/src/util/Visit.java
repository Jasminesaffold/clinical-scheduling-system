package util;

import clinic.Appointment;

/**
 * This class represents a visit in the patient's medical history.
 * Each visit refers to an appointment and the next visit in the linked list.
 *
 * @author Jasmine Saffold
 */
public class Visit {
    private Appointment appointment; // Reference to an appointment object
    private Visit next;              // Reference to the next visit in the list

    /**
     * Constructor to initialize a visit with an appointment and a reference to the next visit.
     *
     * @param appointment the appointment for this visit
     * @param next the next visit in the list
     */
    public Visit(Appointment appointment, Visit next) {
        this.appointment = appointment;
        this.next = next;
    }

    /**
     * Constructor to initialize a visit with just an appointment (no next visit yet).
     *
     * @param appointment the appointment for this visit
     */
    public Visit(Appointment appointment) { this(appointment, null); }

    /**
     * Gives appointment
     *
     * @return appointment
     */
    public Appointment getAppointment() { return this.appointment; }

    /**
     * Getter for the next visit in the list.
     *
     * @return the next visit in the linked list
     */
    public Visit getNext() {
        return this.next;
    }

    /**
     * Setter for the next visit in the list.
     *
     * @param next the next visit to set
     */
    public void setNext(Visit next) {
        this.next = next;
    }

    /**
     * Method to return the charge of the visit by delegating to the appointment's method.
     *
     * @return the charge for the visit
     */
    public int getCharge() { return appointment.getCharge(); }

    /**
     * Returns a string representation of the visit, which is the appointment's toString().
     *
     * @return string for the visit (appointment details)
     */
    @Override
    public String toString() { return appointment.toString(); }
}