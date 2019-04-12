package util;


import java.util.Random;

/**
 * Utilities Class to hold common static method(s) for easy access
 *
 * @author Suthakhar
 * @version 1.2
 * @since 1.1
 */
public class Util {

    /**
     * Generate a random integer between two integers (range)
     * @param min minimum value of the range
     * @param max maximum value of the range
     * @return a random integer
     */
    public static int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}
