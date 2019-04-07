package ca.cmpt276theta.sudokuvocabulary.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import ca.cmpt276theta.sudokuvocabulary.R;
import ca.cmpt276theta.sudokuvocabulary.model.GameData;
import ca.cmpt276theta.sudokuvocabulary.model.GameDataList;
import ca.cmpt276theta.sudokuvocabulary.model.GameSettings;
import ca.cmpt276theta.sudokuvocabulary.model.Word;
import ca.cmpt276theta.sudokuvocabulary.model.WordList;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
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

    private void loadGame() {
        GameData.loadLanguagesList();
        WordList.setOriginalWordList(new ArrayList<Word>());
        WordList.setSelectedWordList(new ArrayList<Word>());
        loadWordList();
        loadGameData();
        loadGameSettings();
    }

    private void loadWordList() {

        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            fileInputStream = openFileInput("word_list");
            objectInputStream = new ObjectInputStream(fileInputStream);
            WordList.setOriginalWordList((List<Word>) objectInputStream.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null) fileInputStream.close();
                if (objectInputStream != null) objectInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void loadGameData() {
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            fileInputStream = openFileInput("game_data");
            objectInputStream = new ObjectInputStream(fileInputStream);
            GameDataList.setGameDataList((List<GameData>) objectInputStream.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null) fileInputStream.close();
                if (objectInputStream != null) objectInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadGameSettings(){
        SharedPreferences mSharedPreference1 = this.getSharedPreferences("game_settings", MODE_PRIVATE);
        GameSettings.setIsSoundOpen(mSharedPreference1.getBoolean("sound", true));
        GameSettings.setIsVibraOpen(mSharedPreference1.getBoolean("vibration", true));
        GameSettings.setIsScreeOn(mSharedPreference1.getBoolean("screen_on", false));
        GameSettings.setIsDuplicHighli(mSharedPreference1.getBoolean("highli_duplic", true));
    }


}
