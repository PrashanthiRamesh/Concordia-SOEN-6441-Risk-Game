package model;

import util.Util;

/**
 *
 */
public class Dice {

    /**
     * @return
     */
    public int roll() {
        return Util.randInt(1, 6);
    }
}
