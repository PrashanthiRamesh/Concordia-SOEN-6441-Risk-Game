package model;

import model.Dice;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;


public class DiceTest {
    private Dice dice;

    @Before
    public void setUp()
    {
        dice = new Dice();
    }
    @Test
    public void validDiceResult()
    {
        boolean check;
        int diceValue=dice.roll();
        assertTrue(diceValue<=6 && diceValue>=1);
        assertFalse( diceValue>6);
        assertFalse( diceValue<1);

    }
}