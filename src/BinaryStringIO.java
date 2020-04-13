import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.BitSet;

public class BinaryStringIO {

    public static byte[] binaryStringToBytesArray(String binaryString) {

        BitSet bitSet = new BitSet(binaryString.length());
        int bitCounter = 0;
        for (Character c : binaryString.toCharArray()) {
            if (c.equals('1')) {
                bitSet.set(bitCounter);
            }
            bitCounter++;
        }

        return bitSet.toByteArray();
    }

    public static String bytesArrayToBinaryString(byte[] bytesArray) {

        StringBuilder binaryStringBuilder = new StringBuilder();
        BitSet set = BitSet.valueOf(bytesArray);

        for (int i = 0; i <= bytesArray.length*8-1; i++) {
            if (set.get(i)) {
                binaryStringBuilder.append("1");
            } else {
                binaryStringBuilder.append("0");
            }
        }
        return binaryStringBuilder.toString();
    }

    public static void writeBinString(String binaryString, String filename) {

        try {
            FileOutputStream fos = new FileOutputStream(filename);
            fos.write(binaryStringToBytesArray(binaryString));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readBinString(String filename) {

        String binaryString = null;

        try {
            FileInputStream fis = new FileInputStream(filename);
            binaryString = bytesArrayToBinaryString(fis.readAllBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return binaryString;
    }
}
