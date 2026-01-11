package clinic;

import util.Date;

/**
 * This class represents a profile of a patient, including their first name, last name, and date of birth.
 * It includes methods to compare profiles, check for equality, and return a formatted string.
 *
 * @author Jasmine Saffold
 */
public class Profile implements Comparable<Profile> {
    private String fname;  // First name of the patient
    private String lname;  // Last name of the patient
    private Date dob;      // Date of birth of the patient

    /**
     * Constructor to create a profile with the patient's first name, last name, and date of birth.
     *
     * @param fname the first name of the patient
     * @param lname the last name of the patient
     * @param dob   the date of birth of the patient
     */
    public Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     * Gives the first name of the profile
     *
     * @return fname
     */
    public String getFname() { return fname; }

    /**
     * Gives the last name of the profile
     *
     * @return lname
     */
    public String getLname() { return lname; }

    /**
     * Gives the date of birth of the profile
     *
     * @return dob
     */
    public Date getDob() { return dob; }

    /**
     * Compares two profiles for equality based on first name, last name, and date of birth.
     *
     * @param obj the object to compare
     * @return true if the profiles have the same first name, last name, and date of birth, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Profile other = (Profile) obj;
        return this.fname.equals(other.fname) && this.lname.equals(other.lname) && this.dob.equals(other.dob);
    }

    /**
     * Returns a string representation of the profile, including the patient's full name and date of birth.
     *
     * @return the string representation of the profile in the format "First Last (MM/DD/YYYY)"
     */
    @Override
    public String toString() {
        return fname + " " + lname + " " + dob.toString();
    }

    /**
     * Compares two profiles based on last name, first name, and date of birth.
     *
     * @param other the other profile to compare
     * @return a negative integer, zero, or a positive integer as this profile is less than,
     *         equal to, or greater than the specified profile
     */
    @Override
    public int compareTo(Profile other) {
        int lnameCompare = this.lname.compareTo(other.lname);
        if (lnameCompare != 0) return Integer.compare(lnameCompare, 0);

        int fnameCompare = this.fname.compareTo(other.fname);
        if (fnameCompare != 0) return Integer.compare(fnameCompare, 0);

        return Integer.compare(this.dob.compareTo(other.dob), 0);
    }

    /**
     * (Test Bed Main) Main method for testing purposes.
     *
     * @param args -- arguments from user input
     */
    public static void main(String[] args) {
        // Creates Date objects
        Date dob1 = new Date(1990, 6, 15); // Test Case 3, 6, 7
        Date dob2 = new Date(1985, 3, 25); // Test Case 1, 4
        Date dob3 = new Date(1992, 1, 1);  // Test Case 2, 5
        Date dob4 = new Date(1995, 6, 15); // Test Case 3, 6
        Date dob5 = new Date(1995, 12, 12); // Test Case 2, 5

        // Creates Profile objects for the test cases
        Profile p1 = new Profile("John", "Doe", dob1);   // Test Case 1, 3, 6, 7
        Profile p2 = new Profile("Jane", "Smith", dob2); // Test Case 1, 4
        Profile p3 = new Profile("Adam", "Smith", dob3); // Test Case 2, 5
        Profile p4 = new Profile("Zoe", "Smith", dob5);  // Test Case 2, 5
        Profile p5 = new Profile("John", "Doe", dob4);   // Test Case 3, 6
        Profile p6 = new Profile("John", "Doe", dob1);   // Identical to p1 (Test Case 7)

        // Test Case 1: Last names are different, "Doe" < "Smith"
        System.out.println("Test Case 1:");
        System.out.println("p1 compared to p2: " + p1.compareTo(p2)); // Expected: -1 (Doe < Smith)

        // Test Case 2: Same last name, "Adam" < "Zoe" (Smith < Smith)
        System.out.println("\nTest Case 2:");
        System.out.println("p3 compared to p4: " + p3.compareTo(p4)); // Expected: -1 (Adam < Zoe)

        // Test Case 3: Same names, compare by DOB (1990 < 1995)
        System.out.println("\nTest Case 3:");
        System.out.println("p1 compared to p5: " + p1.compareTo(p5)); // Expected: -1 (1990 < 1995)

        // Test Case 4: Last names are different, "Smith" > "Doe"
        System.out.println("\nTest Case 4:");
        System.out.println("p2 compared to p1: " + p2.compareTo(p1)); // Expected: 1 (Smith > Doe)

        // Test Case 5: Same last name, "Zoe" > "Adam" (Smith > Smith)
        System.out.println("\nTest Case 5:");
        System.out.println("p4 compared to p3: " + p4.compareTo(p3)); // Expected: 1 (Zoe > Adam)

        // Test Case 6: Same names, compare by DOB (1995 > 1990)
        System.out.println("\nTest Case 6:");
        System.out.println("p5 compared to p1: " + p5.compareTo(p1)); // Expected: 1 (1995 > 1990)

        // Test Case 7: Identical profiles (should return 0)
        System.out.println("\nTest Case 7:");
        System.out.println("p1 compared to p6: " + p1.compareTo(p6)); // Expected: 0 (identical profiles)
    }
}

