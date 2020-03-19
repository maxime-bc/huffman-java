import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    @Option(names = {"-o", "--output"}, description = "Output compressed string into a file.")
    private File outputFile;

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
        String outputString = "";

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

        if (outputFile != null) {
            // Write result in a file
            try {
                Files.write(Paths.get(outputFile.getPath()), outputString.getBytes());
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

        // Print result in stdout
        System.out.println("Raw text = \n'" + inputString + "'\n");
        System.out.println("Compressed text = \n'" + outputString + "'\n");
        System.out.println("Uncompressed text = \n'" + huffman.uncompress() + "'\n");
        System.out.println("Compression gain = " + new DecimalFormat("#.#")
                .format(huffman.compressionGain()) + " %.\n");

        return 0;
    }
}