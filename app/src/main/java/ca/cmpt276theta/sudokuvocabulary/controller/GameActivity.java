package ca.cmpt276theta.sudokuvocabulary.controller;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import ca.cmpt276theta.sudokuvocabulary.R;
import ca.cmpt276theta.sudokuvocabulary.model.GameData;
import ca.cmpt276theta.sudokuvocabulary.view.GameView;

public class GameActivity extends AppCompatActivity {

    private GameData mGameData;
    private Chronometer mTimer;
    private PopupWindow mPopupWindow;
    private GameView mGameView;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable("gameData", mGameData);
        long timeInterval = SystemClock.elapsedRealtime() - mTimer.getBase();
        savedInstanceState.putLong("timeInterval", timeInterval);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPopupWindow.isShowing())
            mPopupWindow.dismiss();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            startActivity(new Intent(GameActivity.this, MainMenuActivity.class));
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        final TextView textView = findViewById(R.id.game_title);
        textView.setText(GameData.getLanguageMode_String());
        final FrameLayout gameLayout = findViewById(R.id.gameLayout);
        mGameData = new GameData();
        mGameView = new GameView(this);

        // Set Timer
        mTimer = findViewById(R.id.chronometer1);
        mTimer.setBase(SystemClock.elapsedRealtime());


        // initialize Victory Pop-up Window
        final View popUpView = LayoutInflater.from(this).inflate(R.layout.victory_popup, null);
        final Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        mPopupWindow = new PopupWindow(popUpView, size.x, size.y, false) {
            @Override
            public void dismiss() {
                super.dismiss();
                startActivity(new Intent(GameActivity.this, MainMenuActivity.class));
            }
        };
        mPopupWindow.setClippingEnabled(false);
        mPopupWindow.getContentView().findViewById(R.id.done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        final TextView time = mPopupWindow.getContentView().findViewById(R.id.time);


        if (savedInstanceState != null) {
            mGameData = savedInstanceState.getParcelable("gameData");
            mTimer.setBase(SystemClock.elapsedRealtime() - savedInstanceState.getLong("timeInterval"));
        }
        final GameController gameController = new GameController(mGameData, mGameView, mPopupWindow, mTimer, time);
        mGameView.setGameData(mGameData);
        gameLayout.addView(mGameView);

        // Set Buttons Bank

        final Drawable drawable = getResources().getDrawable(R.drawable.eraser);
        drawable.setBounds(0, 0, 68, 68);
        final TextView erase = findViewById(R.id.remove_one);
        erase.setCompoundDrawables(null, drawable, null, null);
        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int touchPositionX = mGameView.getTouchPositionX();
                final int touchPositionY = mGameView.getTouchPositionY();
                if (touchPositionX != -1 && mGameData.getPuzzlePreFilled()[touchPositionY][touchPositionX] == 0) {
                    mGameData.removeOneCell(touchPositionX, touchPositionY);
                    mGameView.invalidate();
                } else if (touchPositionX != -1) {
                    GameController.showMessageToast(GameActivity.this, "Can't erase a pre-filled cell", Gravity.CENTER);
                    final Animation shake = AnimationUtils.loadAnimation(GameActivity.this, R.anim.button_shake_anim);
                    erase.startAnimation(shake);
                }
            }
        });

        final Drawable drawable1 = getResources().getDrawable(R.drawable.pause);
        drawable1.setBounds(0, 0, 67, 67);
        final TextView pause = findViewById(R.id.pause);
        pause.setCompoundDrawables(null, drawable1, null, null);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameController.showMessageToast(GameActivity.this, "Coming Soon!", Gravity.CENTER);
            }
        });

        final Drawable drawable2 = getResources().getDrawable(R.drawable.restart);
        drawable2.setBounds(0, 0, 66, 66);
        final TextView restart = findViewById(R.id.restart);
        restart.setCompoundDrawables(null, drawable2, null, null);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameController.showMessageToast(GameActivity.this, "Coming Soon!", Gravity.CENTER);
            }
        });

        final LinearLayout buttonBank1 = findViewById(R.id.button_bank1);
        final LinearLayout buttonBank2 = findViewById(R.id.button_bank2);
        final LinearLayout buttonBank3 = findViewById(R.id.button_bank3);
        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        final int gridSize = GameData.getGridSize();
        final Button[] mButtons = new Button[gridSize];

        // Set Listeners and Buttons' Text
        ArrayList<Integer> randIntList = new ArrayList<Integer>();
        for (int i = 0; i < gridSize; i++) {
            randIntList.add(i);
        }
        Random rand = new Random();
        // Set Listeners and Buttons' Text
        for (int i = 0; i < gridSize; i++) {
            final int j = i;
            int num = randIntList.remove(rand.nextInt(randIntList.size()));
            mButtons[i] = new Button(this);
            mButtons[i].setLayoutParams(lp);
            mButtons[i].setAllCaps(false);
            mButtons[i].getBackground().setTint(getResources().getColor(R.color.word_bank));
            mButtons[i].setTag(String.valueOf(num + 1));
            mButtons[i].setText(gameController.getGameData().getLanguageB()[num]);
            mButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gameController.fillWord(mButtons[j]);
                }
            });
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (gridSize == 4)
                for (int i = 0; i < 4; i++)
                    buttonBank3.addView(mButtons[i]);
            else if (gridSize == 6) {
                for (int i = 0; i < 3; i++) {
                    buttonBank2.addView(mButtons[i]);
                    buttonBank3.addView(mButtons[i + 3]);
                }
            } else if (gridSize == 9) {
                buttonBank1.addView(mButtons[0]);
                for (int i = 1; i < 5; i++) {
                    buttonBank2.addView(mButtons[i]);
                    buttonBank3.addView(mButtons[i + 4]);
                }
            } else {
                for (int i = 0; i < 4; i++) {
                    buttonBank1.addView(mButtons[i]);
                    buttonBank2.addView(mButtons[i + 4]);
                    buttonBank3.addView(mButtons[i + 8]);
                }
            }
        } else {
            if (gridSize == 4) {
                buttonBank2.addView(mButtons[0]);
                for (int i = 1; i < 4; i++)
                    buttonBank3.addView(mButtons[i]);
            } else if (gridSize == 6) {
                for (int i = 0; i < 3; i++) {
                    buttonBank2.addView(mButtons[i]);
                    buttonBank3.addView(mButtons[i + 3]);
                }
            } else if (gridSize == 9) {
                for (int i = 0; i < 3; i++) {
                    buttonBank1.addView(mButtons[i]);
                    buttonBank2.addView(mButtons[i + 3]);
                    buttonBank3.addView(mButtons[i + 6]);
                }
            } else {
                final LinearLayout buttonBank4 = findViewById(R.id.button_bank4);
                buttonBank4.setVisibility(View.VISIBLE);
                for (int i = 0; i < 3; i++) {
                    buttonBank1.addView(mButtons[i]);
                    buttonBank2.addView(mButtons[i + 3]);
                    buttonBank3.addView(mButtons[i + 6]);
                    buttonBank4.addView(mButtons[i + 9]);
                }
            }
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGameView.getTTSHandler().destroy();
    }

}
