import java.io.*;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        readAndCountWords("src/words");

    }

    public static boolean isFileEmpty(File file) {
        return file.length() == 0;
    }

    public static String readWordsFromFile (String filePath) {
        File file = new File(filePath);
        String text = null;
        try (FileReader reader = new FileReader(file)){
            char[] buf = new char[256];
            int i;
            while ((i = reader.read(buf)) > 0) {
                if (i < 256) {
                    buf = Arrays.copyOf(buf, i);
                }
            }
            text = String.valueOf(buf);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    public static void readAndCountWords (String filePath) {
        Map<String, Integer> wordsList = new TreeMap<>();
        String text = readWordsFromFile(filePath);

        String str = text.replaceAll("\\W", " ").trim();

        if (str == null || text.isEmpty() || str.trim().isEmpty()) {
            System.out.println("File is empty");
            return;
        }

        String regex = "";



        String[] words = str.split(" +");
        for (String word: words) {
            if (!wordsList.containsKey(word))wordsList.put(word, 1);
            else wordsList.put(word, wordsList.get(word) + 1);
        }
        System.out.println("[Слово -- Количество повторений]");
        String key = null;
        int value = 0;
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : wordsList.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();
            if (value > maxCount) maxCount = value;
            System.out.printf("[%s -- %d]\n", key, value);
        }

        for (Map.Entry<String, Integer> entry : wordsList.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();
            if (value == maxCount)
                System.out.printf("\nСлово '%s' встречается в файле '%s' %d раз(а)%n", key, filePath, value);
        }
    }
}
