@picocli.CommandLine.Command(name = "parser", mixinStandardHelpOptions = true)
public class CommandLineParser {

    @picocli.CommandLine.ArgGroup(multiplicity = "1")
    Exclusive exclusive;

    static class Exclusive {
        @picocli.CommandLine.Option(
                names = {"-f", "--file"},
                description = "Input text file",
                required = true)
        String inputFile;

        @picocli.CommandLine.Option(
                names = {"-s", "--string"},
                description = "Input string",
                required = true)
        String inputString;
    }
}