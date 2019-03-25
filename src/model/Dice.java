package model;

import util.Util;

public class Dice {

    public Dice() {
    }

    public int roll(){
        return Util.randInt(1,6);
    }
}
