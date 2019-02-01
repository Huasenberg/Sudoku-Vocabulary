package ca.cmpt276theta.sudokuvocabulary;

import android.content.pm.ActivityInfo;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        FrameLayout gameLayout = findViewById(R.id.gameLayout);
        GameView gameView = new GameView(this);
        final GameMain gameMain = new GameMain(gameView);
        gameView.setGameData(gameMain.getGameData());
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

        // Set Listeners and Tags
        for (int i = 0; i < mButtons.length; i++) {
            final int j = i;
            mButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gameMain.fillWord(mButtons[j]);
                }
            });
        }

        // Set Timer
        Chronometer ch = findViewById(R.id.chronometer1);
        ch.setBase(SystemClock.elapsedRealtime());
        ch.setFormat("Time: %s");
        ch.start();
    }


}
