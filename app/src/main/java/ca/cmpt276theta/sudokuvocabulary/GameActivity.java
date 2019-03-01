package ca.cmpt276theta.sudokuvocabulary;

import android.content.res.Configuration;
import android.graphics.Point;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private GameData mGameData;
    private Chronometer mTimer;
    private PopupWindow mPopupWindow;

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
        if(mPopupWindow.isShowing())
            mPopupWindow.dismiss();
    }

    GameView gameView;
    protected TTSHandler tts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        tts = new TTSHandler(this);
        tts.init();
        super.onCreate(savedInstanceState);
        setTitle(GameData.getLanguageMode_String());
        setContentView(R.layout.activity_game);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if(getSupportActionBar() != null)
                getSupportActionBar().hide();
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            final TextView textView = findViewById(R.id.mode);
            textView.setText(GameData.getLanguageMode_String());
        }
        FrameLayout gameLayout = findViewById(R.id.gameLayout);
        GameView gameView = new GameView(this);

        // Set Timer
        mTimer = findViewById(R.id.chronometer1);
        mTimer.setBase(SystemClock.elapsedRealtime());
        mTimer.setFormat("Time: %s");

        // initialize Victory Pop-up Window
        View popUpView = LayoutInflater.from(this).inflate(R.layout.victory_popup, null);
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        mPopupWindow = new PopupWindow(popUpView,size.x,size.y, false) {
            @Override
            public void dismiss() {
                super.dismiss();
                finish();
            }
        };
        mPopupWindow.setClippingEnabled(false);
        mPopupWindow.getContentView().findViewById(R.id.done).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        final TextView time = mPopupWindow.getContentView().findViewById(R.id.time);

        mGameData  = new GameData();
        if (savedInstanceState != null) {
            mGameData = savedInstanceState.getParcelable("gameData");
            mTimer.setBase(SystemClock.elapsedRealtime() - savedInstanceState.getLong("timeInterval"));
        }
        final GameController gameController = new GameController(mGameData, gameView, mPopupWindow, mTimer, time);

        gameView.setGameData(mGameData);
        gameLayout.addView(gameView);


        // Set Buttons Bank
        final Button[] mButtons = new Button[9];
        mButtons[0] = findViewById(R.id.button0);
        mButtons[1] = findViewById(R.id.button1);
        mButtons[2] = findViewById(R.id.button2);
        mButtons[3] = findViewById(R.id.button3);
        mButtons[4] = findViewById(R.id.button4);
        mButtons[5] = findViewById(R.id.button5);
        mButtons[6] = findViewById(R.id.button6);
        mButtons[7] = findViewById(R.id.button7);
        mButtons[8] = findViewById(R.id.button8);

        findViewById(R.id.removeOne).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int touchPositionX = GameActivity.this.gameView.getTouchPositionX();
                final int touchPositionY = GameActivity.this.gameView.getTouchPositionY();
                if(touchPositionX != -1 && touchPositionY != -1 && mGameData.getPuzzlePreFilled()[touchPositionY][touchPositionX] == 0) {
                    mGameData.removeOneCell(touchPositionX, touchPositionY);
                    GameActivity.this.gameView.invalidate();
                }
            }
        });

        // Set Listeners and Buttons' Text
        for (int i = 0; i < mButtons.length; i++) {
            final int j = i;
            mButtons[i].setText(gameController.getGameData().getLanguageB()[i]);
            mButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gameController.fillWord(mButtons[j]);
                }
            });
        }

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        tts.destroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.game_top_menu, menu);
        return true;
    }

}
