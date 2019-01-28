package ca.cmpt276theta.sudokuvocabulary;

import android.widget.Button;


public class GameMain {

    private static int positionX;
    private static int positionY;
    private static GameView gameView;
    private GameData mGameData;

    public GameMain(GameView view) {
        mGameData = new GameData(view);
        gameView = view;
        positionX = -1;
        positionY = -1;
    }


    public static int getPositionX() {
        return positionX;
    }

    public static void setPositionX(int x) {
        positionX = x;
    }

    public static int getPostionY() {
        return positionY;
    }

    public static void setPositionY(int y) {
        positionY = y;
    }

    public static void fillWord(Button button) {

        if(positionX < 0 || positionX > 8 || positionY < 0 || positionY > 8)
            return;
        GameData.setGridContent((String)button.getText(), positionY, positionX);
        gameView.invalidate();
    }


}
