package clinic;

/**
 * Enum class for radiology services.
 * Defines the types of imaging services offered.
 *
 * Imaging services include CATSCAN, ULTRASOUND, and XRAY.
 *
 * @author Jasmine Saffold
 */

public enum Radiology {
    CATSCAN,
    ULTRASOUND,
    XRAY;

    /**
     * Returns a formatted string representation of the imaging service.
     *
     * @return the name of the imaging service in a readable format.
     */
    @Override
    public String toString() {
        return this.name().charAt(0) + this.name().substring(1).toLowerCase();
    }
}
