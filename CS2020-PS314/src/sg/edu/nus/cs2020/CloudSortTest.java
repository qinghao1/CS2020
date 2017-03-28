package sg.edu.nus.cs2020;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class CloudSortTest {
    String[] fileNames = {
            "1000000reverse.txt",
            "1000000Ints.txt",
            "1000000IntsAlmostSorted100.txt",
            "1000000IntsAlmostSorted50.txt",
            "1000000IntsAlmostSorted500.txt",
            "1000000IntsSorted.txt",
            "100000Ints.txt",
            "100000IntsAlmostSorted10.txt",
            "100000IntsAlmostSorted50.txt",
            "100000IntsSorted.txt",
            "10000Ints.txt",
            "10000IntsAlmostSorted10.txt",
            "10000IntsAlmostSorted100.txt",
            "10000IntsAlmostSorted5.txt",
            "10000IntsSorted.txt",
            "1000Ints.txt",
            "1000IntsAlmostSorted1.txt",
            "1000IntsAlmostSorted10.txt",
            "1000IntsAlmostSorted5.txt",
            "1000IntsSorted.txt",
            "100Ints.txt",
            "100IntsAlmostSorted10.txt",
            "100IntsAlmostSorted100.txt",
            "100IntsAlmostSorted50.txt",
            "100IntsSorted.txt",
            "103Ints.txt",
            "10Ints.txt",
            "10IntsAlmostSorted1.txt",
            "10IntsAlmostSorted20.txt",
            "10IntsAlmostSorted5.txt",
            "10IntsSorted.txt",
            "PiDigits.txt"
    };
    @Test
    public void cloudSort() throws Exception {
        String outFile = "sorted.txt";
        System.out.println("****** CloudSort ******");
        for (String file : fileNames) {
            CloudSort.cloudSort(file, outFile, 100);
        }
        System.out.println("****** CloudSort ******");

        System.out.println("****** IsSorted ******");
        for (String file: fileNames) {
            System.out.println("--- " + file + " testing started! ---");
            if (CloudSort.isSorted(file)) {
                System.out.println(file + " is SORTED!");
            } else {
                System.out.println(file + " is NOT SORTED!");
            }
            System.out.println("--- " + file + " testing finished! ---");
        }
        System.out.println("****** IsSorted ******");
    }

}