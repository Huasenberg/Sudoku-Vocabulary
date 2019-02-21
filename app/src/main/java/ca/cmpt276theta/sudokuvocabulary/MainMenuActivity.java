package ca.cmpt276theta.sudokuvocabulary;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenuActivity extends AppCompatActivity {
    private int mOption;
    private PopupWindow mPopupWindow;

    @Override
    protected void onPause() {
        super.onPause();
        if(mPopupWindow.isShowing())
            mPopupWindow.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        final TextView text = mPopupWindow.getContentView().findViewById(R.id.textViewDif);
        final Intent intentToGameActivity = new Intent(MainMenuActivity.this, GameActivity.class);
        final Intent intentToSettingsActivity = new Intent(MainMenuActivity.this, SettingsActivity.class);
        loadSpinner(spinner, mPopupWindow);
        final Button newGameButton = findViewById(R.id.new_game);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiffPopup(intentToGameActivity, mPopupWindow, seekBar, text);
            }
        });

        final Button continueGame = findViewById(R.id.continue_game);
        continueGame.setOnClickListener(new View.OnClickListener() {
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

        final Button mImportWordList = findViewById(R.id.import_word_list);
        mImportWordList.setOnClickListener(new View.OnClickListener() {
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

        final ImageView settings = findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentToSettingsActivity);
            }
        });
        GameDataGenerator.loadPuzzleData();
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

    private void showDiffPopup(final Intent intent, final PopupWindow pw, final SeekBar seekBar, final TextView text) {
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
                startActivity(intent);
                pw.dismiss();
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
