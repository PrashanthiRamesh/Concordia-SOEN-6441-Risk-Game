package util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import java.util.Arrays;

public class UtilTest {

    @Test
    public void randomInteger() {
        int[] arr = {0, 1, 2, 3};

        int x = Util.randInt(0, 4);

        assertTrue(x>=0 && x<=4);

    }
}
