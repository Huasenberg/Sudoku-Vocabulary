
package ca.cmpt276theta.sudokuvocabulary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main_Menu extends AppCompatActivity {

    private Button mLanguageAtoLanguageB;
    private Button mLanguageBtoLanguageA;
    private Button mImportWordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__menu);

        mLanguageAtoLanguageB = (Button) findViewById(R.id.languageA_to_languageB);
        mLanguageAtoLanguageB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main_Menu.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mLanguageBtoLanguageA = (Button) findViewById(R.id.languageB_to_languageA);
        mLanguageBtoLanguageA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Does nothing yet, but soon!
            }
        });

        mImportWordList = (Button) findViewById(R.id.import_word_list);
        mImportWordList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Does nothing yet, but soon!
            }
        });


    }
}


