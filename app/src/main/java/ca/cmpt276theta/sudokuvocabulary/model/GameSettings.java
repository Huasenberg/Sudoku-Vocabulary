package ca.cmpt276theta.sudokuvocabulary.model;


public class GameSettings {
    private static boolean isSoundOpen;
    private static boolean isVibraOpen;
    private static boolean isScreeOn;
    private static boolean isDuplicHighli;

    public static boolean isSoundOpen() {
        return isSoundOpen;
    }

    public static void setSoundOpen(boolean isSoundOpen) {
        GameSettings.isSoundOpen = isSoundOpen;
    }

    public static boolean isVibraOpen() {
        return isVibraOpen;
    }

    public static void setVibraOpen(boolean isVibraOpen) {
        GameSettings.isVibraOpen = isVibraOpen;
    }

    public static boolean isScreeOn() {
        return isScreeOn;
    }

    public static void setScreeOn(boolean isScreeOn) {
        GameSettings.isScreeOn = isScreeOn;
    }

    public static boolean isDuplicHighli() {
        return isDuplicHighli;
    }

    public static void setDuplicHighli(boolean isDuplicHighli) {
        GameSettings.isDuplicHighli = isDuplicHighli;
    }

}
