package ca.cmpt276theta.sudokuvocabulary.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameDataGeneratorTest {

    @Test
    public void getUNITX() {
        GameDataGenerator.setSIZE(1,2);
        assertEquals(1, GameDataGenerator.getUNITX());
    }

    @Test
    public void getUNITY() {
        GameDataGenerator.setSIZE(1,2);
        assertEquals(2, GameDataGenerator.getUNITY());
    }

    @Test
    public void setflipped() {
        GameDataGenerator.setflipped(true);
        assertTrue(GameDataGenerator.isFlipped());
    }

    @Test
    public void getSolvedPuzzle() {
        assertNull(GameDataGenerator.getSolvedPuzzle());
        GameDataGenerator.loadPuzzleData();
        assertNotEquals(null, GameDataGenerator.getSolvedPuzzle());

    }

    @Test
    public void getSIZE() {
        GameDataGenerator.setSIZE(2,3);
        assertEquals(2, GameDataGenerator.getUNITX());
        assertEquals(3, GameDataGenerator.getUNITY());
        assertEquals(6, GameDataGenerator.getSIZE());
    }

    @Test
    public void loadPuzzleData() {
        GameDataGenerator.loadPuzzleData();
        assertNotEquals(null, GameDataGenerator.getSolvedPuzzle());
    }

    @Test
    public void setSIZE() {
        GameDataGenerator.setSIZE(2,2);
        assertEquals(2, GameDataGenerator.getUNITX());
        assertEquals(2, GameDataGenerator.getUNITY());
        assertEquals(4, GameDataGenerator.getSIZE());
    }

    @Test
    public void isFlipped() {
        GameDataGenerator.setflipped(false);
        assertFalse(GameDataGenerator.isFlipped());
    }
}