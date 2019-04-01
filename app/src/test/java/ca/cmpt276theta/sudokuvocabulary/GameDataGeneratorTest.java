package ca.cmpt276theta.sudokuvocabulary;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ca.cmpt276theta.sudokuvocabulary.model.GameDataGenerator;

import static org.junit.Assert.fail;


public class GameDataGeneratorTest {
    private GameDataGenerator mGameDataGenerator;
    @Before
    public void setUp() {
        GameDataGenerator.loadPuzzleData();
        mGameDataGenerator = new GameDataGenerator();
    }

    @After
    public void tearDown() {
        mGameDataGenerator = null;
    }

    @Test
    public void loadPuzzleData() {
        Assert.assertNotEquals(null, GameDataGenerator.getSolvedPuzzle());
    }

    @Test
    public void getSolvedPuzzle() {
        int[][] solvedPuzzle = GameDataGenerator.getSolvedPuzzle();
        for(int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int currentCell = solvedPuzzle[i][j];
                for (int k = 0; k < 9; k++) {
                    if (k != j && solvedPuzzle[i][k] == currentCell)
                        fail();
                    if (k != i && solvedPuzzle[k][j] == currentCell)
                        fail();
                }
                int tempRow = i / 3 * 3;
                int tempCol = j / 3 * 3;
                for (int row = tempRow; row < tempRow + 3; row++)
                    for (int col = tempCol; col < tempCol + 3; col++)
                        if (row != i && col != j && solvedPuzzle[row][col] == currentCell)
                            fail();
            }
        }
    }
}
