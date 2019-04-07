package ca.cmpt276theta.sudokuvocabulary.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import ca.cmpt276theta.sudokuvocabulary.R;
import ca.cmpt276theta.sudokuvocabulary.model.GameData;
import ca.cmpt276theta.sudokuvocabulary.model.GameDataList;
import ca.cmpt276theta.sudokuvocabulary.model.GameSettings;
import ca.cmpt276theta.sudokuvocabulary.view.GameView;

public class GameActivity extends AppCompatActivity {

    private final List<GameData> gameDataList = GameDataList.getGameDataList();
    private GameData mGameData;
    private Chronometer mTimer;
    private PopupWindow mPopupWindow;
    private GameView mGameView;
    private long timeInterval;
    private boolean isPause = false;
    private AlertDialog mAlertDialog;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable("gameData", mGameData);
        if (!isPause)
            timeInterval = SystemClock.elapsedRealtime() - mTimer.getBase();

        savedInstanceState.putLong("timeInterval", timeInterval);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPopupWindow.isShowing())
            mPopupWindow.dismiss();
        if (mAlertDialog.isShowing())
            mAlertDialog.dismiss();
        if (mGameData.getEmptyCellCounter() != 0) {
            gameDataList.remove(mGameData);
            mGameData.setSavedTimeInterval(SystemClock.elapsedRealtime() - mTimer.getBase());
            SimpleDateFormat formatter = new SimpleDateFormat("M/d/YYYY   HH:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());
            mGameData.setSavedTime(formatter.format(curDate));
            gameDataList.add(mGameData);
            saveGameData();
            GameController.showMessageToast(this, "Game Saved!", Gravity.NO_GRAVITY);
        }
        else
            GameDataList.getGameDataList().remove(mGameData);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        if(GameSettings.isIsScreeOn())
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        final TextView textView = findViewById(R.id.game_title);
        final FrameLayout gameLayout = findViewById(R.id.game_layout);
        final FrameLayout pauseScreen = new FrameLayout(this);
        pauseScreen.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        pauseScreen.setBackgroundColor(getResources().getColor(R.color.background));
        pauseScreen.setClickable(true);
        final ImageView resumeImage = new ImageView(this);
        final FrameLayout.LayoutParams frameLayoutParams = new FrameLayout.LayoutParams(200, 200);
        frameLayoutParams.gravity = Gravity.CENTER;
        resumeImage.setLayoutParams(frameLayoutParams);
        resumeImage.setBackgroundResource(R.drawable.round_shape);
        resumeImage.setPadding(40, 30, 20, 30);
        resumeImage.setElevation(40);
        resumeImage.setImageResource(R.drawable.resume);
        pauseScreen.addView(resumeImage);

        // Set Timer
        mTimer = findViewById(R.id.chronometer1);

        // initialize Victory Pop-up Window
        final View popUpView = LayoutInflater.from(this).inflate(R.layout.victory_popup, null);
        final Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        mPopupWindow = new PopupWindow(popUpView, size.x, size.y, false) {
            @Override
            public void dismiss() {
                super.dismiss();
                setActivityBackGroundAlpha(1);
                startActivity(new Intent(GameActivity.this, MainMenuActivity.class));
            }
            @Override
            public void showAtLocation(View parent, int gravity, int x, int y) {
                setAnimationStyle(R.style.pop_animation);
                setActivityBackGroundAlpha(0.3f);
                super.showAtLocation(parent, gravity, x, y);
            }
        };

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
        } else {
            final Intent i = getIntent();
            if (i.getIntExtra("savedGame", -1) != -1) {
                mGameData = gameDataList.get(i.getIntExtra("savedGame", -1));
                timeInterval = mGameData.getSavedTimeInterval();
                mTimer.setBase(SystemClock.elapsedRealtime() - timeInterval);
            } else {
                mGameData = new GameData(i.getIntExtra("languageMode", 1), i.getBooleanExtra("isListenMode", false), i.getIntExtra("difficulty", 1));
                mTimer.setBase(SystemClock.elapsedRealtime());
            }
        }

        textView.setText(mGameData.getLanguageMode_String());

        mGameView = new GameView(this, mGameData);
        final GameController gameController = new GameController(mGameData, mGameView, mPopupWindow, mTimer, time);
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
                findViewById(R.id.border).setBackgroundColor(getResources().getColor(R.color.background));
                mGameView.setTouchPosition(-1, -1);
                gameLayout.removeView(pauseScreen);
                gameLayout.addView(pauseScreen);
                pause.setClickable(false);
                timeInterval = SystemClock.elapsedRealtime() - mTimer.getBase();
                mTimer.stop();
                isPause = true;
            }
        });
        resumeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.border).setBackgroundColor(getResources().getColor(R.color.border));
                gameLayout.removeView(pauseScreen);
                pause.setClickable(true);
                mTimer.setBase(SystemClock.elapsedRealtime() - timeInterval);
                mTimer.start();
                isPause = false;
            }
        });


        final Drawable drawable2 = getResources().getDrawable(R.drawable.restart);
        drawable2.setBounds(0, 0, 66, 66);
        final TextView restart = findViewById(R.id.restart);
        restart.setCompoundDrawables(null, drawable2, null, null);
        mAlertDialog = new AlertDialog.Builder(GameActivity.this)
                .setTitle(R.string.restart)
                .setMessage(R.string.restart_alert)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTimer.setBase(SystemClock.elapsedRealtime());
                        mGameData.removeAllCells();
                        mGameView.invalidate();
                        timeInterval = 0;
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        mTimer.setBase(SystemClock.elapsedRealtime() - timeInterval);
                        mTimer.start();
                        isPause = false;
                    }
                }).create();
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPause = true;
                mAlertDialog.show();
                mTimer.stop();
                timeInterval = SystemClock.elapsedRealtime() - mTimer.getBase();
            }
        });

        final Drawable drawable3 = getResources().getDrawable(R.drawable.dark);
        drawable3.setBounds(0, 0, 66, 66);
        final TextView darkMode = findViewById(R.id.dark_mode);
        darkMode.setCompoundDrawables(null, drawable3, null, null);
        darkMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameController.showMessageToast(GameActivity.this, "Coming Soon!", Gravity.CENTER);
            }
        });

        final LinearLayout buttonBank1 = findViewById(R.id.button_bank1);
        final LinearLayout buttonBank2 = findViewById(R.id.button_bank2);
        final LinearLayout buttonBank3 = findViewById(R.id.button_bank3);
        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        final int gridSize = mGameData.getGridSize();
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

    public void saveGameData() {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = openFileOutput("game_data", MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(gameDataList);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) oos.close();
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setActivityBackGroundAlpha(final float num) {
        final WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = num;
        getWindow().setAttributes(layoutParams);
    }

}
