import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.util.BitSet;

import java.io.*;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.concurrent.Callable;

@Command(name = "Main", mixinStandardHelpOptions = true, version = "huffman-java 0.0.1",
        description = "Compress a string or a text file using Huffman coding")
class Main implements Callable<Integer> {

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }

    // Command line options management with picocli

    @picocli.CommandLine.ArgGroup(multiplicity = "1")
    InputExclusiveOptions inputExclusiveOptions;

    static class InputExclusiveOptions {
        @picocli.CommandLine.Option(
                names = {"-f", "--file"},
                description = "Take a text file as an input.",
                required = true)
        File inputFile;

        @picocli.CommandLine.Option(
                names = {"-s", "--string"},
                description = "Take a string as an input.",
                required = true)
        String inputString;
    }

    @Override
    public Integer call() { // this method is called when the program is launched with valid parameters/options

        File inputFile = inputExclusiveOptions.inputFile;
        String inputString = inputExclusiveOptions.inputString;
        String outputString;

        if (inputString.isEmpty()) {
            System.out.println("An empty string/file cannot be compressed.");
            return 1;
        }

        if (inputFile != null) {
            // A file was provided as an input

            if (!inputFile.isFile() || !inputFile.exists()) {
                System.out.println("Input is not a file or does not exists.");
                return 1;
            }

            try {
                inputString = Files.readString(inputFile.toPath());
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

        Huffman huffman = new Huffman(inputString);
        outputString = huffman.compress();

        StringBuilder compressedString = new StringBuilder("11110000111100001111");
        if ((compressedString.length() % 8) != 0) {
            int tmp = compressedString.length() % 8;
            compressedString.append("0".repeat(Math.max(0, 8 - tmp)));
        }

        HuffmanCodesIO.writeHuffmanCharactersFrequency(huffman.charactersFrequency, compressedString.toString(), "test");
        HuffmanCodesIO.readHuffmanCharactersFrequency("test");

        // Print result in stdout
        System.out.println("----- Raw text -----\n'" + inputString
                + "' (" + (inputString.length() + 1) * Character.BYTES * 8 + " bits)\n");
        // we add 1 to input string size because Huffman adds a EOF char before compressing the string
        System.out.println("----- Compressed text -----\n'" + outputString
                + "' (" + outputString.length() + " bits)\n");
        System.out.println("----- Uncompressed text -----\n'" + huffman.uncompress() + "'\n");
        System.out.println("Volume gain = " + new DecimalFormat("#.#")
                .format(huffman.getVolumeGain()) + "%.\n");

        return 0;
    }

    private static BitSet fromString(String binary) {
        BitSet bitset = new BitSet(binary.length());
        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) == '1') {
                bitset.set(i);
            }
        }
        return bitset;
    }

}