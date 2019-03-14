package ca.cmpt276theta.sudokuvocabulary.controller;

import android.support.annotation.NonNull;

public class Word {
    private String english;
    private String french;
    private int score;

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getFrench() {
        return french;
    }

    public void setFrench(String french) {
        this.french = french;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return english + " -- " + french;
    }

}
