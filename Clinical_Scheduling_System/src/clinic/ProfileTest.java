package clinic;

import org.junit.Test;
import util.Date;
import static org.junit.Assert.*;

public class ProfileTest {

    @Test
    public void testCompareTo_ReturnsNegativeOne() {
        // Case 1: Last names are different, "Doe" < "Smith"
        Profile p1 = new Profile("John", "Doe", new Date(1990, 6, 15));
        Profile p2 = new Profile("Jane", "Smith", new Date(1985, 3, 25));
        assertTrue("Expected p1 to be less than p2", p1.compareTo(p2) < 0);

        // Case 2: Same last name, first name "Adam" < "Zoe"
        Profile p3 = new Profile("Adam", "Smith", new Date(1992, 1, 1));
        Profile p4 = new Profile("Zoe", "Smith", new Date(1995, 12, 12));
        assertTrue("Expected p3 to be less than p4", p3.compareTo(p4) < 0);

        // Case 3: Identical names, but date of birth earlier (1990 < 1995)
        Profile p5 = new Profile("John", "Doe", new Date(1990, 6, 15));
        Profile p6 = new Profile("John", "Doe", new Date(1995, 6, 15));
        assertTrue("Expected p5 to be less than p6 based on DOB", p5.compareTo(p6) < 0);
    }

    @Test
    public void testCompareTo_ReturnsPositiveOne() {
        // Case 4: Last names are different, "Smith" > "Doe"
        Profile p1 = new Profile("Jane", "Smith", new Date(1985, 3, 25));
        Profile p2 = new Profile("John", "Doe", new Date(1990, 6, 15));
        assertTrue("Expected p1 to be greater than p2", p1.compareTo(p2) > 0);

        // Case 5: Same last name, first name "Zoe" > "Adam"
        Profile p3 = new Profile("Zoe", "Smith", new Date(1995, 12, 12));
        Profile p4 = new Profile("Adam", "Smith", new Date(1992, 1, 1));
        assertTrue("Expected p3 to be greater than p4", p3.compareTo(p4) > 0);

        // Case 6: Identical names, but date of birth later (1995 > 1990)
        Profile p5 = new Profile("John", "Doe", new Date(1995, 6, 15));
        Profile p6 = new Profile("John", "Doe", new Date(1990, 6, 15));
        assertTrue("Expected p5 to be greater than p6 based on DOB", p5.compareTo(p6) > 0);
    }

    @Test
    public void testCompareTo_ReturnsZero() {
        // Case 7: Identical profiles, should return 0
        Profile p1 = new Profile("John", "Doe", new Date(1990, 6, 15));
        Profile p2 = new Profile("John", "Doe", new Date(1990, 6, 15));
        assertEquals("Expected p1 to be equal to p2", 0, p1.compareTo(p2));
    }
}
