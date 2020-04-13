import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HuffmanCodesIO {

    public static void writeHuffmanCharactersFrequency(HashMap<Character, Integer> charactersFrequency, String compressedString, String filename) {

        try {
            FileOutputStream fos = new FileOutputStream(filename);

            fos.write(charactersFrequency.size());

            for (Map.Entry<Character, Integer> entry : charactersFrequency.entrySet()) {
                fos.write(entry.getKey());
                fos.write(entry.getValue());
            }

            fos.write(BinaryStringIO.binaryStringToBytesArray(compressedString));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readHuffmanCharactersFrequency(String filename){

        HashMap<Character, Integer> charactersFrequency = new HashMap<>();

        try {
            FileInputStream fis = new FileInputStream(filename);

            int mapSize = fis.read();

            for(int i = 0; i < mapSize; i++){

                char character = (char) fis.read();
                int frequency = fis.read();
                charactersFrequency.put(character, frequency);
            }

            String binaryString = new String(fis.readAllBytes());

//            Huffman huffman = new Huffman(binaryString, charactersFrequency);
//            huffman.uncompress();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
