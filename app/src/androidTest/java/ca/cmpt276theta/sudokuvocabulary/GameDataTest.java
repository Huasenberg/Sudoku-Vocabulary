package ca.cmpt276theta.sudokuvocabulary;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GameDataTest {

    private GameData myClass;

    @Before
    public void setUp() {
        myClass = new GameData();
    }

    @After
    public void tearDown() {
        myClass = null;
    }

    @Test
    public void setEmptyCellCounter() {
        myClass = new GameData();
        myClass.setEmptyCellCounter(0);
        Assert.assertEquals(0, myClass.getEmptyCellCounter());
    }

    @Test
    public void getEmptyCellCounter() {
        for (int i = 0; i < 82; i++) {
            myClass.setEmptyCellCounter(i);
            int counter = myClass.getEmptyCellCounter();
            Assert.assertEquals(counter, i);
        }
    }

    @Test
    public void getGridContent() {
        myClass.generateIncompletePuzzle();
        String [][] grid = myClass.getGridContent();
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
            myClass.setDifficulty(i);
            Assert.assertEquals(i, myClass.getDifficulty());
        }
    }

    @Test
    public void setDifficulty() {
        for (int i = 0; i < 5; i++) {
            myClass.setDifficulty(i);
            Assert.assertEquals(i, myClass.getDifficulty());
        }
    }

    @Test
    public void getLanguageMode() {
        for (int i = 0; i < 2; i++) {
            myClass.setLanguageMode(i);
            Assert.assertEquals(0, myClass.getLanguageMode());
        }
    }

    @Test
    public void setLanguageMode() {
        for (int i = 0; i < 2; i++) {
            myClass.setLanguageMode(i);
            Assert.assertEquals(0, myClass.getLanguageMode());
        }
    }

    @Test
    public void getLanguageMode_String() {
        for (int i = 0; i < 2; i++) {
            if (i == 0){
                myClass.setLanguageMode(i);
                Assert.assertEquals("English - Français", myClass.getLanguageMode_String());
            }
            else {
                myClass.setLanguageMode(i);
                Assert.assertEquals("Français -English", myClass.getLanguageMode_String());
            }
        }
    }

    @Test
    public void loadLanguagesList() {
        List<String> myList = new ArrayList();
        myList = (ArrayList) myClass.getLanguagesList();
        Assert.assertEquals("English - Français", myList.get(0));
        Assert.assertEquals("Français - English", myList.get(1));
    }

    @Test
    public void describeContents() {
        Assert.assertEquals(myClass.describeContents(), 0);
    }

    @Test
    public void writeToParcel() {
    }

    @Test
    public void removeOneCell() {
        int[][] puzzle = myClass.getPuzzle();
        for (int i = 0; i < 9 ; i++) {
            for (int j = 0; j < 9 ; j++) {
                if (puzzle[i][j] != 0){
                    myClass.removeOneCell(i, j);
                    int cellContent = (myClass.getPuzzle())[i][j];
                    String gridContent = (myClass.getGridContent())[i][j];
                    Assert.assertEquals(0, cellContent);
                    Assert.assertEquals(" ", gridContent);
                }
            }
        }
    }

    @Test
    public void generateIncompletePuzzle(){
        myClass.generateIncompletePuzzle();
        assertNotEquals(0, myClass.getEmptyCellCounter());
        assertNotEquals(81, myClass.getEmptyCellCounter());
        Assert.assertTrue(myClass.getEmptyCellCounter() >= 17);
        Assert.assertTrue(myClass.getEmptyCellCounter() < 81);
        int[][] puzzle = myClass.getPuzzle();
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
        String [][] grid = myClass.getGridContent();
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