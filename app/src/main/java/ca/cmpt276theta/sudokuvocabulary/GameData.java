package ca.cmpt276theta.sudokuvocabulary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameData implements Serializable {
    private int mEmptyCellCounter;
    private String[][] mGridContent;
    private String[] mLanguageA;
    private String[] mLanguageB;
    private int[][] mPuzzle;
    private int[][] mPuzzleAnswer;
    private int[][] mPuzzlePreFilled;
    private static List<String> sLanguagesList = new ArrayList<>();
    private static int sDifficulty;

    public GameData(int mode) {
        mEmptyCellCounter = 0;
        mGridContent = new String[9][9];
        mPuzzle = new int[9][9];
        mPuzzlePreFilled = new int[9][9];
        // Puzzle with the answers
        mPuzzleAnswer = GameDataGenerator.getSolvedPuzzle();
        // Pairing the words with numbers
        String[] wordBank1 = {"mango", "cherry", "lemon", "kiwi", "orange", "pear", "apple", "plum", "peach"};
        String[] wordBank2 = {"mangue", "cerise", "citron", "kiwi", "orange", "poire", "pomme", "prune", "pêche"};
        mLanguageA = wordBank1;
        mLanguageB = wordBank2;
        if(mode == 2)
            switchLanguage();
        generateIncompletePuzzle();
    }

    public void setEmptyCellCounter(int emptyCellCounter) {
        mEmptyCellCounter = emptyCellCounter;
    }

    public String[] getLanguageA() {
        return mLanguageA;
    }

    public String[] getLanguageB() {
        return mLanguageB;
    }

    public int getEmptyCellCounter() {
        return mEmptyCellCounter;
    }

    public String[][] getGridContent() {
        return mGridContent;
    }

    public int[][] getPuzzlePreFilled() {
        return mPuzzlePreFilled;
    }

    public int[][] getPuzzle() {
        return mPuzzle;
    }

    public static List<String> getLanguagesList() {
        return sLanguagesList;
    }

    public static int getDifficulty() {
        return sDifficulty;
    }

    public static void setDifficulty(int difficulty) {
        sDifficulty = difficulty;
    }

    public void switchLanguage() {
        String[] temp = mLanguageA;
        mLanguageA = mLanguageB;
        mLanguageB = temp;
    }

    private void generateIncompletePuzzle() {
        Random random = new Random();
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                if(random.nextInt(7) > sDifficulty) {
                    mPuzzle[i][j] = mPuzzleAnswer[i][j];
                    mPuzzlePreFilled[i][j] = mPuzzle[i][j];
                }
                if(mPuzzle[i][j] != 0)
                    mGridContent[i][j] = mLanguageA[mPuzzle[i][j] - 1];
                else {
                    mGridContent[i][j] = " ";
                    mEmptyCellCounter++;
                }
            }
        }
    }

    public static void loadLanguagesList() {
        sLanguagesList.add("English - Français");
        sLanguagesList.add("Français - English");
    }
}
