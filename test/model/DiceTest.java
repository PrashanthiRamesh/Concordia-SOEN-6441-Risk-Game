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
        int diceValue=dice.roll(10,20);
        assertTrue(diceValue<=20 && diceValue>=10);
        assertFalse( diceValue>20);
        assertFalse( diceValue<10);

    }
}