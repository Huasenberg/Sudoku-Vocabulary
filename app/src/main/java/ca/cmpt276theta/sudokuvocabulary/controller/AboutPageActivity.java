package ca.cmpt276theta.sudokuvocabulary.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.View;

import ca.cmpt276theta.sudokuvocabulary.R;

public class AboutPageActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setEnterTransition(new Explode().setDuration(600));
        getWindow().setExitTransition(null);
        setContentView(R.layout.activity_about_page);
        findViewById(R.id.return_to_main_menu).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

    }
}
