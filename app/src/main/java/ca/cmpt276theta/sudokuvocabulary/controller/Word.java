package ca.cmpt276theta.sudokuvocabulary.controller;

import java.io.Serializable;

public class Word implements Serializable {
    private int wordID;
    private String english;
    private String french;
    private int score;

    public int getWordID() {
        return wordID;
    }

    public void setWordID(int wordID) {
        this.wordID = wordID;
    }

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
}

//package ca.cmpt276theta.sudokuvocabulary;
//
//public class Word {
//    public static final String TABLE_NAME = "wordlist";
//
//    public static final String COLUMN_ID = "id";
//    public static final String COLUMN_ENGLISH = "english";
//    public static final String COLUMN_FRENCH = "french";
//    public static final String COLUMN_SCORE = "score";
//
//    private int id;
//    private String english;
//    private String french;
//    private int score;
//
//
//    // Create table SQL query
//    public static final String CREATE_TABLE =
//            "CREATE TABLE " + TABLE_NAME + "("
//                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//                    + COLUMN_ENGLISH + " TEXT,"
//                    + COLUMN_FRENCH + " TEXT"
//                    + COLUMN_SCORE + " INTEGER"
//                    + ")";
//
//    public Word(int id, String english, String french, int score) {
//        this.id = id;
//        this.english = english;
//        this.french = french;
//        this.score = score;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getEnglish() {
//        return english;
//    }
//
//    public void setEnglish(String english) {
//        this.english = english;
//    }
//
//    public String getFrench() {
//        return french;
//    }
//
//    public void setFrench(String french) {
//        this.french = french;
//    }
//
//    public int getScore() {
//        return score;
//    }
//
//    public void setScore(int score) {
//        this.score = score;
//    }
//}
