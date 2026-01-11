package clinic;

/**
 * Person superclass with profile object
 *
 * @author Jasmine Saffold
 */
public class Person implements Comparable<Person> {
    protected Profile profile;

    /**
     * Constructor for person superclass
     *
     * @param profile
     */
    public Person(Profile profile) {
        this.profile = profile;
    }

    /**
     * Gives profile of the person
     *
     * @return profile
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * Override compareTo method using Profile compareTo method
     *
     * @param o the object to be compared.
     * @return  -1 if other profile is less than this profile,
     *          +1 if other profile is greater than this profile,
     *          0 if other profile is equal to this profile
     */
    @Override
    public int compareTo(Person o) { return o.profile.compareTo(this.profile); }
}
