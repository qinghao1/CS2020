public final class FNVHash {

    private static final long FNV_64_INIT = 0xcbf29ce484222325L;
    private static final long FNV_64_PRIME = 0x100000001b3L;

    public static long hash64(final byte[] k) {
        long rv = FNV_64_INIT;
        for(int i = 0; i < 128; i++) {
            rv ^= k[i];
            rv *= FNV_64_PRIME;
        }
        return rv;
    }
}