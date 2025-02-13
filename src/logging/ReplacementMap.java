package logging;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplacementMap {
    private static final Pattern PATTERN = Pattern.compile("@(\\w+)");
    private final HashMap<String, Object> hashMap;

    public ReplacementMap() {
        this.hashMap = new HashMap<>();
    }

    public ReplacementMap(HashMap<String, Object> hashMap) {
        this.hashMap = hashMap;
    }

    public static ReplacementMap create() {
        return new ReplacementMap();
    }

    public static ReplacementMap of(HashMap<String, Object> hashMap) {
        return new ReplacementMap(hashMap);
    }

    public ReplacementMap add(String placeholder, Object value) {
        hashMap.put(placeholder, value);
        return this;
    }

    public ReplacementMap remove(String placeholder) {
        hashMap.remove(placeholder);
        return this;
    }

    public String apply(String message) {
        Matcher matcher = PATTERN.matcher(message);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String value = hashMap.get(matcher.group(1)).toString();
            if (value != null) {
                matcher.appendReplacement(result, Matcher.quoteReplacement(value));
            }
        }

        matcher.appendTail(result);
        return result.toString();
    }
}
