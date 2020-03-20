import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

/* https://web.stanford.edu/class/archive/cs/cs106b/cs106b.1172/assn/huffman.html */

public class Huffman {

    PriorityQueue<HuffmanNode> huffmanNodes = new PriorityQueue<>(new HuffmanNodeComparator());
    TreeMap<Character, String> charactersCode = new TreeMap<>();
    TreeMap<Character, Integer> charactersOccurrence = new TreeMap<>();

    String text;
    String compressedText = "";
    String uncompressedText = "";

    public Huffman(String text) {
        this.text = text;

        generateHuffmanNodes();
        generateTree();
        generateCharactersCode(huffmanNodes.peek(), "");
        printCharactersOccurrence();
        printCharactersCode();
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

        while (huffmanNodes.size() > 1) {

            HuffmanNode leftChild = huffmanNodes.poll();
            HuffmanNode rightChild = huffmanNodes.poll();
            HuffmanNode parent = new HuffmanNode();

            parent.leftChild = leftChild;
            parent.rightChild = rightChild;
            parent.occurrence = leftChild.occurrence + rightChild.occurrence;

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
        charactersOccurrence.forEach((character, occurrence) -> System.out.println("'" + character + "' -> " + occurrence));
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
        double textBitsSize = text.length() * Character.BYTES * 8.0;
        return (1 - ((compressedText.length()) / textBitsSize)) * 100;
    }
}

