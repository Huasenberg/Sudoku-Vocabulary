package ca.cmpt276theta.sudokuvocabulary.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GameDataTest {
    private GameData mGameData;

    @Before
    public void setUp() throws Exception {
        GameDataGenerator.setSIZE(2,2);
        List<Word> wordList = new ArrayList<>();
        wordList.add(new Word("mango","mangue"));
        wordList.add(new Word("cherry", "cerise"));
        wordList.add(new Word("pear","poire"));
        wordList.add(new Word("apple", "pomme"));
        WordList.setSelectedWordList(wordList);
        GameDataGenerator.loadPuzzleData();
        mGameData = new GameData(1, false, 1);
        GameData.loadLanguagesList();
    }

    @After
    public void tearDown() throws Exception {
        mGameData = null;
    }

    @Test
    public void getLanguagesList() {
        assertEquals("English - Français", GameData.getLanguagesList().get(0));
        assertEquals("Français - English", GameData.getLanguagesList().get(1));
    }

    @Test
    public void loadLanguagesList() {
        assertEquals("English - Français", GameData.getLanguagesList().get(0));
        assertEquals("Français - English", GameData.getLanguagesList().get(1));
    }

    @Test
    public void getSavedTime() {
        mGameData.setSavedTime("Time");
        assertEquals("Time", mGameData.getSavedTime());
    }

    @Test
    public void setSavedTime() {
        mGameData.setSavedTime("Time");
        assertEquals("Time", mGameData.getSavedTime());
    }

    @Test
    public void getSavedTimeInterval() {
        mGameData.setSavedTimeInterval(0);
        assertEquals(0, mGameData.getSavedTimeInterval());
    }

    @Test
    public void setSavedTimeInterval() {
        mGameData.setSavedTimeInterval(1);
        assertEquals(1, mGameData.getSavedTimeInterval());
    }

    @Test
    public void getSubGridSizeHori() {
        assertEquals(2, mGameData.getSubGridSizeHori());
    }

    @Test
    public void getSubGridSizeVerti() {
        assertEquals(2, mGameData.getSubGridSizeVerti());
    }

    @Test
    public void getGridSize() {
        assertEquals(4, mGameData.getGridSize());
    }

    @Test
    public void isListenMode() {
        assertFalse(mGameData.isListenMode());
    }

    @Test
    public void getDifficulty() {
        assertEquals(1, mGameData.getDifficulty());
    }

    @Test
    public void getLanguageMode() {
        assertEquals(1, mGameData.getLanguageMode());
    }

    @Test
    public void getLanguageMode_String() {
        assertEquals("Français - English", mGameData.getLanguageMode_String());
    }

    @Test
    public void getLanguageA() {
        String[] strings = {"mangue","cerise", "poire", "pomme"};
        assertArrayEquals(strings, mGameData.getLanguageA());
    }

    @Test
    public void getLanguageB() {
        String[] strings = {"mango","cherry", "pear", "apple"};
        assertArrayEquals(strings, mGameData.getLanguageB());
    }

    @Test
    public void getEmptyCellCounter() {
        mGameData.setEmptyCellCounter(20);
        assertEquals(20, mGameData.getEmptyCellCounter());
    }

    @Test
    public void setEmptyCellCounter() {
        mGameData.setEmptyCellCounter(10);
        assertEquals(10, mGameData.getEmptyCellCounter());
    }

    @Test
    public void getGridContent() {
    }

    @Test
    public void getPuzzlePreFilled() {
        assertArrayEquals(mGameData.getPuzzle(), mGameData.getPuzzlePreFilled());
    }

    @Test
    public void getPuzzle() {
        assertArrayEquals(mGameData.getPuzzlePreFilled(), mGameData.getPuzzle());
    }

    @Test
    public void generateIncompletePuzzle() {
        assertNotEquals(0, mGameData.getEmptyCellCounter());
    }

    @Test
    public void describeContents() {
    }

    @Test
    public void writeToParcel() {
    }

    @Test
    public void removeAllCells() {
        int count = mGameData.getEmptyCellCounter();
        mGameData.removeAllCells();
        assertEquals(count, mGameData.getEmptyCellCounter());
    }

    @Test
    public void removeOneCell() {
        int count = mGameData.getEmptyCellCounter();
        count++;
        mGameData.removeOneCell(0,0);
        assertEquals(count, mGameData.getEmptyCellCounter());
    }
}