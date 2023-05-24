package src.matachi.mapeditor.editor.testerstrategy;

import src.Game;
import src.Map;
import src.matachi.mapeditor.editor.Editor;
import src.utility.GameCallback;
import src.utility.PropertiesLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Properties;

import static src.Driver.DEFAULT_PROPERTIES_PATH;

public class MapFolderTesterStrategy extends TesterStrategy {

    @Override
    public void test(String filePath) {
        // Open File
        File file = new File(filePath);

        // Convert .xml files to Maps
        ArrayList<Map> maps = new ArrayList<>();
        File gameFolder = file;
        File[] mapFiles = gameFolder.listFiles();
        if (mapFiles != null) {
            for (File mapFile : mapFiles) {
                maps.add(new Map(mapFile.getName(), mapFile.getPath()));
            }
        }

        // Apply level checking to all maps
        boolean areLevelsValid = true;
        for (Map map: maps){
            if (!levelChecker.performChecks(map)){
                areLevelsValid = false;
            }
        }

        // If checks succeed, run game for all level maps
        if (areLevelsValid){
            System.out.println("Game and level checks succeeded");
            maps.sort(Comparator.comparing(Map::getFilePath));
            String propertiesPath = DEFAULT_PROPERTIES_PATH;
            final Properties properties = PropertiesLoader.loadPropertiesFile(propertiesPath);
            GameCallback gameCallback = new GameCallback();

            for (Map map : maps){
                new Game(gameCallback, properties, map);
            }

            // After tests are done, return to edit mode with no map
            changeMode(null);

        } else {
            System.out.println("Failed game & level checks");

            // If checks fail, return to edit mode with no map
            changeMode(null);
        }
    }
}
