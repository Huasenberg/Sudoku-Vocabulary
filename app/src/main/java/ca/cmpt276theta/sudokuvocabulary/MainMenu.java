package ca.cmpt276theta.sudokuvocabulary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.SeekBar;

public class MainMenu extends AppCompatActivity {

    void showDiffPopup(final Intent intent)
    {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(this.LAYOUT_INFLATER_SERVICE);
        final PopupWindow pw = new PopupWindow(inflater.inflate(R.layout.difficulty_popup_layout, null, false),600,500, true);

        pw.showAtLocation(findViewById(R.id.mainLayout), Gravity.CENTER, 0, 0);
        pw.getContentView().findViewById(R.id.buttonGo).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SeekBar seekBar = pw.getContentView().findViewById(R.id.seekBar);
                GameData.DIFFICULTY = 76-seekBar.getProgress();
                pw.dismiss();
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Button mLanguageAtoLanguageB = findViewById(R.id.languageA_to_languageB);
        final Intent intent = new Intent(MainMenu.this, MainActivity.class);
        mLanguageAtoLanguageB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent.putExtra("Mode", 1);
                showDiffPopup(intent);
                //startActivity(intent);
            }
        });

        Button mLanguageBtoLanguageA = findViewById(R.id.languageB_to_languageA);
        mLanguageBtoLanguageA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("Mode", 2);
                showDiffPopup(intent);
                //
            }
        });

        Button mImportWordList = findViewById(R.id.import_word_list);
        mImportWordList.setEnabled(false);
        mImportWordList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Does nothing yet, but soon!
            }
        });
    }
}
