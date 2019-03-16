package ca.cmpt276theta.sudokuvocabulary.view;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import ca.cmpt276theta.sudokuvocabulary.controller.GameController;
import ca.cmpt276theta.sudokuvocabulary.controller.Word;
import ca.cmpt276theta.sudokuvocabulary.model.GameData;
import ca.cmpt276theta.sudokuvocabulary.model.WordList;

public class MainMenuActivity extends AppCompatActivity {
    private int mOption;
    private static final int READ_REQUEST_CODE = 42;
    private PopupWindow mDiffWindow;
    private PopupWindow mWordListWindow;
    private static List<CheckBox> sCheckBoxes;
    private LinearLayout mLinearLayout_checkboxes;
    private List<LinearLayout> mLinearLayout_wordList;
    private List<ImageView> mDeleteIcon;
    private boolean isDeleteIconsLoaded = false;

    public static void setCheckBoxes(List<CheckBox> checkBoxes) {
        sCheckBoxes = checkBoxes;
    }

    public static List<CheckBox> getCheckBoxes() {
        return sCheckBoxes;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mDiffWindow.isShowing())
            mDiffWindow.dismiss();
        else if(mWordListWindow.isShowing())
            mWordListWindow.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        View diffView = LayoutInflater.from(this).inflate(R.layout.difficulty_popup, null);
        View wordListView = LayoutInflater.from(this).inflate(R.layout.wordlist_popup, null);
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        loadPopupWindows(diffView, wordListView, size);
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
        loadSpinner(spinner, mDiffWindow);
        mLinearLayout_checkboxes = mWordListWindow.getContentView().findViewById(R.id.checkboxs);
        mLinearLayout_wordList = new ArrayList<>();
        mDeleteIcon = new ArrayList<>();
        findViewById(R.id.new_game).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiffPopup(seekBar);
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
        GameController.writeToArrayList(this, reader);
        GameController.showMessageToast(this, " Words have been imported! ");
        saveArray(WordList.getOriginalWordList());
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

    private void showDiffPopup(final SeekBar seekBar) {
        final TextView text = mDiffWindow.getContentView().findViewById(R.id.textViewDif);
        mDiffWindow.showAtLocation(findViewById(R.id.mainLayout), Gravity.CENTER, 0, 0);
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
    //One Bug Here!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private void showWordListPopup () {
        loadCheckBoxes();
        final Button doneButton = mWordListWindow.getContentView().findViewById(R.id.button_done);
        if(WordList.getSelectedWordList().size() < 9)
            doneButton.setTextColor(getResources().getColor(R.color.subgrid));

        mWordListWindow.showAtLocation(findViewById(R.id.mainLayout), Gravity.CENTER, 0, 0);

        mWordListWindow.getContentView().findViewById(R.id.button_import).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performFileSearch();
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(doneButton.getCurrentTextColor() == getResources().getColor(R.color.subgrid))
                    GameController.showMessageToast(MainMenuActivity.this, " Must Choose Exactly 9 Pairs of Words ");
                else
                    mWordListWindow.dismiss();
            }
        });

        mWordListWindow.getContentView().findViewById(R.id.button_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWordListWindow.dismiss();
            }
        });


        mWordListWindow.getContentView().findViewById(R.id.button_delete_words).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isDeleteIconsLoaded) {
                    loadDeleteIcons();
                    isDeleteIconsLoaded = true;
                }
                else {
                    for(int i = 0; i < sCheckBoxes.size(); i++)
                        mLinearLayout_wordList.get(i).removeView(mDeleteIcon.get(i));
                    isDeleteIconsLoaded = false;
                }
            }
        });

        /*mWordListWindow.getContentView().findViewById(R.id.removeAll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sCheckBoxes.clear();
                WordList.getSelectedWordList().clear();
                WordList.getOriginalWordList().clear();
                saveArray(WordList.getOriginalWordList());
                loadCheckBoxes();
            }
        });*/
    }

    private void loadCheckBoxes() {
        mLinearLayout_checkboxes.removeAllViews();
        final List<Word> wordList = WordList.getSelectedWordList();
        final Button doneButton = mWordListWindow.getContentView().findViewById(R.id.button_done);
        for(int i = 0; i < sCheckBoxes.size(); i++) {
            mLinearLayout_wordList.add(new LinearLayout(this));
            mLinearLayout_wordList.get(i).setOrientation(LinearLayout.HORIZONTAL);
            mLinearLayout_wordList.get(i).removeView(sCheckBoxes.get(i));
            mLinearLayout_wordList.get(i).addView(sCheckBoxes.get(i));
            mLinearLayout_checkboxes.addView(mLinearLayout_wordList.get(i));

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

    private void loadDeleteIcons() {
        mDeleteIcon.clear();
        for(int i = 0; i < sCheckBoxes.size(); i++) {
            final ImageView imageView = new ImageView(this);
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.delete_word));
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(70,70);
            param.gravity = Gravity.CENTER;
            mLinearLayout_wordList.get(i).addView(imageView,0, param);
            mDeleteIcon.add(imageView);
        }
        setDeleteIconListener();
    }


    private void setDeleteIconListener(){
        for(int i = 0; i < sCheckBoxes.size(); i++) {
            final int j = i;
            mDeleteIcon.get(j).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mLinearLayout_wordList.remove(j);
                    sCheckBoxes.remove(j);
                    mDeleteIcon.remove(j);
                    WordList.getOriginalWordList().remove(j);
                    if(WordList.getSelectedWordList().size() > j)
                        WordList.getSelectedWordList().remove(j);
                    loadCheckBoxes();
                    setDeleteIconListener();
                }
            });
        }
    }

    private void setActivityBackGroundAlpha(float num) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = num;
        getWindow().setAttributes(lp);
    }

    public void saveArray(ArrayList<Word> list) {
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

    private void loadPopupWindows(View diffView, View wordListView, Point size) {
        mDiffWindow = new PopupWindow(diffView, size.x, size.y, true) {
            @Override
            public void dismiss() {
                super.dismiss();
                setActivityBackGroundAlpha(1);
            }
            @Override
            public void showAtLocation(View parent, int gravity, int x, int y) {
                setAnimationStyle(R.style.pop_animation);
                setActivityBackGroundAlpha(0.3f);
                super.showAtLocation(parent, gravity, x, y);
            }
        };

        mWordListWindow = new PopupWindow(wordListView, size.x, size.y, true) {
            @Override
            public void dismiss() {
                super.dismiss();
                setActivityBackGroundAlpha(1);
            }
            @Override
            public void showAtLocation(View parent, int gravity, int x, int y) {
                setAnimationStyle(R.style.pop_animation);
                setActivityBackGroundAlpha(0.3f);
                super.showAtLocation(parent, gravity, x, y);
            }
        };
    }


}
