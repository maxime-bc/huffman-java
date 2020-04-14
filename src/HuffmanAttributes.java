import java.util.HashMap;

public class HuffmanAttributes {
    private HashMap<Character, Integer> charactersFrequency;
    private String compressedString;

    public HuffmanAttributes(HashMap<Character, Integer> charactersFrequency, String compressedString) {
        this.charactersFrequency = charactersFrequency;
        this.compressedString = compressedString;
    }

    public HashMap<Character, Integer> getCharactersFrequency() {
        return charactersFrequency;
    }

    public void setCharactersFrequency(HashMap<Character, Integer> charactersFrequency) {
        this.charactersFrequency = charactersFrequency;
    }

    public String getCompressedString() {
        return compressedString;
    }

    public void setCompressedString(String compressedString) {
        this.compressedString = compressedString;
    }
}
