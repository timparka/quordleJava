package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

//make this class into a singleton
public class Solver {
    private static Solver instance;
    public ArrayList<String> wordBank = new ArrayList<>();
    private Solver() {
        loadWordBank();
    }
    public static Solver getInstance() {
        if (instance == null)
        {
            instance = new Solver();
        }
        return instance;
    }

    private void loadWordBank() {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("word-bank.txt");
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine().trim();
                if (!word.isEmpty()) {
                    wordBank.add(word);
                }
            }
            scanner.close();
        } catch (Exception e) {
            System.err.println("Error loading word-bank.txt: " + e.getMessage());
        }
    }

    public List<String> wordleSolver(HashMap<Character, List<Integer>> greenChar,
                                     HashMap<Character, List<Integer>> yellowChar,
                                     HashMap<Character, List<Integer>> greyChar) {

        List<String> validWords = filterGreenCharacters(greenChar);
        List<String> filteredYellow = filterYellowCharacters(yellowChar, validWords);
        List<String> filteredGrey = filterGreyCharacters(greyChar, greenChar, yellowChar, filteredYellow);

        return filteredGrey;
    }

    public List<String> filterGreenCharacters(HashMap<Character, List<Integer>> greenChar) {
        ArrayList<String> validWords = new ArrayList<>();
        for (String word : wordBank) {
            boolean greenValid = true;
            for (Map.Entry<Character, List<Integer>> entry : greenChar.entrySet()) {
                for (Integer index : entry.getValue()) {
                    if (word.charAt(index) != entry.getKey()) {
                        greenValid = false;
                        break;
                    }
                }
                if (!greenValid) break;
            }
            if (greenValid) validWords.add(word);
        }
        return validWords;
    }

    public List<String> filterYellowCharacters(HashMap<Character, List<Integer>> yellowChar, List<String> words) {
        List<String> filteredYellow = new ArrayList<>();
        for (String word : words) {
            boolean yellowValid = true;
            for (Map.Entry<Character, List<Integer>> entry : yellowChar.entrySet()) {
                for (Integer index : entry.getValue()) {
                    if (word.charAt(index) == entry.getKey() || !word.contains(String.valueOf(entry.getKey()))) {
                        yellowValid = false;
                        break;
                    }
                }
                if (!yellowValid) break;
            }
            if (yellowValid) {
                filteredYellow.add(word);
            }
        }
        return filteredYellow.isEmpty() ? words : filteredYellow;
    }


    public List<String> filterGreyCharacters(HashMap<Character, List<Integer>> greyChar,
                                             HashMap<Character, List<Integer>> greenChar,
                                             HashMap<Character, List<Integer>> yellowChar,
                                             List<String> words) {
        HashMap<Character, List<Integer>> filteredGreyChar = new HashMap<>(greyChar);
        greenChar.keySet().forEach(filteredGreyChar::remove);
        yellowChar.keySet().forEach(filteredGreyChar::remove);

        List<String> filteredGrey = new ArrayList<>();
        for (String word : words) {
            boolean greyValid = true;
            outerLoop:
            for (Map.Entry<Character, List<Integer>> entry : filteredGreyChar.entrySet()) {
                for (Integer index : entry.getValue()) {
                    if (word.charAt(index) == entry.getKey()) {
                        greyValid = false;
                        break outerLoop;
                    }
                }
            }
            if (greyValid) {
                filteredGrey.add(word);
            }
        }
        return filteredGrey.isEmpty() ? words : filteredGrey;
    }

}