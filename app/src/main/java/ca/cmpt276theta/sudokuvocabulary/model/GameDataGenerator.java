package ca.cmpt276theta.sudokuvocabulary.model;

import java.util.Random;

public class GameDataGenerator {
    private static final int MAX_SHUFFLE = 200;
    //    private static final int UNIT = 3;
    private static int UNITX;
    private static int UNITY;
    private static int SIZE;
    private static int[][] sSolvedPuzzle;
    private static boolean flipped = false;

    public static int getUNITX() {
        return UNITX;
    }

    public static int getUNITY() {
        return UNITY;
    }

    static void setflipped(final boolean flip) {
        GameDataGenerator.flipped = flip;
    }

    static int[][] getSolvedPuzzle() {
        return sSolvedPuzzle;
    }

    public static int getSIZE() {
        return SIZE;
    }

    public static void loadPuzzleData() {
        sSolvedPuzzle = generateSolved();
    }

    public static void setSIZE(final int x, final int y) {
        GameDataGenerator.UNITX = x;
        GameDataGenerator.UNITY = y;
        GameDataGenerator.SIZE = x * y;
    }

    private static int[][] generateSolved() {
        int[][] array = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++) {
                array[i][j] = (i * UNITX + i / UNITY + j) % SIZE + 1; // this is for rows of 3 and columns of 2
            }
        Random random = new Random();
        int limit = random.nextInt(MAX_SHUFFLE);
        for (int i = 0; i < limit; i++) {
            if (random.nextBoolean())
                transpose(array);
            if (flipped) {
                if (random.nextBoolean()) shuffleSquareRows(array, UNITY, UNITX);
                if (random.nextBoolean()) shuffleSingleRows(array, UNITY, UNITX);
            } else {
                if (random.nextBoolean()) shuffleSquareRows(array, UNITX, UNITY);
                if (random.nextBoolean()) shuffleSingleRows(array, UNITX, UNITY);
            }
        }
        return array;
    }

    /**
     * Transposes the given square matrix/2D array.
     *
     * @param array The array to be transposed.
     */
    private static void transpose(int[][] array) {
        flipped = !flipped;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < i; j++) {
                int temp = array[i][j];
                array[i][j] = array[j][i];
                array[j][i] = temp;
            }
        }
    }
//

    /**
     * Shuffles square rows in their entirety, i.e. moves 3 rows at a time.
     *
     * @param array The array to be transformed.
     */

    private static void shuffleSquareRows(int[][] array, int width, int height) {
        Random random = new Random();
        for (int i = 0; i < width - 1; i++) {
            int j = 1 + i + random.nextInt(width - 1 - i);
            swapSquareRows(array, i, j, height);
        }
    }

    /**
     * Shuffles single rows within each square row.
     *
     * @param array The array to be transformed.
     */

    private static void shuffleSingleRows(int[][] array, int x, int y) {
        Random random = new Random();
        for (int i = 0; i < x; i++) {
            int start = i * y;
            int limit = start + y - 1;
            for (int j = start; j < limit; j++) {
                int k = start + 1 + random.nextInt(limit - j);
                swapSingleRows(array, j, k);
            }
        }
    }

    /**
     * Swaps two rows within a square.
     *
     * @param array The array to be transformed.
     * @param i     The first row.
     * @param j     The second row.
     */
    private static void swapSingleRows(int[][] array, int i, int j) {
        int[] temp = new int[SIZE];
        System.arraycopy(array[i], 0, temp, 0, SIZE);
        System.arraycopy(array[j], 0, array[i], 0, SIZE);
        System.arraycopy(temp, 0, array[j], 0, SIZE);
    }

    /**
     * Swaps 6 rows at a time. Swaps the ith square row with the jth one.
     *
     * @param array The array to be transformed.
     * @param i     The first row.
     * @param j     The second row.
     */
    private static void swapSquareRows(int[][] array, int i, int j, int height) {
        //if (i == j) return;
        int[][] temp = new int[height][SIZE];
        int iStart = i * height;
        int jStart = j * height;
        int iLimit = iStart + height;
        int jLimit = jStart + height;

        // copy to temp
        for (int k = iStart, l = 0; k < iLimit; k++, l++) {
            System.arraycopy(array[k], 0, temp[l], 0, SIZE);
        }
        // copy to array[i] & following
        for (int k = iStart, l = jStart; k < iLimit; k++, l++) {
            System.arraycopy(array[l], 0, array[k], 0, SIZE);
        }
        // copy to array[j] & following
        for (int k = jStart, l = 0; k < jLimit; k++, l++) {
            System.arraycopy(temp[l], 0, array[k], 0, SIZE);
        }
    }

    static boolean isFlipped() {
        return flipped;
    }
}
