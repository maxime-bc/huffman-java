/**
 * This class represents a node of the Huffman tree.
 */

public class HuffmanNode {
    private HuffmanNode leftChild, rightChild;
    private int frequency;
    private char character;

    public HuffmanNode(char character, int frequency, HuffmanNode leftChild, HuffmanNode rightChild) {
        this.character = character;
        this.frequency = frequency;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public HuffmanNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(HuffmanNode leftChild) {
        this.leftChild = leftChild;
    }

    public HuffmanNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(HuffmanNode rightChild) {
        this.rightChild = rightChild;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public char getCharacter() {
        return character;
    }

    public void setCharacter(char character) {
        this.character = character;
    }
}