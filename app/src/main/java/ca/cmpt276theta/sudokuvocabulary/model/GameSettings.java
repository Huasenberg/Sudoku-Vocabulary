package ca.cmpt276theta.sudokuvocabulary.model;


public class GameSettings {
    private static boolean isSoundOpen;
    private static boolean isVibraOpen;
    private static boolean isScreeOn;
    private static boolean isDuplicHighli;

    public static boolean isIsSoundOpen() {
        return isSoundOpen;
    }

    public static void setIsSoundOpen(boolean isSoundOpen) {
        GameSettings.isSoundOpen = isSoundOpen;
    }

    public static boolean isIsVibraOpen() {
        return isVibraOpen;
    }

    public static void setIsVibraOpen(boolean isVibraOpen) {
        GameSettings.isVibraOpen = isVibraOpen;
    }

    public static boolean isIsScreeOn() {
        return isScreeOn;
    }

    public static void setIsScreeOn(boolean isScreeOn) {
        GameSettings.isScreeOn = isScreeOn;
    }

    public static boolean isIsDuplicHighli() {
        return isDuplicHighli;
    }

    public static void setIsDuplicHighli(boolean isDuplicHighli) {
        GameSettings.isDuplicHighli = isDuplicHighli;
    }

}
