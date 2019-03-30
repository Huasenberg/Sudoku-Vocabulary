package ca.cmpt276theta.sudokuvocabulary.model;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ca.cmpt276theta.sudokuvocabulary.R;
import ca.cmpt276theta.sudokuvocabulary.controller.GameStartActivity;
import ca.cmpt276theta.sudokuvocabulary.controller.MainMenuActivity;
import ca.cmpt276theta.sudokuvocabulary.controller.WordListActivity;

public class WordList {
    private static ArrayList<Word> sOriginalWordList;
    private static ArrayList<Word> sSelectedWordList;

    public static ArrayList<Word> getSelectedWordList() {
        return sSelectedWordList;
    }

    public static void setSelectedWordList(ArrayList<Word> selectedWordList) {
        sSelectedWordList = selectedWordList;
    }

    public static ArrayList<Word> getOriginalWordList() {
        return sOriginalWordList;
    }

    public static void setOriginalWordList(ArrayList<Word> originalWordList) {
        WordList.sOriginalWordList = originalWordList;
    }

    public static void writeToArrayList(Context context, BufferedReader reader) {
        String line = "";
        try {
            // Step over headers
            reader.readLine();
            final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0,15,0,15);
            // If buffer is not empty
            while ((line = reader.readLine()) != null) {
                Log.d("My Activity","Line: " + line);
                // use comma as separator columns of CSV
                String[] tokens = line.split(",");
                // Read the data
                final Word sample = new Word(tokens[1],tokens[2]);

                // Setters
                sample.setScore(Integer.parseInt(tokens[3]));
                if(sOriginalWordList.contains(sample))
                    continue;
                // Adding object to a class
                final CheckBox checkBox = new CheckBox(context);
                checkBox.setText("   " + sample.toString());
                checkBox.setTextColor(context.getResources().getColor(R.color.check_box));
                checkBox.setTextSize(17);
                GameStartActivity.getCheckBoxes().add(checkBox);
                final TextView textView = new TextView(context);
                textView.setText(sample.toString());
                textView.setTextSize(18);
                textView.setLayoutParams(lp);
                WordListActivity.getTextViews().add(textView);
                sOriginalWordList.add(sample);
                // Log the object
                Log.d("My Activity", "Just created: " + sample);
            }

        } catch (IOException e) {
            // Logs error with priority level
            Log.d("My Activity", "Error reading data file on line" + line, e);
            // Prints throwable details
            e.printStackTrace();
        }
    }

    public static void sortWordDataByScore() {
        Collections.sort(WordList.getOriginalWordList(), new Comparator<Word>() {
            @Override
            public int compare(Word o1, Word o2) {
                return Integer.compare(o2.getScore(), o1.getScore());
            }
        });
    }

    public static void sortWordDataByAlphbet() {
        Collections.sort(WordList.getOriginalWordList(), new Comparator<Word>() {
            @Override
            public int compare(Word o1, Word o2) {
                return o1.getEnglish().charAt(0) - o2.getEnglish().charAt(0);
            }
        });
    }
}
