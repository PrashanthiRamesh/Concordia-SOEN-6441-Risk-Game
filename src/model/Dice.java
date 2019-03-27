package model;

import util.Util;

/**
 * Dice Class represents a dice
 *
 * @author Manasa
 * @version 1.1
 * @since 1.1
 */
class Dice {

    /**
     * method to generate random number between 1 and 6
     * @return a random number from 1 to 6 (both inclusive)
     */
    int roll() {
        return Util.randInt(1, 6);
    }
}
