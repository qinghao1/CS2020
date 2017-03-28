import java.util.*;

public class BinarySearch {
    private static int searchHelp(int[] a, int x, int left, int right) {
        if (left > right) {
            return -1;
        } else {
            int middle = (left + right) / 2;
            if (a[middle] == x) {
                return middle;
            } else if (a[middle] < x) {
                return searchHelp(a, x, middle + 1, right);
            } else {
                return searchHelp(a, x, left, middle - 1);
            }
        }
    }

    private static int searchBin(int[] a, int x) {
        return searchHelp(a, x, 0, a.length - 1);
    }

    public static int search(int[] a, int x) {
        int b = searchBin(a, x);
        if (b == -1) return b;
        while(b >= 0 && a[b] == x) {
            b--;
        };
        return ++b;
    }

    public static int[] searchRange(int[] a, int x) {
        int b = searchBin(a, x);
        if (b == -1) {
            int[] ans = {-1, -1};
            return ans;
        }
        int c, d;
        c = d = b;
        while(c >= 0 && a[c] == x) {
            c--;
        };
        while(d < a.length && a[d] == x) {
            d++;
        };
        int[] ans = {++c, --d};
        return ans;

    }

    public static void main(String[] args) {
        int[] a1 = {0, 0, 1, 3, 3, 5};
        System.out.println(search(a1, 1));
        System.out.println(search(a1, 3));
        System.out.println(Arrays.toString(searchRange(a1, 0)));
    }
}
