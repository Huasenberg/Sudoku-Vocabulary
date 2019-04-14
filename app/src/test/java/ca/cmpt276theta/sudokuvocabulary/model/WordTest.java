package ca.cmpt276theta.sudokuvocabulary.model;

import android.support.annotation.NonNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WordTest {
    private Word word;
    @Before
    public void setUp() throws Exception {
        word = new Word("mango", "mangue");
    }

    @After
    public void tearDown() throws Exception {
        word = null;
    }

    @Test
    public void getEnglish() {
        assertEquals("mango", word.getEnglish());
    }

    @Test
    public void setEnglish() {
        word.setEnglish("hello");
        assertEquals("hello", word.getEnglish());
    }

    @Test
    public void getFrench() {
        assertEquals("mangue", word.getFrench());
    }

    @Test
    public void setFrench() {
        word.setFrench("bonjour");
        assertEquals("bonjour", word.getFrench());
    }

    @Test
    public void getScore() {
        assertEquals(0, word.getScore());
    }

    @Test
    public void setScore() {
        word.setScore(20);
        assertEquals(20, word.getScore());
    }

    @Test
    public void addOneScore() {
        word.addOneScore();
        assertEquals(1, word.getScore());
    }

    @NonNull
    @Test
    public String toString() {
        assertEquals(word.getScore() + "        " + word.getEnglish() + " --- " + word.getFrench(), word.toString());
        word.setScore(20);
        assertEquals(word.getScore() + "      " + word.getEnglish() + " --- " + word.getFrench(), word.toString());
        return null;
    }

    @Test
    public void equals() {
        Word newWord = new Word("mango", "mangue");
        assertEquals(word, newWord);
        Word newWord2 = new Word("hello", "mangue");
        assertNotEquals(word, newWord2);
        assertNotEquals(word, null);
    }
}