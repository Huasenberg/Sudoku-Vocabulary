package ca.cmpt276theta.sudokuvocabulary;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TableLayout layoutSudoku = findViewById(R.id.tableLayoutSudoku);
        //layoutSudoku.setBackgroundResource(R.drawable.table_border);
        for(int y = 0; y < 9; y++)
        {
            TableRow row = new TableRow(this);
            for(int x = 0; x < 9; x++)
            {
                TextView button = new TextView(this);
                button.setText("Pomme" + x);
                button.setTextSize(9);
                button.setWidth(dpToPx(40, this));
                button.setHeight(dpToPx(40, this));
                button.setGravity(Gravity.CENTER);
                button.setBackgroundResource(R.drawable.textview_border);
                button.setTextColor(Color.BLACK);
                row.addView(button);
            }
            layoutSudoku.addView(row);
        }
    }

    public static int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}
