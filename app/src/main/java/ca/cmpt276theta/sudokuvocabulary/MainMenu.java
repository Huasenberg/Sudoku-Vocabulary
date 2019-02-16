package ca.cmpt276theta.sudokuvocabulary;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainMenu extends AppCompatActivity {
    private int option;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        View contentView = LayoutInflater.from(this).inflate(R.layout.difficulty_popup, null);
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        final PopupWindow pw = new PopupWindow(contentView, size.x, size.y, false);
        pw.setClippingEnabled(false);
        final LinearLayout diffPopUp = pw.getContentView().findViewById(R.id.difficulty_popup);
        final Spinner spinner = pw.getContentView().findViewById(R.id.spinner);
        final SeekBar seekBar = pw.getContentView().findViewById(R.id.seekBar);
        final TextView text = pw.getContentView().findViewById(R.id.textViewDif);
        final Intent intent = new Intent(MainMenu.this, MainActivity.class);
        loadSpinner(spinner, pw);
        final Button newGameButton = findViewById(R.id.new_game);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiffPopup(intent, diffPopUp, pw, seekBar, text);
            }
        });

        Button continueGame = findViewById(R.id.continue_game);
        continueGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(MainMenu.this, "Coming soon!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                View view = toast.getView();
                view.setBackgroundColor(getResources().getColor(R.color.conflict));
                TextView text = view.findViewById(android.R.id.message);
                text.setTextColor(getResources().getColor(R.color.background));
                text.setTextSize(18);
                toast.show();
            }
        });

        Button mImportWordList = findViewById(R.id.import_word_list);
        mImportWordList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(MainMenu.this, "Coming soon!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                View view = toast.getView();
                view.setBackgroundColor(getResources().getColor(R.color.conflict));
                TextView text = view.findViewById(android.R.id.message);
                text.setTextColor(getResources().getColor(R.color.background));
                text.setTextSize(18);
                toast.show();
            }
        });

        ImageView settings = findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(MainMenu.this, "Coming soon!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                View view = toast.getView();
                view.setBackgroundColor(getResources().getColor(R.color.conflict));
                TextView text = view.findViewById(android.R.id.message);
                text.setTextColor(getResources().getColor(R.color.background));
                text.setTextSize(18);
                toast.show();
            }
        });
    }

    public void loadSpinner(Spinner spinner, PopupWindow pw) {
        final List<String> data_list = new ArrayList<>();
        data_list.add(getResources().getString(R.string.languageA_to_languageB));
        data_list.add(getResources().getString(R.string.languageB_to_languageA));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(pw.getContentView().getContext(), R.layout.spinner_dropdown, data_list);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                option = arg2 + 1;
                arg0.setVisibility(View.VISIBLE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    public void showDiffPopup(final Intent intent, final LinearLayout diffPopUp, final PopupWindow pw, final SeekBar seekBar, final TextView text) {
        pw.setAnimationStyle(R.style.pop_animation);
        pw.showAtLocation(findViewById(R.id.mainLayout), Gravity.CENTER, 0, 0);
        diffPopUp.setBackgroundColor(getResources().getColor(R.color.popUpBg));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                text.setText(String.format(getResources().getString(R.string.difficulty), seekBar.getProgress() + 1));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        pw.getContentView().findViewById(R.id.buttonGo).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                GameData.DIFFICULTY = seekBar.getProgress() + 1;
                intent.putExtra("Mode", option);
                startActivity(intent);
                pw.dismiss();
            }
        });
        pw.getContentView().findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                pw.dismiss();
            }
        });
    }
}
