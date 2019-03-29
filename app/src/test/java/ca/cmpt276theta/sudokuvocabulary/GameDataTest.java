package ca.cmpt276theta.sudokuvocabulary;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import ca.cmpt276theta.sudokuvocabulary.model.Word;
import ca.cmpt276theta.sudokuvocabulary.model.GameData;
import ca.cmpt276theta.sudokuvocabulary.model.GameDataGenerator;
import static org.junit.Assert.*;

public class GameDataTest {

    private GameData mGameData;

    @Before
    public void setUp() {
        GameDataGenerator.loadPuzzleData();
        GameData.setWordlist(new ArrayList<Word>());
        mGameData = new GameData();
    }

    @After
    public void tearDown() {
        mGameData = null;
    }

    @Test
    public void setEmptyCellCounter() {
        mGameData.setEmptyCellCounter(0);
        Assert.assertEquals(0, mGameData.getEmptyCellCounter());
    }

    @Test
    public void getEmptyCellCounter() {
        for (int i = 0; i < 82; i++) {
            mGameData.setEmptyCellCounter(i);
            int counter = mGameData.getEmptyCellCounter();
            Assert.assertEquals(counter, i);
        }
    }

    @Test
    public void getGridContent() {
        mGameData.generateIncompletePuzzle();
        String [][] grid = mGameData.getGridContent();
        boolean contentExists = false;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!grid[i][j].equals(" ")){
                    contentExists = true;
                }
            }
        }
        Assert.assertTrue(contentExists);
    }

    @Test
    public void getDifficulty() {
        for (int i = 0; i < 5; i++) {
            GameData.setDifficulty(i);
            Assert.assertEquals(i, GameData.getDifficulty());
        }
    }

    @Test
    public void setDifficulty() {
        for (int i = 0; i < 5; i++) {
            GameData.setDifficulty(i);
            Assert.assertEquals(i, GameData.getDifficulty());
        }
    }

    @Test
    public void getLanguageMode() {
        for (int i = 0; i < 2; i++) {
            GameData.setLanguageMode(i);
            Assert.assertEquals(i, GameData.getLanguageMode());
        }
    }

    @Test
    public void setLanguageMode() {
        for (int i = 0; i < 2; i++) {
            GameData.setLanguageMode(i);
            Assert.assertEquals(i, GameData.getLanguageMode());
        }
    }

    @Test
    public void getLanguageMode_String() {
        for (int i = 0; i < 2; i++) {
            if (i == 0){
                GameData.setLanguageMode(i);
                Assert.assertEquals("English - Français", GameData.getLanguageMode_String());
            }
            else {
                GameData.setLanguageMode(i);
                Assert.assertEquals("Français - English", GameData.getLanguageMode_String());
            }
        }
    }

    @Test
    public void loadLanguagesList() {
        GameData.loadLanguagesList();
        List<String> myList = GameData.getLanguagesList();
        Assert.assertEquals("English - Français", myList.get(0));
        Assert.assertEquals("Français - English", myList.get(1));
    }

    @Test
    public void describeContents() {
        Assert.assertEquals(mGameData.describeContents(), 0);
    }

    @Test
    public void writeToParcel() {
    }

    @Test
    public void removeOneCell() {
        int[][] puzzle = mGameData.getPuzzle();
        for (int i = 0; i < 9 ; i++) {
            for (int j = 0; j < 9 ; j++) {
                if (puzzle[i][j] != 0){
                    mGameData.removeOneCell(i, j);
                    int cellContent = mGameData.getPuzzle()[j][i];
                    String gridContent = mGameData.getGridContent()[j][i];
                    Assert.assertEquals(0, cellContent);
                    Assert.assertEquals(" ", gridContent);
                }
            }
        }
    }

    @Test
    public void generateIncompletePuzzle(){
        assertNotEquals(0, mGameData.getEmptyCellCounter());
        assertNotEquals(81, mGameData.getEmptyCellCounter());
        assertTrue(mGameData.getEmptyCellCounter() < 81);
        int[][] puzzle = mGameData.getPuzzle();
        boolean not_empty = false;
        int counter = 0;
        for (int i = 0; i < 9 ; i++) {
            for (int j = 0; j < 9; j++) {
                if (puzzle[i][j] != 0){
                    not_empty = true;
                    counter += 1;
                }
            }
        }
        Assert.assertTrue(not_empty);
        assertNotEquals(81, counter);
        String [][] grid = mGameData.getGridContent();
        boolean not_empty2 = false;
        int counter2 = 0;
        for (int i = 0; i < 9 ; i++) {
            for (int j = 0; j < 9; j++) {
                if (!grid[i][j].equals(" ")){
                    not_empty2 = true;
                    counter2 += 1;
                }
            }
        }
        Assert.assertTrue(not_empty2);
        assertNotEquals(81, counter2);
    }
}
