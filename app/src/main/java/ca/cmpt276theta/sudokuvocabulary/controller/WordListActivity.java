package ca.cmpt276theta.sudokuvocabulary.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import ca.cmpt276theta.sudokuvocabulary.R;
import ca.cmpt276theta.sudokuvocabulary.model.Word;
import ca.cmpt276theta.sudokuvocabulary.model.WordList;

public class WordListActivity extends AppCompatActivity {
    private Button finishDelButton;
    private boolean isDeletionMode;
    private static List<TextView> sTextViews;
    private LinearLayout mLinearLayout_textViewList;
    private static final int READ_REQUEST_CODE = 42;
    final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    public static void setTextViews(List<TextView> textViews) {
        WordListActivity.sTextViews = textViews;
    }

    public static List<TextView> getTextViews() {
        return sTextViews;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLinearLayout_textViewList.removeAllViews();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);
        mLinearLayout_textViewList = findViewById(R.id.text_view_list);
        lp.setMargins(0,15,0,15);
        initWordList();
    }

    private void initWordList() {
        isDeletionMode = false;
        loadTextViews();
        final Button doneButton = findViewById(R.id.button_done);
        finishDelButton = findViewById(R.id.button_finish_deletion);
        final Animation enterAnim1 = AnimationUtils.loadAnimation(WordListActivity.this, R.anim.word_list_side_button_enter_anim);
        final Animation enterAnim2 = AnimationUtils.loadAnimation(WordListActivity.this, R.anim.word_list_side_button_enter_anim);
        enterAnim2.setStartOffset(250);
        final Animation enterAnim3 = AnimationUtils.loadAnimation(WordListActivity.this, R.anim.word_list_side_button_enter_anim);
        enterAnim3.setStartOffset(500);
        final Animation enterAnim4 = AnimationUtils.loadAnimation(WordListActivity.this, R.anim.word_list_logo_enter_anim);
        final Animation exitAnim = AnimationUtils.loadAnimation(WordListActivity.this, R.anim.word_list_side_button_exit_anim);
        final ImageView wordListLogo = findViewById(R.id.word_list_logo);

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
                                    sTextViews.get(j).setText(WordList.getOriginalWordList().get(j).toString());
                                loadTextViews();
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
                    finish();
            }
        });

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(WordListActivity.this)
                .setTitle(R.string.alert_title)
                .setMessage(R.string.alert_content)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sTextViews.clear();
                        WordList.getOriginalWordList().clear();
                        WordList.getSelectedWordList().clear();
                        GameStartActivity.getCheckBoxes().clear();
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
                doneButton.setVisibility(View.GONE);
                finishDelButton.setVisibility(View.VISIBLE);
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
                sortButton.setVisibility(View.VISIBLE);
                importButton.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);
                sortButton.startAnimation(enterAnim1);
                importButton.startAnimation(enterAnim2);
                deleteButton.startAnimation(enterAnim3);
                deleteAllButton.startAnimation(exitAnim);
                finishDelButton.setVisibility(View.GONE);
                doneButton.setVisibility(View.VISIBLE);
                sTextViews.clear();
                mLinearLayout_textViewList.removeAllViews();
                final int size = WordList.getOriginalWordList().size();
                for(int i = 0; i < size; i++) {
                    final TextView textView = new TextView(WordListActivity.this);
                    textView.setText(WordList.getOriginalWordList().get(i).toString());
                    textView.setLayoutParams(lp);
                    textView.setTextSize(18);
                    sTextViews.add(textView);
                    mLinearLayout_textViewList.addView(textView);
                }

                saveArray();
                new Handler().postDelayed(new Runnable(){
                    public void run(){
                        deleteAllButton.clearAnimation();
                        deleteAllButton.setVisibility(View.GONE);
                    }
                },600);
            }


        });

        final TextView title = findViewById(R.id.word_list_title);

        sortButton.setVisibility(View.VISIBLE);
        importButton.setVisibility(View.VISIBLE);
        deleteButton.setVisibility(View.VISIBLE);
        sortButton.startAnimation(enterAnim1);
        importButton.startAnimation(enterAnim2);
        deleteButton.startAnimation(enterAnim3);
        wordListLogo.startAnimation(enterAnim4);
        title.startAnimation(enterAnim1);
    }

    private void loadTextViews() {
        mLinearLayout_textViewList.removeAllViews();
        for(TextView textView : sTextViews)
            mLinearLayout_textViewList.addView(textView);
    }

    private void enterDeletionMode() {
        isDeletionMode = true;
        mLinearLayout_textViewList.removeAllViews();
        final Drawable drawable = getResources().getDrawable(R.drawable.delete_word);
        final int[] attrs = new int[] { android.R.attr.selectableItemBackground };
        final TypedArray a = getTheme().obtainStyledAttributes(attrs);
        drawable.setBounds(7, 5, 74, 72);
        for(TextView textView : sTextViews) {
            textView.setBackground(a.getDrawable(0));
            textView.setCompoundDrawables(drawable,null,null,null);
            textView.setCompoundDrawablePadding(17);
            mLinearLayout_textViewList.addView(textView);
        }
        setDeletionModeListener();
    }

    private void setDeletionModeListener() {
        final List<CheckBox> checkBoxes = GameStartActivity.getCheckBoxes();
        for(int i = 0; i < sTextViews.size(); i++) {
            final int j = i;
            final TextView textView = sTextViews.get(i);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mLinearLayout_textViewList.removeView(textView);
                    sTextViews.remove(j);
                    checkBoxes.remove(j);
                    WordList.getOriginalWordList().remove(j);
                    setDeletionModeListener();
                }
            });
        }
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
        Toast.makeText(this, "Words have been imported!", Toast.LENGTH_SHORT).show();
        saveArray();
        loadTextViews();
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
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(isDeletionMode)
                finishDelButton.performClick();
            else
                finish();
        }
        return true;
    }
}
