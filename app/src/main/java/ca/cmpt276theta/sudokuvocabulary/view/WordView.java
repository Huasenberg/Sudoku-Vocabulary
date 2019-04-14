package ca.cmpt276theta.sudokuvocabulary.view;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import ca.cmpt276theta.sudokuvocabulary.R;
import ca.cmpt276theta.sudokuvocabulary.model.GameData;

public class WordView extends View {

    private final int gridSize;
    private final GameData mGameData;
    private float mGridWidth;
    private float mGridHeight;
    private boolean isLandscapeMode;


    public WordView(Context context, GameData gameData) {
        super(context);
        mGameData = gameData;
        gridSize = gameData.getGridSize();
        isLandscapeMode = getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!isLandscapeMode)
            setMeasuredDimension(widthMeasureSpec, widthMeasureSpec);
        else
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mGridWidth = w / (float) gridSize;
        if (!isLandscapeMode)
            mGridHeight = mGridWidth;
        else
            mGridHeight = h / (float) gridSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawWord(canvas);
    }

    private void drawWord(Canvas canvas) {
        final Paint preFilledPaint = new Paint();
        preFilledPaint.setColor(getResources().getColor(R.color.word_bank));
        final Paint wordPaint = new Paint();
        wordPaint.setAntiAlias(true);
        wordPaint.setTextAlign(Paint.Align.CENTER);
        wordPaint.setTextSize(mGridWidth * 0.25f);
        wordPaint.setFakeBoldText(true);
        final float y = mGridHeight / 2 - (wordPaint.getFontMetrics().top + wordPaint.getFontMetrics().bottom) / 2;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                final String word = mGameData.getGridContent()[i][j];
                wordPaint.setColor(getResources().getColor(R.color.border));
                if (word.length() < 7 && (!word.contains("m")) || word.length() < 5)
                    wordPaint.setTextSize(mGridWidth * 0.29f);
                else
                    wordPaint.setTextSize(mGridWidth * 0.25f);
                if (mGameData.getPuzzlePreFilled()[i][j] != 0) {
                    wordPaint.setColor(getResources().getColor(R.color.colorPrimary));
                    //canvas.drawRect(j * mGridWidth, i * mGridHeight, (j + 1) * mGridWidth, (i + 1) * mGridHeight, preFilledPaint);
                }

                canvas.drawText(word, (j + 0.5f) * mGridWidth, (i * mGridHeight + y), wordPaint);
            }
        }
    }
}
