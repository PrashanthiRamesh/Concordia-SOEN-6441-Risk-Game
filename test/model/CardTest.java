package model;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;



public class CardTest {

    private Card cardInfantry;

    private Card cardCavalry;

    private Card cardCannon;

    @Before
    public void setUp()
    {
        cardInfantry = new Card("Infantry",1);
        cardCavalry = new Card("Cavalry",2);
        cardCannon = new Card("Cannon",3);
    }
    @Test
    public void checkCard()
    {
        assertEquals("Infantry",cardInfantry.getName());
        assertEquals("Cavalry",cardCavalry.getName());
        assertNotEquals("Infantry",cardCannon.getName());

    }
}