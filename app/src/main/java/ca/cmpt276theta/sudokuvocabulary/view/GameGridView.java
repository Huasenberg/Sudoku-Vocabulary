package ca.cmpt276theta.sudokuvocabulary.view;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import ca.cmpt276theta.sudokuvocabulary.R;

public class GameGridView extends View {

    private float mGridWidth;
    private float mGridHeight;
    private boolean isLandscapeMode;

    public GameGridView(Context context) {
        super(context);
        isLandscapeMode = getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(!isLandscapeMode)
            setMeasuredDimension(widthMeasureSpec, widthMeasureSpec);
        else
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mGridWidth = w / 9f;
        if(!isLandscapeMode)
            mGridHeight = mGridWidth;
        else
            mGridHeight = h / 9f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawGrid(canvas);
    }

    private void drawGrid(Canvas canvas) {
        final float girdEdgeHorizontal = mGridWidth * 9;
        final float girdEdgeVertical = mGridHeight * 9;
        // draw the border
        final Paint borderPaint = new Paint();
        borderPaint.setColor(getResources().getColor(R.color.border));
        borderPaint.setStrokeWidth(5);
        for(int i = 1; i <= 2; i++) {
            final float vertex1 = i * mGridWidth * 3;
            final float vertex2 = i * mGridHeight * 3;
            canvas.drawLine(vertex1, 0,
                    vertex1, girdEdgeVertical, borderPaint);
            canvas.drawLine(0, vertex2,
                    girdEdgeHorizontal, vertex2, borderPaint);
        }

        // draw the subgrid
        borderPaint.setStrokeWidth(1);
        for(int i = 1; i < 9; i++) {
            final float vertex1 = i * mGridWidth;
            final float vertex2 = i * mGridHeight;
            canvas.drawLine(vertex1, 0, vertex1,
                    girdEdgeVertical, borderPaint);
            canvas.drawLine(0, vertex2,girdEdgeHorizontal,
                    vertex2, borderPaint);
        }
    }
}
