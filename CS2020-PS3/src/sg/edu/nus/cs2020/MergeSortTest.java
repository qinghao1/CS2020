package sg.edu.nus.cs2020;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class MergeSortTest {

    @Test
    public void testSort() {
        double[] testArray = {7.9, 7.2, 1.0};
        double[] expectedArray = {1.0, 7.9, 7.2};
        MergeSort.sort(testArray);
        assertTrue("Sample testcase", Arrays.equals(testArray,expectedArray));
    }
}