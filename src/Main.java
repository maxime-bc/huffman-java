import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

@Command(name = "Main", mixinStandardHelpOptions = true, version = "huffman-java 0.0.1",
        description = "Compress a string or a text file using Huffman coding")
class Main implements Callable<Integer> {

    @Option(names = {"-o", "--output"}, description = "Output compressed string into a file instead of stdout")
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

    // this example implements Callable, so parsing, error handling and handling user
    // requests for usage help or version help can be done with one line of code.
    public static void main(String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() { // your business logic goes here...

        File inputFile = inputExclusiveOptions.inputFile;
        String inputString = inputExclusiveOptions.inputString;

        // for debug
        System.out.println("Input file = " + inputFile);
        System.out.println("Input string = " + inputString);
        System.out.println("Output string = " + outputFile);

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
        // Compress input
        StringOccurrencesCounter stringOccurrencesCounter = new StringOccurrencesCounter(inputString);
        String compressedString = stringOccurrencesCounter.getOccurrences().toString();

        // TODO: place here huffman compression algorithm

        if (outputFile != null) {
            // Write result in a file
            try {
                Files.write(Paths.get(outputFile.getPath()), compressedString.getBytes());
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        } else {
            // Print result in stdout
            System.out.println(compressedString);
        }

        return 0;
    }
}