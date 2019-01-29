package ca.cmpt276theta.sudokuvocabulary;

import android.util.Pair;

public class GameData {

    private static String[][] gridContent;
    private static GameView gameView;

    public GameData(GameView view) {
        gameView = view;
        gridContent = new String[9][9];
        int[][] puzzle = new int[][]{
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
        int[][] puzzle_answers = new int[][]{
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
        Pair<Integer, String> pair1 = new Pair<>(1, "Pomme");
        Pair<Integer, String> pair2 = new Pair<>(2, "Poir");
        Pair<Integer, String> pair3 = new Pair<>(3, "Fraise");
        Pair<Integer, String> pair4 = new Pair<>(4, "Ananans");
        Pair<Integer, String> pair5 = new Pair<>(5, "Banane");
        Pair<Integer, String> pair6 = new Pair<>(6, "Canneberge");
        Pair<Integer, String> pair7 = new Pair<>(7, "Figue");
        Pair<Integer, String> pair8 = new Pair<>(8, "Pêche");
        Pair<Integer, String> pair9 = new Pair<>(9, "Cerise");

        Pair mapping_array[] = { pair1, pair2, pair3, pair4, pair5,
                pair6, pair7, pair8, pair9 };

        for(int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if puzzle[i][j] != 0 {
                    for (int k = 0; k < 9; k++){
                        Pair<Integer, String> temp_pair = mapping_array[k];
                        int key = temp_pair.first;
                        if (puzzle[i][j] == key) {
                            gridContent[i][j] = temp_pair.second();
                        }
                    }
                }
                else{
                    gridContent[i][j] = " ";
                }
            }
        }
    }

    public static String getGridContent(int i, int j) {
        return gridContent[i][j];
    }

    public static void setGridContent(String word, int x, int y) {
        gridContent[x][y] = word;
    }
}
