import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.io.*;
import java.text.DecimalFormat;
import java.util.concurrent.Callable;

@Command(name = "Main", mixinStandardHelpOptions = true, version = "huffman-java 0.0.1",
        description = "Compress a string or a text file using Huffman coding.")
class Main implements Callable<Integer> {

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }

    // Command line options management with picocli

    @CommandLine.Option(names = {"-i", "--input"}, required = true, description = "Input file.")
    private File inputFile;

    @CommandLine.Option(names = {"-o", "--output"}, required = true, description = "Output file.")
    private File outputFile;

    @CommandLine.Option(names = {"-u", "--uncompress"}, description = "Uncompress input file into output file.")
    private boolean uncompressMode;

    @CommandLine.Option(names = {"-e", "--extra"},
            description = "Print extra information (compression rate, input and output file sizes).")
    private boolean extra;

    @Override
    public Integer call() { // this method is called when the program is launched with valid parameters/options

        Huffman huffman;
        HuffmanAttributes huffmanAttributes;

        if (uncompressMode) {
            /* Uncompress mode */
            huffmanAttributes = HuffmanIO.readHuffmanFile(inputFile);
            huffman = new Huffman(huffmanAttributes.getCharactersFrequency());
            String uncompressedString = huffman.uncompress(huffmanAttributes.getCompressedString());
            HuffmanIO.writeFile(outputFile, uncompressedString);
            if (extra) {
                System.out.println("Input file size : " + inputFile.length() +
                        " bytes.\nOutput file size : " + outputFile.length() + " bytes.\n");
                System.out.println(new DecimalFormat("#.#").
                        format(huffman.getVolumeGain(uncompressedString,
                                huffmanAttributes.getCompressedString())) + " % compression rate.\n");
            }
        } else {
            /* Compress mode */
            String inputString = HuffmanIO.readFile(inputFile);
            huffman = new Huffman(inputString);
            huffmanAttributes = huffman.compress();
            HuffmanIO.writeHuffmanFile(outputFile,
                    new HuffmanAttributes(huffmanAttributes.getCharactersFrequency(),
                            huffmanAttributes.getCompressedString()));
            if (extra) {
                System.out.println("Input file size : " + inputFile.length() +
                        " bytes.\nOutput file size : " + outputFile.length() + " bytes.\n");
                System.out.println(new DecimalFormat("#.#").
                        format(huffman.getVolumeGain(inputString,
                                huffmanAttributes.getCompressedString())) + " % compression rate.\n");
            }
        }
        return 0;
    }
}