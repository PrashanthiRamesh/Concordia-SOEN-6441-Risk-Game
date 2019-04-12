package model;

import util.Util;

/**
 * Dice Class represents a dice
 *
 * @author Manasa
 * @version 1.2
 * @since 1.1
 */
public class Dice {

    /**
     * method to generate random number between 1 and 6
     * @return a random number from 1 to 6 (both inclusive)
     */
    int roll(int min, int max) {
        return Util.randInt(min, max);
    }
}
