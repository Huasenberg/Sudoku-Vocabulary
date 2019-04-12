package ca.cmpt276theta.sudokuvocabulary.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WordList {
    private static List<Word> sOriginalWordList;
    private static List<Word> sSelectedWordList;

    public static List<Word> getSelectedWordList() {
        return sSelectedWordList;
    }

    public static void setSelectedWordList(ArrayList<Word> selectedWordList) {
        sSelectedWordList = selectedWordList;
    }

    public static List<Word> getOriginalWordList() {
        return sOriginalWordList;
    }

    public static void setOriginalWordList(List<Word> originalWordList) {
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

    public static void sortWordDataByAlphbet() {
        Collections.sort(WordList.getOriginalWordList(), new Comparator<Word>() {
            @Override
            public int compare(Word o1, Word o2) {
                return o1.getEnglish().charAt(0) - o2.getEnglish().charAt(0);
            }
        });
    }
}
