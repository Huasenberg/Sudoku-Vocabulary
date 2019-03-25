package ca.cmpt276theta.sudokuvocabulary.controller;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import ca.cmpt276theta.sudokuvocabulary.R;
import ca.cmpt276theta.sudokuvocabulary.model.GameData;
import ca.cmpt276theta.sudokuvocabulary.model.WordList;
import ca.cmpt276theta.sudokuvocabulary.view.GamePopupWindowView;

public class MainMenuActivity extends AppCompatActivity {
    private int mOption;
    private static final int READ_REQUEST_CODE = 42;
    private PopupWindow mDiffWindow;
    private PopupWindow mWordListWindow;
    private static List<CheckBox> sCheckBoxes;
    private LinearLayout mLinearLayout_checkboxes;
    private List<ImageView> mDeleteIcon;
    private Button backButton;
    private Button finishDelButton;
    private boolean isDeletionMode;

    public static void setCheckBoxes(List<CheckBox> checkBoxes) {
        sCheckBoxes = checkBoxes;
    }

    public static List<CheckBox> getCheckBoxes() {
        return sCheckBoxes;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLinearLayout_checkboxes.removeAllViews();
        if(mDiffWindow.isShowing())
            mDiffWindow.dismiss();
        else if(mWordListWindow.isShowing())
            mWordListWindow.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        final View wordListView = LayoutInflater.from(this).inflate(R.layout.wordlist_popup, null);
        mDiffWindow = new GamePopupWindowView(LayoutInflater.from(this).inflate(R.layout.difficulty_popup,null), findViewById(R.id.mainLayout), RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, getWindow());
        mWordListWindow = new GamePopupWindowView(wordListView, findViewById(R.id.mainLayout), RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, getWindow());
        mWordListWindow.setFocusable(false);

        final Spinner spinner = mDiffWindow.getContentView().findViewById(R.id.spinner);
        final SeekBar seekBar = mDiffWindow.getContentView().findViewById(R.id.seekBar);
        mDiffWindow.getContentView().findViewById(R.id.radioRead).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameData.setListenMode(false);
            }
        });
        mDiffWindow.getContentView().findViewById(R.id.radioListen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameData.setListenMode(true);
            }
        });
        loadSpinner(spinner);
        mLinearLayout_checkboxes = mWordListWindow.getContentView().findViewById(R.id.checkboxs);
        mDeleteIcon = new ArrayList<>();
        findViewById(R.id.new_game).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDiffWindow.showAtLocation(findViewById(R.id.mainLayout), Gravity.CENTER, 0, 0);
            }
        });

        findViewById(R.id.continue_game).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameController.showMessageToast(MainMenuActivity.this, " Coming soon! ", Gravity.NO_GRAVITY);
            }
        });

        findViewById(R.id.import_word_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWordListPopup();
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

        //load pop_up window
        final TextView text = mDiffWindow.getContentView().findViewById(R.id.textViewDif);
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
        mDiffWindow.getContentView().findViewById(R.id.buttonGo).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                GameData.setDifficulty(seekBar.getProgress() + 1);
                GameData.setLanguageMode(mOption);
                startActivity(new Intent(MainMenuActivity.this, GameActivity.class));
            }
        });
        mDiffWindow.getContentView().findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mDiffWindow.dismiss();
            }
        });

    }


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
            Uri uri;
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
        WordList.writeToArrayList(this, reader);
        GameController.showMessageToast(this, " Words have been imported! ", Gravity.NO_GRAVITY);
        saveArray();
    }



    private void loadSpinner(Spinner spinner) {
        GameData.loadLanguagesList();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(mDiffWindow.getContentView().getContext(), R.layout.spinner_dropdown, GameData.getLanguagesList());
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

    private void showWordListPopup () {
        isDeletionMode = false;
        loadCheckBoxes();
        final Button doneButton = mWordListWindow.getContentView().findViewById(R.id.button_done);
        backButton = mWordListWindow.getContentView().findViewById(R.id.button_back);
        finishDelButton = mWordListWindow.getContentView().findViewById(R.id.button_finish_deletion);


        if(WordList.getSelectedWordList().size() < 9)
            doneButton.setTextColor(getResources().getColor(R.color.subgrid));

        mWordListWindow.showAtLocation(findViewById(R.id.mainLayout), Gravity.CENTER, 0, 0);

        final Animation enterAnim1 = AnimationUtils.loadAnimation(MainMenuActivity.this, R.anim.word_list_side_button_enter_anim);
        final Animation enterAnim2 = AnimationUtils.loadAnimation(MainMenuActivity.this, R.anim.word_list_side_button_enter_anim);
        enterAnim2.setStartOffset(200);
        final Animation enterAnim3 = AnimationUtils.loadAnimation(MainMenuActivity.this, R.anim.word_list_side_button_enter_anim);
        enterAnim3.setStartOffset(400);
        final Animation enterAnim4 = AnimationUtils.loadAnimation(MainMenuActivity.this, R.anim.word_list_button_enter_anim);
        final Animation exitAnim = AnimationUtils.loadAnimation(MainMenuActivity.this, R.anim.word_list_side_button_exit_anim);
        final Animation exitAnim2 = AnimationUtils.loadAnimation(MainMenuActivity.this, R.anim.word_list_button_exit_anim);

        final ImageButton sortButton = mWordListWindow.getContentView().findViewById(R.id.button_sort);
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sCheckBoxes.clear();
                WordList.sortWordDataByAlphbet();
                for(int i = 0; i < WordList.getOriginalWordList().size(); i++) {
                    CheckBox checkBox = new CheckBox(MainMenuActivity.this);
                    checkBox.setText(WordList.getOriginalWordList().get(i).toString());
                    checkBox.setTextSize(17);
                    sCheckBoxes.add(checkBox);
                }
                loadCheckBoxes();
                saveArray();
            }
        });

        final ImageButton importButton = mWordListWindow.getContentView().findViewById(R.id.button_import);


        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performFileSearch();
            }
        });
        final ImageButton deleteButton = mWordListWindow.getContentView().findViewById(R.id.button_delete_words);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(doneButton.getCurrentTextColor() == getResources().getColor(R.color.subgrid))
                    GameController.showMessageToast(MainMenuActivity.this, " Must Choose Exactly 9 Pairs of Words ", Gravity.CENTER);
                else {
                    mWordListWindow.dismiss();
                    sortButton.setVisibility(View.GONE);
                    importButton.setVisibility(View.GONE);
                    deleteButton.setVisibility(View.GONE);
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWordListWindow.dismiss();
                sortButton.setVisibility(View.GONE);
                importButton.setVisibility(View.GONE);
                deleteButton.setVisibility(View.GONE);
            }
        });

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainMenuActivity.this);
        alertDialog.setTitle(R.string.alert_title);
        alertDialog.setMessage(R.string.alert_content);
        alertDialog.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sCheckBoxes.clear();
                WordList.getOriginalWordList().clear();
                WordList.getSelectedWordList().clear();
                finishDelButton.performClick();
            }
        });
        alertDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final ImageButton deleteAllButton = mWordListWindow.getContentView().findViewById(R.id.button_delete_all);
        deleteAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortButton.startAnimation(exitAnim);
                importButton.startAnimation(exitAnim);
                deleteButton.startAnimation(exitAnim);

                backButton.startAnimation(exitAnim2);
                finishDelButton.setVisibility(View.VISIBLE);

                ObjectAnimator.ofInt(finishDelButton, "width", 1000).setDuration(600).start();
                new Handler().postDelayed(new Runnable(){
                    public void run(){
                        sortButton.setVisibility(View.GONE);
                        importButton.setVisibility(View.GONE);
                        deleteButton.setVisibility(View.GONE);
                    }
                },600);



                deleteAllButton.setVisibility(View.VISIBLE);
                deleteAllButton.startAnimation(enterAnim2);
                enterDeletionMode();
            }
        });

        finishDelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDeletionMode = false;
                ObjectAnimator.ofInt(finishDelButton, "width", 440).setDuration(600).start();
                sortButton.setVisibility(View.VISIBLE);
                importButton.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);
                sortButton.startAnimation(enterAnim1);
                importButton.startAnimation(enterAnim2);
                deleteButton.startAnimation(enterAnim3);
                backButton.startAnimation(enterAnim4);
                deleteAllButton.startAnimation(exitAnim);
                loadCheckBoxes();
                saveArray();
                new Handler().postDelayed(new Runnable(){
                    public void run(){
                        finishDelButton.setVisibility(View.GONE);
                        deleteAllButton.setVisibility(View.GONE);
                    }
                },600);
            }
        });

        new Handler().postDelayed(new Runnable(){
            public void run(){
                sortButton.setVisibility(View.VISIBLE);
                importButton.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);
                sortButton.startAnimation(enterAnim1);
                importButton.startAnimation(enterAnim2);
                deleteButton.startAnimation(enterAnim3);
            }
        },550);

    }

    private void loadCheckBoxes() {
        mLinearLayout_checkboxes.removeAllViews();
        final List<Word> wordList = WordList.getSelectedWordList();
        final Button doneButton = mWordListWindow.getContentView().findViewById(R.id.button_done);
        for(int i = 0; i < sCheckBoxes.size(); i++) {
            mLinearLayout_checkboxes.addView(sCheckBoxes.get(i));
            final int j = i;
            sCheckBoxes.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        wordList.add(WordList.getOriginalWordList().get(j));
                        if(wordList.size() == 9) {
                            doneButton.setTextColor(getResources().getColor(R.color.background));
                            for (int k = 0; k < sCheckBoxes.size(); k++)
                                if (!sCheckBoxes.get(k).isChecked())
                                    sCheckBoxes.get(k).setEnabled(false);
                        }
                    }
                    else {
                        wordList.remove(WordList.getOriginalWordList().get(j));
                        doneButton.setTextColor(getResources().getColor(R.color.subgrid));
                        for (int k = 0; k < sCheckBoxes.size(); k++)
                            sCheckBoxes.get(k).setEnabled(true);
                    }
                }
            });
        }

    }
    private void enterDeletionMode() {
        isDeletionMode = true;
        mLinearLayout_checkboxes.removeAllViews();
        final List<TextView> textViews = new ArrayList<>();
        final Drawable drawable = getResources().getDrawable(R.drawable.delete_word);

        drawable.setBounds(7, 5, 73, 71);
        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,14,0,14);//
        for(Word word : WordList.getOriginalWordList()) {
            final TextView textView = new TextView(this);
            textView.setText("   " + word.toString());
            textView.setTextSize(17);
            textView.setLayoutParams(lp);
            textView.setTextColor(getResources().getColor(R.color.checkBox));
            textView.setCompoundDrawables(drawable,null,null,null);
            textViews.add(textView);
            mLinearLayout_checkboxes.addView(textView);
        }
        setDeletionModeListener(textViews);
    }

    private void setDeletionModeListener(final List<TextView> textViews) {
        for(int i = 0; i < sCheckBoxes.size(); i++) {
            final int j = i;
            final TextView textView = textViews.get(i);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mLinearLayout_checkboxes.removeView(textView);
                    sCheckBoxes.remove(j);
                    WordList.getOriginalWordList().remove(j);
                    if(WordList.getSelectedWordList().size() > j)
                        WordList.getSelectedWordList().remove(j);
                    textViews.remove(j);
                    setDeletionModeListener(textViews);
                }
            });
        }
    }

    public void saveArray() {
        final List<Word> list = WordList.getOriginalWordList();
        SharedPreferences sp = this.getSharedPreferences("wordList", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("Size",list.size());
        for(int i = 0; i < list.size(); i++) {
            editor.remove("English" + i);
            editor.putString("English" + i, list.get(i).getEnglish());
            editor.remove("French" + i);
            editor.putString("French" + i, list.get(i).getFrench());
            editor.remove("Score" + i);
            editor.putInt("Score" + i, list.get(i).getScore());
        }
        editor.apply();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(mWordListWindow.isShowing())
                if(isDeletionMode)
                    finishDelButton.performClick();
                else
                    backButton.performClick();
            else
                finish();
        }
        return true;
    }

}
