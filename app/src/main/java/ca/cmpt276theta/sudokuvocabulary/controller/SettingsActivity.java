package ca.cmpt276theta.sudokuvocabulary.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.io.File;
import java.math.BigDecimal;

import ca.cmpt276theta.sudokuvocabulary.R;
import ca.cmpt276theta.sudokuvocabulary.model.GameDataList;
import ca.cmpt276theta.sudokuvocabulary.model.GameSettings;
import ca.cmpt276theta.sudokuvocabulary.model.WordList;

public class SettingsActivity extends AppCompatActivity {
    private AlertDialog ad;
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
        if(ad.isShowing())
            ad.dismiss();
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

        final TextView dataSize = findViewById(R.id.data_size);
        final Button clearData = findViewById(R.id.clear_all_data);

        ad = new AlertDialog.Builder(SettingsActivity.this)
                .setTitle(R.string.clear_data)
                .setMessage(R.string.clear_message)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteDir(getCacheDir());
                        deleteDir(getFilesDir());
                        WordList.getOriginalWordList().clear();
                        WordList.getSelectedWordList().clear();
                        GameDataList.getGameDataList().clear();
                        dataSize.setText("Total: 0 B");
                    }
                }).setNegativeButton(R.string.cancel,null).create();

        clearData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.show();
            }
        });

        long size = getFolderSize(getFilesDir()) + getFolderSize(getCacheDir());
        dataSize.setText("Total: " + getFormatSize(size));

    }

    private long getFolderSize(final File file) {
        long size = 0;
        File[] fileList = file.listFiles();
        for (File aFileList : fileList) {
            if (aFileList.isDirectory()) {
                size = size + getFolderSize(aFileList);
            } else {
                size = size + aFileList.length();
            }
        }
        return size;
    }

    private String getFormatSize(final double size) {
        final double kiloByte = size / 1024;
        if (kiloByte < 1)
            return size + " B";

        final double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            final BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + " KB";
        }

        final double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            final BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + " MB";
        }

        final BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
        return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + " GB";
    }

    private boolean deleteDir(final File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
}

