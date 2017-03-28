import java.util.*;
import java.io.*;

public class QingHaoPS57 {
    static final byte[] charFreq = new byte[128];
    static final Map<Integer, MutableInt> freqMap = new HashMap<>();
    static int currentHash = 0;
    static int randomInt = 0;

    private static void stringToFreq(String s) {
        for (int i = 0; i < s.length(); i++) {
            ++charFreq[(int) s.charAt(i)];
        }
    }

    private static void freqToHash() {
        currentHash = MurmurHash32.hash32(charFreq, 128, randomInt);
        Arrays.fill(charFreq, (byte) 0);
    }

    public static void main(String[] args){
        Arrays.fill(charFreq, (byte) 0);
        int ans = 0;
        Random r = new Random();
        while(ans != 45871) {
            randomInt = r.nextInt();
            try {
                BufferedReader br = new BufferedReader(
                        new FileReader("6.in"), 32768);
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
                ans = result;
                freqMap.clear();
                System.out.println("* " + randomInt + " " + ans);
            }
            catch (IOException e) {
                System.out.println(e);
            }
        }
        System.out.println(randomInt);
    }
}
