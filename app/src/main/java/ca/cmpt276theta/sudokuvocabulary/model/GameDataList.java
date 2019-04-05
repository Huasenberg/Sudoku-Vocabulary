package ca.cmpt276theta.sudokuvocabulary.model;

import java.util.ArrayList;
import java.util.List;

public class GameDataList {
    private static List<GameData> sGameDataList = new ArrayList<GameData>() {
        @Override
        public boolean add(GameData gameData) {
            if (size() >= 3)
                remove(0);
            return super.add(gameData);
        }
    };

    public static List<GameData> getGameDataList() {
        return sGameDataList;
    }

    public static void setGameDataList(List<GameData> gameDataList) {
        sGameDataList = gameDataList;
    }
}
