package ca.cmpt276theta.sudokuvocabulary.model;

public class Word {
    private String english;
    private String french;
    private int score;

    public Word(String english, String french) {
        this.english = english;
        this.french = french;
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

    @Override
    public String toString() {
        if(String.valueOf(score).length() > 1)
            return score + "      " + english + " --- " + french;
        else
            return score + "        " + english + " --- " + french;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        return english.equals(((Word) obj).getEnglish()) && french.equals(((Word) obj).getFrench());
    }
}
