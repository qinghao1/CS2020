package sg.edu.nus.cs2020;

/**
 * Created by qinghao1 on 25/1/17.
 */
public class SortObj implements Comparable<SortObj>{
    private static final int RANGE = 500; //less than size of array
    private static int counter = 0;
    public int key = 0;
    public int value = 0;

    SortObj() {
        key = (int) (Math.random() * RANGE);
        value = ++counter;
    }
    //Overloaded constructor to generate sorted arrays
    SortObj(int i) {
        key = i;
        value = ++counter;
    }

    public int compareTo(SortObj other) {
        return key == other.key ? 0 :
                key < other.key ? -1 : 1;
    }
}
