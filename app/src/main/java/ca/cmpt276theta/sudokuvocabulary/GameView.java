package ca.cmpt276theta.sudokuvocabulary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;

import static android.content.Context.VIBRATOR_SERVICE;

public class GameView extends View {

    private float mGridWidth;
    private final int mTableMargin;
    private int mTouchPositionX;
    private int mTouchPositionY;
    private GameData mGameData;
    private boolean isLongPress;
    private boolean isVibrated;
    private final Vibrator mVibrator;

    public GameView(Context context) {
        super(context);
        isLongPress = false;
        isVibrated = false;
        mVibrator = (Vibrator) this.getContext().getSystemService(VIBRATOR_SERVICE);
        mTableMargin = 13;
        mTouchPositionX = -1;
        mTouchPositionY = -1;
    }

    public void setGameData(GameData gameData) {
        this.mGameData = gameData;
    }

    public int getTouchPositionX() {
        return mTouchPositionX;
    }

    public int getTouchPositionY() {
        return mTouchPositionY;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mGridWidth = (w - mTableMargin * 2) / 9f;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawHighlight(canvas);
        drawConflict(canvas);
        drawGrid(canvas);
        drawWord(canvas);
        if(isLongPress)
            drawHint(canvas);
        super.onDraw(canvas);
    }

