import org.example.Solver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class SolverTest {
    private Solver solver;
    private Random random;

    @BeforeEach
    void setUp() {
        solver = Solver.getInstance();
        random = new Random();
    }

//    @Test
//    void testFilterGreenCharacters() {
//        HashMap<Character, List<Integer>> greenChar = new HashMap<>();
//        greenChar.put('e', Arrays.asList(2)); // Testing for 'a' at index 0
//        List<String> result = solver.filterGreenCharacters(greenChar);
//        List<String> expected = Arrays.asList("alert");
//        assertEquals(expected, result, "FilterGreenCharacters test failed");
//    }
//
//    @Test
//    void testFilterYellowCharacters() {
//        List<String> words = Arrays.asList("apple", "amaze", "angle", "alien", "alert");
//        HashMap<Character, List<Integer>> yellowChar = new HashMap<>();
//        yellowChar.put('l', Arrays.asList(0)); // Testing for 'l' not at index 0 and contains 'l'
//        List<String> result = solver.filterYellowCharacters(yellowChar, words);
//        List<String> expected = Arrays.asList("apple", "angle", "alien", "alert");
//        assertEquals(expected, result, "FilterYellowCharacters test failed");
//    }
//
//    @Test
//    void testFilterGreyCharacters() {
//        List<String> words = Arrays.asList("apple", "amaze", "angle", "alien", "alert");
//        HashMap<Character, List<Integer>> greyChar = new HashMap<>();
//        greyChar.put('p', Arrays.asList(1)); // Testing for 'p' not in index 1
//        List<String> result = solver.filterGreyCharacters(greyChar, words);
//        List<String> expected = Arrays.asList("amaze", "angle", "alien", "alert");
//        assertEquals(expected, result, "FilterGreyCharacters test failed");
//    }
//
//    @Test
//    void testWordleSolver() {
//        HashMap<Character, List<Integer>> greenChar = new HashMap<>();
//        greenChar.put('a', Arrays.asList(0)); // 'a' at index 0
//
//        HashMap<Character, List<Integer>> yellowChar = new HashMap<>();
//        yellowChar.put('l', Arrays.asList(0, 1)); // 'l' not at index 0 or 1
//
//        HashMap<Character, List<Integer>> greyChar = new HashMap<>();
//        greyChar.put('e', Arrays.asList(2)); // 'e' not at index 2
//
//        List<String> result = solver.wordleSolver(greenChar, yellowChar, greyChar);
//        List<String> expected = Arrays.asList("apple", "angle");
//        assertEquals(expected, result, "WordleSolver test failed");
//    }


    @Test
    void testWordleSimulation() {
        String correctWord = solver.wordBank.get(random.nextInt(solver.wordBank.size()));
        List<String> previousGuesses = new ArrayList<>();
        boolean foundCorrectWord = false;

        System.out.println("Correct Word: " + correctWord);

        // Add the initial guess to previousGuesses
        previousGuesses.add(makeInitialGuess());

        for (int attempt = 0; attempt < 9 && !foundCorrectWord; attempt++) {
            HashMap<Character, List<Integer>> greenChar = new HashMap<>();
            HashMap<Character, List<Integer>> yellowChar = new HashMap<>();
            HashMap<Character, List<Integer>> greyChar = new HashMap<>();

            // Process all previous guesses including the initial guess
            for (String pastGuess : previousGuesses) {
                for (int i = 0; i < pastGuess.length(); i++) {
                    char guessChar = pastGuess.charAt(i);
                    if (correctWord.charAt(i) == guessChar) {
                        greenChar.computeIfAbsent(guessChar, k -> new ArrayList<>()).add(i);
                    } else if (correctWord.contains(String.valueOf(guessChar))) {
                        yellowChar.computeIfAbsent(guessChar, k -> new ArrayList<>()).add(i);
                    } else {
                        greyChar.computeIfAbsent(guessChar, k -> new ArrayList<>()).add(i);
                    }
                }
            }

            List<String> result = solver.wordleSolver(greenChar, yellowChar, greyChar);

            for (String possibleGuess : result) {
                if (!previousGuesses.contains(possibleGuess)) {
                    previousGuesses.add(possibleGuess);

                    // Debugging Information
                    System.out.println("New Guess: " + possibleGuess);
                    System.out.println("Green Characters: " + greenChar);
                    System.out.println("Yellow Characters: " + yellowChar);
                    System.out.println("Grey Characters: " + greyChar);
                    System.out.println("Solver Output: " + result);

                    if (possibleGuess.equals(correctWord)) {
                        foundCorrectWord = true; // Correct guess found
                    }
                    break;
                }
            }

            if (result.isEmpty()) {
                break; // No valid guesses left
            }
        }

        assertTrue(foundCorrectWord, "Failed to find the correct word in 9 attempts.");
    }


    private String makeInitialGuess() {
        return solver.wordBank.get(random.nextInt(solver.wordBank.size()));
    }
}
