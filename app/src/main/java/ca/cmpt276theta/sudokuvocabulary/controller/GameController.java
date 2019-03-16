package ca.cmpt276theta.sudokuvocabulary.controller;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import ca.cmpt276theta.sudokuvocabulary.R;
import ca.cmpt276theta.sudokuvocabulary.model.GameData;
import ca.cmpt276theta.sudokuvocabulary.model.WordList;
import ca.cmpt276theta.sudokuvocabulary.view.GameView;
import ca.cmpt276theta.sudokuvocabulary.view.MainMenuActivity;

public class GameController {

    private final MediaPlayer mp;
    private final GameData mGameData;
    private final GameView mGameView;
    private final PopupWindow mPopupWindow;
    private final Chronometer mTimer;
    private final TextView mTime;

    public GameController(GameData gameData, GameView view, PopupWindow popupWindow, Chronometer timer, TextView time) {
        mGameView = view;
        mGameData = gameData;
        this.mTimer = timer;
        mTimer.start();
        this.mTime = time;
        this.mPopupWindow = popupWindow;
        mp = MediaPlayer.create(view.getContext(), R.raw.tada);
    }

    public GameData getGameData() {
        return mGameData;
    }

    public void fillWord(Button button) {
        final int positionX = mGameView.getTouchPositionX();
        final int positionY = mGameView.getTouchPositionY();
        if(positionX < 0 || positionX > 8 || positionY < 0 || positionY > 8)
            return;
        if(mGameData.getPuzzlePreFilled()[positionY][positionX] != 0) {
            showMessageToast(mGameView.getContext(), "  Can't fill in pre-filled cell  ");
            final Animation shake = AnimationUtils.loadAnimation(mGameView.getContext(), R.anim.button_shake);
            button.startAnimation(shake);
            return;
        }
        if(mGameData.getPuzzle()[positionY][positionX] == 0)
            mGameData.setEmptyCellCounter(mGameData.getEmptyCellCounter() - 1);
        mGameData.getPuzzle()[positionY][positionX] = Integer.valueOf(button.getTag().toString());
        mGameData.getGridContent()[positionY][positionX] = (String) button.getText();
        mGameView.invalidate();
        if(mGameData.getEmptyCellCounter() == 0)
            checkGameResult();
    }

    private void checkGameResult() {
        for(int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int currentCell = mGameData.getPuzzle()[i][j];
                for (int k = 0; k < 9; k++) {
                    if (k != j && mGameData.getPuzzle()[i][k] == currentCell)
                        return;
                    if (k != i && mGameData.getPuzzle()[k][j] == currentCell)
                        return;
                }
                int tempRow = i / 3 * 3;
                int tempCol = j / 3 * 3;
                for (int row = tempRow; row < tempRow + 3; row++)
                    for (int col = tempCol; col < tempCol + 3; col++)
                        if (row != i && col != j && mGameData.getPuzzle()[row][col] == currentCell)
                            return;
            }
        }
        mTimer.stop();
        mPopupWindow.setAnimationStyle(R.style.pop_animation);
        showVicPopup();
        if(mp != null)
            mp.start();
    }

    private void showVicPopup() {
        final TextView difficulty = mPopupWindow.getContentView().findViewById(R.id.difficulty);
        difficulty.setText(String.format(mGameView.getResources().getString(R.string.difficulty), GameData.getDifficulty()));
        mTime.setText(mTimer.getText().toString());
        mPopupWindow.showAtLocation(mGameView, Gravity.CENTER, 0, 0);
    }

    public static void showMessageToast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0,0);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.button_shape);
        TextView text = view.findViewById(android.R.id.message);
        text.setTextSize(17);
        text.setTextColor(Color.WHITE);
        toast.show();
    }

    public static void writeToArrayList(Context context, BufferedReader reader) {
        String line = "";
        try {
            // Step over headers
            reader.readLine();

            // If buffer is not empty
            while ((line = reader.readLine()) != null) {
                Log.d("My Activity","Line: " + line);
                // use comma as separator columns of CSV
                String[] tokens = line.split(",");
                // Read the data
                Word sample = new Word();

                // Setters
                sample.setEnglish(tokens[1]);
                sample.setFrench(tokens[2]);
                sample.setScore(Integer.parseInt(tokens[3]));

                // Adding object to a class
                CheckBox checkBox = new CheckBox(context);
                checkBox.setText(sample.toString());
                checkBox.setTextSize(16);
                MainMenuActivity.getCheckBoxes().add(checkBox);
                WordList.getOriginalWordList().add(sample);
                // Log the object
                Log.d("My Activity", "Just created: " + sample);
            }

        } catch (IOException e) {
            // Logs error with priority level
            Log.d("My Activity", "Error reading data file on line" + line, e);
            // Prints throwable details
            e.printStackTrace();
        }
    }
}
