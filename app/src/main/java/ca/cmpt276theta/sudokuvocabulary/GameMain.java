package ca.cmpt276theta.sudokuvocabulary;

import android.widget.Button;
import android.widget.Toast;


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

    public void fillWord(Button button) {
        Pair<Integer, String> buttonContent = new Pair<>(Integer.parseInt((String)button.getTag()), (String)button.getText());
        mPositionX = mGameView.getTouchPositionX();
        mPositionY = mGameView.getTouchPositionY();
        if(mPositionX < 0 || mPositionX > 8 || mPositionY < 0 || mPositionY > 8)
            return;
        else if(mGameData.getPuzzle(mPositionY, mPositionX) != 0) {
            Toast.makeText(mGameView.getContext(), "Can't fill in pre-filled cell", Toast.LENGTH_SHORT).show();
            return;
        }
        mGameData.setGridContent(buttonContent, mPositionY, mPositionX);
        mGameView.invalidate();
    }


}
