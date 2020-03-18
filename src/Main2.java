import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main2 {

    public static void main(String[] args) {

        CommandLineParser commandLineParser = new CommandLineParser();
        picocli.CommandLine cmd = new picocli.CommandLine(commandLineParser);

        try {
            cmd.parseArgs(args);
        } catch (CommandLine.MutuallyExclusiveArgsException ex) {
            System.out.println(ex.getMessage());
        }

        String inputFile = commandLineParser.exclusive.inputFile;
        String inputString = commandLineParser.exclusive.inputString;

        if (inputFile != null) {
            // An input file was provided, so we store its content into `inputString`

            Path path = Paths.get(inputFile);

            if (Files.exists(path) && Files.isRegularFile(path)) {
                try {
                    inputString = Files.readString(path);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                System.out.println(inputFile + " does not exists.");
                System.exit(1);
            }
        }
        StringOccurrencesCounter stringOccurrencesCounter = new StringOccurrencesCounter(inputString);
        String res = stringOccurrencesCounter.getOccurrences();
        System.out.println(res);
    }
}