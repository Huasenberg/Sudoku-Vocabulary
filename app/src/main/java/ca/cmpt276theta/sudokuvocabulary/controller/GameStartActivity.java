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

import ca.cmpt276theta.sudokuvocabulary.R;
import ca.cmpt276theta.sudokuvocabulary.model.GameData;
import ca.cmpt276theta.sudokuvocabulary.model.GameDataGenerator;
import ca.cmpt276theta.sudokuvocabulary.model.Word;
import ca.cmpt276theta.sudokuvocabulary.model.WordList;

public class GameStartActivity extends AppCompatActivity {
    private static List<CheckBox> sCheckBoxes;
    private int mOption;
    private LinearLayout mLinearLayout;
    private int mNumOfWords;
    private Button mStartButton;
    private Spinner mSpinner;
    private List<View> mViewList;
    private ViewPager mViewPager;

    public static List<CheckBox> getCheckBoxes() {
        return sCheckBoxes;
    }

    public static void setCheckBoxes(List<CheckBox> checkBoxes) {
        sCheckBoxes = checkBoxes;
    }

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
        mSpinner = findViewById(R.id.spinner);
        mLinearLayout = findViewById(R.id.checkboxs);
        mNumOfWords = 9;
        loadSpinner();
        loadCheckBoxes();
        final SeekBar seekBar = findViewById(R.id.seekBar);
        GameData.setListenMode(false);
        findViewById(R.id.radioRead).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameData.setListenMode(false);
            }
        });
        findViewById(R.id.radioListen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameData.setListenMode(true);
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
                    GameData.setDifficulty(seekBar.getProgress() + 1);
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
                    GameData.setLanguageMode(mOption);
                    GameDataGenerator.loadPuzzleData();
                    startActivity(new Intent(GameStartActivity.this, GameActivity.class));
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

        PagerAdapter pagerAdapter = new PagerAdapter() {
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
                loadCheckBoxes();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void loadSpinner() {
        GameData.loadLanguagesList();
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
        if (WordList.getOriginalWordList().isEmpty())
            findViewById(R.id.empty_image).setVisibility(View.VISIBLE);
        else {
            final List<Word> wordList = WordList.getSelectedWordList();
            for (int i = 0; i < sCheckBoxes.size(); i++) {
                sCheckBoxes.get(i).setChecked(false);
                mLinearLayout.removeView(sCheckBoxes.get(i));
                mLinearLayout.addView(sCheckBoxes.get(i));
                final int j = i;
                sCheckBoxes.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            wordList.add(WordList.getOriginalWordList().get(j));
                            if (wordList.size() == mNumOfWords) {
                                mStartButton.setTextColor(getResources().getColor(R.color.background));
                                for (int k = 0; k < sCheckBoxes.size(); k++)
                                    if (!sCheckBoxes.get(k).isChecked())
                                        sCheckBoxes.get(k).setEnabled(false);
                            }
                        } else {
                            wordList.remove(WordList.getOriginalWordList().get(j));
                            mStartButton.setTextColor(getResources().getColor(R.color.subgrid));
                            for (int k = 0; k < sCheckBoxes.size(); k++)
                                sCheckBoxes.get(k).setEnabled(true);
                        }
                    }
                });
            }
        }
    }
}
