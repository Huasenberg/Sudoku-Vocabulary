package ca.cmpt276theta.sudokuvocabulary;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.cmpt276theta.sudokuvocabulary.model.GameDataGenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameDataGeneratorTest {

    private GameDataGenerator myGDG;

    @Before
    public void setUp() {
        myGDG = new GameDataGenerator();
    }

    @After
    public void tearDown() {
        myGDG = null;
    }

    @Test
    public void getUNITX() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                GameDataGenerator.setSIZE(i, j);
                assertEquals(GameDataGenerator.getUNITX(), i);
            }
        }
    }

    @Test
    public void getUNITY() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                GameDataGenerator.setSIZE(i, j);
                assertEquals(GameDataGenerator.getUNITY(), j);
            }
        }
    }


    @Test
    public void getSIZE() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                GameDataGenerator.setSIZE(i, j);
                assertEquals(GameDataGenerator.getSIZE(), i * j);
            }
        }
    }

    @Test
    public void loadPuzzleData() {
    }

    @Test
    public void setSIZE() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                GameDataGenerator.setSIZE(i, j);
                assertEquals(GameDataGenerator.getSIZE(), i * j);
            }
        }
    }

    @Test
    public void isPerfectSquare() {
        int square;
        for (int i = 0; i < 100; i++) {
            square = i * i;
            assertTrue(GameDataGenerator.isPerfectSquare(square));
        }
    }

    @Test
    public void getSolvedPuzzle() {

    }
}
