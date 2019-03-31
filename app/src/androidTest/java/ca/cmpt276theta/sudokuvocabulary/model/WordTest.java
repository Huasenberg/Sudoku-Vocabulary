package ca.cmpt276theta.sudokuvocabulary.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WordTest {

    private Word myWord;

    @Before
    public void setUp() throws Exception {
        myWord = new Word();
    }

    @After
    public void tearDown() throws Exception {
        myWord = null;
    }

    @Test
    public void getWordID() {
        for (int i = 0; i < 100; i++) {
            myWord.setWordID(i);
            assertEquals(myWord.getWordID(), i);
        }
    }

    @Test
    public void setWordID() {
        for (int i = 0; i <100; i++) {
            myWord.setWordID(i);
            assertEquals(myWord.getWordID(), i);
        }
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