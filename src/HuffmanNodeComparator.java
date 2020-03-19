import java.util.Comparator;

/**
 * This class is used by a PriorityQueue to determine the node with the lower value.
 */

class HuffmanNodeComparator implements Comparator<HuffmanNode> {

    /**
     * Compare the values of two Huffman Nodes
     * @param x Huffman Node 1
     * @param y Huffman Node 2
     * @return the difference of the values of the two nodes.
     */

    public int compare(HuffmanNode x, HuffmanNode y)
    {
        return x.occurrence - y.occurrence;
    }
}