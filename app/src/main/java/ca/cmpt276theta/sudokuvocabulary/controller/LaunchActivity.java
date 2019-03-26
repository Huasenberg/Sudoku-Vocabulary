package ca.cmpt276theta.sudokuvocabulary.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ca.cmpt276theta.sudokuvocabulary.R;
import ca.cmpt276theta.sudokuvocabulary.model.GameDataGenerator;
import ca.cmpt276theta.sudokuvocabulary.model.WordList;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_launch);
        loadGame();
        TextView textView = findViewById(R.id.name);
        String str = "<font color='#0373D6'>S</font>udoku  <font color='#0373D6'>V</font>ocabulary";
        textView.setText(Html.fromHtml(str));
        final Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.launch_page_part1_anim);
        final ImageView logoPart1 = findViewById(R.id.logo_1);
        final Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.launch_page_part2_anim);
        final ImageView logoPart2 = findViewById(R.id.logo_2);
        final Animation animation3 = AnimationUtils.loadAnimation(this, R.anim.launch_page_part3_anim);
        final ImageView logo = findViewById(R.id.logo);
        logoPart1.startAnimation(animation1);
        logoPart2.startAnimation(animation2);
        animation3.setStartOffset(700);
        logo.startAnimation(animation3);
        textView.startAnimation(animation3);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(LaunchActivity.this, MainMenuActivity.class));
                finish();
            }
        }, 3200);
    }

    private void loadGame (){
        GameDataGenerator.loadPuzzleData();
        WordList.setOriginalWordList(new ArrayList<Word>());
        WordList.setSelectedWordList(new ArrayList<Word>());
        loadWordList(WordList.getOriginalWordList());
    }

    private void loadWordList(ArrayList<Word> list) {
        List<CheckBox> checkBoxes = new ArrayList<>();
        SharedPreferences mSharedPreference1 = this.getSharedPreferences("wordList", MODE_PRIVATE);
        list.clear();
        int size = mSharedPreference1.getInt("Size", 0);
        for(int i = 0; i < size; i++) {
            Word word = new Word(mSharedPreference1.getString("English" + i, null), mSharedPreference1.getString("French" + i, null));
            CheckBox checkBox = new CheckBox(this);
            word.setScore(mSharedPreference1.getInt("Score" + i,0));
            list.add(word);
            checkBox.setText(word.toString());
            checkBox.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            checkBox.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            checkBox.setTextSize(16);
            checkBoxes.add(checkBox);
        }
        MainMenuActivity.setCheckBoxes(checkBoxes);

    }
}
