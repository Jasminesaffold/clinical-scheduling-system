package util;

import clinic.Doctor;
import clinic.Technician;
import clinic.Provider;
import clinic.Location;
import clinic.Specialty;
import clinic.Profile;
import util.Date;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class ListTest {

    private List<Provider> providerList;
    private Doctor doctor;
    private Technician technician;

    @Before
    public void setUp() {
        providerList = new List<>();

        // Create Profile for Doctor
        Profile doctorProfile = new Profile("John", "Doe", new Date(1990, 6, 15));
        doctor = new Doctor(Location.BRIDGEWATER, doctorProfile, Specialty.FAMILY, "123456789");

        // Create Profile for Technician
        Profile technicianProfile = new Profile("Jane", "Smith", new Date(1992, 4, 10));
        technician = new Technician(Location.PISCATAWAY, technicianProfile, 150);
    }

    @Test
    public void testAddDoctor() {
        providerList.add(doctor);
        assertTrue("Doctor should be added to the provider list", providerList.contains(doctor));
    }

    @Test
    public void testAddTechnician() {
        providerList.add(technician);
        assertTrue("Technician should be added to the provider list", providerList.contains(technician));
    }

    @Test
    public void testRemoveDoctor() {
        providerList.add(doctor);
        providerList.remove(doctor);
        assertFalse("Doctor should be removed from the provider list", providerList.contains(doctor));
    }

    @Test
    public void testRemoveTechnician() {
        providerList.add(technician);
        providerList.remove(technician);
        assertFalse("Technician should be removed from the provider list", providerList.contains(technician));
    }
}
