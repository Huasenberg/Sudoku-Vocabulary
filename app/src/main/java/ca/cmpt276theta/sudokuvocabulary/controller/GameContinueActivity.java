package ca.cmpt276theta.sudokuvocabulary.controller;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ca.cmpt276theta.sudokuvocabulary.R;
import ca.cmpt276theta.sudokuvocabulary.model.GameData;
import ca.cmpt276theta.sudokuvocabulary.model.GameDataList;
import ca.cmpt276theta.sudokuvocabulary.view.GameThumbnailView;

public class GameContinueActivity extends AppCompatActivity {

    private int selection;
    private String gameMode;

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
        setContentView(R.layout.activity_continue_game);
        final List<GameData> gameDataList = GameDataList.getGameDataList();
        final int size = gameDataList.size();
        selection = size - 1;
        final Intent intent = new Intent(GameContinueActivity.this, GameActivity.class);
        final ViewPager viewPager = findViewById(R.id.viewpager2);
        final DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            viewPager.setLayoutParams(new LinearLayout.LayoutParams(metrics.widthPixels, metrics.widthPixels));
        else
            viewPager.setLayoutParams(new LinearLayout.LayoutParams(metrics.heightPixels, metrics.heightPixels));
        final List<View> viewList = new ArrayList<>();
        final LayoutInflater inflater = getLayoutInflater();
        final TextView gameProperty = findViewById(R.id.game_property);
        final View view1 = inflater.inflate(R.layout.saved_game_1, null);
        final View view2 = inflater.inflate(R.layout.saved_game_2, null);
        final View view3 = inflater.inflate(R.layout.saved_game_3, null);
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        final FrameLayout[] frameLayouts = {view1.findViewById(R.id.thumbnail1), view2.findViewById(R.id.thumbnail2), view3.findViewById(R.id.thumbnail3)};
        final TextView[] textViews = {view1.findViewById(R.id.saved_time1), view2.findViewById(R.id.saved_time2), view3.findViewById(R.id.saved_time3)};
        gameMode = gameDataList.get(selection).isListenMode() ? "   |   Listening Mode" : "    |    Reading Mode";
        gameProperty.setText("Difficulty: " + String.valueOf(gameDataList.get(selection).getDifficulty()) + gameMode);
        for (int i = 0; i < size; i++) {
            GameThumbnailView gameThumbnailView = new GameThumbnailView(this, gameDataList.get(i));
            textViews[i].setText(gameDataList.get(i).getSavedTime());
            frameLayouts[i].addView(gameThumbnailView);
        }
        textViews[size - 1].setText(textViews[size - 1].getText() + " (Most Recent)");
        final PagerAdapter pagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return size;
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(viewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewList.get(position));
                return viewList.get(position);
            }
        };

        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(2);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                gameMode = gameDataList.get(i).isListenMode() ? "   |   Listening Mode" : "    |    Reading Mode";
                gameProperty.setText("Difficulty: " + String.valueOf(gameDataList.get(i).getDifficulty()) + gameMode);
                selection = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        final Button start = findViewById(R.id.button_start2);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("savedGame", selection);
                startActivity(intent);
                finish();
            }
        });

    }
}
