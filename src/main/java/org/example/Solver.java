package org.example;

import java.util.ArrayList;
import java.util.List;

public class Solver {
    private List<String>[] wordBanks;

    @SuppressWarnings("unchecked")
    public Solver(List<String> initialWordBank) {
        this.wordBanks = (List<String>[]) new List[4];
        for (int i = 0; i < wordBanks.length; i++) {
            this.wordBanks[i] = new ArrayList<>(initialWordBank); //initialize each quadrant with its own wordBank
        }
    }
    public List<String> filterWordList(
            int quadrant,
            String userGuess,
            List<Integer> validCharPositions,
            List<Integer> charWrongIndexPositions,
            List<Character> greyLetters
    ) {
        List<String> filteredWords = new ArrayList<>(this.wordBanks[quadrant]);
        List<String> validWords = new ArrayList<>();

        for (String word : filteredWords) {
            boolean isValidWord = true;

            // Check for grey letters
            for (char greyLetter : greyLetters) {
                if (word.indexOf(greyLetter) != -1) {
                    isValidWord = false;
                    break;
                }
            }

            // Check for green indices
            if (isValidWord) {
                for (int index : validCharPositions) {
                    if (userGuess.charAt(index) != 0 && word.charAt(index) != userGuess.charAt(index)) {
                        isValidWord = false;
                        break;
                    }
                }
            }

            // Check for yellow indices
            if (isValidWord) {
                for (int index : charWrongIndexPositions) {
                    if (word.indexOf(userGuess.charAt(index)) == -1 || word.charAt(index) == userGuess.charAt(index)) {
                        isValidWord = false;
                        break;
                    }
                }
            }

            if (isValidWord) {
                validWords.add(word);
            }
        }

        return validWords;
    }

    //suggests a random word from validWords list
    public List<String> quorateSuggester(
            int quadrant,
            String userGuess,
            List<Integer> validCharPositions,
            List<Integer> charWrongIndexPositions,
            List<Character> greyLetters
    ) {
        List<String> validWords = filterWordList(
                quadrant,
                userGuess,
                validCharPositions,
                charWrongIndexPositions,
                greyLetters
        );

        if (validWords.isEmpty()) {
            throw new IllegalStateException("No valid words found.");
        }

        // Select a random word to return
        int randomIndex = (int) (Math.random() * validWords.size());
        return List.of(validWords.get(randomIndex)); // Return as a list containing only the selected word
    }
}
