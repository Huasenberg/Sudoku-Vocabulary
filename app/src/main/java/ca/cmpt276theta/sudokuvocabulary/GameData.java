package ca.cmpt276theta.sudokuvocabulary;

import android.util.Pair;

import java.util.Random;

public class GameData {

    private Pair<String, String>[][] mGridContent;
    private Pair<String, String>[] mMappingArray;
    private int[][] mPuzzle;
    private int[][] mPuzzleAnswer;

    public GameData() {
        Random random = new Random();
        int DIFFICULTY = 5;
        mGridContent = new Pair[9][9];
        mPuzzle = new int[9][9];

        // Puzzle with the answers
        mPuzzleAnswer = Generator.generateSolved();

        // Pairing the words with numbers
        mMappingArray = new Pair [9];
        mMappingArray[0] = new Pair<>("1", "mango");
        mMappingArray[1] = new Pair<>("2", "cherry");
        mMappingArray[2] = new Pair<>("3", "lemon");
        mMappingArray[3] = new Pair<>("4", "kiwi");
        mMappingArray[4] = new Pair<>("5", "orange");
        mMappingArray[5] = new Pair<>("6", "pear");
        mMappingArray[6] = new Pair<>("7", "apple");
        mMappingArray[7] = new Pair<>("8", "plum");
        mMappingArray[8] = new Pair<>("9", "peach");

        for(int x = 0; x < mPuzzle.length; x++)
        {
            for(int y = 0; y < mPuzzle[x].length; y++)
            {
                if(random.nextInt(DIFFICULTY+1) == 0) {
                    mPuzzle[x][y] = mPuzzleAnswer[x][y];
                }
            }
        }

        final Pair<String, String> nullPair = new Pair<>(" ", " ");

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (mPuzzle[i][j] != 0)
                    mGridContent[i][j] = mMappingArray[mPuzzle[i][j] - 1];
                else {
                    mGridContent[i][j] = nullPair;
                }
            }
        }
    }

    public Pair<String, String> getGridContent(int i, int j) {
        return mGridContent[i][j];
    }

    public void setGridContent(Pair<String, String> pair, int i, int j) {
        mGridContent[i][j] = pair;
    }
}


