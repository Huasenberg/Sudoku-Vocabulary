package ca.cmpt276theta.sudokuvocabulary.model;

import java.util.Random;

public class GameDataGenerator {
//    private static final int UNIT = 3;
    private static final int UNITX = 2;
    private static final int UNITY = 2;
    private static final int SIZE = UNITX * UNITY;
    private static final int MAX_SHUFFLE = 20;
    private static int[][] sSolvedPuzzle;

    public static int[][] getSolvedPuzzle() {
        return sSolvedPuzzle;
    }

    public static void loadPuzzleData() {
        sSolvedPuzzle = generateSolved();
    }

    private static int[][] generateSolved() {
        int[][] array = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++) //}
//                int i = 0;
//        array[0][2] = 1;
                array[i][j] = (i * UNITY + i / UNITX + j) % SIZE + 1;
//            }
        Random random = new Random();
        int limit = random.nextInt(MAX_SHUFFLE);
        for (int i = 0; i < limit; i++) {
            if (isPerfectSquare(SIZE)){ if (random.nextBoolean()) transpose(array);}
            if (random.nextBoolean()) shuffleSquareRows(array);
            if (random.nextBoolean()) shuffleSingleRows(array);
//            if (random.nextBoolean() shuffleSquareCols(array));
        }
        return array;
    }

    static boolean isPerfectSquare(double x)
    {

        // Find floating point value of
        // square root of x.
        double sr = Math.sqrt(x);

        // If square root is an integer
        return ((sr - Math.floor(sr)) == 0);
    }

    /**
     * Transposes the given square matrix/2D array.
     *
     * @param array The array to be transposed.
     */
    private static void transpose(int[][] array) {
        System.out.println("array.length = "+array.length);
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
    private static void shuffleSquareRows(int[][] array) {
        Random random = new Random();
        for (int i = 0; i < UNITY - 1; i++) {
            int j = 1 + i + random.nextInt(UNITY - 1 - i);
            swapSquareRows(array, i, j);
        }
    }

//
    /**
     * Shuffles single rows within each square row.
     *
     * @param array The array to be transformed.
     */
    private static void shuffleSingleRows(int[][] array) {
        Random random = new Random();
        for (int i = 0; i < UNITY; i++) {
            int start = i * UNITX;
            int limit = start + UNITX - 1;
            for (int j = start; j < limit; j++) {
                int k = start + 1 + random.nextInt(limit - j);
                swapSingleRows(array, j, k);
            }
        }
    }
//
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
    private static void swapSquareRows(int[][] array, int i, int j) {
        //if (i == j) return;
        int[][] temp = new int[UNITX][SIZE];
        int iStart = i * UNITX;
        int jStart = j * UNITX;
        int iLimit = iStart + UNITX;
        int jLimit = jStart + UNITX;
        System.out.println("i = " + i);
        System.out.println("j = " + j);

        System.out.println("iStart = " + iStart);
        System.out.println("jStart = " + jStart);
        System.out.println("iLimit = " + iLimit);
        System.out.println("jLimit = " + jLimit);
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
}
