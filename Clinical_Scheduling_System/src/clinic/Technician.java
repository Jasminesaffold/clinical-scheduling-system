package clinic;

/**
 * Technician class to keep track of the rate per visit
 * Extends Provider class
 *
 * @author Jasmine Saffold
 */
public class Technician extends Provider {
    private int ratePerVisit; // keeps track of technician's charging rate per visit

    /**
     * Constructor for technician class
     *
     * @param location -- from Provider
     * @param profile -- from Provider
     * @param ratePerVisit -- rate for charge
     */
    public Technician(Location location, Profile profile, int ratePerVisit) {
        super(profile, location);
        this.ratePerVisit = ratePerVisit;
    }

    /**
     * Gives the rate of the technician unique to the provider
     *
     * @return ratePerVisit
     */
    @Override
    public int rate() {
        return this.ratePerVisit;
    }

    @Override
    public String toString() { return String.format("rate: $%d.00", ratePerVisit); }
}
