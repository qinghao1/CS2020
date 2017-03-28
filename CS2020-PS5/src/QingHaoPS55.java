import java.util.*;
import java.io.*;

public class QingHaoPS55 {
    static byte[] charFreq = new byte[128];
    static Map<Long, MutableInt> freqMap = new HashMap<>();
    static long currentHash = 0;

    private static void freqToHash() {
        currentHash = MurmurHash.hash64(charFreq, 128);
        Arrays.fill(charFreq, (byte) 0);
    }

    public static void main(String[] args){
        Arrays.fill(charFreq, (byte) 0);
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(args[0]), 1 << 16);
            int n = 0;
            int c = 0;
            while((c = in.read()) > 0 && !(c == 10 || c == 13)) {
                if (n > 0) n *= 10;
                n += (c - 48);
            }
            int result = 0;
            while (n > 0) {
                while((c = in.read()) > 0 && !(c == 10 || c == 13)) {
                    ++charFreq[c];
                }
                n--;
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
