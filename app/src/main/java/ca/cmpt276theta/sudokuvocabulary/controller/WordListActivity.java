package ca.cmpt276theta.sudokuvocabulary.controller;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ca.cmpt276theta.sudokuvocabulary.R;
import ca.cmpt276theta.sudokuvocabulary.model.Word;
import ca.cmpt276theta.sudokuvocabulary.model.WordList;

public class WordListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);
        final TextView title = findViewById(R.id.word_list_title);
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
                final AlertDialog alertDialog2 = new AlertDialog.Builder(MainMenuActivity.this)
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
                    GameController.showMessageToast(MainMenuActivity.this, "Must Select 9 Pairs of Words", Gravity.CENTER);
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

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainMenuActivity.this)
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
        final int[] attrs = new int[] { android.R.attr.selectableItemBackground };
        final TypedArray a = getTheme().obtainStyledAttributes(attrs);
        drawable.setBounds(7, 5, 73, 71);
        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,15,0,15);//
        for(Word word : WordList.getOriginalWordList()) {
            final TextView textView = new TextView(this);
            textView.setText("   " + word.toString());
            textView.setTextSize(16);
            textView.setLayoutParams(lp);
            textView.setBackground(a.getDrawable(0));
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
}
