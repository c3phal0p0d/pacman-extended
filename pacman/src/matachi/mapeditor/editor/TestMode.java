package src.matachi.mapeditor.editor;

import src.Game;
import src.Map;
import src.matachi.mapeditor.editor.checker.gamechecker.GameChecker;
import src.matachi.mapeditor.editor.checker.levelchecker.LevelChecker;
import src.utility.GameCallback;
import src.utility.PropertiesLoader;

import java.io.File;
import java.util.*;

import static src.Driver.DEFAULT_PROPERTIES_PATH;

public class TestMode extends Mode {
    LevelChecker levelChecker;
    GameChecker gameChecker;

    public TestMode(Controller controller, String filePath) {
        super(controller, filePath);
        File file = new File(filePath);
        System.out.println(filePath);

        // Game folder is being tested
        if (file.isDirectory()){
            // Convert .xml files to Maps
            ArrayList<Map> maps = new ArrayList<>();
            File gameFolder = file;
            File[] mapFiles = gameFolder.listFiles();
            if (mapFiles != null) {
                for (File mapFile : mapFiles) {
                    maps.add(new Map(gameFolder + "/" + mapFile.getName()));
                }
            }

            // Apply game & level checking
            boolean isGameValid = controller.getGameChecker().performChecks(filePath);

            boolean areLevelsValid = true;
            for (Map map: maps){
                if (!controller.getLevelChecker().performChecks(map)){
                    areLevelsValid = false;
                }
            }

            // If checks succeed, run game for all level maps
            if (isGameValid && areLevelsValid){
                System.out.println("Game and level checks succeeded");
                maps.sort(Comparator.comparing(Map::getFilePath));
                String propertiesPath = DEFAULT_PROPERTIES_PATH;
                final Properties properties = PropertiesLoader.loadPropertiesFile(propertiesPath);
                GameCallback gameCallback = new GameCallback();

                for (Map map : maps){
                    new Game(gameCallback, properties, map);
                }

                // After tests are done, return to edit mode with no map
                controller.changeMode(new EditMode(controller, null));

            } else {
                System.out.println("Failed game & level checks");

                // If checks fail, return to edit mode with no map
                controller.changeMode(new EditMode(controller, null));
            }
        }

        // Single level map is being tested
        else if (file.isFile()){
            // Convert .xml file to Map
            Map map = new Map(filePath);

            // Apply level checking
            boolean isLevelValid = controller.getLevelChecker().performChecks(map);

            // If checks succeed, run game with that level map
            if (isLevelValid){
                System.out.println("Level checks succeeded");
                String propertiesPath = DEFAULT_PROPERTIES_PATH;
                final Properties properties = PropertiesLoader.loadPropertiesFile(propertiesPath);
                GameCallback gameCallback = new GameCallback();
                new Game(gameCallback, properties, map);

                // After test is done, return to edit mode with map open
                controller.changeMode(new EditMode(controller, filePath));
            } else {
                System.out.println("Failed game & level checks");

                // If checks fail, return to edit mode with map open
                controller.changeMode(new EditMode(controller, filePath));
            }
        }
    }
}
