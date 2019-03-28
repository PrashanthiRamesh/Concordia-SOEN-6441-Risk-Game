package util;

import org.junit.Test;

import java.util.Arrays;

public class UtilTest {
    
    @Test
    public void randomInteger() {
        int[] arr = {0, 1, 2, 3};

        int x = Arrays.binarySearch(arr, Util.randInt(0, 4));

        //assertEquals( 1, x);

    }
}
