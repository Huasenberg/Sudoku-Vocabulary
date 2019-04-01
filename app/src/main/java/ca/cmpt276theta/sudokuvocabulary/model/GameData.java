package ca.cmpt276theta.sudokuvocabulary.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameData implements Parcelable {
    public static final Parcelable.Creator<GameData> CREATOR = new Parcelable.Creator<GameData>() {
        @Override
        public GameData createFromParcel(Parcel source) {
            return new GameData(source);
        }

        @Override
        public GameData[] newArray(int size) {
            return new GameData[size];
        }
    };
    private static List<String> sLanguagesList;
    private static int sDifficulty;
    private static int sLanguageMode;
    private static boolean sIsListenMode = false;
    private static int gridSize;
    private static int subGridSizeHori;
    private static int subGridSizeVerti;
    private int mEmptyCellCounter;
    private String[][] mGridContent;
    private String[] mLanguageA;
    private String[] mLanguageB;
    private int[][] mPuzzle;
    private int[][] mPuzzleAnswer;
    private int[][] mPuzzlePreFilled;

    public GameData() {
        gridSize = GameDataGenerator.getSIZE();
        subGridSizeHori = GameDataGenerator.getUNITX();
        subGridSizeVerti = GameDataGenerator.getUNITY();
        ArrayList<Word> wordlist = WordList.getSelectedWordList();
        mEmptyCellCounter = 0;
        mGridContent = new String[gridSize][gridSize];
        mPuzzle = new int[gridSize][gridSize];
        mPuzzlePreFilled = new int[gridSize][gridSize];
        // Puzzle with the answers
        mPuzzleAnswer = GameDataGenerator.getSolvedPuzzle();

        String[] wordBank1 = {"mango", "cherry", "lemon", "kiwi", "orange", "pear", "apple", "plum", "peach", "1", "2", "3"};
        String[] wordBank2 = {"mangue", "cerise", "citron", "kiwi", "orange", "poire", "pomme", "prune", "pêche", "1", "2", "3"};

        // Pairing the words with numbers


        if (!wordlist.isEmpty())
            for (int i = 0; i < gridSize; i++) {
                wordBank1[i] = wordlist.get(i).getEnglish();
                wordBank2[i] = wordlist.get(i).getFrench();
            }
        mLanguageA = wordBank1;
        mLanguageB = wordBank2;
        if (getLanguageMode() == 1)
            switchLanguage();
        generateIncompletePuzzle();
    }

    private GameData(Parcel in) {
        this.mEmptyCellCounter = in.readInt();
        for (int i = 0; i < gridSize; i++) {
            in.readStringArray(this.mGridContent[i]);
            in.readIntArray(this.mPuzzle[i]);
            in.readIntArray(this.mPuzzlePreFilled[i]);
        }
    }

    public static int getSubGridSizeHori() {
        return subGridSizeHori;
    }

    public static int getSubGridSizeVerti() {
        return subGridSizeVerti;
    }

    public static int getGridSize() {
        return gridSize;
    }

    public static void setGridSize(int gridSize) {
        GameData.gridSize = gridSize;
    }

    public static boolean isListenMode() {
        return sIsListenMode;
    }

    public static void setListenMode(boolean isListenMode) {
        sIsListenMode = isListenMode;
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

    public static int getLanguageMode() {
        return sLanguageMode;
    }

    public static void setLanguageMode(int languageMode) {
        sLanguageMode = languageMode;
    }

    public static String getLanguageMode_String() {
        return sLanguagesList.get(sLanguageMode);
    }

    public static void loadLanguagesList() {
        sLanguagesList = new ArrayList<>();
        sLanguagesList.add("English - Français");
        sLanguagesList.add("Français - English");
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

    public void setEmptyCellCounter(int emptyCellCounter) {
        mEmptyCellCounter = emptyCellCounter;
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

    private void switchLanguage() {
        String[] temp = mLanguageA;
        mLanguageA = mLanguageB;
        mLanguageB = temp;
    }

    public void generateIncompletePuzzle() {
        Random random = new Random();
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (random.nextInt(4) > sDifficulty) {
                    mPuzzle[i][j] = mPuzzleAnswer[i][j];
                    mPuzzlePreFilled[i][j] = mPuzzle[i][j];
                }
                if (mPuzzle[i][j] != 0)
                    if (sIsListenMode)
                        mGridContent[i][j] = String.valueOf(mPuzzle[i][j]);
                    else
                        mGridContent[i][j] = mLanguageA[mPuzzle[i][j] - 1];
                else {
                    mGridContent[i][j] = " ";
                    mEmptyCellCounter++;
                }
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mEmptyCellCounter);
        for (int i = 0; i < gridSize; i++) {
            dest.writeStringArray(this.mGridContent[i]);
            dest.writeIntArray(this.mPuzzle[i]);
            dest.writeIntArray(this.mPuzzlePreFilled[i]);
        }
    }

    public void removeAllCells() {
        mEmptyCellCounter = 0;
        for (int i = 0; i < gridSize; i++)
            for (int j = 0; j < gridSize; j++) {
                mPuzzle[i][j] = mPuzzlePreFilled[i][j];
                if (mPuzzle[i][j] != 0)
                    mGridContent[i][j] = mLanguageA[mPuzzle[i][j] - 1];
                else {
                    mGridContent[i][j] = " ";
                    mEmptyCellCounter++;
                }
            }
    }

    public void removeOneCell(int positionX, int positionY) {
        mPuzzle[positionY][positionX] = 0;
        mGridContent[positionY][positionX] = " ";
        mEmptyCellCounter++;
    }
}
