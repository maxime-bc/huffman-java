import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.io.*;
import java.util.concurrent.Callable;

@Command(name = "Main", mixinStandardHelpOptions = true, version = "huffman-java 0.0.1",
        description = "Compress or uncompress text files using Huffman coding.")
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

        HuffmanAttributes huffmanAttributes;

        if (!inputFile.exists() || inputFile.isDirectory()) {
            System.out.println("Error : file " + inputFile.toString() + " doesn't exists or is a directory.");
            System.exit(1);
        }

        if (uncompressMode) {
            /* Uncompress mode */
            huffmanAttributes = (HuffmanAttributes) HuffmanIO.deserialize(inputFile);
            if (huffmanAttributes == null) {
                System.out.println("Error : invalid format for file `" + inputFile.toString() + '`');
                System.exit(1);
            }
            String uncompressedString = Huffman.uncompress(huffmanAttributes, extra);
            HuffmanIO.writeFile(outputFile, uncompressedString);
            System.out.println("Done ! Input uncompressed in `" + outputFile + '`');
        } else {
            /* Compress mode */
            String inputString = HuffmanIO.readFile(inputFile);
            huffmanAttributes = Huffman.compress(inputString, extra);
            HuffmanIO.serialize(huffmanAttributes, outputFile);
            System.out.println("Done ! Input compressed in `" + outputFile + '`');
        }
        return 0;
    }
}
