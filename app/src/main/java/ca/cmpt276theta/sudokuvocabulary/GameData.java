package ca.cmpt276theta.sudokuvocabulary;

public class GameData {

    private static String[][] gridContent;
    private static GameView gameView;

    public GameData(GameView view) {
        gameView = view;
        gridContent = new String[9][9];
        for(int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                gridContent[i][j] = "apple";
                gridContent[i][i] = "kiwi";
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
