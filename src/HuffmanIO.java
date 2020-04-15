import java.io.*;
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
        // here we lost the end 0
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

    public static void writeHuffmanFile(File file, HuffmanAttributes huffmanAttributes) {

        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file.getPath()))) {

            dos.writeInt(huffmanAttributes.getCharactersFrequency().size());

            for (Map.Entry<Character, Integer> entry : huffmanAttributes.getCharactersFrequency().entrySet()) {
                dos.writeChar(entry.getKey());
                dos.writeInt(entry.getValue());
            }

            byte[] bytesArray = binaryStringToBytesArray(huffmanAttributes.getCompressedString());
            dos.writeInt(huffmanAttributes.getCompressedString().length());
            dos.write(bytesArray);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HuffmanAttributes readHuffmanFile(File file) {

        HuffmanAttributes huffmanAttributes = null;

        try (DataInputStream dis = new DataInputStream(new FileInputStream(file.getPath()))) {

            HashMap<Character, Integer> charactersFrequency = new HashMap<>();
            int mapSize = dis.readInt();

            for (int i = 0; i < mapSize; i++) {
                char character = dis.readChar();
                int frequency = dis.readInt();
                charactersFrequency.put(character, frequency);
            }

            int compressedStringSize = dis.readInt();
            byte[] readBytes = dis.readAllBytes();

            StringBuilder compressedString = new StringBuilder(bytesArrayToBinaryString(readBytes));
            compressedString.append("0".repeat(Math.max(0, compressedStringSize - compressedString.length())));

            huffmanAttributes = new HuffmanAttributes(charactersFrequency, compressedString.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return huffmanAttributes;
    }

    public static String readFile(File file) {

        String text = null;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file.getPath()))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();

            while (line != null) {
                stringBuilder.append(line);
                stringBuilder.append(System.lineSeparator());
                line = bufferedReader.readLine();
            }

            text = stringBuilder.toString();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return text;

    }

    public static void writeFile(File file, String text) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file.getPath()))) {
            bufferedWriter.write(text);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
