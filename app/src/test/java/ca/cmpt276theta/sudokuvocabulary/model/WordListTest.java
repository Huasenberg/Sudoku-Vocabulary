package ca.cmpt276theta.sudokuvocabulary.model;


import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class WordListTest {

    @Test
    public void getSelectedWordList() {
        List<Word> wordList = new ArrayList<>();
        wordList.add(new Word("mango", "mangue"));
        WordList.setSelectedWordList(wordList);
        assertEquals(wordList, WordList.getSelectedWordList());
    }

    @Test
    public void setSelectedWordList() {
        List<Word> wordList = new ArrayList<>();
        wordList.add(new Word("mango", "mangue"));
        WordList.setSelectedWordList(wordList);
        assertEquals(wordList, WordList.getSelectedWordList());
    }

    @Test
    public void getOriginalWordList() {
        List<Word> wordList = new ArrayList<>();
        wordList.add(new Word("mango", "mangue"));
        WordList.setOriginalWordList(wordList);
        assertEquals(wordList, WordList.getOriginalWordList());
    }

    @Test
    public void setOriginalWordList() {
        List<Word> wordList = new ArrayList<>();
        wordList.add(new Word("mango", "mangue"));
        WordList.setOriginalWordList(wordList);
        assertEquals(wordList, WordList.getOriginalWordList());
    }

    @Test
    public void sortWordDataByScore() {
        List<Word> wordList = new ArrayList<>();
        wordList.add(new Word("mango", "mangue"));
        wordList.add(new Word("apple", "pomme"));
        WordList.setOriginalWordList(wordList);
        WordList.sortWordDataByAlphbet();
        assertEquals(new Word("apple", "pomme"), WordList.getOriginalWordList().get(0));
    }

    @Test
    public void sortWordDataByAlphbet() {
        List<Word> wordList = new ArrayList<>();
        Word word1 = new Word("mango", "mangue");
        word1.setScore(20);
        Word word2 = new Word("apple", "pomme");
        word2.setScore(1);
        wordList.add(word1);
        wordList.add(word2);
        WordList.setOriginalWordList(wordList);
        WordList.sortWordDataByScore();
        assertEquals(word1, WordList.getOriginalWordList().get(0));
    }
}