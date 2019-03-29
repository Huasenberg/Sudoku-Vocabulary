package ca.cmpt276theta.sudokuvocabulary.controller;

import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import ca.cmpt276theta.sudokuvocabulary.R;
import ca.cmpt276theta.sudokuvocabulary.model.GameData;
import ca.cmpt276theta.sudokuvocabulary.model.Word;
import ca.cmpt276theta.sudokuvocabulary.model.WordList;
import ca.cmpt276theta.sudokuvocabulary.view.GamePopupWindowView;

public class MainMenuActivity extends AppCompatActivity {
    private int mOption;
    private PopupWindow mDiffWindow;

    @Override
    protected void onPause() {
        super.onPause();
        if(mDiffWindow.isShowing())
            mDiffWindow.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        mDiffWindow = new GamePopupWindowView(LayoutInflater.from(this).inflate(R.layout.difficulty_popup,null), findViewById(R.id.mainLayout), RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, getWindow());

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

        findViewById(R.id.new_game).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(WordList.getSelectedWordList().size() != 9)
                    GameController.showMessageToast(MainMenuActivity.this, "Must Select 9 Pairs of Words From the Word List.", Gravity.NO_GRAVITY);
                else*/
                    mDiffWindow.showAtLocation(findViewById(R.id.mainLayout), Gravity.CENTER, 0, 0);
            }
        });

        findViewById(R.id.continue_game).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameController.showMessageToast(MainMenuActivity.this, "Coming soon!", Gravity.NO_GRAVITY);
            }
        });
        final Button wordListButton = findViewById(R.id.import_word_list);
        wordListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, WordListActivity.class),ActivityOptions.makeSceneTransitionAnimation(MainMenuActivity.this, wordListButton, "shared_elem").toBundle());
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

    /*private void initWordList() {
        isDeletionMode = false;
        loadCheckBoxes();
        final Button doneButton = findViewById(R.id.button_done);
        backButton = findViewById(R.id.button_back);
        finishDelButton = findViewById(R.id.button_finish_deletion);

        if(WordList.getSelectedWordList().size() < 9)
            doneButton.setTextColor(getResources().getColor(R.color.subgrid));


        final Animation enterAnim1 = AnimationUtils.loadAnimation(WordListActivity.this, R.anim.word_list_side_button_enter_anim);
        final Animation enterAnim2 = AnimationUtils.loadAnimation(WordListActivity.this, R.anim.word_list_side_button_enter_anim);
        enterAnim2.setStartOffset(200);
        final Animation enterAnim3 = AnimationUtils.loadAnimation(WordListActivity.this, R.anim.word_list_side_button_enter_anim);
        enterAnim3.setStartOffset(400);
        final Animation enterAnim4 = AnimationUtils.loadAnimation(WordListActivity.this, R.anim.word_list_button_enter_anim);
        final Animation exitAnim = AnimationUtils.loadAnimation(WordListActivity.this, R.anim.word_list_side_button_exit_anim);
        final Animation exitAnim2 = AnimationUtils.loadAnimation(WordListActivity.this, R.anim.word_list_button_exit_anim);

        final ImageButton sortButton = findViewById(R.id.button_sort);
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog2 = new AlertDialog.Builder(WordListActivity.this)
                        .setTitle(R.string.select_sort_order)
                        .setItems(new String[]{"Sort by alphabetical order", "Sort by difficulty score"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(i == 0)
                                    WordList.sortWordDataByAlphbet();
                                else
                                    WordList.sortWordDataByScore();
                                for(int j = 0; j < WordList.getOriginalWordList().size(); j++)
                                    sCheckBoxes.get(j).setText(WordList.getOriginalWordList().get(j).toString());
                                loadCheckBoxes();
                                saveArray();
                            }
                        })
                        .create();
                alertDialog2.show();
            }
        });

        final ImageButton importButton = findViewById(R.id.button_import);

        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performFileSearch();
            }
        });

        final ImageButton deleteButton = findViewById(R.id.button_delete_words);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(doneButton.getCurrentTextColor() == getResources().getColor(R.color.subgrid))
                    GameController.showMessageToast(WordListActivity.this, "Must Select 9 Pairs of Words", Gravity.CENTER);
                else {
                    sortButton.setVisibility(View.GONE);
                    importButton.setVisibility(View.GONE);
                    deleteButton.setVisibility(View.GONE);
                }
            }
        });


        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(WordListActivity.this)
                .setTitle(R.string.alert_title)
                .setMessage(R.string.alert_content)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sCheckBoxes.clear();
                        WordList.getOriginalWordList().clear();
                        WordList.getSelectedWordList().clear();
                        finishDelButton.performClick();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


        final ImageButton deleteAllButton = findViewById(R.id.button_delete_all);
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
                        deleteAllButton.clearAnimation();
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
        final Button doneButton = findViewById(R.id.button_done);
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

    }*/
}