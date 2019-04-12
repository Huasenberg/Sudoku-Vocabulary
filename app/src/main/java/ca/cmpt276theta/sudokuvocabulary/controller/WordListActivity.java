package ca.cmpt276theta.sudokuvocabulary.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import ca.cmpt276theta.sudokuvocabulary.R;
import ca.cmpt276theta.sudokuvocabulary.model.Word;
import ca.cmpt276theta.sudokuvocabulary.model.WordList;

public class WordListActivity extends AppCompatActivity {
    final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private final int READ_REQUEST_CODE = 42;
    private List<TextView> textViews;
    private Button finishButton;
    private boolean isDeletionMode;
    private LinearLayout mLinearLayout_textViewList;
    private AlertDialog mAlertDialog2;
    private AlertDialog mAlertDialog;
    private AlertDialog mAlertDialog3;
    private List<Word> mWordList = WordList.getOriginalWordList();

    @Override
    protected void onPause() {
        super.onPause();
        if (mAlertDialog.isShowing())
            mAlertDialog.dismiss();
        if (mAlertDialog2.isShowing())
            mAlertDialog2.dismiss();
        if (mAlertDialog3 != null && mAlertDialog3.isShowing())
            mAlertDialog3.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isDeletionMode)
            finishButton.performClick();
        mLinearLayout_textViewList.removeAllViews();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);
        textViews = new ArrayList<>();
        mLinearLayout_textViewList = findViewById(R.id.text_view_list);
        lp.setMargins(0, 15, 0, 15);
        initWordList();
    }

    private void initWordList() {
        isDeletionMode = false;
        loadTextViews();
        final Button doneButton = findViewById(R.id.button_done);
        finishButton = findViewById(R.id.button_finish_deletion);
        final Animation enterAnim1 = AnimationUtils.loadAnimation(WordListActivity.this, R.anim.word_list_side_button_enter_anim);
        final Animation enterAnim2 = AnimationUtils.loadAnimation(WordListActivity.this, R.anim.word_list_side_button_enter_anim);
        enterAnim2.setStartOffset(230);
        final Animation enterAnim3 = AnimationUtils.loadAnimation(WordListActivity.this, R.anim.word_list_side_button_enter_anim);
        enterAnim3.setStartOffset(460);
        final Animation enterAnim4 = AnimationUtils.loadAnimation(WordListActivity.this, R.anim.word_list_side_button_enter_anim);
        enterAnim4.setStartOffset(690);
        final Animation exitAnim = AnimationUtils.loadAnimation(WordListActivity.this, R.anim.word_list_side_button_exit_anim);

        final ImageButton sortButton = findViewById(R.id.button_sort);
        mAlertDialog2 = new AlertDialog.Builder(WordListActivity.this)
                .setTitle(R.string.select_sort_order)
                .setItems(new String[]{"Sort by alphabetical order", "Sort by difficulty score"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0)
                            WordList.sortWordDataByAlphbet();
                        else
                            WordList.sortWordDataByScore();
                        loadTextViews();
                        saveArray();
                    }
                }).create();
        mAlertDialog2.getWindow().setBackgroundDrawableResource(R.drawable.pop_up_shape);
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog2.show();
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
        final ImageButton editButton = findViewById(R.id.button_set_words);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortButton.startAnimation(exitAnim);
                importButton.startAnimation(exitAnim);
                editButton.startAnimation(exitAnim);
                deleteButton.startAnimation(exitAnim);
                doneButton.setVisibility(View.GONE);
                finishButton.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        sortButton.setVisibility(View.GONE);
                        importButton.setVisibility(View.GONE);
                        deleteButton.setVisibility(View.GONE);
                        sortButton.setVisibility(View.GONE);
                    }
                }, 600);
                enterSettingMode();
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mAlertDialog = new AlertDialog.Builder(WordListActivity.this)
                .setTitle(R.string.alert_title)
                .setMessage(R.string.alert_content)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mWordList.clear();
                        finishButton.performClick();
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .create();


        final ImageButton deleteAllButton = findViewById(R.id.button_delete_all);
        deleteAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.show();
            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortButton.startAnimation(exitAnim);
                importButton.startAnimation(exitAnim);
                editButton.startAnimation(exitAnim);
                deleteButton.startAnimation(exitAnim);
                doneButton.setVisibility(View.GONE);
                finishButton.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        sortButton.setVisibility(View.GONE);
                        importButton.setVisibility(View.GONE);
                        deleteButton.setVisibility(View.GONE);
                        sortButton.setVisibility(View.GONE);
                    }
                }, 600);

                deleteAllButton.setVisibility(View.VISIBLE);
                deleteAllButton.startAnimation(enterAnim2);
                enterDeletionMode();
            }
        });

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDeletionMode = false;
                sortButton.setVisibility(View.VISIBLE);
                importButton.setVisibility(View.VISIBLE);
                editButton.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);
                sortButton.startAnimation(enterAnim1);
                importButton.startAnimation(enterAnim2);
                editButton.startAnimation(enterAnim3);
                deleteButton.startAnimation(enterAnim4);
                if (deleteAllButton.getVisibility() == View.VISIBLE)
                    deleteAllButton.startAnimation(exitAnim);
                finishButton.setVisibility(View.GONE);
                doneButton.setVisibility(View.VISIBLE);
                loadTextViews();
                saveArray();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        deleteAllButton.clearAnimation();
                        deleteAllButton.setVisibility(View.GONE);
                    }
                }, 600);
            }


        });

        final LinearLayout title = findViewById(R.id.word_list_title);
        sortButton.setVisibility(View.VISIBLE);
        importButton.setVisibility(View.VISIBLE);
        editButton.setVisibility(View.VISIBLE);
        deleteButton.setVisibility(View.VISIBLE);
        sortButton.startAnimation(enterAnim1);
        importButton.startAnimation(enterAnim2);
        editButton.startAnimation(enterAnim3);
        deleteButton.startAnimation(enterAnim4);
        title.startAnimation(enterAnim1);
    }

    private void loadTextViews() {
        mLinearLayout_textViewList.removeAllViews();
        textViews.clear();
        final int size = mWordList.size();
        for (int i = 0; i < size; i++) {
            final TextView textView = new TextView(this);
            textView.setText(mWordList.get(i).toString());
            textView.setTextSize(18);
            textView.setLayoutParams(lp);
            textViews.add(textView);
            mLinearLayout_textViewList.addView(textView);
        }

    }

    private void enterDeletionMode() {
        isDeletionMode = true;
        mLinearLayout_textViewList.removeAllViews();
        final Drawable drawable = getResources().getDrawable(R.drawable.delete_word);
        final int[] attrs = new int[]{android.R.attr.selectableItemBackground};
        final TypedArray a = getTheme().obtainStyledAttributes(attrs);
        drawable.setBounds(0, 5, 68, 72);
        for (TextView textView : textViews) {
            textView.setBackground(a.getDrawable(0));
            textView.setCompoundDrawables(drawable, null, null, null);
            textView.setCompoundDrawablePadding(17);
            mLinearLayout_textViewList.addView(textView);
        }
        setDeletionModeListener();
    }

    private void setDeletionModeListener() {
        for (int i = 0; i < textViews.size(); i++) {
            final int j = i;
            final TextView textView = textViews.get(i);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mLinearLayout_textViewList.removeView(textView);
                    textViews.remove(j);
                    WordList.getOriginalWordList().remove(j);
                    setDeletionModeListener();
                }
            });
        }
    }

    private void enterSettingMode() {
        mLinearLayout_textViewList.removeAllViews();
        isDeletionMode = true;
        final Drawable drawable = getResources().getDrawable(R.drawable.edit_word);
        final int[] attrs = new int[]{android.R.attr.selectableItemBackground};
        final TypedArray a = getTheme().obtainStyledAttributes(attrs);
        drawable.setBounds(0, 0, 53, 53);
        final int size = mWordList.size();
        for (int i = 0; i < size; i++) {
            final int j = i;
            final TextView textView = textViews.get(i);
            textView.setBackground(a.getDrawable(0));
            textView.setCompoundDrawables(drawable, null, null, null);
            textView.setCompoundDrawablePadding(17);
            mLinearLayout_textViewList.addView(textView);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final View textEntryView = LayoutInflater.from(WordListActivity.this).inflate(R.layout.edit_word_pair_popup, null);

                    final Word word = mWordList.get(j);
                    final EditText editScore = textEntryView.findViewById(R.id.edit_score);
                    editScore.setText(String.valueOf(word.getScore()));
                    editScore.setInputType(InputType.TYPE_CLASS_NUMBER);
                    final EditText editWord1 = textEntryView.findViewById(R.id.edit_word1);
                    editWord1.setText(word.getEnglish());
                    final EditText editWord2 = textEntryView.findViewById(R.id.edit_word2);
                    editWord2.setText(word.getFrench());
                    mAlertDialog3 = new AlertDialog.Builder(WordListActivity.this)
                            .setTitle(R.string.edit_word_pair)
                            .setView(textEntryView)
                            .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (!editScore.getText().toString().equals(""))
                                        word.setScore(Integer.parseInt(editScore.getText().toString()));
                                    word.setEnglish(editWord1.getText().toString());
                                    word.setFrench(editWord2.getText().toString());
                                    textView.setText(mWordList.get(j).toString());
                                }
                            }).setNegativeButton(R.string.cancel, null).create();
                    mAlertDialog3.show();
                }
            });
        }
    }

    private void performFileSearch() {

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
        writeToArrayList(this, reader);
        saveArray();
        loadTextViews();
    }

    private void saveArray() {

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = openFileOutput("word_list", MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(WordList.getOriginalWordList());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) oos.close();
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isDeletionMode)
                finishButton.performClick();
            else
                finish();
        }
        return true;
    }

    private void writeToArrayList(Context context, BufferedReader reader) {
        String line = "";
        try {
            // Step over headers
            reader.readLine();
            // If buffer is not empty
            while ((line = reader.readLine()) != null) {
                Log.d("My Activity", "Line: " + line);
                // use comma as separator columns of CSV
                String[] tokens = line.split(",");
                // Read the data
                final Word sample = new Word(tokens[1], tokens[2]);
                // Setters
                sample.setScore(Integer.parseInt(tokens[3]));
                if (mWordList.contains(sample))
                    continue;
                // Adding object to a class
                final TextView textView = new TextView(context);
                textView.setText(sample.toString());
                textView.setTextSize(18);
                textView.setLayoutParams(lp);
                textViews.add(textView);
                mWordList.add(sample);
                // Log the object
                Log.d("My Activity", "Just created: " + sample);
            }

        } catch (IOException e) {
            // Logs error with priority level
            Log.d("My Activity", "Error reading data file on line" + line, e);
            // Prints throwable details
            e.printStackTrace();
        }
    }
}
