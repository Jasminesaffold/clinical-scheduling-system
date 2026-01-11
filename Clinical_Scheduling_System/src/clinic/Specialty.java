package clinic;

/**
 * Enum class for matching charges with the specialty
 * Specialties have a charge parameter
 *
 * @author Jasmine Saffold
 */

public enum Specialty {
    FAMILY(250),        // charge for family specialty
    PEDIATRICIAN(300),  // charge for pediatrician specialty
    ALLERGIST(350);     // charge for allergist specialty

    private final int charge;  // constant for charge

    /**
     * Specialty constructor with charge amount for specialty service
     *
     * @param charge
     */
    Specialty(int charge) {
        this.charge = charge;
    }

    /**
     * Gives the amount charged for a specialist
     *
     * @return charge for specialist
     */
    public int getCharge() {
        return charge;
    }

    /**
     * Gives a formatted string for the specialty
     * Includes name of the specialty and charge associated with the service
     * Overrides toString() method
     *
     * @return string for the specialty
     */
    @Override
    public String toString() {
        return String.format("%s", this.name());
    }
}
