package ca.cmpt276theta.sudokuvocabulary.controller;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import ca.cmpt276theta.sudokuvocabulary.R;
import ca.cmpt276theta.sudokuvocabulary.model.GameSettings;
import ca.cmpt276theta.sudokuvocabulary.view.GameView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        final SharedPreferences sp = this.getSharedPreferences("game_settings", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("sound", GameSettings.isIsSoundOpen());
        editor.putBoolean("vibration", GameSettings.isIsVibraOpen());
        editor.putBoolean("screen_on", GameSettings.isIsScreeOn());
        editor.putBoolean("highli_duplic", GameSettings.isIsDuplicHighli());
        editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setEnterTransition(new Explode().setDuration(400));
        getWindow().setExitTransition(null);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        findViewById(R.id.return_to_main_menu_2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        final Switch soundSwitch = findViewById(R.id.sound_switch);
        final Drawable drawable1 = getResources().getDrawable(R.drawable.sound);
        drawable1.setBounds(2, 2, 66, 66);
        soundSwitch.setCompoundDrawables(drawable1, null, null, null);
        soundSwitch.setChecked(GameSettings.isIsSoundOpen());
        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                GameSettings.setIsSoundOpen(isChecked);
            }
        });
        final Switch vibrationSwitch = findViewById(R.id.vibration_switch);
        final Drawable drawable2 = getResources().getDrawable(R.drawable.vibration);
        drawable2.setBounds(2, 2, 66, 66);
        vibrationSwitch.setCompoundDrawables(drawable2, null, null, null);
        vibrationSwitch.setChecked(GameSettings.isIsVibraOpen());
        vibrationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                GameSettings.setIsVibraOpen(isChecked);
            }
        });
        final Switch keepScreOn = findViewById(R.id.keep_screen_on_switch);
        final Drawable drawable3 = getResources().getDrawable(R.drawable.screen_on);
        drawable3.setBounds(2, 2, 66, 66);
        keepScreOn.setCompoundDrawables(drawable3, null, null, null);
        keepScreOn.setChecked(GameSettings.isIsScreeOn());
        keepScreOn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                GameSettings.setIsScreeOn(isChecked);
            }
        });
        final Switch highliDuplic = findViewById(R.id.duplic_highli_switch);
        final Drawable drawable4 = getResources().getDrawable(R.drawable.highlight);
        drawable4.setBounds(2, 2, 66, 66);
        highliDuplic.setCompoundDrawables(drawable4, null, null, null);
        highliDuplic.setChecked(GameSettings.isIsDuplicHighli());
        highliDuplic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                GameSettings.setIsDuplicHighli(isChecked);
            }
        });
    }
}
