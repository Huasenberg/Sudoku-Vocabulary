package ca.cmpt276theta.sudokuvocabulary.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameData implements Parcelable, Serializable {
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
    private int mDifficulty;
    private int mLanguageMode;
    private boolean mIsListenMode;
    private int mGridSize;
    private int mSubGridSizeHori;
    private int mSubGridSizeVerti;
    private int mEmptyCellCounter;
    private String[][] mGridContent;
    private String[] mLanguageA;
    private String[] mLanguageB;
    private int[][] mPuzzle;
    private int[][] mPuzzleAnswer;
    private int[][] mPuzzlePreFilled;
    private long savedTimeInterval;
    private String savedTime;
    long endTime = 0;
    public GameData(int languageMode, boolean isListenMode, int difficulty) {
        mLanguageMode = languageMode;
        mIsListenMode = isListenMode;
        mDifficulty = difficulty;
        mGridSize = GameDataGenerator.getSIZE();
        if (GameDataGenerator.isFlipped()) {
            mSubGridSizeHori = GameDataGenerator.getUNITY();
            mSubGridSizeVerti = GameDataGenerator.getUNITX();
            GameDataGenerator.setflipped(false);
        } else {
            mSubGridSizeHori = GameDataGenerator.getUNITX();
            mSubGridSizeVerti = GameDataGenerator.getUNITY();
        }
        List<Word> wordList = WordList.getSelectedWordList();
        mEmptyCellCounter = 0;
        mGridContent = new String[mGridSize][mGridSize];
        mPuzzle = new int[mGridSize][mGridSize];
        mPuzzlePreFilled = new int[mGridSize][mGridSize];
        // Puzzle with the answers
        mPuzzleAnswer = GameDataGenerator.getSolvedPuzzle();

        String[] wordBank1 = new String[mGridSize];
        String[] wordBank2 = new String[mGridSize];

        // Pairing the words with numbers


        for (int i = 0; i < mGridSize; i++) {
            wordBank1[i] = wordList.get(i).getEnglish();
            wordBank2[i] = wordList.get(i).getFrench();
        }
        mLanguageA = wordBank1;
        mLanguageB = wordBank2;
        if (getLanguageMode() == 1)
            switchLanguage();


        /*int random_word[] = new int[gridSize];
        int iCount;
        if (!wordlist.isEmpty()) {
            for (iCount = 0; iCount < gridSize; iCount++) {
                random_word[iCount] = random_weighted_scores(wordlist);
                //System.out.println("randomlist" + Arrays.toString(random_word));
            }
            for (iCount = 0; iCount < gridSize; iCount++) {
                wordBank1[iCount] = wordlist.get(random_word[iCount]).getEnglish();
                wordBank2[iCount] = wordlist.get(random_word[iCount]).getFrench();
                //System.out.println("USING"+wordlist.get(iCount).getEnglish());
            }
        }
        mLanguageA = wordBank1;
        mLanguageB = wordBank2;
        if(getLanguageMode() == 1)
            switchLanguage();*/
        generateIncompletePuzzle();
    }

    /*private ArrayList<Integer> usedIndex = new ArrayList<>();
    private int random_weighted_scores(ArrayList<Word> wordlist) {
        int iCount;
        final int min = 1;
        final int max_score = max_score(wordlist);
        //System.out.println("MAX" + max_score);
        int target = new Random().nextInt((max_score - min) + 1) + min;
        //System.out.println("RANDOM" + target);
        int random_word = 0;

        for (iCount = 0; iCount < wordlist.size(); iCount++) {
            if (target <= wordlist.get(iCount).getScore() && !usedIndex.contains(iCount)) {
                //System.out.println("choosing"+wordlist.get(iCount).getScore());
                random_word = iCount;
                usedIndex.add(iCount);
                //System.out.println("1RANDOM_WORD"+random_word);
                return random_word;
            } else {
                target -= wordlist.get(iCount).getScore();
            }
        }
        for (iCount = 0; iCount < wordlist.size(); iCount++) {
            if(!usedIndex.contains(iCount)) {
                return iCount;
            }
        }
        //System.out.println("2RANDOM_WORD"+random_word);
        return random_word;
    }

    private int max_score(ArrayList<Word> wordlist) {
        int max_score = 0;
        int iCount;
        for (iCount = 0; iCount < wordlist.size(); iCount++) {
            max_score += wordlist.get(iCount).getScore();
        }
        return max_score;
    }*/

    private GameData(Parcel in) {
        this.mEmptyCellCounter = in.readInt();
        for (int i = 0; i < mGridSize; i++) {
            in.readStringArray(this.mGridContent[i]);
            in.readIntArray(this.mPuzzle[i]);
            in.readIntArray(this.mPuzzlePreFilled[i]);
        }
    }

    public static List<String> getLanguagesList() {
        return sLanguagesList;
    }

    public static void loadLanguagesList() {
        sLanguagesList = new ArrayList<>();
        sLanguagesList.add("English - Français");
        sLanguagesList.add("Français - English");
    }

    public String getSavedTime() {
        return savedTime;
    }

    public void setSavedTime(String savedTime) {
        this.savedTime = savedTime;
    }

    public long getSavedTimeInterval() {
        return savedTimeInterval;
    }

    public void setSavedTimeInterval(long savedTimeInterval) {
        this.savedTimeInterval = savedTimeInterval;
    }

    public int getSubGridSizeHori() {
        return mSubGridSizeHori;
    }

    public int getSubGridSizeVerti() {
        return mSubGridSizeVerti;
    }

    public int getGridSize() {
        return mGridSize;
    }

    public boolean isListenMode() {
        return mIsListenMode;
    }

    public int getDifficulty() {
        return mDifficulty;
    }

    public void setDifficulty(int difficulty) {
        mDifficulty = difficulty;
    }

    public int getLanguageMode() {
        return mLanguageMode;
    }

    public void setLanguageMode(int languageMode) {
        mLanguageMode = languageMode;
    }

    public String getLanguageMode_String() {
        if (mLanguageMode == 0)
            return "English - Français";
        else
            return "Français - English";
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
        for (int i = 0; i < mGridSize; i++) {
            for (int j = 0; j < mGridSize; j++) {
                if (random.nextInt(7) > mDifficulty) {
                    mPuzzle[i][j] = mPuzzleAnswer[i][j];
                    mPuzzlePreFilled[i][j] = mPuzzle[i][j];
                }
                if (mPuzzle[i][j] != 0)
                    if (mIsListenMode)
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
        for (int i = 0; i < mGridSize; i++) {
            dest.writeStringArray(this.mGridContent[i]);
            dest.writeIntArray(this.mPuzzle[i]);
            dest.writeIntArray(this.mPuzzlePreFilled[i]);
        }
    }

    public void removeAllCells() {
        mEmptyCellCounter = 0;
        for (int i = 0; i < mGridSize; i++)
            for (int j = 0; j < mGridSize; j++) {
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

    public void setTimeEnd(long l) {
        endTime = l;
    }
    public long getTimeEnd() {
        return endTime;
    }
}
