package clinic;

/**
 * Enum class for the provider information
 * Providers have a location and a specialty
 *
 * @author Jasmine Saffold
 */

public abstract class Provider extends Person {
    private final Location location;
    public abstract int rate();

    /**
     * Provider constructor with location and specialty
     *
     * @param location -- location of provider
     */
    public Provider(Profile profile, Location location) {
        super(profile);
        this.profile = profile;
        this.location = location;
    }

    /**
     * Gives the location of the provider
     *
     * @return location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Gives a formatted string for the provider
     * Includes name, location and specialty of the provider
     * Overrides toString() method
     *
     * @return string for the provider
     */
    @Override
    public String toString() {
        return String.format("%s %s %s, %s", profile.getFname(), profile.getLname(),
                profile.getDob(), location.toString());
    }
}