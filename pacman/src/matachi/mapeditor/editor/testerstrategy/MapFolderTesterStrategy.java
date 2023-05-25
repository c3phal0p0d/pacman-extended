package src.matachi.mapeditor.editor.testerstrategy;

import src.game.Game;
import src.game.Map;
import src.game.utility.GameCallback;
import src.game.utility.PropertiesLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Properties;

import static src.Driver.DEFAULT_PROPERTIES_PATH;

public class MapFolderTesterStrategy extends TesterStrategy {

    private int currentGameIndex = 0;
    private int maxMapIndex;
    private ArrayList<Map> maps = new ArrayList<>();
    private Properties properties;

    @Override
    public void test(String filePath) {
        // Open File
        File file = new File(filePath);

        // Convert .xml files to Maps
        File gameFolder = file;
        File[] mapFiles = gameFolder.listFiles();
        if (mapFiles != null) {
            for (File mapFile : mapFiles) {
                maps.add(new Map(mapFile.getName(), mapFile.getPath()));
            }
        }
        maxMapIndex = maps.size() - 1;

        // Apply level checking to all maps
        boolean areLevelsValid = true;
        for (Map map: maps){
            if (!levelChecker.performChecks(map)){
                areLevelsValid = false;
            }
        }

        // If checks succeed, run game for all level maps
        if (areLevelsValid){
            System.out.println("Level checks succeeded");
            maps.sort(Comparator.comparing(Map::getFilePath));
            properties = PropertiesLoader.loadPropertiesFile(DEFAULT_PROPERTIES_PATH);

            startNextMap();
        } else {
            System.out.println("Failed level checks");

            // If checks fail, return to edit mode with no map
            changeMode(null);
        }
    }

    private void startNextMap() {
        Map map = maps.get(currentGameIndex);
        GameCallback gameCallback = new GameCallback();
        currentGameIndex++;
        Game game = new Game(gameCallback, properties, map); // run game
        if(currentGameIndex <= maxMapIndex) { // Final map hasn't been played
            if(game.getIsPlayerAlive()) { // Player is still alive
                startNextMap();
            }
            else {
                changeMode(null);
            }
        }
        else {
            changeMode(null);
        }
    }
}
