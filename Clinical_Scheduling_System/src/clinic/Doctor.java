package clinic;

/**
 * Extends the Provider class
 * Includes the doctor's specialty and National Provider Identification (NPI)
 *
 * @author Jasmine Saffold
 */
public class Doctor extends Provider {
    private Specialty specialty; // encapsulate the rate per visit based on specialty
    private String npi; // National Provider Identification unique to the doctor

    /**
     * Constructor for doctor subclass
     *
     * @param location -- from Provider superclass
     * @param profile -- from Provider superclass
     * @param specialty -- specialty
     * @param npi -- National Provider Identification
     */
    public Doctor(Location location, Profile profile, Specialty specialty, String npi) {
        super(profile, location);
        this.specialty = specialty;
        this.npi = npi;
    }

    /**
     * Gives specialty of doctor
     *
     * @return specialty
     */
    public Specialty getSpecialty() { return this.specialty; }

    /**
     * Gives NPI of doctor
     *
     * @return npi
     */
    public String getNpi() { return this.npi; }

    /**
     * Gives the rate of the doctor
     *
     * @return charge of specialist
     */
    @Override
    public int rate() {
        return specialty.getCharge();
    }

    @Override
    public String toString() {
        return String.format("%s, #%s", specialty.toString(), npi);
    }
}
