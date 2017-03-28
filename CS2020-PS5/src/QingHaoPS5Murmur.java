import java.util.*;
import java.io.*;

public class QingHaoPS5Murmur {
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
        for (int i = 0; i < s.length(); i++) {
            ++charFreq[(int) s.charAt(i)];
        }
    }

    private static long freqToHash() {
        return MurmurHash.hash64(charFreq, 128);
    }

    public static void main(String[] args){
        Arrays.fill(charFreq, (byte) 0);
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader(args[0]));
            int n = Integer.parseInt(br.readLine()); //number of test cases
            int result = 0;
            while (n != 0) {
                n--;
                stringToFreq(br.readLine());
                long currHash = freqToHash();
                Arrays.fill(charFreq, (byte) 0);
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