    float tempX, tempY = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                tempX = (int) ((event.getX() - mTableMargin) / mGridWidth);
                tempY = (int) ((event.getY() - mTableMargin) / mGridWidth);
                return true;
            case MotionEvent.ACTION_MOVE:
                mTouchPositionX = (int) ((event.getX() - mTableMargin) / mGridWidth);
                mTouchPositionY = (int) ((event.getY() - mTableMargin) / mGridWidth);
                if(event.getEventTime() - event.getDownTime() >= 630)
                    isLongPress = true;
                if(Math.abs(tempX - mTouchPositionX) > 0 || Math.abs(tempY - mTouchPositionY) > 0)
                    isLongPress = false;
                break;
            case MotionEvent.ACTION_UP:
                isLongPress = false;
                isVibrated = false;
                break;
        }
        if(mTouchPositionX < 0 || mTouchPositionY < 0 || mTouchPositionX > 8 || mTouchPositionY > 8) {
            mTouchPositionX = -1;
            mTouchPositionY = -1;
        }
        this.invalidate();
        return super.onTouchEvent(event);
    }

    private void drawHighlight(Canvas canvas) {
        if(mTouchPositionX != -1) {
            Paint highlightPaint = new Paint();
            highlightPaint.setColor(getResources().getColor(R.color.highlightRec));
            highlightPaint.setAlpha(80);
            canvas.drawRect(mTableMargin + mTouchPositionX * mGridWidth, mTableMargin, mTableMargin + (mTouchPositionX + 1) * mGridWidth,
                    mTableMargin + 9 * mGridWidth, highlightPaint);
            canvas.drawRect(mTableMargin, mTableMargin + mTouchPositionY * mGridWidth, mTableMargin + 9 * mGridWidth,
                    mTableMargin + (mTouchPositionY + 1) * mGridWidth, highlightPaint);
        }
    }

    private void drawConflict(Canvas canvas) {
        if(mTouchPositionX != -1 && !mGameData.getGridContent(mTouchPositionY, mTouchPositionX).second.equals(" ")) {
            Paint conflictPaint = new Paint();
            conflictPaint.setColor(getResources().getColor(R.color.conflict));
            for(int i = 0; i < 9; i++) {
                if (mGameData.getGridContent(mTouchPositionY, mTouchPositionX).first.equals(mGameData.getGridContent(i, mTouchPositionX).first) && i != mTouchPositionY)
                    canvas.drawRect(mTableMargin + mTouchPositionX * mGridWidth, mTableMargin + i * mGridWidth,
                        mTableMargin + (mTouchPositionX + 1) * mGridWidth, mTableMargin + (i + 1) * mGridWidth, conflictPaint);
                if (mGameData.getGridContent(mTouchPositionY, mTouchPositionX).first.equals(mGameData.getGridContent(mTouchPositionY, i).first) && i != mTouchPositionX)
                    canvas.drawRect(mTableMargin + i * mGridWidth, mTableMargin + mTouchPositionY * mGridWidth,
                        mTableMargin + (i + 1) * mGridWidth, mTableMargin + (mTouchPositionY + 1) * mGridWidth, conflictPaint);
            }

            int firstCellOfSubgridX = mTouchPositionX / 3 * 3;
            int firstCellOfSubgridY = mTouchPositionY / 3 * 3;
            for(int i = 0; i < 3; i++) {
                if(firstCellOfSubgridX + i != mTouchPositionX) {
                    if (mGameData.getGridContent(mTouchPositionY, mTouchPositionX).first.equals(mGameData.getGridContent(firstCellOfSubgridY, firstCellOfSubgridX + i).first) && (firstCellOfSubgridY != mTouchPositionY))
                        canvas.drawRect(mTableMargin + (firstCellOfSubgridX + i) * mGridWidth, mTableMargin + firstCellOfSubgridY * mGridWidth,
                                mTableMargin + (firstCellOfSubgridX + i + 1) * mGridWidth, mTableMargin + (firstCellOfSubgridY + 1) * mGridWidth, conflictPaint);
                    if (mGameData.getGridContent(mTouchPositionY, mTouchPositionX).first.equals(mGameData.getGridContent(firstCellOfSubgridY + 1, firstCellOfSubgridX + i).first) && (firstCellOfSubgridY + 1 != mTouchPositionY))
                        canvas.drawRect(mTableMargin + (firstCellOfSubgridX + i) * mGridWidth, mTableMargin + (firstCellOfSubgridY + 1) * mGridWidth,
                                mTableMargin + (firstCellOfSubgridX + i + 1) * mGridWidth, mTableMargin + (firstCellOfSubgridY + 2) * mGridWidth, conflictPaint);
                    if (mGameData.getGridContent(mTouchPositionY, mTouchPositionX).first.equals(mGameData.getGridContent(firstCellOfSubgridY + 2, firstCellOfSubgridX + i).first) && (firstCellOfSubgridY + 2 != mTouchPositionY))
                        canvas.drawRect(mTableMargin + (firstCellOfSubgridX + i) * mGridWidth, mTableMargin + (firstCellOfSubgridY + 2) * mGridWidth,
                                mTableMargin + (firstCellOfSubgridX + i + 1) * mGridWidth, mTableMargin + (firstCellOfSubgridY + 3) * mGridWidth, conflictPaint);
                }
            }

        }
    }

    private void drawGrid(Canvas canvas) {
        // draw the border
        Paint borderPaint = new Paint();
        borderPaint.setColor(getResources().getColor(R.color.border));
        borderPaint.setStrokeWidth(7);
        float GirdEndEdge = mTableMargin + mGridWidth * 9;
        for(int i = 0; i <= 3; i++) {
            canvas.drawLine(mTableMargin + i * mGridWidth * 3, mTableMargin,
                    mTableMargin + i * mGridWidth * 3, GirdEndEdge, borderPaint);
            canvas.drawLine(mTableMargin, mTableMargin + i * mGridWidth * 3,
                    GirdEndEdge, mTableMargin + i * mGridWidth * 3, borderPaint);
        }

        // draw the subgrid
        Paint subgridPaint = new Paint();
        subgridPaint.setColor(getResources().getColor(R.color.subgrid));
        for(int i = 1; i < 9; i++) {
            canvas.drawLine(mTableMargin + i * mGridWidth, mTableMargin, mTableMargin + i * mGridWidth,
                    GirdEndEdge, subgridPaint );
            canvas.drawLine(mTableMargin, mTableMargin + i * mGridWidth,GirdEndEdge,
                    mTableMargin + i * mGridWidth, subgridPaint );
        }
    }

    private void drawWord(Canvas canvas) {
        Paint wordPaint = new Paint();
        wordPaint.setAntiAlias(true);
        wordPaint.setTextAlign(Paint.Align.CENTER);
        wordPaint.setTextSize(mGridWidth * 0.25f);
        float y = mGridWidth / 2 - (wordPaint.getFontMetrics().top + wordPaint.getFontMetrics().bottom) / 2;
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                String word = mGameData.getGridContent(i,j).second;
                wordPaint.setColor(getResources().getColor(R.color.border));
                wordPaint.setFakeBoldText(false);
                if(word.length() < 7 && (!word.contains("m")) || word.length() < 5)
                    wordPaint.setTextSize(mGridWidth * 0.29f);
                else
                    wordPaint.setTextSize(mGridWidth * 0.25f);
                if(mGameData.getPuzzle(i,j) != 0) {
                    wordPaint.setColor(getResources().getColor(R.color.preFilledWord));
                    wordPaint.setFakeBoldText(true);
                }
                canvas.drawText(word, mTableMargin + (j + 0.5f) * mGridWidth, mTableMargin + (i * mGridWidth + y), wordPaint);
            }
        }
    }

    private void drawHint(Canvas canvas) {
        if(mTouchPositionX != -1 && !mGameData.getGridContent(mTouchPositionY, mTouchPositionX).second.equals(" ")) {
            if(!isVibrated) {
                mVibrator.vibrate(65);
                isVibrated = true;
            }
            Paint bubblePaint = new Paint();
            bubblePaint.setColor(getResources().getColor(R.color.hintBubble));
            bubblePaint.setAntiAlias(true);
            Paint hintPaint = new Paint();
            hintPaint.setTextAlign(Paint.Align.CENTER);
            hintPaint.setColor(getResources().getColor(R.color.background));
            hintPaint.setTextSize(mGridWidth * 0.4f);
            hintPaint.setAntiAlias(true);
            hintPaint.setFakeBoldText(true);
            RectF rec;
            String hintWord1 = mGameData.getLanguageA(mGameData.getGridContent(mTouchPositionY, mTouchPositionX).first - 1).second;
            String hintWord2 = mGameData.getLanguageB(mGameData.getGridContent(mTouchPositionY, mTouchPositionX).first - 1).second;
            float hintWordPositionY1 = mTableMargin + mGridWidth * (mTouchPositionY - 0.93f);
            float hintWordPositionY2 = mTableMargin + mGridWidth * (mTouchPositionY - 0.43f);
            if(mTouchPositionX == 0) {
                rec = new RectF(mTableMargin, mTableMargin + mGridWidth * (mTouchPositionY - 1.4f),
                        mTableMargin + mGridWidth * (mTouchPositionX + 1.8f), mTableMargin + mGridWidth * (mTouchPositionY - 0.2f));
                canvas.drawRoundRect(rec, 25, 25, bubblePaint);
                canvas.drawText(hintWord1, mTableMargin + mGridWidth * (mTouchPositionX + 0.9f), hintWordPositionY1, hintPaint);
                hintPaint.setFakeBoldText(false);
                canvas.drawText(hintWord2, mTableMargin + mGridWidth * (mTouchPositionX + 0.9f), hintWordPositionY2, hintPaint);
            }
            else if(mTouchPositionX == 8){
                rec = new RectF(mTableMargin + mGridWidth * (mTouchPositionX - 0.8f), mTableMargin + mGridWidth * (mTouchPositionY - 1.4f),
                        mTableMargin + mGridWidth * 9, mTableMargin + mGridWidth * (mTouchPositionY - 0.2f));
                canvas.drawRoundRect(rec, 25, 25, bubblePaint);
                canvas.drawText(hintWord1, mTableMargin + mGridWidth * (mTouchPositionX + 0.15f), hintWordPositionY1, hintPaint);
                hintPaint.setFakeBoldText(false);
                canvas.drawText(hintWord2, mTableMargin + mGridWidth * (mTouchPositionX + 0.15f), hintWordPositionY2, hintPaint);
            }
            else {
                rec = new RectF(mTableMargin + mGridWidth * (mTouchPositionX - 0.4f), mTableMargin + mGridWidth * (mTouchPositionY - 1.4f),
                        mTableMargin + mGridWidth * (mTouchPositionX + 1.4f), mTableMargin + mGridWidth * (mTouchPositionY - 0.2f));
                canvas.drawRoundRect(rec, 25, 25, bubblePaint);
                canvas.drawText(hintWord1, mTableMargin + mGridWidth * (mTouchPositionX + 0.5f), hintWordPositionY1, hintPaint);
                hintPaint.setFakeBoldText(false);
                canvas.drawText(hintWord2, mTableMargin + mGridWidth * (mTouchPositionX + 0.5f), hintWordPositionY2, hintPaint);
            }

        }
    }
}
