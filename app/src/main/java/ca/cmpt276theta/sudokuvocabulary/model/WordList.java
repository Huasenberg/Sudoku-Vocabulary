package ca.cmpt276theta.sudokuvocabulary.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ca.cmpt276theta.sudokuvocabulary.controller.Word;

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

    public static void sortWordDataByScore() {
        Collections.sort(WordList.getOriginalWordList(), new Comparator<Word>() {
            @Override
            public int compare(Word o1, Word o2) {
                return Integer.compare(o2.getScore(), o1.getScore());
            }
        });
    }
}
