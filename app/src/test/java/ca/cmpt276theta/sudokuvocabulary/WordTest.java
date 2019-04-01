package ca.cmpt276theta.sudokuvocabulary;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.cmpt276theta.sudokuvocabulary.model.Word;

import static org.junit.Assert.*;

public class WordTest {

    private Word myWord;

    @Before
    public void setUp() throws Exception {
        myWord = new Word("1", "2");
    }

    @After
    public void tearDown() throws Exception {
        myWord = null;
    }

    @Test
    public void getEnglish() {
        myWord.setEnglish("English");
        assertEquals(myWord.getEnglish(), "English");
    }

    @Test
    public void setEnglish() {
        myWord.setEnglish("English");
        assertEquals(myWord.getEnglish(), "English");
    }

    @Test
    public void getFrench() {
        myWord.setEnglish("French");
        assertEquals(myWord.getEnglish(), "French");
    }

    @Test
    public void setFrench() {
        myWord.setEnglish("French");
        assertEquals(myWord.getEnglish(), "French");
    }

    @Test
    public void getScore() {
        for (int i = 0; i < 100; i++) {
            myWord.setScore(i);
            assertEquals(myWord.getScore(), i);
        }
    }

    @Test
    public void setScore() {
        for (int i = 0; i < 100; i++) {
            myWord.setScore(i);
            assertEquals(myWord.getScore(), i);
        }
    }
}