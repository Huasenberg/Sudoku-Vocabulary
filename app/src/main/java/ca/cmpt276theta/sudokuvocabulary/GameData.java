package ca.cmpt276theta.sudokuvocabulary;

import android.util.Pair;

import java.util.Random;

public class GameData {

    private Pair<Integer, String>[][] mGridContent;
    private Pair<Integer, String>[] mMappingArray;
    private Pair<Integer, String>[] mMappingArrayOfButton;
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
        mMappingArray = new Pair[9];
        mMappingArray[0] = new Pair<>(1, "mango");
        mMappingArray[1] = new Pair<>(2, "cherry");
        mMappingArray[2] = new Pair<>(3, "lemon");
        mMappingArray[3] = new Pair<>(4, "kiwi");
        mMappingArray[4] = new Pair<>(5, "orange");
        mMappingArray[5] = new Pair<>(6, "pear");
        mMappingArray[6] = new Pair<>(7, "apple");
        mMappingArray[7] = new Pair<>(8, "plum");
        mMappingArray[8] = new Pair<>(9, "peach");

        mMappingArrayOfButton = new Pair[9];
        mMappingArrayOfButton[0] = new Pair<>(1, "mangue");
        mMappingArrayOfButton[1] = new Pair<>(2, "cerise");
        mMappingArrayOfButton[2] = new Pair<>(3, "citron");
        mMappingArrayOfButton[3] = new Pair<>(4, "kiwi");
        mMappingArrayOfButton[4] = new Pair<>(5, "orange");
        mMappingArrayOfButton[5] = new Pair<>(6, "poire");
        mMappingArrayOfButton[6] = new Pair<>(7, "pomme");
        mMappingArrayOfButton[7] = new Pair<>(8, "prune");
        mMappingArrayOfButton[8] = new Pair<>(9, "pêche");


        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                if(random.nextInt(DIFFICULTY+1) == 0)
                    mPuzzle[i][j] = mPuzzleAnswer[i][j];
                if (mPuzzle[i][j] != 0)
                    mGridContent[i][j] = mMappingArray[mPuzzle[i][j] - 1];
                else
                    mGridContent[i][j] = new Pair<>(-1, " ");
            }
        }

    }

    public Pair<Integer, String> getGridContent(int i, int j) {
        return mGridContent[i][j];
    }

    public void setGridContent(Pair<Integer, String> pair, int i, int j) {
        mGridContent[i][j] = pair;
    }

    public int getPuzzle(int i, int j) {
        return mPuzzle[i][j];
    }

    public Pair<Integer, String> getMappingArray(int i) {
        return mMappingArray[i];
    }

    public Pair<Integer, String> getMappingArrayOfButton(int i) {
        return mMappingArrayOfButton[i];
    }
}
