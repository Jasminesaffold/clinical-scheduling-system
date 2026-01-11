package util;

import org.junit.Test;
import static org.junit.Assert.*;

public class DateTest {

    // Test Case #1: Valid date (non-leap year)
    @Test
    public void testValidDate() {
        Date validDate = new Date(2024, 9, 30); // A standard valid date
        assertTrue("9/30/2024 should be a valid date", validDate.isValid());
    }

    // Test Case #2: Valid leap year date (February 29 in a leap year)
    @Test
    public void testValidLeapYearDate() {
        Date leapYearDate = new Date(2000, 2, 29); // February 29 on a leap year
        assertTrue("2/29/2000 should be a valid date (leap year)", leapYearDate.isValid());
    }

    // Test Case #3: Invalid day (day < 1)
    @Test
    public void testInvalidDay() {
        Date invalidDayDate = new Date(2024, 1, -1); // Negative day value
        assertFalse("1/-1/2024 should be an invalid date", invalidDayDate.isValid());
    }

    // Test Case #4: Invalid year (year < 1900)
    @Test
    public void testInvalidYear() {
        Date invalidYearDate = new Date(1899, 1, 1); // Year below the minimum threshold
        assertFalse("1/1/1899 should be an invalid date (year below minimum)", invalidYearDate.isValid());
    }

    // Test Case #5: Invalid month (month > 12)
    @Test
    public void testInvalidMonth() {
        Date invalidMonthDate = new Date(2023, 13, 1); // Month greater than 12
        assertFalse("13/1/2023 should be an invalid date (month above 12)", invalidMonthDate.isValid());
    }

    // Test Case #6: Invalid leap year date (February 29 in a non-leap year)
    @Test
    public void testInvalidLeapYearDate() {
        Date invalidLeapDate = new Date(2001, 2, 29); // February 29 on a non-leap year
        assertFalse("2/29/2001 should be an invalid date (non-leap year)", invalidLeapDate.isValid());
    }
}