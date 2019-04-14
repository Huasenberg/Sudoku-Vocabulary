package ca.cmpt276theta.sudokuvocabulary.model;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WordTest {
    private Word mWord;
    @Before
    public void setUp() throws Exception {
        mWord = new Word("mango", "mangue");
    }

    @After
    public void tearDown() throws Exception {
        mWord = null;
    }

    @Test
    public void getEnglish() {
        assertEquals("mango", mWord.getEnglish());
    }

    @Test
    public void setEnglish() {
        mWord.setEnglish("hello");
        assertEquals("hello", mWord.getEnglish());
    }

    @Test
    public void getFrench() {
        assertEquals("mangue", mWord.getFrench());
    }

    @Test
    public void setFrench() {
        mWord.setFrench("bonjour");
        assertEquals("bonjour", mWord.getFrench());
    }

    @Test
    public void getScore() {
        assertEquals(0, mWord.getScore());
    }

    @Test
    public void setScore() {
        mWord.setScore(20);
        assertEquals(20, mWord.getScore());
    }

    @Test
    public void addOneScore() {
        mWord.addOneScore();
        assertEquals(1, mWord.getScore());
    }


    @Test
    public void equals() {
        Word newWord = new Word("mango", "mangue");
        assertEquals(mWord, newWord);
        Word newWord2 = new Word("hello", "mangue");
        assertNotEquals(mWord, newWord2);
        assertNotEquals(mWord, null);
        assertEquals(mWord.getScore() + "        " + mWord.getEnglish() + " --- " + mWord.getFrench(), mWord.toString());
        mWord.setScore(20);
        assertEquals(mWord.getScore() + "      " + mWord.getEnglish() + " --- " + mWord.getFrench(), mWord.toString());
    }
}