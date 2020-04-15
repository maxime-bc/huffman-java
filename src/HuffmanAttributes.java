import java.util.HashMap;

public class HuffmanAttributes {
    private HashMap<Character, Integer> charactersFrequency;
    private String compressedString;

    /**
     * This class is used to read and write huffman tree data more easily.
     *
     * @param charactersFrequency dictionary of characters and their frequency.
     * @param compressedString    compressed string.
     */

    public HuffmanAttributes(HashMap<Character, Integer> charactersFrequency, String compressedString) {
        this.charactersFrequency = charactersFrequency;
        this.compressedString = compressedString;
    }

    /**
     * Getter for the dictionary of characters and their frequency.
     *
     * @return a dictionary of characters and their frequency.
     */

    public HashMap<Character, Integer> getCharactersFrequency() {
        return charactersFrequency;
    }

    /**
     * Setter for the dictionary of characters and their frequency.
     */

    public void setCharactersFrequency(HashMap<Character, Integer> charactersFrequency) {
        this.charactersFrequency = charactersFrequency;
    }

    /**
     * Getter for the compressed string.
     *
     * @return the compressed string.
     */

    public String getCompressedString() {
        return compressedString;
    }

    /**
     * Setter for the compressed string.
     */

    public void setCompressedString(String compressedString) {
        this.compressedString = compressedString;
    }
}
