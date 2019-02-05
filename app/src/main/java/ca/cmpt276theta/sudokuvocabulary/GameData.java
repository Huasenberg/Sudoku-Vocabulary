package ca.cmpt276theta.sudokuvocabulary;

import android.util.Pair;

import java.util.Random;

public class GameData {

    private Pair<Integer, String>[][] mGridContent;
    private Pair<Integer, String>[] mLanguageA;
    private Pair<Integer, String>[] mLanguageB;
    private int[][] mPuzzle;
    private int[][] mPuzzleAnswer;

    public GameData(int mode) {
        Random random = new Random();
        int DIFFICULTY = 4;
        mGridContent = new Pair[9][9];
        mPuzzle = new int[9][9];

        // Puzzle with the answers
        mPuzzleAnswer = GameDataGenerator.generateSolved();

        // Pairing the words with numbers
        Pair<Integer, String>[] wordBank1 = new Pair[9];
        wordBank1[0] = new Pair<>(1, "mango");
        wordBank1[1] = new Pair<>(2, "cherry");
        wordBank1[2] = new Pair<>(3, "lemon");
        wordBank1[3] = new Pair<>(4, "kiwi");
        wordBank1[4] = new Pair<>(5, "orange");
        wordBank1[5] = new Pair<>(6, "pear");
        wordBank1[6] = new Pair<>(7, "apple");
        wordBank1[7] = new Pair<>(8, "plum");
        wordBank1[8] = new Pair<>(9, "peach");
        mLanguageA = wordBank1;
        Pair<Integer, String>[] wordBank2 = new Pair[9];
        wordBank2[0] = new Pair<>(1, "mangue");
        wordBank2[1] = new Pair<>(2, "cerise");
        wordBank2[2] = new Pair<>(3, "citron");
        wordBank2[3] = new Pair<>(4, "kiwi");
        wordBank2[4] = new Pair<>(5, "orange");
        wordBank2[5] = new Pair<>(6, "poire");
        wordBank2[6] = new Pair<>(7, "pomme");
        wordBank2[7] = new Pair<>(8, "prune");
        wordBank2[8] = new Pair<>(9, "pêche");
        mLanguageB = wordBank2;
        if(mode==2)
            switchLanguage();
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                if(random.nextInt(DIFFICULTY+1) == 0)
                    mPuzzle[i][j] = mPuzzleAnswer[i][j];
                if (mPuzzle[i][j] != 0)
                    mGridContent[i][j] = mLanguageA[mPuzzle[i][j] - 1];
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

    public Pair<Integer, String> getLanguageA(int i) {
        return mLanguageA[i];
    }

    public Pair<Integer, String> getLanguageB(int i) {
        return mLanguageB[i];
    }

    public void switchLanguage() {
        Pair<Integer, String>[] temp = mLanguageA;
        mLanguageA = mLanguageB;
        mLanguageB = temp;
    }
}


