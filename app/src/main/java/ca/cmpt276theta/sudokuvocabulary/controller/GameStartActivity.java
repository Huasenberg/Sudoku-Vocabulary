package ca.cmpt276theta.sudokuvocabulary.controller;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import ca.cmpt276theta.sudokuvocabulary.R;
import ca.cmpt276theta.sudokuvocabulary.model.GameData;
import ca.cmpt276theta.sudokuvocabulary.model.GameDataGenerator;
import ca.cmpt276theta.sudokuvocabulary.model.Word;
import ca.cmpt276theta.sudokuvocabulary.model.WordList;

public class GameStartActivity extends AppCompatActivity {
    private int mOption;
    private static List<CheckBox> sCheckBoxes;
    private LinearLayout mLinearLayout;
    private int mNumOfWords;
    private Button startButton;
    private Spinner spinner;


    public static void setCheckBoxes(List<CheckBox> checkBoxes) {
        sCheckBoxes = checkBoxes;
    }

    public static List<CheckBox> getCheckBoxes() {
        return sCheckBoxes;
    }

    @Override
    protected void onPause() {
        finish();
        super.onPause();
        new Handler().postDelayed(new Runnable(){
            public void run(){
                mLinearLayout.removeAllViews();
            }
        },200);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setEnterTransition(new Explode().setDuration(400));
        getWindow().setExitTransition(null);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_start);
        spinner = findViewById(R.id.spinner);

        mLinearLayout = findViewById(R.id.checkboxs);
        mNumOfWords = 4;
        loadSpinner();
        loadCheckBoxes();
        final SeekBar seekBar = findViewById(R.id.seekBar);
        final SeekBar seekBar2 = findViewById(R.id.seek_bar_2);
        findViewById(R.id.radioRead).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameData.setListenMode(false);
            }
        });
        findViewById(R.id.radioListen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameData.setListenMode(true);
            }
        });

        startButton = findViewById(R.id.button_start);
        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(startButton.getCurrentTextColor() == getResources().getColor(R.color.subgrid))
                    GameController.showMessageToast(GameStartActivity.this, "Must Select " + mNumOfWords + " Pairs of Words", Gravity.CENTER);
                else {
                    GameData.setDifficulty(seekBar.getProgress() + 1);
                    switch (seekBar2.getProgress()) {
                        case 0:
                            GameDataGenerator.setSIZE(2, 2);
                            break;
                        case 1:
                            GameDataGenerator.setSIZE(2, 3);
                            break;
                        case 2:
                            GameDataGenerator.setSIZE(3, 3);
                            break;
                        case 3:
                            GameDataGenerator.setSIZE(4, 3);
                    }
                    GameData.setLanguageMode(mOption);
                    GameDataGenerator.loadPuzzleData();
                    startActivity(new Intent(GameStartActivity.this, GameActivity.class));
                }
            }
        });
        if(WordList.getSelectedWordList().size() < mNumOfWords)
            startButton.setTextColor(getResources().getColor(R.color.subgrid));
        final TextView text = findViewById(R.id.textViewDif);
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

        final TextView text2 = findViewById(R.id.textview_size);
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                switch (progress) {
                    case 0:
                        text2.setText("Grid Size: 4 x 4");
                        mNumOfWords = 4;
                        break;
                    case 1:
                        text2.setText("Grid Size: 6 x 6");
                        mNumOfWords = 6;
                        break;
                    case 2:
                        text2.setText("Grid Size: 9 x 9");
                        mNumOfWords = 9;
                        break;
                    case 3:
                        text2.setText("Grid Size: 12 x 12");
                        mNumOfWords = 12;
                }
                loadCheckBoxes();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void loadSpinner() {
        GameData.loadLanguagesList();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown, GameData.getLanguagesList());
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

    private void loadCheckBoxes() {
        mLinearLayout.removeAllViews();
        final List<Word> wordList = WordList.getSelectedWordList();
        for(int i = 0; i < sCheckBoxes.size(); i++) {
            sCheckBoxes.get(i).setChecked(false);
            mLinearLayout.addView(sCheckBoxes.get(i));
            final int j = i;
            sCheckBoxes.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        wordList.add(WordList.getOriginalWordList().get(j));
                        if(wordList.size() == mNumOfWords) {
                            startButton.setTextColor(getResources().getColor(R.color.background));
                            for (int k = 0; k < sCheckBoxes.size(); k++)
                                if (!sCheckBoxes.get(k).isChecked())
                                    sCheckBoxes.get(k).setEnabled(false);
                        }
                    }
                    else {
                        wordList.remove(WordList.getOriginalWordList().get(j));
                        startButton.setTextColor(getResources().getColor(R.color.subgrid));
                        for (int k = 0; k < sCheckBoxes.size(); k++)
                            sCheckBoxes.get(k).setEnabled(true);
                    }
                }
            });
        }
    }
}
