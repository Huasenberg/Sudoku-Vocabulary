package ca.cmpt276theta.sudokuvocabulary.view;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import ca.cmpt276theta.sudokuvocabulary.R;
import ca.cmpt276theta.sudokuvocabulary.model.GameData;

public class GridView extends View {

    private float mGridWidth;
    private float mGridHeight;
    private final int subGridSizeHori;
    private final int subGridSizeVerti;
    private final int gridSize;
    private final boolean isLandscapeMode;

    public GridView(Context context, GameData gameData) {
        super(context);
        gridSize = gameData.getGridSize();
        subGridSizeHori = gameData.getSubGridSizeHori();
        subGridSizeVerti = gameData.getSubGridSizeVerti();
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
        drawGrid(canvas);
    }

    private void drawGrid(Canvas canvas) {
        final float girdEdgeHorizontal = mGridWidth * gridSize;
        final float girdEdgeVertical = mGridHeight * gridSize;
        // draw the border
        final Paint borderPaint = new Paint();
        borderPaint.setColor(getResources().getColor(R.color.game_border));
        borderPaint.setStrokeWidth(5);
        for (int i = 1; i < subGridSizeVerti; i++) {
            final float vertex = i * mGridWidth * subGridSizeHori;
            canvas.drawLine(vertex, 0, vertex, girdEdgeVertical, borderPaint);
        }
        for (int i = 1; i < subGridSizeHori; i++) {
            final float vertex = i * mGridHeight * subGridSizeVerti;
            canvas.drawLine(0, vertex, girdEdgeHorizontal, vertex, borderPaint);
        }

        // draw the subgrid
        borderPaint.setStrokeWidth(1);
        for (int i = 1; i < gridSize; i++) {
            final float vertex1 = i * mGridWidth;
            final float vertex2 = i * mGridHeight;
            canvas.drawLine(vertex1, 0, vertex1,
                    girdEdgeVertical, borderPaint);
            canvas.drawLine(0, vertex2, girdEdgeHorizontal,
                    vertex2, borderPaint);
        }
    }
}
