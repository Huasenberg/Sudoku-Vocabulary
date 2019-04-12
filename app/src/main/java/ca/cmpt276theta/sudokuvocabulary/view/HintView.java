package ca.cmpt276theta.sudokuvocabulary.view;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Vibrator;
import android.view.View;

import ca.cmpt276theta.sudokuvocabulary.R;
import ca.cmpt276theta.sudokuvocabulary.model.GameData;
import ca.cmpt276theta.sudokuvocabulary.model.GameSettings;

import static android.content.Context.VIBRATOR_SERVICE;

public class HintView extends View {

    private final Vibrator mVibrator;
    private final boolean isLandscapeMode;

    private final int gridSize;
    private final boolean isVibraOpen = GameSettings.isIsVibraOpen();
    private float mGridWidth;
    private float mGridHeight;
    private int mTouchPositionX;
    private int mTouchPositionY;
    private GameData mGameData;
    private boolean isLongPress;
    private boolean isVibrated;

    public HintView(Context context, GameData gameData) {
        super(context);
        mGameData = gameData;
        isLandscapeMode = getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT;
        isLongPress = false;
        isVibrated = false;
        mVibrator = (Vibrator) this.getContext().getSystemService(VIBRATOR_SERVICE);
        mTouchPositionX = -1;
        mTouchPositionY = -1;
        gridSize = mGameData.getGridSize();
    }

    public void setLongPress(boolean longPress) {
        isLongPress = longPress;
    }

    public void setVibrated(boolean vibrated) {
        isVibrated = vibrated;
    }

    public void setTouchPosition(int x, int y) {
        mTouchPositionX = x;
        mTouchPositionY = y;
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
        if (isLongPress && mTouchPositionX != -1 && !(mGameData.getPuzzle()[mTouchPositionY][mTouchPositionX] == 0))
            drawHint(canvas);
    }

    private void drawHint(Canvas canvas) {
        if (!isVibrated && isVibraOpen) {
            mVibrator.vibrate(65);
            isVibrated = true;
        }
        final Paint bubblePaint = new Paint();
        bubblePaint.setColor(getResources().getColor(R.color.hintBubble));
        bubblePaint.setAntiAlias(true);
        bubblePaint.setAlpha(200);
        final Paint hintPaint = new Paint();
        hintPaint.setTextAlign(Paint.Align.CENTER);
        hintPaint.setColor(getResources().getColor(R.color.background));
        hintPaint.setTextSize(mGridWidth * 0.4f);
        hintPaint.setAntiAlias(true);
        hintPaint.setFakeBoldText(true);
        final RectF rec;
        final String hintWord1 = mGameData.getLanguageA()[mGameData.getPuzzle()[mTouchPositionY][mTouchPositionX] - 1];
        final String hintWord2 = mGameData.getLanguageB()[mGameData.getPuzzle()[mTouchPositionY][mTouchPositionX] - 1];
        if ((mTouchPositionY == 0 || mTouchPositionY == 1) && mTouchPositionX < ((gridSize + 1) / 2)) {
            final float x = mGridWidth * (mTouchPositionX + 2.15f);
            rec = new RectF(mGridWidth * (mTouchPositionX + 1.2f), mGridHeight * mTouchPositionY,
                    mGridWidth * (mTouchPositionX + 3.1f), mGridHeight * (mTouchPositionY + 1.2f));
            canvas.drawRoundRect(rec, 25, 25, bubblePaint);
            canvas.drawText(hintWord1, x, mGridHeight * (mTouchPositionY + 0.47f), hintPaint);
            hintPaint.setFakeBoldText(false);
            canvas.drawText(hintWord2, x, mGridHeight * (mTouchPositionY + 0.97f), hintPaint);
        } else if ((mTouchPositionY == 0 || mTouchPositionY == 1)) {
            final float x = mGridWidth * (mTouchPositionX - 1f);
            rec = new RectF(mGridWidth * mTouchPositionX, mGridHeight * mTouchPositionY,
                    mGridWidth * (mTouchPositionX - 2f), mGridHeight * (mTouchPositionY + 1.2f));
            canvas.drawRoundRect(rec, 25, 25, bubblePaint);
            canvas.drawText(hintWord1, x, mGridHeight * (mTouchPositionY + 0.47f), hintPaint);
            hintPaint.setFakeBoldText(false);
            canvas.drawText(hintWord2, x, mGridHeight * (mTouchPositionY + 0.97f), hintPaint);
        } else if (mTouchPositionX == 0) {
            final float x = mGridWidth * (mTouchPositionX + 0.9f);
            rec = new RectF(0, mGridHeight * (mTouchPositionY - 1.4f), mGridWidth * (mTouchPositionX + 1.8f), mGridHeight * (mTouchPositionY - 0.2f));
            canvas.drawRoundRect(rec, 25, 25, bubblePaint);
            canvas.drawText(hintWord1, x, mGridHeight * (mTouchPositionY - 0.93f), hintPaint);
            hintPaint.setFakeBoldText(false);
            canvas.drawText(hintWord2, x, mGridHeight * (mTouchPositionY - 0.43f), hintPaint);
        } else if (mTouchPositionX == (gridSize - 1)) {
            final float x = mGridWidth * (mTouchPositionX + 0.15f);
            rec = new RectF(mGridWidth * (mTouchPositionX - 0.8f), mGridHeight * (mTouchPositionY - 1.4f),
                    mGridWidth * 9, mGridHeight * (mTouchPositionY - 0.2f));
            canvas.drawRoundRect(rec, 25, 25, bubblePaint);
            canvas.drawText(hintWord1, x, mGridHeight * (mTouchPositionY - 0.93f), hintPaint);
            hintPaint.setFakeBoldText(false);
            canvas.drawText(hintWord2, x, mGridHeight * (mTouchPositionY - 0.43f), hintPaint);
        } else {
            final float x = mGridWidth * (mTouchPositionX + 0.5f);
            rec = new RectF(mGridWidth * (mTouchPositionX - 0.4f), mGridHeight * (mTouchPositionY - 1.4f),
                    mGridWidth * (mTouchPositionX + 1.4f), mGridHeight * (mTouchPositionY - 0.2f));
            canvas.drawRoundRect(rec, 25, 25, bubblePaint);
            canvas.drawText(hintWord1, x, mGridHeight * (mTouchPositionY - 0.93f), hintPaint);
            hintPaint.setFakeBoldText(false);
            canvas.drawText(hintWord2, x, mGridHeight * (mTouchPositionY - 0.43f), hintPaint);
        }
    }


}
