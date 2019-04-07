package ca.cmpt276theta.sudokuvocabulary.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ca.cmpt276theta.sudokuvocabulary.R;
import ca.cmpt276theta.sudokuvocabulary.model.GameData;
import ca.cmpt276theta.sudokuvocabulary.model.GameDataGenerator;
import ca.cmpt276theta.sudokuvocabulary.model.Word;
import ca.cmpt276theta.sudokuvocabulary.model.WordList;

public class GameStartActivity extends AppCompatActivity {
    private List<CheckBox> checkBoxes;
    private int mOption;
    private LinearLayout mLinearLayout;
    private int mNumOfWords;
    private Button mStartButton;
    private Spinner mSpinner;
    private List<View> mViewList;
    private ViewPager mViewPager;
    private boolean isListenMode;
    private final int size = WordList.getOriginalWordList().size();
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLinearLayout.removeAllViews();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setEnterTransition(new Explode().setDuration(400));
        getWindow().setExitTransition(null);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_start);
        checkBoxes = new ArrayList<>();
        final Intent i = new Intent(GameStartActivity.this, GameActivity.class);
        mSpinner = findViewById(R.id.spinner);
        mLinearLayout = findViewById(R.id.checkboxs);
        mNumOfWords = 9;
        loadSpinner();
        loadCheckBoxes();
        isListenMode = false;
        final SeekBar seekBar = findViewById(R.id.seekBar);
        findViewById(R.id.radioRead).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isListenMode = false;
            }
        });
        findViewById(R.id.radioListen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isListenMode = true;
            }
        });
        mViewPager = findViewById(R.id.viewpager);
        mStartButton = findViewById(R.id.button_start);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStartButton.getCurrentTextColor() == getResources().getColor(R.color.subgrid)) {
                    mStartButton.startAnimation(AnimationUtils.loadAnimation(GameStartActivity.this, R.anim.button_shake_anim));
                    GameController.showMessageToast(GameStartActivity.this, "Must Select " + mNumOfWords + " Pairs of Words", Gravity.CENTER);
                } else {
                    i.putExtra("difficulty", seekBar.getProgress() + 1);
                    switch (mViewPager.getCurrentItem()) {
                        case 0:
                            GameDataGenerator.setSIZE(2, 2);
                            break;
                        case 1:
                            GameDataGenerator.setSIZE(2, 3);
                            break;
                        case 2:
                            GameDataGenerator.setSIZE(3, 3);
                            break;
                        case 3:
                            GameDataGenerator.setSIZE(4, 3);
                    }
                    GameDataGenerator.loadPuzzleData();
                    i.putExtra("languageMode", mOption);
                    i.putExtra("isListenMode", isListenMode);
                    startActivity(i);
                    finish();
                }
            }
        });
        if (WordList.getSelectedWordList().size() < mNumOfWords)
            mStartButton.setTextColor(getResources().getColor(R.color.subgrid));
        final TextView text = findViewById(R.id.textViewDif);
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

        final PagerAdapter pagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view == o;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mViewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mViewList.get(position));
                return mViewList.get(position);
            }
        };

        final LayoutInflater inflater = getLayoutInflater();
        final View view1 = inflater.inflate(R.layout.grid_size_setter, null);
        final View view2 = inflater.inflate(R.layout.grid_size_setter2, null);
        final View view3 = inflater.inflate(R.layout.grid_size_setter3, null);
        final View view4 = inflater.inflate(R.layout.grid_size_setter4, null);
        view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view3.startAnimation(AnimationUtils.loadAnimation(GameStartActivity.this, R.anim.button_shake_anim));
            }
        });
        mViewList = new ArrayList<>();
        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);
        mViewList.add(view4);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(2);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        mNumOfWords = 4;
                        break;
                    case 1:
                        mNumOfWords = 6;
                        break;
                    case 2:
                        mNumOfWords = 9;
                        break;
                    case 3:
                        mNumOfWords = 12;
                }
                cancelAllchecked();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        findViewById(R.id.random_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < size; i++)
                    checkBoxes.get(i).setChecked(false);
                final Random random = new Random();
                for(int i = 0; i < mNumOfWords; i++) {
                    while(true) {
                        final int randomInt = random.nextInt(size);
                        if (!checkBoxes.get(randomInt).isChecked()) {
                            checkBoxes.get(randomInt).setChecked(true);
                            break;
                        }
                    }
                }
            }
        });
    }

    private void loadSpinner() {
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown, GameData.getLanguagesList());
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                mOption = arg2;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private void loadCheckBoxes() {
        if (WordList.getOriginalWordList().isEmpty()) {
            findViewById(R.id.empty_image).setVisibility(View.VISIBLE);
            findViewById(R.id.random_select).setVisibility(View.GONE);
        }
        else {
            final List<Word> wordLists2 = WordList.getSelectedWordList();
            final List<Word> wordLists = WordList.getOriginalWordList();
            wordLists2.clear();
            for (int i = 0; i < size; i++) {
                final CheckBox checkBox = new CheckBox(this);
                checkBox.setText("   " + wordLists.get(i).toString());
                checkBox.setTextColor(getResources().getColor(R.color.check_box));
                checkBox.setTextSize(17);
                checkBoxes.add(checkBox);
                checkBox.setChecked(false);
                mLinearLayout.addView(checkBox);
                final int j = i;
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            wordLists2.add(WordList.getOriginalWordList().get(j));
                            if (wordLists2.size() == mNumOfWords) {
                                mStartButton.setTextColor(getResources().getColor(R.color.background));
                                for (int k = 0; k < size; k++)
                                    if (!checkBoxes.get(k).isChecked())
                                        checkBoxes.get(k).setEnabled(false);
                            }
                        } else {
                            wordLists2.remove(WordList.getOriginalWordList().get(j));
                            mStartButton.setTextColor(getResources().getColor(R.color.subgrid));
                            for (int k = 0; k < size; k++)
                                checkBoxes.get(k).setEnabled(true);
                        }
                    }
                });
            }
        }
    }

    private void cancelAllchecked(){
        for(CheckBox checkBox : checkBoxes)
            checkBox.setChecked(false);
    }
}
