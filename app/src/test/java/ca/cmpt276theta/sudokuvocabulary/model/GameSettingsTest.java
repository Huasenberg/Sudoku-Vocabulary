package ca.cmpt276theta.sudokuvocabulary.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class GameSettingsTest {


    @Test
    public void isSoundOpen() {
        GameSettings.setSoundOpen(true);
        assertTrue(GameSettings.isSoundOpen());
        GameSettings.setSoundOpen(false);
        assertFalse(GameSettings.isSoundOpen());
    }

    @Test
    public void setSoundOpen() {
        GameSettings.setSoundOpen(true);
        assertTrue(GameSettings.isSoundOpen());
        GameSettings.setSoundOpen(false);
        assertFalse(GameSettings.isSoundOpen());
    }

    @Test
    public void isVibraOpen() {
        GameSettings.setVibraOpen(true);
        assertTrue(GameSettings.isVibraOpen());
        GameSettings.setVibraOpen(false);
        assertFalse(GameSettings.isVibraOpen());
    }

    @Test
    public void setVibraOpen() {
        GameSettings.setVibraOpen(true);
        assertTrue(GameSettings.isVibraOpen());
        GameSettings.setVibraOpen(false);
        assertFalse(GameSettings.isVibraOpen());
    }

    @Test
    public void isScreeOn() {
        GameSettings.setScreeOn(true);
        assertTrue(GameSettings.isScreeOn());
        GameSettings.setScreeOn(false);
        assertFalse(GameSettings.isScreeOn());
    }

    @Test
    public void setScreeOn() {
        GameSettings.setScreeOn(true);
        assertTrue(GameSettings.isScreeOn());
        GameSettings.setScreeOn(false);
        assertFalse(GameSettings.isScreeOn());
    }

    @Test
    public void isDuplicHighli() {
        GameSettings.setDuplicHighli(true);
        assertTrue(GameSettings.isDuplicHighli());
        GameSettings.setDuplicHighli(false);
        assertFalse(GameSettings.isDuplicHighli());
    }

    @Test
    public void setDuplicHighli() {
        GameSettings.setDuplicHighli(true);
        assertTrue(GameSettings.isDuplicHighli());
        GameSettings.setDuplicHighli(false);
        assertFalse(GameSettings.isDuplicHighli());
    }
}