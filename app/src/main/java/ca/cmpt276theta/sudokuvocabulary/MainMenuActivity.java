package ca.cmpt276theta.sudokuvocabulary;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainMenuActivity extends AppCompatActivity {
    private int mOption;
    private PopupWindow mPopupWindow;

    //private static final int DATABASE_VERSION = 1;
    //private static final String DATABASE_NAME = "studentDB.db";
    //public static final String TABLE_NAME = "Word_DB";
    //public static final String WORD_ID = "Word_ID";
    //public static final String ENGLISH = "English";
    //public static final String FRENCH = "French";
    //public static final String SCORE = "Score";

    @Override
    protected void onPause() {
        super.onPause();
        if(mPopupWindow.isShowing())
            mPopupWindow.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //SQLiteDatabase database = this.openOrCreateDatabase("SudokuVocab", MODE_WORLD_READABLE, null);
        //database.execSQL("CREATE TABLE IF NOT EXISTS MyBeautifulTable(INT id, INT random, TEXT text)");
        //String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS" + TABLE_NAME + "(" + WORD_ID +
        //        "INTEGER PRIMARYKEY," + ENGLISH + "TEXT," + FRENCH + "TEXT," + SCORE + "INTEGER )";
        //database.execSQL(CREATE_TABLE);

        readWordData();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        View contentView = LayoutInflater.from(this).inflate(R.layout.difficulty_popup, null);
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        mPopupWindow = new PopupWindow(contentView, size.x, size.y, true) {
            @Override
            public void dismiss() {
                super.dismiss();
                setActivityBackGroundAlpha(1);
            }
        };
        final Spinner spinner = mPopupWindow.getContentView().findViewById(R.id.spinner);
        final SeekBar seekBar = mPopupWindow.getContentView().findViewById(R.id.seekBar);
        mPopupWindow.getContentView().findViewById(R.id.radioRead).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameData.listenMode = false;
            }
        });
        mPopupWindow.getContentView().findViewById(R.id.radioListen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameData.listenMode = true;
            }
        });
        loadSpinner(spinner, mPopupWindow);

        findViewById(R.id.new_game).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiffPopup(mPopupWindow, seekBar);
            }
        });

        findViewById(R.id.continue_game).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(MainMenuActivity.this, "Coming soon!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                View view = toast.getView();
                view.setBackgroundColor(getResources().getColor(R.color.conflict));
                TextView text = view.findViewById(android.R.id.message);
                text.setTextColor(getResources().getColor(R.color.background));
                text.setTextSize(18);
                toast.show();
            }
        });

        findViewById(R.id.import_word_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(MainMenuActivity.this, "Coming soon!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                View view = toast.getView();
                view.setBackgroundColor(getResources().getColor(R.color.conflict));
                TextView text = view.findViewById(android.R.id.message);
                text.setTextColor(getResources().getColor(R.color.background));
                text.setTextSize(18);
                toast.show();
            }
        });

        findViewById(R.id.about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, AboutPageActivity.class), ActivityOptions.makeSceneTransitionAnimation(MainMenuActivity.this).toBundle());
            }
        });

        findViewById(R.id.settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, SettingsActivity.class));
            }
        });
        GameDataGenerator.loadPuzzleData();
    }
    private List<Word> wordlist = new ArrayList<>();

    private void readWordData() {
        InputStream is = getResources().openRawResource(R.raw.words);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
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
                wordlist.add(sample);

                // Log the object
                System.out.println("HI" + wordlist.get(0));
                Log.d("My Activity", "Just created: " + sample);
            }

        } catch (IOException e) {
            // Logs error with priority level
            Log.wtf("My Activity", "Error reading data file on line" + line, e);

            // Prints throwable details
            e.printStackTrace();
        }
    }

    private void loadSpinner(Spinner spinner, PopupWindow pw) {
        GameData.loadLanguagesList();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(pw.getContentView().getContext(), R.layout.spinner_dropdown, GameData.getLanguagesList());
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                mOption = arg2;
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private void showDiffPopup(final PopupWindow pw, final SeekBar seekBar) {
        final TextView text = mPopupWindow.getContentView().findViewById(R.id.textViewDif);
        pw.setAnimationStyle(R.style.pop_animation);
        setActivityBackGroundAlpha(0.3f);
        pw.showAtLocation(findViewById(R.id.mainLayout), Gravity.CENTER, 0, 0);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                text.setText(String.format(getResources().getString(R.string.difficulty), seekBar.getProgress() + 1));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        pw.getContentView().findViewById(R.id.buttonGo).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                GameData.setDifficulty(seekBar.getProgress() + 1);
                GameData.setLanguageMode(mOption);
                startActivity(new Intent(MainMenuActivity.this, GameActivity.class));
            }
        });
        pw.getContentView().findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                pw.dismiss();
            }
        });
    }

    private void setActivityBackGroundAlpha(float num) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = num;
        getWindow().setAttributes(lp);
    }
}
