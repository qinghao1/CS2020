import java.util.*;
import java.io.*;

public class QingHaoPS5FNV {
    static final byte[] charFreq = new byte[128];
    static final Map<Long, MutableInt> freqMap = new HashMap<>();

    static class MutableInt {
        int value = 1;

        public void increment() {
            ++value;
        }

        public int getValue() {
            return value;
        }
    }


    private static void stringToFreq(String s) {
        Arrays.fill(charFreq, (byte) 0);
        for (int i = 0; i < s.length(); i++) {
            ++charFreq[(int) s.charAt(i)];
        }
    }

    private static long freqToHash() {
        long hash = 0xcbf29ce484222325L;
        for (int i = 0; i < 128; i++) {
            hash ^= charFreq[i];
            hash *= 0x100000001b3L;
        }
        return hash;
    }

    public static void main(String[] args){
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader(args[0]));
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
