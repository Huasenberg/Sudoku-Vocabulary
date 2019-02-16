package ca.cmpt276theta.sudokuvocabulary;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class GameMain {

    private int mPositionX;
    private int mPositionY;
    private GameData mGameData;
    private GameView mGameView;
    private int mEmptyCellCounter;
    private final MediaPlayer mp;
    private PopupWindow mPopupWindow;
    private Chronometer mTimer;
    private TextView mTime;

    public GameMain(GameView view, int mode, PopupWindow popupWindow, Chronometer timer, TextView time) {
        mGameView = view;
        mGameData = new GameData(mode);
        mEmptyCellCounter = mGameData.getEmptyCellCounter();
        this.mTimer = timer;
        this.mTime = time;
        this.mPopupWindow = popupWindow;
        mp = MediaPlayer.create(view.getContext(), R.raw.tada);
    }

    public GameData getGameData() {
        return mGameData;
    }


    public void fillWord(Button button) {
        Pair<Integer, String> buttonContent = new Pair<>(Integer.parseInt((String)button.getTag()), (String)button.getText());
        mPositionX = mGameView.getTouchPositionX();
        mPositionY = mGameView.getTouchPositionY();
        if(mPositionX < 0 || mPositionX > 8 || mPositionY < 0 || mPositionY > 8)
            return;
        else if(mGameData.getPuzzle()[mPositionY][mPositionX] != 0) {
            Toast toast = Toast.makeText(mGameView.getContext(), "Can't fill in pre-filled cell", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0,0);
            View view = toast.getView();
            view.setBackgroundResource(R.drawable.button_shape);
            TextView text = view.findViewById(android.R.id.message);
            text.setTextSize(17);
            text.setTextColor(Color.WHITE);
            toast.show();
            return;
        }
        else if(mGameData.getGridContent()[mPositionY][mPositionX].first.equals(-1))
            mEmptyCellCounter--;
        mGameData.setGridContent(buttonContent, mPositionY, mPositionX);
        mGameView.invalidate();
        if(mEmptyCellCounter == 0)
            checkGameResult();
    }

    public void checkGameResult() {
        for(int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int currentCell = mGameData.getGridContent()[i][j].first;
                for (int k = 0; k < 9; k++) {
                    if (k != j && mGameData.getGridContent()[i][k].first == currentCell)
                        return;
                    if (k != i && mGameData.getGridContent()[k][j].first == currentCell)
                        return;
                }
                int tempRow = i / 3 * 3;
                int tempCol = j / 3 * 3;
                for (int row = tempRow; row < tempRow + 3; row++)
                    for (int col = tempCol; col < tempCol + 3; col++)
                        if (row != i && col != j && mGameData.getGridContent()[row][col].first == currentCell)
                            return;
            }
        }
        mTimer.stop();
        showVicPopup(mTimer.getText().toString());
        if(mp != null)
            mp.start();
    }

    public void showVicPopup(String time) {
        final TextView difficulty = mPopupWindow.getContentView().findViewById(R.id.difficulty);
        difficulty.setText(String.format(mGameView.getResources().getString(R.string.difficulty), mGameData.DIFFICULTY));
        mTime.setText(time);
        mPopupWindow.showAtLocation(mGameView, Gravity.CENTER, 0, 0);
    }

}
