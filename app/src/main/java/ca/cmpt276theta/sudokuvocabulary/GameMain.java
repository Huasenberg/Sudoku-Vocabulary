package ca.cmpt276theta.sudokuvocabulary;

import android.content.Context;
import android.util.Pair;
import android.widget.Button;
import android.widget.Toast;

public class GameMain {

    private int mPositionX;
    private int mPositionY;
    private GameData mGameData;
    private GameView mGameView;
    private Context context;

    public GameMain(GameView view) {
        mGameView = view;
        mGameData = new GameData();
    }

    public GameData getGameData() {
        return mGameData;
    }

    public int getPositionX() {
        return mPositionX;
    }

    public void setPositionX(int x) {
        mPositionX = x;
    }

    public int getPostionY() {
        return mPositionY;
    }

    public void setPositionY(int y) {
        mPositionY = y;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void fillWord(Button button) {
        Pair<String, String> buttonContent = new Pair<>((String)button.getTag(), (String)button.getText());
        mPositionX = mGameView.getTouchPositionX();
        mPositionY = mGameView.getTouchPositionY();
        if(mPositionX < 0 || mPositionX > 8 || mPositionY < 0 || mPositionY > 8)
            return;
        else if(mGameData.getmPuzzle(mPositionY, mPositionX) != 0) {
            Toast.makeText(context, "Can't fill in pre-filled cell", Toast.LENGTH_SHORT).show();
            return;
        }
        mGameData.setGridContent(buttonContent, mPositionY, mPositionX);
        mGameView.invalidate();
    }
}
