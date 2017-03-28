import java.util.*;
import java.io.*;

public class QingHaoPS59 {

    static class MutableInt {
        int value = 1;

        public void increment() {
            ++value;
        }

        public int getValue() {
            return value;
        }
    }

    public static void main(String[] args){
        final byte[] charFreq = new byte[128];
        Arrays.fill(charFreq, (byte) 0);
        final Map<Long, MutableInt> freqMap = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader(args[0]));
            int n = Integer.parseInt(br.readLine()); //number of test cases
            int result = 0;
            while (n != 0) {
                n--;
                String currLine = br.readLine();
                for (int i = 0; i < currLine.length(); i++) {
                    ++charFreq[(int) currLine.charAt(i)];
                }
                long currHash = MurmurHash.hash64(charFreq, 128);
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
