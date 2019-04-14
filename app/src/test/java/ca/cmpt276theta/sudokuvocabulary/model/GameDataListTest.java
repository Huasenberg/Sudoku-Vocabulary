package ca.cmpt276theta.sudokuvocabulary.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GameDataListTest {

    @Test
    public void getGameDataList() {
        List<GameData> gameDataList = new ArrayList<>();
        gameDataList.add(new GameData(0,true,0));
        GameDataList.setGameDataList(gameDataList);
        assertEquals(gameDataList, GameDataList.getGameDataList());

    }

    @Test
    public void setGameDataList() {

        GameDataList.getGameDataList().add(new GameData(1, false, 2));
        GameDataList.getGameDataList().add(new GameData(0, false, 1));
        GameDataList.getGameDataList().add(new GameData(0, false, 1));
        GameDataList.getGameDataList().add(new GameData(0, false, 1));
        assertEquals(3, GameDataList.getGameDataList().size());
    }
}