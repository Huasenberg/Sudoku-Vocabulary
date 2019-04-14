package ca.cmpt276theta.sudokuvocabulary.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GameDataListTest {

    @Test
    public void getGameDataList() {
        GameDataGenerator.setSIZE(2,2);
        List<Word> wordList = new ArrayList<>();
        wordList.add(new Word("mango","mangue"));
        wordList.add(new Word("cherry", "cerise"));
        wordList.add(new Word("pear","poire"));
        wordList.add(new Word("apple", "pomme"));
        WordList.setSelectedWordList(wordList);
        GameDataGenerator.loadPuzzleData();
        GameDataList.getGameDataList().add(new GameData(0,true,0));
        assertEquals(1, GameDataList.getGameDataList().size());
        GameDataList.getGameDataList().add(new GameData(1,false,0));
        GameDataList.getGameDataList().add(new GameData(2,true,2));
        GameDataList.getGameDataList().add(new GameData(1,true,0));
        GameDataList.getGameDataList().add(new GameData(0,false,0));
        assertEquals(3, GameDataList.getGameDataList().size());

    }

    @Test
    public void setGameDataList() {
        List<GameData> gameDataList = new ArrayList<>();
        gameDataList.add(new GameData(0,true,0));
        GameDataList.setGameDataList(gameDataList);
        assertEquals(gameDataList, GameDataList.getGameDataList());
    }
}