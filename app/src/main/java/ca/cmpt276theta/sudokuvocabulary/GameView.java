package ca.cmpt276theta.sudokuvocabulary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {

    private float gridLength;
    private int touchPositionX;
    private int touchPositionY;

    public GameView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        gridLength = (w - 26) / (float)9;
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
        touchPositionX = (int) ((event.getX() - 13) / gridLength);
        touchPositionY = (int) ((event.getY() - 13) / gridLength);
        if(touchPositionX < 0 || touchPositionX > 8 || touchPositionY < 0 || touchPositionY > 8)
            return false;
        this.invalidate();
        GameMain.setPositionX(touchPositionX);
        GameMain.setPositionY(touchPositionY);
        return super.onTouchEvent(event);
    }

    private void drawHighlight(Canvas canvas) {
        if(touchPositionX != -1) {
            Paint highlightPaint = new Paint();
            highlightPaint.setColor(getResources().getColor(R.color.highlightRec));
            highlightPaint.setAlpha(80);
            canvas.drawRect(13 + touchPositionX * gridLength, 13, 13 + (touchPositionX + 1) * gridLength,
                    13 + 9 * gridLength, highlightPaint);
            canvas.drawRect(13, 13 + touchPositionY * gridLength, 13 + 9 * gridLength,
                    13 + (touchPositionY + 1) * gridLength, highlightPaint);
        }
    }

    private void drawConflict(Canvas canvas) {
        Paint conflictPaint = new Paint();
        conflictPaint.setColor(getResources().getColor(R.color.conflict));
        for(int i = 0; i < 9; i++) {

            if (GameData.getGridContent(touchPositionY, touchPositionX).equals(GameData.getGridContent(i, touchPositionX)) && i != touchPositionY)
                canvas.drawRect(13 + touchPositionX * gridLength, 13 + i * gridLength,
                        13 + touchPositionX * gridLength + gridLength, 13 + i * gridLength + gridLength, conflictPaint);

            if (GameData.getGridContent(touchPositionY, touchPositionX).equals(GameData.getGridContent(touchPositionY, i)) && i !=touchPositionX)
                canvas.drawRect(13 + i * gridLength, 13 + touchPositionY * gridLength,
                        13 + i * gridLength + gridLength, 13 + touchPositionY * gridLength + gridLength, conflictPaint);
        }

        int tempX = touchPositionX / 3 * 3;
        int tempY = touchPositionY / 3 * 3;
        for(int i = 0; i < 3; i++) {
            if (GameData.getGridContent(touchPositionY, touchPositionX).equals(GameData.getGridContent(tempY, tempX + i)) && (tempX + i != touchPositionX) && (tempY != touchPositionY)) {
                canvas.drawRect(13 + (tempX + i) * gridLength, 13 + tempY * gridLength,
                        13 + (tempX + i) * gridLength + gridLength, 13 + tempY * gridLength + gridLength, conflictPaint);
            }
            if (GameData.getGridContent(touchPositionY, touchPositionX).equals(GameData.getGridContent(tempY + 1, tempX + i)) && (tempX + i != touchPositionX) && (tempY + 1 != touchPositionY))
                canvas.drawRect(13 + (tempX + i) * gridLength, 13 + (tempY + 1) * gridLength,
                        13 + (tempX + i) * gridLength + gridLength, 13 + (tempY + 1) * gridLength + gridLength, conflictPaint);
            if (GameData.getGridContent(touchPositionY, touchPositionX).equals(GameData.getGridContent(tempY + 2, tempX + i)) && (tempX + i != touchPositionX) && (tempY + 2 != touchPositionY))
                canvas.drawRect(13 + (tempX + i) * gridLength, 13 + (tempY + 2) * gridLength,
                        13 + (tempX + i) * gridLength + gridLength, 13 + (tempY + 2) * gridLength + gridLength, conflictPaint);
        }
    }

    private void drawGrid(Canvas canvas) {
        // draw the border
        Paint borderPaint = new Paint();
        borderPaint.setColor(getResources().getColor(R.color.border));
        borderPaint.setStrokeWidth(7);
        for(int i = 0; i <= 3; i++) {
            canvas.drawLine(13 + i * gridLength * 3, 13,
                    13 + i * gridLength * 3, 13 + gridLength * 9, borderPaint);
            canvas.drawLine(13, 13 + i * gridLength * 3,
                    getMeasuredWidth() - 13, 13 + i * gridLength * 3, borderPaint);
        }

        // draw the subgrid
        Paint subgridPaint = new Paint();
        subgridPaint.setColor(getResources().getColor(R.color.subgrid));
        for(int i = 1; i < 9; i++) {
            canvas.drawLine(13 + i * gridLength, 13,13 + i * gridLength,
                    13 + gridLength * 9, subgridPaint );
            canvas.drawLine(13, 13 + i * gridLength,getMeasuredWidth() - 13,
                    13 + i * gridLength, subgridPaint );
        }
    }

    private void drawWord(Canvas canvas) {
        Paint wordPaint = new Paint();
        wordPaint.setColor(getResources().getColor(R.color.border));
        wordPaint.setAntiAlias(true);
        wordPaint.setTextSize(gridLength * 0.3f);
        wordPaint.setTextAlign(Paint.Align.CENTER);
        float x = gridLength / 2;
        float y = gridLength / 2 - (wordPaint.getFontMetrics().ascent + wordPaint.getFontMetrics().descent) / 2;
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                canvas.drawText(GameData.getGridContent(i, j), 13 + (j * gridLength + x), 13 + (i * gridLength + y), wordPaint);
            }
        }
    }
}
