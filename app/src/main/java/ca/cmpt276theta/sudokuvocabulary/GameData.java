package ca.cmpt276theta.sudokuvocabulary;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameData implements Parcelable {
    private int mEmptyCellCounter;
    private String[][] mGridContent;
    private String[] mLanguageA;
    private String[] mLanguageB;
    private int[][] mPuzzle;
    private int[][] mPuzzleAnswer;
    private int[][] mPuzzlePreFilled;
    private static List<String> sLanguagesList;
    private static int sDifficulty;
    private static int sLanguageMode;

    GameData() {
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
        if(getLanguageMode() == 1)
            switchLanguage();
        generateIncompletePuzzle();
    }

    void setEmptyCellCounter(int emptyCellCounter) {
        mEmptyCellCounter = emptyCellCounter;
    }

    String[] getLanguageA() {
        return mLanguageA;
    }

    String[] getLanguageB() {
        return mLanguageB;
    }

    int getEmptyCellCounter() {
        return mEmptyCellCounter;
    }

    String[][] getGridContent() {
        return mGridContent;
    }

    int[][] getPuzzlePreFilled() {
        return mPuzzlePreFilled;
    }

    int[][] getPuzzle() {
        return mPuzzle;
    }

    static List<String> getLanguagesList() {
        return sLanguagesList;
    }

    static int getDifficulty() {
        return sDifficulty;
    }

    static void setDifficulty(int difficulty) {
        sDifficulty = difficulty;
    }

    private static int getLanguageMode() {
        return sLanguageMode;
    }

    static void setLanguageMode(int languageMode) {
        sLanguageMode = languageMode;
    }

    static String getLanguageMode_String() {
        return sLanguagesList.get(sLanguageMode);
    }

    private void switchLanguage() {
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

    static void loadLanguagesList() {
        sLanguagesList = new ArrayList<>();
        sLanguagesList.add("English - Français");
        sLanguagesList.add("Français - English");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mEmptyCellCounter);
        for(int i = 0; i < 9; i++) {
            dest.writeStringArray(this.mGridContent[i]);
            dest.writeIntArray(this.mPuzzle[i]);
            dest.writeIntArray(this.mPuzzlePreFilled[i]);
        }
    }

    private GameData(Parcel in) {
        this.mEmptyCellCounter = in.readInt();
        for(int i = 0; i < 9; i++) {
            in.readStringArray(this.mGridContent[i]);
            in.readIntArray(this.mPuzzle[i]);
            in.readIntArray(this.mPuzzlePreFilled[i]);
        }
    }

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

    public void removeAllCells() {
        mEmptyCellCounter = 0;
        for(int i = 0; i < 9; i++)
            for(int j = 0; j < 9; j++) {
                mPuzzle[i][j] = mPuzzlePreFilled[i][j];
                if(mPuzzle[i][j] != 0)
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
