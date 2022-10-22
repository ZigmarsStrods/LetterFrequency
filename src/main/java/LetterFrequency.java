import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class LetterFrequency {

    public static void main(String[] args) throws IOException {

        String fileLocation = createFile("https://www.folgerdigitaltexts.org/Oth/text");
        String example = getText(fileLocation);
        Map<String, Double> result = getLetterOccurrence(example);
        for (Map.Entry<String, Double> element : result.entrySet()) {
            System.out.printf(Locale.ROOT, element.getKey() + " occurrence is %.2f%%\n", element.getValue());
        }
    }

    private static Map<String, Double> getLetterOccurrence(String inputText) {
        String modified = inputText.toUpperCase().replaceAll("[^A-Z]", "");
        return Arrays.stream(modified.split(""))
                .collect(Collectors.groupingBy(Object::toString, Collectors.counting()))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> e.getValue().doubleValue() * 100 / modified.length(),
                        (e1, e2) -> e1, TreeMap::new));
    }

    private static String getText(String fileLocation) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileLocation));
        StringBuilder input = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            input.append(line);
        }
        return input.toString();
    }

    private static String createFile(String stringURL) throws IOException {
        String fileLocation = "src/main/resources/file.txt";
        URL url = new URL(stringURL);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        BufferedWriter out = new BufferedWriter(new FileWriter(fileLocation));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            out.write(inputLine);
        }
        in.close();
        out.close();
        return fileLocation;
    }
}
