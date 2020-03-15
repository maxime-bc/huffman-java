public class Main {

    public static void main(String[] args) {

        String myString = "Hello world !";
        StringOccurrencesCounter stringOccurrencesCounter = new StringOccurrencesCounter(myString);

        String res = stringOccurrencesCounter.getOccurrences();
        System.out.println(res);

    }
}