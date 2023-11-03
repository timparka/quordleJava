import org.example.Solver;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.*;

public class SolverTest {
    private Solver solver;
    private List<String> wordBank;

    @Before
    public void setUp() {
            wordBank = Arrays.asList("apple", "grape", "bread", "mouse", "crown", "chair", "zebra", "bored");
            solver = new Solver(wordBank);
    }

    @Test
    public void greyLetterTest() {
        List<Character> greyLetters = Arrays.asList('l');
        List<Integer> validCharPositions = Arrays.asList();
        List<Integer> charWrongIndex = Arrays.asList();

        //apple should be filtered out of the list
        List<String> result = solver.filterWordList(0, "", validCharPositions, charWrongIndex, greyLetters);
        assertFalse(result.contains("apple"));
        assertEquals(6, result.size());
        assertTrue(result.contains("grape"));
    }

    @Test
    public void validCharTest() {
        List<Character> greyLetters = Arrays.asList();
        List<Integer> validCharPositions = Arrays.asList(0); // 'b' is in the correct position
        List<Integer> charWrongIndexPositions = Arrays.asList();

        // All words start with 'b', but let's say our guess is "bored" and check that it returns a valid word
        List<String> result = solver.quorateSuggester(0, "bored", validCharPositions, charWrongIndexPositions, greyLetters);
        assertTrue(result.contains("bread"));
        assertFalse(result.contains("bored"));
    }

    @Test
    public void indexTest() {
        List<Character> greyLetters = Arrays.asList();
        List<Integer> validCharPositions = Arrays.asList();
        List<Integer> charWrongIndexPositions = Arrays.asList(1); // 'o' is in the word but not in position 1

        // Since 'o' should not be at index 1, "bored" and "mouse" should be filtered out
        List<String> result = solver.quorateSuggester(0, "mouse", validCharPositions, charWrongIndexPositions, greyLetters);
        assertFalse(result.contains("bored"));
        assertFalse(result.contains("mouse"));
        assertTrue(result.contains("crown")); //should contain crown as 'o' is in a different index
    }


}
