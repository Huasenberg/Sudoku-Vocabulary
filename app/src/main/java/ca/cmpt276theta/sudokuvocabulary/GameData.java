package ca.cmpt276theta.sudokuvocabulary;

import android.util.Pair;

public class GameData {

    private Pair<String, String>[][] mGridContent;
    private Pair<String, String>[] mMappingArray;
    private int[][] mPuzzle;
    private int[][] mPuzzleAnswer;

    public GameData() {

        mGridContent = new Pair[9][9];
        mPuzzle = new int[][]{
                {0, 7, 0, 0, 4, 0, 0, 0, 9},
                {2, 0, 0, 0, 0, 3, 0, 8, 0},
                {8, 0, 5, 9, 0, 0, 0, 0, 4},
                {0, 6, 0, 0, 0, 0, 8, 4, 0},
                {0, 0, 0, 0, 3, 0, 2, 9, 0},
                {0, 8, 4, 6, 9, 0, 0, 0, 0},
                {7, 0, 6, 4, 0, 0, 0, 0, 0},
                {0, 0, 3, 0, 0, 5, 1, 0, 8},
                {0, 5, 0, 0, 0, 9, 0, 7, 0}
        };

        // Puzzle with the answers
        mPuzzleAnswer = new int[][]{
                {6, 7, 1, 2, 4, 8, 3, 5, 9},
                {2, 4, 9, 5, 7, 3, 6, 8, 1},
                {8, 3, 5, 9, 1, 6, 7, 2, 4},
                {9, 6, 2, 1, 5, 7, 8, 4, 3},
                {5, 1, 7, 8, 3, 4, 2, 9, 6},
                {3, 8, 4, 6, 9, 2, 5, 1, 7},
                {7, 2, 6, 4, 8, 1, 9, 3, 5},
                {4, 9, 3, 7, 2, 5, 1, 6, 8},
                {1, 5, 8, 3, 6, 9, 4, 7, 2}
        };

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


