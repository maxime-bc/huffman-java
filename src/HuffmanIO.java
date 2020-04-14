import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class HuffmanIO {

    private static byte[] binaryStringToBytesArray(String binaryString) {

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

    private static String bytesArrayToBinaryString(byte[] bytesArray) {

        int byteSize = 8;
        StringBuilder binaryStringBuilder = new StringBuilder();
        BitSet set = BitSet.valueOf(bytesArray);

        for (int i = 0; i <= bytesArray.length * byteSize - 1; i++) {
            if (set.get(i)) {
                binaryStringBuilder.append("1");
            } else {
                binaryStringBuilder.append("0");
            }
        }
        return binaryStringBuilder.toString();
    }

    public static void writeHuffman(HuffmanAttributes huffmanAttributes, String filename) {

        try {
            FileOutputStream fos = new FileOutputStream(filename);

            fos.write(huffmanAttributes.getCharactersFrequency().size());

            for (Map.Entry<Character, Integer> entry : huffmanAttributes.getCharactersFrequency().entrySet()) {
                fos.write(entry.getKey());
                fos.write(entry.getValue());
            }

            byte[] bytesArray = binaryStringToBytesArray(huffmanAttributes.getCompressedString());
            fos.write(bytesArray.length);
            fos.write(bytesArray);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HuffmanAttributes readHuffman(String filename) {

        HuffmanAttributes huffmanAttributes = null;

        try {
            FileInputStream fis = new FileInputStream(filename);
            HashMap<Character, Integer> charactersFrequency = new HashMap<>();

            int mapSize = fis.read();

            for (int i = 0; i < mapSize; i++) {

                char character = (char) fis.read();
                int frequency = fis.read();
                charactersFrequency.put(character, frequency);
            }

            int stringSize = fis.read();
            byte[] readBytes = new byte[stringSize];

            for (int i = 0; i < stringSize; i++) {
                readBytes[i] = (byte) fis.read();
            }

            String compressedString = bytesArrayToBinaryString(readBytes);
            huffmanAttributes = new HuffmanAttributes(charactersFrequency, compressedString);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return huffmanAttributes;
    }
}
