import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class Huffman {

    private static final int ETX = 3; //End of text character used as EOF
    PriorityQueue<HuffmanNode> huffmanNodes = new PriorityQueue<>(new HuffmanNodeComparator());
    TreeMap<Character, String> charactersCode = new TreeMap<>();
    TreeMap<Character, Integer> charactersOccurrence = new TreeMap<>();

    String text;
    String compressedText = "";
    String uncompressedText = "";

    public Huffman(String text) {
        this.text = addExtChar(text);

        generateHuffmanNodes();
        generateTree();
        generateCharactersCode(huffmanNodes.peek(), "");
        printCharactersOccurrence();
        printCharactersCode();
    }

    /**
     * Adds a character to a string marking the end of the string
     *
     * @param str the string for which we need to add a character marking its end.
     * @return the string with the extra character.
     */

    private String addExtChar(String str) {
        int len = str.length();
        char[] updatedArr = new char[len + 1];
        str.getChars(0, len, updatedArr, 0);
        updatedArr[len] = (char) ETX;
        str.getChars(len, len, updatedArr, len + 1);
        return new String(updatedArr);
    }

    /**
     * Populates all characters and their occurrence number inside Huffman nodes.
     */

    private void generateHuffmanNodes() {

        charactersOccurrence = getCharactersOccurrences(text);

        for (Map.Entry<Character, Integer> entry : charactersOccurrence.entrySet()) {
            char character = entry.getKey();
            int occurrence = entry.getValue();

            HuffmanNode huffmanNode = new HuffmanNode();
            huffmanNode.occurrence = occurrence;
            huffmanNode.character = character;
            huffmanNodes.add(huffmanNode);
        }
    }

    /**
     * Obtains the number of occurrences of the characters constituting a string.
     *
     * @param string String for which we need to determine the occurrences of each of its characters.
     * @return A map (= a dictionary) associating each character to the number of its occurrences in the string.
     */

    private TreeMap<Character, Integer> getCharactersOccurrences(String string) {

        TreeMap<Character, Integer> map = new TreeMap<>();

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
     * Generates a tree with nodes stored into the priority queue.
     * <p>
     * This function collects in the priority queue the two Huffman nodes with the smallest occurrence.
     * Then, a new parent node linked to these two nodes is created and added to the priority queue,
     * until only one node remains. This remaining node is the root of the Huffman tree.
     */

    private void generateTree() {
        HuffmanNodeComparator huffmanNodeComparator = new HuffmanNodeComparator();

        while (huffmanNodes.size() > 1) {

            HuffmanNode childOne = huffmanNodes.poll();
            HuffmanNode childTwo = huffmanNodes.poll();
            HuffmanNode parent = new HuffmanNode();

            int comparisonRes = huffmanNodeComparator.compare(childOne, childTwo);
            if (comparisonRes > 0) {
                parent.leftChild = childTwo;
                parent.rightChild = childOne;
            } else {
                parent.leftChild = childOne;
                parent.rightChild = childTwo;
            }

            parent.character = parent.rightChild.character;
            parent.occurrence = childOne.occurrence + childTwo.occurrence;

            huffmanNodes.add(parent);
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
            if (huffmanNode.rightChild != null) {
                generateCharactersCode(huffmanNode.rightChild, code + "1");
            }

            if (huffmanNode.leftChild != null) {
                generateCharactersCode(huffmanNode.leftChild, code + "0");
            }

            if (huffmanNode.leftChild == null && huffmanNode.rightChild == null) {
                charactersCode.put(huffmanNode.character, code);
            }
        }
    }

    /**
     * Prints Huffman code for each character.
     */

    private void printCharactersCode() {
        System.out.println("----- Characters codes -----");
        charactersCode.forEach((character, code) -> System.out.println("'" + character + "' -> " + code));
        System.out.println("\n");
    }

    /**
     * Prints Huffman occurrences for each character.
     */

    private void printCharactersOccurrence() {
        System.out.println("----- Characters occurrences -----");
        charactersOccurrence.forEach((character, occurrence) ->
                System.out.println("'" + character + "' (" + (int) character + ")" + " -> " + occurrence));
        System.out.println("\n");
    }

    /**
     * Compress text using Huffman tree.
     *
     * @return Compressed text.
     */

    public String compress() {
        StringBuilder compressedStringBuilder = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            compressedStringBuilder.append(charactersCode.get(text.charAt(i)));
        }

        compressedText = compressedStringBuilder.toString();
        return compressedText;
    }

    /**
     * Uncompress text using Huffman tree.
     *
     * @return Uncompressed text.
     */

    public String uncompress() {
        StringBuilder uncompressedStringBuilder = new StringBuilder();
        HuffmanNode rootNode = huffmanNodes.peek();

        for (int i = 0; i < compressedText.length(); ) {

            HuffmanNode currentNode = rootNode;

            while (currentNode.leftChild != null && currentNode.rightChild != null
                    && i < compressedText.length()) {

                if (compressedText.charAt(i) == '1') {
                    currentNode = currentNode.rightChild;
                } else {
                    currentNode = currentNode.leftChild;
                }

                i++;
            }

            if ((int) currentNode.character == ETX) {
                break;
            }

            uncompressedStringBuilder.append(currentNode.character);
        }

        uncompressedText = uncompressedStringBuilder.toString();
        return uncompressedText;
    }

    /**
     * Computes the storage gain from compression with Huffman algorithm
     *
     * @return A percentage representing the compression gain.
     */

    public double compressionGain() {
        double textBitsSize = (text.length()) * Character.BYTES * 8.0;
        return (1 - ((compressedText.length()) / textBitsSize)) * 100;
    }
}

