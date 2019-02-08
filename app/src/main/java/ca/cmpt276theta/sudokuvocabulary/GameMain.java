package ca.cmpt276theta.sudokuvocabulary;

import android.media.MediaPlayer;
import android.util.Pair;
import android.view.Gravity;
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

    public void fillWord(Button button) {
        Pair<Integer, String> buttonContent = new Pair<>(Integer.parseInt((String)button.getTag()), (String)button.getText());
        mPositionX = mGameView.getTouchPositionX();
        mPositionY = mGameView.getTouchPositionY();
        if(mPositionX < 0 || mPositionX > 8 || mPositionY < 0 || mPositionY > 8)
            return;
        else if(mGameData.getPuzzle()[mPositionY][mPositionX] != 0) {
            Toast.makeText(mGameView.getContext(), "Can't fill in pre-filled cell", Toast.LENGTH_SHORT).show();
            return;
        }
        mGameData.setGridContent(buttonContent, mPositionY, mPositionX);
        mGameView.invalidate();
    }

    public void checkGameResult() {
    Toast not_completed = Toast.makeText(mGameView.getContext(),
            "You filled in a wrong word or the game is not completed.",
            Toast.LENGTH_SHORT);
    not_completed.setGravity(Gravity.CENTER,0,0);
        for(int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int currentCell = mGameData.getGridContent()[i][j].first;
                for (int k = 0; k < 9; k++) {
                    if (k != j && mGameData.getGridContent()[i][k].first == currentCell) {
                        //Toast.makeText(mGameView.getContext(), "You filled in the wrong word or the game is not completed.", Toast.LENGTH_SHORT).show();
                        not_completed.show();
                        return;
                    }
                    if (k != i && mGameData.getGridContent()[k][j].first == currentCell) {
                        //Toast.makeText(mGameView.getContext(), "You filled in the wrong word or the game is not completed.", Toast.LENGTH_SHORT).show();
                        not_completed.show();
                        return;
                    }
                }
                int tempRow = i / 3 * 3;
                int tempCol = j / 3 * 3;
                for (int row = tempRow; row < tempRow + 3; row++)
                    for (int col = tempCol; col < tempCol + 3; col++)
                        if (row != i && col != j && mGameData.getGridContent()[row][col].first == currentCell) {
                            //Toast.makeText(mGameView.getContext(), "You filled in the wrong words or the game is not completed.", Toast.LENGTH_SHORT).show();
                            not_completed.show();
                            return;
                        }
            }
        }
        Toast success = Toast.makeText(mGameView.getContext(),
                "Congratulations! You Win!",
                Toast.LENGTH_SHORT);
        success.setGravity(Gravity.CENTER, 0,0);
        success.show();
        //mp.start();
    }
}
