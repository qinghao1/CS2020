package sg.edu.nus.cs2020;

import static org.junit.Assert.*;

import org.junit.Test;

public class QuickSelectTest {

    @Test
    public void test() {
        int[] testArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        int x = 3;
        assertEquals("Basic select test", 3, QuickSelect.select(testArray, x));
    }

}