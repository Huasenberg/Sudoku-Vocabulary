package ca.cmpt276theta.sudokuvocabulary;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        FrameLayout gameLayout = (FrameLayout) findViewById(R.id.gameLayout);
        GameView gameView = new GameView(this);
        GameMain gameMain = new GameMain(gameView);
        gameLayout.addView(gameView);

        // Empty Puzzle
        int[][] puzzle = new int[][]{
                {0, 7, 0, 0, 4, 0, 0, 0, 9},
                {2, 0, 0, 0, 0, 3, 0, 8, 0},
                {8, 0, 5, 9, 0, 0, 0, 0, 4},
                {0, 6, 0, 0, 0, 0, 8, 4, 0},
                {0, 0, 0, 0, 3, 0, 2, 9, 0},
                {0, 8, 4, 6, 9, 0, 0, 0, 0},
                {7, 0, 6, 4, 0, 0, 0, 0, 0},
                {0, 0, 3, 0, 0, 5, 1, 0, 8},
                {0, 5, 0, 0, 0, 9, 0, 7, 0}
        };

        // Puzzle with the answers
        int[][] puzzle_answers = new int[][]{
            {6, 7, 1, 2, 4, 8, 3, 5, 9},
            {2, 4, 9, 5, 7, 3, 6, 8, 1},
            {8, 3, 5, 9, 1, 6, 7, 2, 4},
            {9, 6, 2, 1, 5, 7, 8, 4, 3},
            {5, 1, 7, 8, 3, 4, 2, 9, 6},
            {3, 8, 4, 6, 9, 2, 5, 1, 7},
            {7, 2, 6, 4, 8, 1, 9, 3, 5},
            {4, 9, 3, 7, 2, 5, 1, 6, 8},
            {1, 5, 8, 3, 6, 9, 4, 7, 2}
        };

        // Pairing the words with numbers
        Pair<Integer, String> pair1 = new Pair<>(1, "Pomme");
        Pair<Integer, String> pair2 = new Pair<>(2, "Poir");
        Pair<Integer, String> pair3 = new Pair<>(3, "Fraise");
        Pair<Integer, String> pair4 = new Pair<>(4, "Ananans");
        Pair<Integer, String> pair5 = new Pair<>(5, "Banane");
        Pair<Integer, String> pair6 = new Pair<>(6, "Canneberge");
        Pair<Integer, String> pair7 = new Pair<>(7, "Figue");
        Pair<Integer, String> pair8 = new Pair<>(8, "Pêche");
        Pair<Integer, String> pair9 = new Pair<>(9, "Cerise");

        Pair mapping_array[] = { pair1, pair2, pair3, pair4, pair5,
                pair6, pair7, pair8, pair9 };

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
            mButtons[i].setTag("3");
            mButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GameMain.fillWord(mButtons[j]);
                }
            });
        }

        // Set Timer
        Chronometer ch = (Chronometer) findViewById(R.id.chronometer1);
        ch.setBase(SystemClock.elapsedRealtime());
        ch.setFormat("Time: %s");
        ch.start();

    }


    public static void main(String[] args) {


    }
};
