import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * This algorithm sacrifices guaranteed correctness for speed
 * because it does not store the actual string representation.
 * Here is how it works:
 *
 * The algorithm counts the number of characters in a
 * string and stores it in a 128 byte frequency array.
 *
 * Note that because the frequency is stored in bytes two
 * strings will be regarded as equal if for all characters
 * that differ in frequency between them, the difference
 * is a multiple of 256. This is a weakness of the algorithm
 * but highly unlikely unless a specific edge test case is
 * used.
 *
 * Then this 1 kb frequency array is hashed to 64 bits using
 * the FNV-1a hashing algorithm. Given that the number of
 * strings n is less than 10 million < 2^24, and that the
 * hash function is quite uniform collisions are highly
 * unlikely.
 *
 * Then this 64 bit hashcode is stored in a java HashMap
 * (using its native hashcode() implementation). Again
 * collisions are unlikely given that the hashcode() space
 * is 2^32. It is mapped to a MutableInt class that stores
 * the number of occurrences of the string, which is
 * added to the result and then increased.
 */
public class QingHaoPS5 {
    /**
     * Character frequency array, index is ASCII character code
     */
    static final byte[] charFreq = new byte[128];

    /**
     * Helper MutableInt class to increment HashMap
     */
    static class MutableInt {
        int value = 1;

        public void increment() {
            ++value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * Method that iterates through string to count the characters,
     * and put them in the frequency array
     * @param s line to count characters in
     */
    private static void stringToFreq(String s) {
        Arrays.fill(charFreq, (byte) 0);
        for (int i = 0; i < s.length(); i++) {
            ++charFreq[(int) s.charAt(i)];
        }
    }

    /**
     * Generates a 64 bit hashcode from the frequency array
     * which is 1024 bits. Should not have collisions given
     * the input size.
     *
     * Hash function is based on public domain FNV-1a algorithm
     * by Fowler, Noll and Vo.
     * (https://en.wikipedia.org/wiki/Fowler%E2%80%93Noll%E2%80%93Vo_hash_function)
     * @return hashcode
     */
    private static long freqToHash() {
        long hash = 0xcbf29ce484222325L;
        for (int i = 0; i < 128; i++) {
            hash ^= charFreq[i];
            hash *= 0x100000001b3L;
        }
        return hash;
    }

    /**
     * Counts number of string permutation pairs in file.
     * @param args file name
     */
    public static void main(String[] args){
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader(args[0]));
            Map<Long, MutableInt> freqMap = new HashMap<>(); //initialize HashMap
            int n = Integer.parseInt(br.readLine()); //number of test cases
            int result = 0;
            while (n != 0) {
                n--;
                stringToFreq(br.readLine());
                long currHash = freqToHash();
                MutableInt count = freqMap.get(currHash);
                if (count == null) {
                    freqMap.put(currHash, new MutableInt());
                } else {
                    result += count.getValue();
                    count.increment();
                }
            }
            System.out.println(result);
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
}
