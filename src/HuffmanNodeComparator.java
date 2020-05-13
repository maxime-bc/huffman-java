import java.util.Comparator;

/**
 * This class is used by the priority queue to determine the order in which the nodes are collected.
 */

class HuffmanNodeComparator implements Comparator<HuffmanNode> {

    /**
     * Compare two Huffman Nodes.
     * <p>
     * Nodes with the smallest occurrences have priority.
     * If nodes have the same occurrence, the priority goes to the one with a character having the smallest ascii code.
     *
     * @param x Huffman Node 1
     * @param y Huffman Node 2
     * @return the difference of the comparison of the two nodes.
     */

    public int compare(HuffmanNode x, HuffmanNode y) {
        // If occurrences are equals, we compare character's ascii code.
        if (x.getFrequency() == y.getFrequency()) {
            return (int) x.getCharacter() - (int) y.getCharacter();
        }
        return x.getFrequency() - y.getFrequency();
    }
}