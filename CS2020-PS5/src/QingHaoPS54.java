import java.util.*;
import java.io.*;

public class QingHaoPS54 {
    static byte[] charFreq = new byte[128];
    static Map<Long, MutableInt> freqMap = new HashMap<>();

    private static void stringToFreq(String s) {
        for (int i = 0; i < s.length(); i++) {
            ++charFreq[(int) s.charAt(i)];
        }
    }

    private static long freqToHash() {
        long hash = MurmurHash.hash64(charFreq, 128);
        Arrays.fill(charFreq, (byte) 0);
        return hash;
    }

    public static void main(String[] args){
        Arrays.fill(charFreq, (byte) 0);
        try {
            BufferedReader br = new BufferedReader(new FileReader(args[0]), 1 << 15);
            int n = Integer.parseInt(br.readLine()); //number of test cases
            int result = 0;
            while (n > 0) {
                n--;
                stringToFreq(br.readLine());
                long lineHash = freqToHash();
                MutableInt count = freqMap.get(lineHash);
                if (count == null) {
                    freqMap.put(lineHash, new MutableInt());
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
