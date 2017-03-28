package sg.edu.nus.cs2020;
import java.util.*;

public class TestSorts {
    //Test parameters
    private static final int TESTSIZE = 100000;
    private static int CHECKINGMODE = 0;
    private static String[] MODES = {
            "Random",
            "Sorted",
            "Reverse"
    };
    /*
        Generates random test array of SortObj
        SortObj array can be thought of as a hashmap with keys in a
        range that is smaller than array size and a value that is
        monotonically increasing
        @param size
        size of array to be generated
        @return test array
     */
    private static SortObj[] generateArray(int  size) {
        SortObj[] arr = new SortObj[size];
        for (int i = 0; i < size; i++) {
            switch (CHECKINGMODE) {
                case 0 : arr[i] = new SortObj(); break;
                case 1 : arr[i] = new SortObj(i); break;
                case 2 : arr[i] = new SortObj(size - i); break;
            }
        }
        return arr;
    }
    /* Does a linear sweep over the array to make sure value is
        monotonically increasing
        @param sorter
        sorter to be tested
        @param size
        size of test arr to be sorted
        @return whether it's stable
     */
    public static boolean isStable (ISort sorter, int size) {
        SortObj[] testArr = generateArray(size);
        sorter.sort(testArr);
        int prevKey = -1;
        int prevVal = -1;
        for (int i = 0; i < testArr.length; i++) {
            if (testArr[i].key == prevKey && testArr[i].value < prevVal) return false;
            prevKey = testArr[i].key;
            prevVal = testArr[i].value;
        }
        return true;
    }
    /* Does a linear sweep over the array to make sure key is
        monotonically increasing
        @param sorter
        sorter to be tested
        @param size
        size of test arr to be sorted
        @return whether it's sorted
     */
    public static boolean checkSorted(ISort sorter, int size) {
        SortObj[] testArr = generateArray(size);
        sorter.sort(testArr);
        int prevKey = -1;
        for (int i = 0; i < testArr.length; i++) {
            if (testArr[i].key < prevKey) return false;
            prevKey = testArr[i].key;
        }
        return true;
    }
    /* Iterates over all sorters and prints out if they are stable/sorted
        Does the isSorted check three times, first is random,
        second is sorted forwards and third is sorted backwards
     */
    public static void getTime(StopWatch s) {
        s.stop();
        System.out.println("Time: " + s.getTime());
        s.reset();
    }
    public static void main(String[] args) {
        System.out.println("TESTSIZE " + TESTSIZE);
        System.out.println("--Started--");

        ArrayList<ISort> sorters = new ArrayList<>();
        ISort sorterA = new SorterA();
        ISort sorterB = new SorterB();
        ISort sorterC = new SorterC();
        ISort sorterD = new SorterD();
        ISort sorterE = new SorterE();
        ISort sorterF = new SorterF();
        sorters.add(sorterA);
        sorters.add(sorterB);
        sorters.add(sorterC);
        sorters.add(sorterD);
        sorters.add(sorterE);
        sorters.add(sorterF);

        StopWatch s = new StopWatch();

        for (ISort sorter : sorters) {
            //sorts four times in total
            System.out.println();
            System.out.println(sorter.getClass().getName());
            s.start();
            System.out.println("Stable: " + isStable(sorter, TESTSIZE));
            getTime(s);
            for (int i = 0; i <= 2; i++) {
                CHECKINGMODE = i;
                s.start();
                System.out.println("Mode: " + MODES[CHECKINGMODE] + " Sorted: " + checkSorted(sorter, TESTSIZE));
                getTime(s);
            }
            CHECKINGMODE = 0;
        }
        System.out.println();
        System.out.println("--Finished--");
    }
}
