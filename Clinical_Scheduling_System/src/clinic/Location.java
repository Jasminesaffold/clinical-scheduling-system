package clinic;

/**
 * Enum class for locations for appointments
 * Locations have a county and zip
 *
 * @author Jasmine Saffold
 */

public enum Location {
    /**
     * Enum for Bridgewater location
     */
    BRIDGEWATER("Somerset", "08807"),
    /**
     * Enum for Edison location
     */
    EDISON("Middlesex","08817"),
    /**
     * Enum for Piscataway location
     */
    PISCATAWAY("Middlesex", "08854"),
    /**
     * Enum for Princeton location
     */
    PRINCETON("Mercer", "08542"),
    /**
     * Enum for Morristown location
     */
    MORRISTOWN("Morris", "07960"),
    /**
     * Enum for Clark location
     */
    CLARK("Union", "07066");
    /**
     * Constant for county of the location
     */
    private final String county;
    /**
     * Constant for zip code of the location
     */
    private final String zip;

    /**
     * Location constructor with county and zip
     *
     * @param county
     * @param zip
     */
    Location(String county, String zip) {
        this.county = county;
        this.zip = zip;
    }

    /**
     * Gives the county for the location
     *
     * @return county
     */
    public String getCounty() {
        return county;
    }

    /**
     * Gives the zip code for the location
     *
     * @return zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * Gives a formatted string for the location
     * Includes name of the town, county and zip
     * Overrides toString() method
     *
     * @return string for the location
     */
    @Override
    public String toString() {
        return String.format("%s, %s %s", this.name(), county, zip);
    }
}
