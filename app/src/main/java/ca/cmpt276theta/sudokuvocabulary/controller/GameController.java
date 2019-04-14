package ca.cmpt276theta.sudokuvocabulary.controller;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import ca.cmpt276theta.sudokuvocabulary.R;
import ca.cmpt276theta.sudokuvocabulary.model.GameData;
import ca.cmpt276theta.sudokuvocabulary.model.GameSettings;
import ca.cmpt276theta.sudokuvocabulary.view.HighlightView;
import ca.cmpt276theta.sudokuvocabulary.view.WordView;

class GameController {

    private final MediaPlayer mp;
    private final GameData mGameData;
    private final HighlightView mHighlightViewView;
    private final WordView mWordView;
    private final PopupWindow mPopupWindow;
    private final Chronometer mTimer;
    private final TextView mTime;
    private final int gridSize;
    private final int subGridSizeHori;
    private final int subGridSizeVerti;

    GameController(GameData gameData, HighlightView view, WordView wordView, PopupWindow popupWindow, Chronometer timer, TextView time) {
        mHighlightViewView = view;
        mGameData = gameData;
        mWordView = wordView;
        this.mTimer = timer;
        mTimer.start();
        this.mTime = time;
        this.mPopupWindow = popupWindow;
        gridSize = gameData.getGridSize();
        mp = MediaPlayer.create(view.getContext(), R.raw.tada);
        subGridSizeHori = gameData.getSubGridSizeHori();
        subGridSizeVerti = gameData.getSubGridSizeVerti();
    }

    static void showMessageToast(Context context, String message, int gravity) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        if (gravity != Gravity.NO_GRAVITY)
            toast.setGravity(gravity, 0, 0);
        View view = toast.getView();
        view.getBackground().setTint(context.getResources().getColor(R.color.colorPrimary));
        TextView text = view.findViewById(android.R.id.message);
        text.setTextSize(18);
        text.setTextColor(Color.WHITE);
        toast.show();
    }

    GameData getGameData() {
        return mGameData;
    }

    void fillWord(Button button) {
        final int positionX = mHighlightViewView.getTouchPositionX();
        final int positionY = mHighlightViewView.getTouchPositionY();
        if (positionX < 0 || positionX > (gridSize - 1) || positionY < 0 || positionY > (gridSize - 1))
            return;
        if (mGameData.getPuzzlePreFilled()[positionY][positionX] != 0) {
            showMessageToast(mHighlightViewView.getContext(), "Can't fill in pre-filled cell", Gravity.CENTER);
            final Animation shake = AnimationUtils.loadAnimation(mHighlightViewView.getContext(), R.anim.button_shake_anim);
            button.startAnimation(shake);
            return;
        }
        if (mGameData.getPuzzle()[positionY][positionX] == 0)
            mGameData.setEmptyCellCounter(mGameData.getEmptyCellCounter() - 1);
        mGameData.getPuzzle()[positionY][positionX] = Integer.valueOf(button.getTag().toString());
        mGameData.getGridContent()[positionY][positionX] = (String) button.getText();
        mWordView.invalidate();
        mHighlightViewView.invalidate();
        if (mGameData.getEmptyCellCounter() == 0)
            checkGameResult();
    }

    private void checkGameResult() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                int currentCell = mGameData.getPuzzle()[i][j];
                for (int k = 0; k < gridSize; k++) {
                    if (k != j && mGameData.getPuzzle()[i][k] == currentCell)
                        return;
                    if (k != i && mGameData.getPuzzle()[k][j] == currentCell)
                        return;
                }

                int tempRow = i / subGridSizeVerti * subGridSizeVerti;
                int tempCol = j / subGridSizeHori * subGridSizeHori;
                for (int row = tempRow; row < tempRow + subGridSizeVerti; row++)
                    for (int col = tempCol; col < tempCol + subGridSizeHori; col++)
                        if (row != i && col != j && mGameData.getPuzzle()[row][col] == currentCell)
                            return;
            }
        }
        mTimer.stop();
        showVicPopup();
        if (mp != null && GameSettings.isSoundOpen())
            mp.start();
    }

    private void showVicPopup() {
        final TextView difficulty = mPopupWindow.getContentView().findViewById(R.id.difficulty);
        final TextView langMode = mPopupWindow.getContentView().findViewById(R.id.game_mode);
        difficulty.setText(String.format(mHighlightViewView.getResources().getString(R.string.difficulty), mGameData.getDifficulty()));
        final String string = mGameData.isListenMode() ? "Listening" : "Reading";
        langMode.setText("Game Mode: " + string);
        mTime.setText("Time: " + mTimer.getText().toString());
        mGameData.setTimeEnd(SystemClock.elapsedRealtime()-mTimer.getBase());
        mPopupWindow.showAtLocation(mHighlightViewView, Gravity.CENTER, 0, 0);
    }
}
