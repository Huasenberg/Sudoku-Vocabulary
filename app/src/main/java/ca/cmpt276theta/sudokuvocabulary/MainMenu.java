package ca.cmpt276theta.sudokuvocabulary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

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
                startActivity(intent);
            }
        });

        Button mLanguageBtoLanguageA = findViewById(R.id.languageB_to_languageA);
        mLanguageBtoLanguageA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("Mode", 2);
                startActivity(intent);
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
