package ca.cmpt276theta.sudokuvocabulary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import ca.cmpt276theta.sudokuvocabulary.R;
import ca.cmpt276theta.sudokuvocabulary.model.GameData;

public class GameThumbnailView extends View {

    private final int gridSize;
    private final int subGridSizeHori;
    private final int subGridSizeVerti;
    private final int puzzle[][];
    private final int preFilledPuzzle[][];
    private float mGridWidth;

    public GameThumbnailView(Context context, GameData gameData) {
        super(context);
        puzzle = gameData.getPuzzle();
        preFilledPuzzle = gameData.getPuzzlePreFilled();
        gridSize = gameData.getGridSize();
        subGridSizeHori = gameData.getSubGridSizeHori();
        subGridSizeVerti = gameData.getSubGridSizeVerti();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mGridWidth = w / (float) gridSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawData(canvas);
        drawGrid(canvas);
    }

    private void drawGrid(Canvas canvas) {
        final float girdEdge = mGridWidth * gridSize;
        // draw the border
        final Paint borderPaint = new Paint();
        borderPaint.setColor(getResources().getColor(R.color.border));
        borderPaint.setStrokeWidth(5);
        for (int i = 1; i < subGridSizeVerti; i++) {
            final float vertex = i * mGridWidth * subGridSizeHori;
            canvas.drawLine(vertex, 0, vertex, girdEdge, borderPaint);
        }
        for (int i = 1; i < subGridSizeHori; i++) {
            final float vertex = i * mGridWidth * subGridSizeVerti;
            canvas.drawLine(0, vertex, girdEdge, vertex, borderPaint);
        }

        // draw the subgrid
        borderPaint.setStrokeWidth(1);
        for (int i = 1; i < gridSize; i++) {
            final float vertex = i * mGridWidth;
            canvas.drawLine(vertex, 0, vertex,
                    girdEdge, borderPaint);
            canvas.drawLine(0, vertex, girdEdge,
                    vertex, borderPaint);
        }
    }

    private void drawData(Canvas canvas) {
        final Paint paint1 = new Paint();
        final Paint paint2 = new Paint();
        paint1.setColor(getResources().getColor(R.color.highlightRec));
        paint2.setColor(getResources().getColor(R.color.word_bank));
        paint1.setAlpha(60);
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (puzzle[i][j] != 0 && preFilledPuzzle[i][j] == 0)
                    canvas.drawRect(j * mGridWidth, i * mGridWidth,
                            (j + 1) * mGridWidth, (i + 1) * mGridWidth, paint1);
                if (preFilledPuzzle[i][j] != 0)
                    canvas.drawRect(j * mGridWidth, i * mGridWidth,
                            (j + 1) * mGridWidth, (i + 1) * mGridWidth, paint2);

            }
        }
    }
}
