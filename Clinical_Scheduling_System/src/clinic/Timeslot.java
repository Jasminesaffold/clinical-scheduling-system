package clinic;

/**
 * Class for timeslots for appointments
 * Timeslots have both an hour and a minute parameter
 *
 * @author Jasmine Saffold
 */

public class Timeslot implements Comparable<Timeslot> {

    private final int hour;
    private final int minute;

    /**
     * Timeslot constructor with hour and minute
     *
     * @param hour - must be 9-16
     * @param minute - must be 0-59
     * @throws IllegalArgumentException if the hour and/or minute are invalid
     */
    Timeslot(int hour, int minute) {

        if(hour < 9 || hour > 16) {
            throw new IllegalArgumentException("Hour needs to be between 9 and 16");
        }
        if(minute < 0 || minute > 59) {
            throw new IllegalArgumentException("Minute needs to be between 0 and 59");
        }
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Gives the hour in the timeslot
     *
     * @return hour
     */
    public int getHour() {
        return hour;
    }

    /**
     * Gives the minute in the timeslot
     *
     * @return minute
     */
    public int getMinute() {
        return minute;
    }

    /**
     * Override equals() method for timeslot class
     *
     * @param o - object to compare
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        Timeslot timeslot = (Timeslot) o;
        return hour == timeslot.hour && minute == timeslot.minute;
    }

    /**
     * Creates a new format for standard time
     *
     * @return newTime string
     */
    public String getStandardTime() {
        String newTime = "";

        if(hour > 12) {
            newTime = String.format("%02d:%02d PM", hour - 12, minute);
        } else {
            newTime = String.format("%02d:%02d AM", hour, minute);
        }
        return newTime;
    }
    /**
     * Gives a string for the timeslot formatted as HH:MM
     * Overrides toString() method
     *
     * @return string for the timeslot
     */
    @Override
    public String toString() {
        return this.getStandardTime();
    }

    /**
     * Override compareTo() method for timeslot
     *
     * @param o - the object to be compared.
     * @return -1 if earlier, 1 if later, 0 if equal
     */
    @Override
    public int compareTo(Timeslot o) {
        if(this.hour < o.hour) { // Compare hours returning -1 if earlier, 1 if later
            return -1;
        } else if(this.hour > o.hour) {
            return 1;
        }
        if(this.minute < o.minute) { // Compare minutes returning -1 if earlier, 1 if later
            return -1;
        } else if(this.minute > o.minute) {
            return 1;
        }
        return 0; // Return 0 if equal
    }
}


