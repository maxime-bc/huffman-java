import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class Huffman {

    PriorityQueue<HuffmanNode> huffmanNodes = new PriorityQueue<>(new HuffmanNodeComparator());
    TreeMap<Character, String> charactersCode = new TreeMap<>();
    String text;

    public Huffman(String text) {
        this.text = text;

        generateHuffmanNodes();
        generateTree();
        generateCharactersCode(huffmanNodes.peek(), "");
        printCharactersCode();

        //System.out.println(uncompress(compress()));
    }

    /**
     * Populates all characters and their occurrence number inside Huffman nodes.
     */

    private void generateHuffmanNodes() {

        for (Map.Entry<Character, Integer> entry : getCharactersOccurrences(text).entrySet()) {
            Character character = entry.getKey();
            Integer occurrence = entry.getValue();

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

    private Map<Character, Integer> getCharactersOccurrences(String string) {

        Map<Character, Integer> map = new TreeMap<>();

        for (char c : string.toCharArray()) {

            if (!map.containsKey(c)) {
                map.put(c, 1);
            } else {
                map.put(c, map.get(c) + 1);
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
     * Prints huffman code for each character.
     */

    private void printCharactersCode() {
        System.out.println("--- Printing Codes ---");
        charactersCode.forEach((key, value) -> System.out.println("'" + key + "' : " + value));
    }

    /**
     * Compress text using Huffman tree.
     *
     * @return Compressed text.
     */

    public String compress() {
        StringBuilder compressedText = new StringBuilder();
        for (int i = 0; i < text.length(); i++)
            compressedText.append(charactersCode.get(text.charAt(i)));

        return compressedText.toString();
    }

/*    public String uncompress(String compressedText) {

        StringBuilder uncompressedText = new StringBuilder();
        HuffmanNode node = huffmanNodes.peek();

        for (int i = 0; i < text.length(); ) {
            HuffmanNode tmpNode = node;
            while (tmpNode.leftChild != null && tmpNode.rightChild != null && i < compressedText.length()) {
                if (compressedText.charAt(i) == '1') {
                    tmpNode = tmpNode.rightChild;
                } else {
                    tmpNode = tmpNode.leftChild;
                }
                i++;
            }

            uncompressedText.append(tmpNode.character);

        }
        return uncompressedText.toString();
    }*/
}

