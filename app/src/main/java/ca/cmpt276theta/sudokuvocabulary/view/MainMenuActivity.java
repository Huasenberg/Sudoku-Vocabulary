package ca.cmpt276theta.sudokuvocabulary.view;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.net.Uri;
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
import java.util.Collections;
import java.util.Comparator;

import ca.cmpt276theta.sudokuvocabulary.R;
import ca.cmpt276theta.sudokuvocabulary.controller.GameController;
import ca.cmpt276theta.sudokuvocabulary.controller.Word;
import ca.cmpt276theta.sudokuvocabulary.model.GameData;

public class MainMenuActivity extends AppCompatActivity {
    private int mOption;
    private PopupWindow mPopupWindow;
//    private DatabaseHelper db;

    @Override
    protected void onPause() {
        super.onPause();
        if(mPopupWindow.isShowing())
            mPopupWindow.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        db = new DatabaseHelper(this);
//        wordlist.addAll(db.getAllWords());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        //readWordData();

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
                GameData.setListenMode(false);
            }
        });
        mPopupWindow.getContentView().findViewById(R.id.radioListen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameData.setListenMode(true);
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
                GameController.showMessageToast(MainMenuActivity.this, " Coming soon! ");
            }
        });

        findViewById(R.id.import_word_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performFileSearch();
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
    }

    private static final int READ_REQUEST_CODE = 42;

    public void performFileSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        intent.setType("text/*");

        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                try {
                    readTextFromUri(uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void readTextFromUri(Uri uri) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        GameController.writeToArrayList(reader);
        GameController.showMessageToast(this, " Words have been imported! ");
        saveArray(GameData.getWordlist());
    }


   /* private void readWordData() {
        InputStream is = getResources().openRawResource(R.raw.words);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8")));
        writeToArrayList(reader);
    }*/




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

    public void saveArray(ArrayList<Word> list) {
        SharedPreferences sp = this.getSharedPreferences("wordList", MODE_PRIVATE);
        SharedPreferences.Editor mEdit1= sp.edit();
        mEdit1.putInt("Size",list.size());

        for(int i = 0; i < list.size(); i++) {
            mEdit1.remove("Status_English" + i);
            mEdit1.putString("Status_English" + i, list.get(i).getEnglish());
            mEdit1.remove("Status_French" + i);
            mEdit1.putString("Status_French" + i, list.get(i).getFrench());
            mEdit1.remove("Status_Score" + i);
            mEdit1.putInt("Status_Score" + i, list.get(i).getScore());
        }
        mEdit1.commit();
    }



}
