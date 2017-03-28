package sg.edu.nus.cs2020;


public class Tower implements Comparable<Tower>{
    public int location, range, start, end;

    Tower(int l, int r) {
        location = l;
        range = r;
        start = location - range;
        end = location + range;
    }

    public int compareTo(Tower other) {
        return start == other.start ? 0 :
                start < other.start ? -1 : 1;
    }

    public String toString() {
        return "Location: " + location
                + " Range: " + range
                + " Start: " + start
                + " End: " + end;
    }
}
