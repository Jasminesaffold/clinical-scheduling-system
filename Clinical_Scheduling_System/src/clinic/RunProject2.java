package clinic;

import java.io.FileNotFoundException;

/**
 * Runs project 2
 *
 * @author Jasmine Saffold
 */
public class RunProject2 {

    /**
     * Main method for graders to run project
     *
     * @param args - arguments from user input
     */
    public static void main(String[] args) throws FileNotFoundException {
        new ClinicManager().run();
    }
}
