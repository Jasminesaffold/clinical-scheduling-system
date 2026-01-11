package util;

import java.util.Calendar;

/**
 * This class creates a date with day, month, and year.
 * It has methods to check if the date is valid and compares dates.
 *
 * @author Jasmine Saffold
 */
public class Date implements Comparable<Date>{
    private int year;
    private int month;
    private int day;

    /**
     * Constant for every four years
     */
    public static final int QUADRENNIAL = 4;
    /**
     * Constant for every one hundred years
     */
    public static final int CENTENNIAL = 100;
    /**
     * Constant for every four hundred years
     */
    public static final int QUATERCENTENNIAL = 400;
    /**
     * The minimum year
     */
    private static final int MIN_YEAR = 1900;
    /**
     * Days in every month stored in an array
     */
    private static final int[] DAYS_IN_MONTH = {31, 28, 31, 30, 31, 30,
            31, 31, 30, 31, 30, 31};

    /**
     * Default constructor, sets an arbitrary date
     */
    public Date(){

        this.year = 1900;
        this.month = 1;
        this.day = 1;
    }

    /**
     * Constructor with parameters, creates a date with a specified year, month, and day
     *
     * @param day -- the day in the date
     * @param month -- the month in the date
     * @param year -- the year in the date
     */
    public Date(int year, int month, int day) {

        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Checks if the year is a leap year
     *
     * @return true if the year is a leap year, false otherwise
     */
    private boolean isLeapYear(){

        boolean leapYear = false;

        if(year % QUADRENNIAL == 0 || (year % CENTENNIAL == 0 && year % QUATERCENTENNIAL == 0)) {
            leapYear = true;
        }
        return leapYear;
    }

    /**
     * Checks to see if the date is a valid calendar date
     *
     * @return true if the date is valid, false otherwise
     */
    public boolean isValid() {

        boolean valid = false;

        if(year < MIN_YEAR || month < 1 || month > 12){
            return false; // year and month are not valid
        }
        int maxDays = DAYS_IN_MONTH[month - 1];
        if(month - 1 == Calendar.FEBRUARY && isLeapYear()) {
            maxDays = 29; // February has 29 days in a month during a leap year
        }
        if(day >= 1 && day <= maxDays) {
            valid = true;
        }
        return valid;
    }

    /**
     * Checks if the date is a valid appointment date
     *
     * @return true if the appoinment time is valid, false otherwise
     */
    public boolean isValidAppTime(){

        if (isValid()){

            Calendar calendar = Calendar.getInstance();

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            Calendar setDates = Calendar.getInstance();
            setDates.set(this.year, this.month - 1, this.day);

            Calendar sixMonths = Calendar.getInstance();
            sixMonths.add(Calendar.MONTH, 6);

            if ((this.year < year) || (this.year == year && this.month < month) || (this.year == year && this.month == month && this.day <= day)) { // checking if date is today or before today
                System.out.println("Appointment date: " + this.toString() + " is today or a date before today.");
                return false;
            } else if (setDates.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || (setDates.get(Calendar.DAY_OF_WEEK)) == Calendar.SUNDAY) { // return false if it's a weekend
                System.out.println("Appointment date: " + this.toString() + " is Saturday or Sunday.");
                return false;
            } else if (!(setDates.compareTo(calendar) >= 0 && setDates.compareTo(sixMonths) <= 0)){
                System.out.println("Appointment date: " + this.toString() + " is not within six months.");
                return false;
            }

        } else{
            System.out.println("Appointment date: " + this.toString() + " is not a valid calendar date");
            return false;
        }
        // needs to be within a six month period
        return true;

    }

    /**
     * Checks if the date is a valid birthday
     *
     * @return true if the year is a valid birthday, false otherwise
     */
    public boolean isValidBirthday(){

        if (isValid()){

            Calendar calendar = Calendar.getInstance();

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            if ((this.year > year) || (this.year == year && this.month > month) || (this.year == year && this.month == month && this.day >= day)) {
                System.out.println("Patient dob: " + this.toString() + " is today or a date after today.");
                return false;
            }

        }else{
            System.out.println("Patient dob: " + this.toString() + " is not a valid calendar date");
            return false;
        }
        return true;

    }

    /**
     * Compares two dates
     * Overrides compareTo() method
     *
     * @param date -- the date you are comparing to the original date
     * @return a negative integer if the date is less than the compared date,
     * zero if the date is equal to the compared date, and a positive
     * integer if the date is greater than the compared date
     */
    @Override
    public int compareTo(Date date) {

        if(this.year != date.year) {
            return this.year - date.year;
        }
        if(this.month != date.month) {
            return this.month - date.month;
        }
        if(this.day != date.day) {
            return this.day - date.day;
        }
        return 0;
    }

    /**
     * Checks to see if two dates are equal
     * Overrides equals() method
     *
     * @param date -- the date you are comparing to the original date
     * @return true if the dates are equal, false otherwise
     */
    @Override
    public boolean equals(Object date) {

        if(this == date) {
            return true;
        }
        if(date instanceof Date) {
            Date otherDate = (Date) date;
            return this.year == otherDate.year && this.month == otherDate.month && this.day == otherDate.day;
        }
        return false;
    }

    /**
     * Converts the numerical date to a string with the format "MM/DD/YYYY"
     *
     * @return the string date with the new format
     */
    @Override
    public String toString() {
        return String.format("%02d/%02d/%04d", month, day, year);
    }

    /**
     * Main method for testing
     *
     * @param args -- arguments inputted by user
     */
    public static void main(String[] args) {
        // Tests isValid() method
        Date valid = new Date(2024, 9, 30);
        System.out.println("Is 9/30/2024 a valid date? " + valid.isValid()); // Test Case # 1

        Date notValidDay = new Date(2024, 1, -1);
        System.out.println("Is 1/-1/2024 a valid date? " + notValidDay.isValid()); // Test Case # 2

        Date notValidYear = new Date(1899, 1, 1);
        System.out.println("Is 1/1/1899 a valid date? " + notValidYear.isValid()); // Test Case # 3

        Date notValidMonth = new Date(2023, 13, 1);
        System.out.println("Is 13/1/2023 a valid date? " + notValidMonth.isValid()); // Test Case # 4

        Date invalidLeapYear = new Date(2001, 2, 29);
        System.out.println("Is 2/29/2001 a valid date? " + invalidLeapYear.isValid()); // Test Case # 5

        Date leapYear = new Date(2000, 2, 29);
        System.out.println("Is 2/29/2000 a valid date? " + leapYear.isValid()); // Test Case # 6
    }
}