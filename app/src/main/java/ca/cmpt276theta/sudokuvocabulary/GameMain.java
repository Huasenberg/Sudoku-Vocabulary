package ca.cmpt276theta.sudokuvocabulary;

import android.media.MediaPlayer;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class GameMain {

    private int mPositionX;
    private int mPositionY;
    private GameData mGameData;
    private GameView mGameView;
    private final MediaPlayer mp;
    public GameMain(GameView view, int mode) {
        mGameView = view;
        mGameData = new GameData(mode);
        mp = MediaPlayer.create(view.getContext(), R.raw.tada);
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
