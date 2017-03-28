import java.util.*;
import java.io.*;

public class QingHaoPS58 {
    static final byte[] charFreq = new byte[128];
    static Map<Long, MutableInt> freqMap = new HashMap<>();;
    static long currentHash = 0;

    private static void stringToFreq(String s) {
        for (int i = 0; i < s.length(); i++) {
            ++charFreq[(int) s.charAt(i)];
        }
    }

    private static void freqToHash() {
        currentHash = MurmurHash.hash64(charFreq, 128);
        Arrays.fill(charFreq, (byte) 0);
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
                freqToHash();
                MutableInt count = freqMap.get(currentHash);
                if (count == null) {
                    freqMap.put(currentHash, new MutableInt());
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
