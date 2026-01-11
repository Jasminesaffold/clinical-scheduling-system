package clinic;

/**
 * This class holds a list of patient objects from the Patient class
 * Implements a "bag" data structure with arrays
 *
 * @author Jasmine Saffold
 */

public class MedicalRecord {
    private Patient[] patients;
    private int size; // number of patient objects in the array
    /**
     * The initial capacity of patients array
     */
    private static final int INIT_CAPACITY = 10;
    /**
     * MedicalRecord constructor initializing the capacity and
     * the patients list and size
     */
    public MedicalRecord() {
        this.patients = new Patient[INIT_CAPACITY];
        this.size = 0;
    }

    /**
     * Resizes the patients array by a growth rate of 2 for every resize
     */
    private void resize() {
        final int GROWTH_RATE = 2;

        Patient[] newPatients = new Patient[patients.length * GROWTH_RATE];

        for(int i = 0; i < patients.length; i++) {
            newPatients[i] = patients[i];
        }
        patients = newPatients;
    }

    /**
     * Adds a patient to the array, resizing as needed
     *
     * @param patient -- from patients array
     */
    public void add(Patient patient) {
        if(size >= patients.length) {
            resize();
        }
        patients[size++] = patient;
    }

    /**
     * Gives the size of the array
     *
     * @return size
     */
    public int getSize() {
        return size;
    }

    /**
     * Gives the patient given an index in the patients array
     *
     * @param index -- index of the patient you are looking for
     * @return patient in the patient array
     */
    public Patient getPatient(int index) {
        if(index < 0 || index >= size) {
            return null; // Index not in array
        }
        return patients[index];
    }

    /**
     * Formats the Medical Record
     * Overrides the toString() method
     *
     * @return newString -- new formatted Medical Record string
     */
    @Override
    public String toString() {
        String newString = "Medical Record: \n";

        for(int i = 0; i < size; i++) {
            newString += patients[i].toString() + "\n";
        }
        return newString;
    }
}
