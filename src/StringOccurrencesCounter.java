import java.util.Map;
import java.util.TreeMap;

public class StringOccurrencesCounter {

    private final String str;

    public StringOccurrencesCounter(String str) {
        this.str = str;
    }

    public Map<Character, Integer> getOccurrences() {

        // Ordered dict of chars present in the given string
        Map<Character, Integer> map = new TreeMap<>();

        for (char c : str.toCharArray()) {

            if (!map.containsKey(c)) {
                map.put(c, 1);
            } else {
                map.put(c, map.get(c) + 1);
            }
        }

        return map;
    }
}
