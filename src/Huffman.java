import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Huffman {

    private static final int ETX = 3; //End of text character used as EOF

    /**
     * Generate a priority queue with nodes containing a character and its frequency.
     *
     * @param charactersFrequency A dictionary containing all characters and their frequency to be added to the queue.
     * @return The priority queue created with all the nodes.
     */

    private static PriorityQueue<HuffmanNode> generateHuffmanNodes(HashMap<Character, Integer> charactersFrequency) {

        PriorityQueue<HuffmanNode> huffmanNodes = new PriorityQueue<>(new HuffmanNodeComparator());

        for (Map.Entry<Character, Integer> entry : charactersFrequency.entrySet()) {
            huffmanNodes.add(new HuffmanNode(entry.getKey(), entry.getValue(), null, null));
        }

        return huffmanNodes;
    }

    /**
     * Obtains the number of occurrences for each character of a given string.
     *
     * @param string A string for which we need to determine the occurrences of each of its characters.
     * @return A dictionary associating each character to its occurrences in the string.
     */

    private static HashMap<Character, Integer> getCharactersOccurrences(String string) {

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
     * Generates an Huffman tree.
     * <p>
     * This function collects the two smallest Huffman nodes from the queue.
     * Then, a new parent node for these two nodes is created and added to queue,
     * until only one node remains, the root of the Huffman tree.
     *
     * @param huffmanNodes Queue containing all nodes.
     * @return The generated Huffman tree.
     */

    private static PriorityQueue<HuffmanNode> generateHuffmanTree(PriorityQueue<HuffmanNode> huffmanNodes) {

        while (huffmanNodes.size() > 1) {
            HuffmanNode childOne = huffmanNodes.poll();
            HuffmanNode childTwo = huffmanNodes.poll();
            huffmanNodes.add(new HuffmanNode(childTwo.getCharacter(),
                    childOne.getFrequency() + childTwo.getFrequency(), childOne, childTwo));
        }

        return huffmanNodes;
    }

    /**
     * Go down the Huffman tree recursively to generate a code for each character.
     *
     * @param huffmanNode Root of the Huffman tree.
     * @param code        Code being generated.
     * @return A dictionary associating a character with its code.
     */

    private static HashMap<Character, String> generateCharactersCode(HuffmanNode huffmanNode, String code) {
        HashMap<Character, String> charactersCode = new HashMap<>();

        if (huffmanNode != null) {
            if (huffmanNode.getRightChild() != null) {
                charactersCode.putAll(generateCharactersCode(huffmanNode.getRightChild(), code + "1"));
            }

            if (huffmanNode.getLeftChild() != null) {
                charactersCode.putAll(generateCharactersCode(huffmanNode.getLeftChild(), code + "0"));
            }

            if (huffmanNode.getLeftChild() == null && huffmanNode.getRightChild() == null) {
                charactersCode.put(huffmanNode.getCharacter(), code);
            }
        }
        return charactersCode;
    }

    /**
     * Print each character with its ascii code, occurrence and code.
     *
     * @param charactersFrequency A dictionary associating a character with its frequency.
     * @param charactersCode      A dictionary associating a character with its code.
     */

    private static void printCharactersOccurrenceAndCode(HashMap<Character, Integer> charactersFrequency, HashMap<Character, String> charactersCode) {
        System.out.printf("\n     %-10s %-20s %-20s %-30s%n", "char", "ASCII value", "frequency", "code");
        charactersFrequency.forEach((character, occurrence) ->
                System.out.printf("     %-10s %-20d %-20s %-30s%n",
                        character, (int) character, occurrence, charactersCode.get(character)));
        System.out.print("\n");
    }

    /**
     * Computes the volume gain from a compression using the Huffman algorithm.
     *
     * @param originalString   String before compression
     * @param compressedString String after compression
     */

    private static void printVolumeGain(String originalString, String compressedString) {
        double textBitsSize = (originalString.length()) * Character.BYTES * 8.0;
        double volumeGain = (1 - ((compressedString.length()) / textBitsSize)) * 100;
        System.out.println(new DecimalFormat("#.#").format(volumeGain) + " % compression rate.\n");
    }

    /**
     * Compress text using the Huffman tree.
     *
     * @param stringToCompress The string to compress.
     * @param verbose          If set to true, print extra information about compression
     *                         (characters frequencies, codes, ascii values and compression gain, ...).
     * @return Characters frequency and compressed string stored into an HuffmanAttributes class.
     */

    public static HuffmanAttributes compress(String stringToCompress, boolean verbose) {
        HashMap<Character, Integer> charactersFrequency;
        HashMap<Character, String> charactersCode;
        PriorityQueue<HuffmanNode> huffmanNodes, huffmanTree;
        StringBuilder compressedStringBuilder = new StringBuilder();
        String compressedString;

        stringToCompress += (char) ETX;
        charactersFrequency = getCharactersOccurrences(stringToCompress);
        huffmanNodes = generateHuffmanNodes(charactersFrequency);
        huffmanTree = generateHuffmanTree(huffmanNodes);

        charactersCode = generateCharactersCode(huffmanTree.peek(), "");

        for (int i = 0; i < stringToCompress.length(); i++) {
            compressedStringBuilder.append(charactersCode.get(stringToCompress.charAt(i)));
        }

        compressedString = compressedStringBuilder.toString();


        if (verbose) {
            printCharactersOccurrenceAndCode(charactersFrequency, charactersCode);
            printVolumeGain(stringToCompress, compressedString);
        }
        return new HuffmanAttributes(charactersFrequency, compressedString);
    }

    /**
     * Uncompress text using the Huffman tree.
     *
     * @param huffmanAttributes Characters frequency and compressed string stored into an HuffmanAttributes class.
     * @param verbose           If set to true, print extra information about compression
     *                          (characters frequencies, codes, ascii values and compression gain, ...).
     * @return The uncompressed text.
     */

    public static String uncompress(HuffmanAttributes huffmanAttributes, boolean verbose) {
        HashMap<Character, String> charactersCode;
        HashMap<Character, Integer> charactersFrequency = huffmanAttributes.getCharactersFrequency();
        PriorityQueue<HuffmanNode> huffmanNodes, huffmanTree;
        StringBuilder uncompressedStringBuilder = new StringBuilder();
        String uncompressedString, compressedString = huffmanAttributes.getCompressedString();

        huffmanNodes = generateHuffmanNodes(charactersFrequency);
        huffmanTree = generateHuffmanTree(huffmanNodes);

        charactersCode = generateCharactersCode(huffmanTree.peek(), "");

        HuffmanNode rootNode = huffmanTree.peek();

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

        uncompressedString = uncompressedStringBuilder.toString();

        if (verbose) {
            printCharactersOccurrenceAndCode(charactersFrequency, charactersCode);
            printVolumeGain(uncompressedString, compressedString);
        }

        return uncompressedString;
    }
}

