package ca.cmpt276theta.sudokuvocabulary.view;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Locale;

import ca.cmpt276theta.sudokuvocabulary.R;
import ca.cmpt276theta.sudokuvocabulary.controller.TTSHandler;
import ca.cmpt276theta.sudokuvocabulary.model.GameData;
import ca.cmpt276theta.sudokuvocabulary.model.GameSettings;
import ca.cmpt276theta.sudokuvocabulary.model.WordList;

public class HighlightView extends View {
    private float mGridWidth;
    private float mGridHeight;
    private int mTouchPositionX;
    private int mTouchPositionY;
    private final boolean isLandscapeMode;
    private final int gridSize;
    private final GameData mGameData;
    private final int subGridSizeHori;
    private final TTSHandler mTTSHandler;
    private final int subGridSizeVerti;
    private final boolean isDuplicHighli = GameSettings.isIsDuplicHighli();
    private final Handler handler = new Handler();
    private float tempX = 0;
    private float tempY = 0;
    private final HintView mHintView;

    private final Runnable longPressed = new Runnable() {
        public void run() {
            if (mGameData.isListenMode())
                readWord();
            else {
                mHintView.setTouchPosition(mTouchPositionX, mTouchPositionY);
                mHintView.setLongPress(true);
                final int index = mGameData.getPuzzle()[mTouchPositionY][mTouchPositionX] - 1;
                if(mGameData.getSavedTime() == null && index != -1)
                    WordList.getSelectedWordList().get(index).addOneScore();
                mHintView.invalidate();
            }
        }
    };

    public int getTouchPositionX() {
        return mTouchPositionX;
    }

    public TTSHandler getTTSHandler() {
        return mTTSHandler;
    }

    public int getTouchPositionY() {
        return mTouchPositionY;
    }
    public void setTouchPosition(int x, int y) {
        mTouchPositionX = x;
        mTouchPositionY = y;
    }
    public HighlightView(Context context, HintView hintView, GameData gameData) {
        super(context);
        mHintView = hintView;
        mGameData = gameData;
        gridSize = gameData.getGridSize();
        mTouchPositionX = -1;
        mTouchPositionY = -1;
        mTTSHandler = new TTSHandler(context);
        subGridSizeHori = mGameData.getSubGridSizeHori();
        subGridSizeVerti = mGameData.getSubGridSizeVerti();
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
        drawHighlight(canvas);
        if(isDuplicHighli)
            drawConflict(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mTouchPositionX = (int) (event.getX() / mGridWidth);
        mTouchPositionY = (int) (event.getY() / mGridHeight);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handler.postDelayed(longPressed, 500);
                tempX = mTouchPositionX;
                tempY = mTouchPositionY;
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(tempX - mTouchPositionX) > 0 || Math.abs(tempY - mTouchPositionY) > 0) {
                    handler.removeCallbacks(longPressed);
                    mHintView.setLongPress(false);
                    mHintView.invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                mHintView.setLongPress(false);
                handler.removeCallbacks(longPressed);
                mHintView.setVibrated(false);
        }
        if (mTouchPositionX < 0 || mTouchPositionY < 0 || mTouchPositionX > (gridSize - 1) || mTouchPositionY > (gridSize - 1)) {
            mTouchPositionX = -1;
            mTouchPositionY = -1;
        }
        this.invalidate();
        mHintView.invalidate();
        return true;
    }

    private void drawHighlight(Canvas canvas) {
        if (mTouchPositionX != -1) {
            final Paint highlightPaint = new Paint();
            highlightPaint.setColor(getResources().getColor(R.color.highlightRec));
            highlightPaint.setAlpha(80);
            canvas.drawRect(mTouchPositionX * mGridWidth, 0, (mTouchPositionX + 1) * mGridWidth,
                    gridSize * mGridHeight, highlightPaint);
            canvas.drawRect(0, mTouchPositionY * mGridHeight, gridSize * mGridWidth,
                    (mTouchPositionY + 1) * mGridHeight, highlightPaint);
        }
    }

    private void drawConflict(Canvas canvas) {
        if (mTouchPositionX != -1 && !(mGameData.getPuzzle()[mTouchPositionY][mTouchPositionX] == 0)) {
            final Paint conflictPaint = new Paint();
            conflictPaint.setColor(getResources().getColor(R.color.conflict));
            final int key = mGameData.getPuzzle()[mTouchPositionY][mTouchPositionX];
            for (int i = 0; i < gridSize; i++) {
                if ((key == mGameData.getPuzzle()[i][mTouchPositionX]) && i != mTouchPositionY)
                    canvas.drawRect(mTouchPositionX * mGridWidth, i * mGridHeight,
                            (mTouchPositionX + 1) * mGridWidth, (i + 1) * mGridHeight, conflictPaint);
                if ((key == mGameData.getPuzzle()[mTouchPositionY][i]) && i != mTouchPositionX)
                    canvas.drawRect(i * mGridWidth, mTouchPositionY * mGridHeight,
                            (i + 1) * mGridWidth, (mTouchPositionY + 1) * mGridHeight, conflictPaint);
            }

            final int firstCellOfSubgridX = mTouchPositionX / subGridSizeHori * subGridSizeHori;
            final int firstCellOfSubgridY = mTouchPositionY / subGridSizeVerti * subGridSizeVerti;
            for (int i = 0; i < subGridSizeVerti; i++) {
                if (firstCellOfSubgridY + i != mTouchPositionY) {
                    final float topPosition = (firstCellOfSubgridY + i) * mGridHeight;
                    final float bottomPosition = (firstCellOfSubgridY + i + 1) * mGridHeight;
                    for (int j = 0; j < subGridSizeHori; j++) {
                        final float rightPosition = (firstCellOfSubgridX + j + 1) * mGridWidth;
                        final float leftPosition = (firstCellOfSubgridX + j) * mGridWidth;
                        if ((firstCellOfSubgridX + j != mTouchPositionX) && (key == mGameData.getPuzzle()[firstCellOfSubgridY + i][firstCellOfSubgridX + j]))
                            canvas.drawRect(leftPosition, topPosition, rightPosition, bottomPosition, conflictPaint);
                    }
                }
            }

        }
    }
    private void readWord() {
        if (mTouchPositionX != -1 && mTouchPositionY != -1 && mGameData.getPuzzlePreFilled()[mTouchPositionY][mTouchPositionX] != 0) {
            Locale locale = Locale.US;
            if (mGameData.getLanguageMode() == 1)
                locale = Locale.FRENCH;
            mTTSHandler.speak(mGameData.getLanguageA()[mGameData.getPuzzle()[mTouchPositionY][mTouchPositionX] - 1], locale);
            Toast toast = Toast.makeText(getContext(), "Reading...", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 210);
            toast.show();
        }
    }
}
