import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Huffman {

    private static final int ETX = 3; //End of text character used as EOF
    private final PriorityQueue<HuffmanNode> huffmanNodes = new PriorityQueue<>(new HuffmanNodeComparator());
    private final HashMap<Character, String> charactersCode = new HashMap<>();
    private final HashMap<Character, Integer> charactersFrequency;

    private String text;

    /**
     * Generate an Huffman tree with the text given in input.
     * The character used to mark the end of a file is EXT (ascii code = 3).
     *
     * @param text input string on which the Huffman tree will be based.
     */

    public Huffman(String text) {
        this.text = text += (char) ETX;
        this.charactersFrequency = getCharactersOccurrences(this.text);
        generateHuffmanNodes(this.charactersFrequency);
        generateTree();
        generateCharactersCode(huffmanNodes.peek(), "");
    }

    public Huffman(HashMap<Character, Integer> charactersFrequency) {
        this.charactersFrequency = charactersFrequency;
        generateHuffmanNodes(this.charactersFrequency);
        generateTree();
        generateCharactersCode(huffmanNodes.peek(), "");
    }

    /**
     * Generate a node for each character and add all the nodes to the priority queue.
     */

    private void generateHuffmanNodes(HashMap<Character, Integer> charactersFrequency) {

        for (Map.Entry<Character, Integer> entry : charactersFrequency.entrySet()) {
            huffmanNodes.add(new HuffmanNode(entry.getKey(), entry.getValue(), null, null));
        }
    }

    /**
     * Obtains the number of occurrences of each character constituting a string.
     *
     * @param string String for which we need to determine the occurrences of each of its characters.
     * @return An association between each character and the number of its occurrences in the input string.
     */

    private HashMap<Character, Integer> getCharactersOccurrences(String string) {

        HashMap<Character, Integer> map = new HashMap<>();

        for (char character : string.toCharArray()) {

            if (!map.containsKey(character)) {
                map.put(character, 1);
            } else {
                map.put(character, map.get(character) + 1);
            }
        }

        return map;
    }

    /**
     * Generates a tree with all the nodes stored inside the priority queue.
     * <p>
     * This function collects the two Huffman nodes with the smallest occurrence
     * and with the smallest ascii codes from the priority queue.
     * Then, a new parent node for these two nodes is created and added to the priority queue,
     * until only one node remains, the root of the Huffman tree.
     */

    private void generateTree() {

        while (huffmanNodes.size() > 1) {
            HuffmanNode childOne = huffmanNodes.poll();
            HuffmanNode childTwo = huffmanNodes.poll();
            huffmanNodes.add(new HuffmanNode(childTwo.getCharacter(),
                    childOne.getFrequency() + childTwo.getFrequency(), childOne, childTwo));
        }
    }

    /**
     * Go down the Huffman tree recursively to generate a code for each character.
     *
     * @param huffmanNode Root of the Huffman tree.
     * @param code        Code being generated.
     */

    private void generateCharactersCode(HuffmanNode huffmanNode, String code) {
        if (huffmanNode != null) {
            if (huffmanNode.getRightChild() != null) {
                generateCharactersCode(huffmanNode.getRightChild(), code + "1");
            }

            if (huffmanNode.getLeftChild() != null) {
                generateCharactersCode(huffmanNode.getLeftChild(), code + "0");
            }

            if (huffmanNode.getLeftChild() == null && huffmanNode.getRightChild() == null) {
                charactersCode.put(huffmanNode.getCharacter(), code);
            }
        }
    }

    /**
     * Print characters presents in the input string, their ascii code, their occurrence number and their generated code.
     */

    public void printCharactersOccurrenceAndCode() {
        System.out.printf("\n     %-10s %-20s %-20s %-30s%n", "char", "(ascii value)", "occurrence", "code");
        charactersFrequency.forEach((character, occurrence) ->
                System.out.printf("     %-10s %-20d %-20s %-30s%n",
                        character, (int) character, occurrence, charactersCode.get(character)));
        System.out.print("\n");
    }

    /**
     * Compress text using the Huffman tree.
     *
     * @return Compressed text.
     */

    public HuffmanAttributes compress() {
        StringBuilder compressedStringBuilder = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            compressedStringBuilder.append(charactersCode.get(text.charAt(i)));
        }
        return new HuffmanAttributes(charactersFrequency, compressedStringBuilder.toString());
    }

    /**
     * Uncompress text using the Huffman tree.
     *
     * @return Uncompressed text.
     */

    public String uncompress(String compressedString) {
        StringBuilder uncompressedStringBuilder = new StringBuilder();
        HuffmanNode rootNode = huffmanNodes.peek();

        for (int i = 0; i < compressedString.length(); ) {

            HuffmanNode currentNode = rootNode;

            while (currentNode.getLeftChild() != null && currentNode.getRightChild() != null && i < compressedString.length()) {

                if (compressedString.charAt(i) == '1') {
                    currentNode = currentNode.getRightChild();
                } else {
                    currentNode = currentNode.getLeftChild();
                }

                i++;
            }

            // if the character EXT is read, we exit the loop because the end of the string was reached.
            if ((int) currentNode.getCharacter() == ETX) {
                break;
            }

            uncompressedStringBuilder.append(currentNode.getCharacter());
        }

        return uncompressedStringBuilder.toString();
    }

    /**
     * Computes the volume gain from compression with the Huffman algorithm.
     *
     * @return A percentage representing the compression gain.
     */

    public double getVolumeGain(String originalString, String compressedString) {
        double textBitsSize = (originalString.length()) * Character.BYTES * 8.0;
        return (1 - ((compressedString.length()) / textBitsSize)) * 100;
    }
}

