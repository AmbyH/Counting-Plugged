import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    private String mostCommonWord;
    private int occurences;
    static ArrayList<WordCount> wordList = new ArrayList<WordCount>();
    private static String commonWords = ""; // Initialize commonWords
    private String in;

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the path of the file you would like to analyze: ");
        String filePath = scanner.nextLine();
        System.out.println("Enter 1 to find the top 5 most common words in the file, or 2 to find the most common word: ");
        int b = scanner.nextInt();
        if (b == 1) {
            System.out.println(findTop5Occurences(filePath, b));
        } else {
            System.out.println("The most common word is " + findTop5Occurences(filePath, b - 2) + ". ");
        }
    }

    private static String findTop5Occurences(String filePath, int b) throws IOException {
        ArrayList<String> words = cleanString("src/main/java/" + filePath); // Use filePath directly
        for (String a : words) {
            if (!isInList(a)) {
                wordList.add(new WordCount(a, 1));
            } else {
                for (WordCount wordCount : wordList) {
                    if (wordCount.getWord().equals(a)) {
                        wordCount.addCount();
                    }
                }
            }
        }
        Collections.sort(wordList, new Comparator<WordCount>() {
            public int compare(WordCount wc1, WordCount wc2) {
                return Integer.compare(wc2.count, wc1.count);
            }
        });

        // Ensure wordList has at least 5 elements
        if (wordList.size() < 5) {
            return "Not enough words in the file.";
        }

        if (b == 1) {
            return "The top five most common words in the text file " + filePath + " are" + "\n1. " + wordList.get(0).toString() + "\n2. " + wordList.get(1).toString() + "\n3. " + wordList.get(2).toString() + "\n4. " + wordList.get(3).toString() + "\n5. " + wordList.get(4).toString();
        } else {
            return wordList.get(0).toString();
        }
    }

    public static boolean isInList(String word) {
        for (WordCount wordCount : wordList) {
            if (wordCount.getWord().equals(word)) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<String> cleanString(String input) throws IOException {
        String output = readFile(input);
        ArrayList<String> out = new ArrayList<String>();
        String curWord = "";
        for (int i = 0; i < output.length(); i++) {
            char c = output.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                curWord += c;
            } else {
                if (!curWord.isEmpty() && !commonWords.contains(curWord)) {
                    out.add(curWord.toLowerCase());
                    curWord = "";
                }
            }
        }
        if (!curWord.isEmpty() && !commonWords.contains(curWord)) {
            out.add(curWord.toLowerCase());
        }
        return out;
    }

    public static int countLinesInFile(String nameOfFile) throws FileNotFoundException {
        File file = new File(nameOfFile);
        Scanner scanner = new Scanner(file);
        int lineCount = 0;
        while (scanner.hasNextLine()) {
            lineCount++;
            scanner.nextLine();
        }
        return lineCount;
    }

    private static String readFile(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
            return stringBuilder.toString();
        } finally {
            reader.close();
        }
    }
}