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
        this.text = addExtChar(text);
        this.charactersFrequency = getCharactersOccurrences(this.text);
        generateHuffmanNodes(this.charactersFrequency);
        generateTree();
        generateCharactersCode(huffmanNodes.peek(), "");
        printCharactersOccurrenceAndCode();
    }

    public Huffman(HashMap<Character, Integer> charactersFrequency) {
        this.charactersFrequency = charactersFrequency;
        generateHuffmanNodes(this.charactersFrequency);
        generateTree();
        generateCharactersCode(huffmanNodes.peek(), "");
        printCharactersOccurrenceAndCode();
    }

    /**
     * Adds the character ETX (End of TeXt) to a string, marking its end.
     *
     * @param str the string for which we need to add the ETX character.
     * @return the string with the EXT character added.
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
     * Generate a node for each character and add all the nodes to the priority queue.
     */

    private void generateHuffmanNodes(HashMap<Character, Integer> charactersFrequency) {

        for (Map.Entry<Character, Integer> entry : charactersFrequency.entrySet()) {
            char character = entry.getKey();
            int occurrence = entry.getValue();

            HuffmanNode huffmanNode = new HuffmanNode();
            huffmanNode.occurrence = occurrence;
            huffmanNode.character = character;
            huffmanNodes.add(huffmanNode);
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
     * Print characters presents in the input string, their ascii code, their occurrence number and their generated code.
     */

    private void printCharactersOccurrenceAndCode() {
        System.out.println("char (ascii value) -> occurrence -> code");
        charactersFrequency.forEach((character, occurrence) ->
                System.out.println("'" + character + "' (" + (int) character + ")" +
                        " -> " + occurrence + " -> " + charactersCode.get(character)));
        System.out.println("\n");
    }

    /**
     * Compress text using the Huffman tree.
     *
     * @return Compressed text.
     */

    public String compress() {
        StringBuilder compressedStringBuilder = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            compressedStringBuilder.append(charactersCode.get(text.charAt(i)));
        }
        return binaryStringCompleteZeros(compressedStringBuilder.toString());
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

            while (currentNode.leftChild != null && currentNode.rightChild != null && i < compressedString.length()) {

                if (compressedString.charAt(i) == '1') {
                    currentNode = currentNode.rightChild;
                } else {
                    currentNode = currentNode.leftChild;
                }

                i++;
            }

            // if the character EXT is read, we exit the loop because the end of the string was reached.
            if ((int) currentNode.character == ETX) {
                break;
            }

            uncompressedStringBuilder.append(currentNode.character);
        }

        return uncompressedStringBuilder.toString();
    }

//    /**
//     * Computes the volume gain from compression with the Huffman algorithm.
//     *
//     * @return A percentage representing the compression gain.
//     */

//    public double getVolumeGain() {
//        double textBitsSize = (text.length()) * Character.BYTES * 8.0;
//        return (1 - ((compressedText.length()) / textBitsSize)) * 100;
//    }

    /**
     *
     */
    private String binaryStringCompleteZeros(String str) {

        int byteSize = 8;
        StringBuilder compressedString = new StringBuilder(str);
        if ((compressedString.length() % byteSize) != 0) {
            int zerosToAdd = byteSize - (compressedString.length() % byteSize);
            compressedString.append("0".repeat(Math.max(0, zerosToAdd)));
        }
        return compressedString.toString();
    }

    public HashMap<Character, Integer> getCharactersFrequency() {
        return this.charactersFrequency;
    }
}

