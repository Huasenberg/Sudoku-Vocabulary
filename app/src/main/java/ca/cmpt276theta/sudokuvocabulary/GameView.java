package ca.cmpt276theta.sudokuvocabulary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {

    private float mGridWidth;
    private int mTableMargin;
    private int mTouchPositionX;
    private int mTouchPositionY;
    private GameData mGameData;

    public GameView(Context context) {
        super(context);
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
        mGridWidth = (w - mTableMargin * 2) / (float)9;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawHighlight(canvas);
        drawConflict(canvas);
        drawGrid(canvas);
        drawWord(canvas);
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mTouchPositionX = (int) ((event.getX() - mTableMargin) / mGridWidth);
        mTouchPositionY = (int) ((event.getY() - mTableMargin) / mGridWidth);
        if(mTouchPositionX < 0 || mTouchPositionX > 8 || mTouchPositionY < 0 || mTouchPositionY > 8)
            return false;
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
                        mTableMargin + mTouchPositionX * mGridWidth + mGridWidth, mTableMargin + i * mGridWidth + mGridWidth, conflictPaint);
                if (mGameData.getGridContent(mTouchPositionY, mTouchPositionX).first.equals(mGameData.getGridContent(mTouchPositionY, i).first) && i != mTouchPositionX)
                    canvas.drawRect(mTableMargin + i * mGridWidth, mTableMargin + mTouchPositionY * mGridWidth,
                        mTableMargin + i * mGridWidth + mGridWidth, mTableMargin + mTouchPositionY * mGridWidth + mGridWidth, conflictPaint);
            }

            int firstCellOfSubgridX = mTouchPositionX / 3 * 3;
            int firstCellOfSubgridY = mTouchPositionY / 3 * 3;
            for(int i = 0; i < 3; i++) {
                if(firstCellOfSubgridX + i != mTouchPositionX) {
                    if (mGameData.getGridContent(mTouchPositionY, mTouchPositionX).first.equals(mGameData.getGridContent(firstCellOfSubgridY, firstCellOfSubgridX + i).first) && (firstCellOfSubgridY != mTouchPositionY))
                        canvas.drawRect(mTableMargin + (firstCellOfSubgridX + i) * mGridWidth, mTableMargin + firstCellOfSubgridY * mGridWidth,
                                mTableMargin + (firstCellOfSubgridX + i) * mGridWidth + mGridWidth, mTableMargin + firstCellOfSubgridY * mGridWidth + mGridWidth, conflictPaint);
                    if (mGameData.getGridContent(mTouchPositionY, mTouchPositionX).first.equals(mGameData.getGridContent(firstCellOfSubgridY + 1, firstCellOfSubgridX + i).first) && (firstCellOfSubgridY + 1 != mTouchPositionY))
                        canvas.drawRect(mTableMargin + (firstCellOfSubgridX + i) * mGridWidth, mTableMargin + (firstCellOfSubgridY + 1) * mGridWidth,
                                mTableMargin + (firstCellOfSubgridX + i) * mGridWidth + mGridWidth, mTableMargin + (firstCellOfSubgridY + 1) * mGridWidth + mGridWidth, conflictPaint);
                    if (mGameData.getGridContent(mTouchPositionY, mTouchPositionX).first.equals(mGameData.getGridContent(firstCellOfSubgridY + 2, firstCellOfSubgridX + i).first) && (firstCellOfSubgridY + 2 != mTouchPositionY))
                        canvas.drawRect(mTableMargin + (firstCellOfSubgridX + i) * mGridWidth, mTableMargin + (firstCellOfSubgridY + 2) * mGridWidth,
                                mTableMargin + (firstCellOfSubgridX + i) * mGridWidth + mGridWidth, mTableMargin + (firstCellOfSubgridY + 2) * mGridWidth + mGridWidth, conflictPaint);
                }
            }

        }
    }

    private void drawGrid(Canvas canvas) {
        // draw the border
        Paint borderPaint = new Paint();
        borderPaint.setColor(getResources().getColor(R.color.border));
        borderPaint.setStrokeWidth(7);
        for(int i = 0; i <= 3; i++) {
            canvas.drawLine(mTableMargin + i * mGridWidth * 3, mTableMargin,
                    mTableMargin + i * mGridWidth * 3, mTableMargin + mGridWidth * 9, borderPaint);
            canvas.drawLine(mTableMargin, mTableMargin + i * mGridWidth * 3,
                    getMeasuredWidth() - mTableMargin, mTableMargin + i * mGridWidth * 3, borderPaint);
        }

        // draw the subgrid
        Paint subgridPaint = new Paint();
        subgridPaint.setColor(getResources().getColor(R.color.subgrid));
        for(int i = 1; i < 9; i++) {
            canvas.drawLine(mTableMargin + i * mGridWidth, mTableMargin, mTableMargin + i * mGridWidth,
                    mTableMargin + mGridWidth * 9, subgridPaint );
            canvas.drawLine(mTableMargin, mTableMargin + i * mGridWidth,getMeasuredWidth() - mTableMargin,
                    mTableMargin + i * mGridWidth, subgridPaint );
        }
    }

    private void drawWord(Canvas canvas) {
        Paint wordPaint = new Paint();
        wordPaint.setColor(getResources().getColor(R.color.border));
        wordPaint.setAntiAlias(true);
        wordPaint.setTextAlign(Paint.Align.CENTER);
        wordPaint.setTextSize(mGridWidth * 0.25f);
        float x = mGridWidth / 2;
        float y = mGridWidth / 2 - (wordPaint.getFontMetrics().top + wordPaint.getFontMetrics().bottom) / 2;
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                String word = mGameData.getGridContent(i,j).second;
                if(word.length() < 7 && (!word.contains("m")))
                    wordPaint.setTextSize(mGridWidth * 0.29f);
                else
                    wordPaint.setTextSize(mGridWidth * 0.25f);
                canvas.drawText(mGameData.getGridContent(i,j).second, mTableMargin + (j * mGridWidth + x), mTableMargin + (i * mGridWidth + y), wordPaint);
            }
        }
    }
}
