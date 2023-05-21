package src.matachi.mapeditor.editor;

import src.Game;
import src.Map;
import src.matachi.mapeditor.editor.checker.GameChecker;
import src.matachi.mapeditor.editor.checker.LevelChecker;
import src.utility.GameCallback;
import src.utility.PropertiesLoader;

import java.util.Properties;

import static src.Driver.DEFAULT_PROPERTIES_PATH;

public class TestMode extends Mode {
    LevelChecker levelChecker;
    GameChecker gameChecker;

    public TestMode(Controller controller, String filePath) {
        super(controller, filePath);
    }
    // Apply game & level checking
    // ...

    // If checks succeed, run game
    // First convert .xml file to Map class to be used by game
    // ...

    // Run game
    // Temporarily hardcoded for test purposes
    String propertiesPath = DEFAULT_PROPERTIES_PATH;
    final Properties properties = PropertiesLoader.loadPropertiesFile(propertiesPath);
    GameCallback gameCallback = new GameCallback();
    Map map = new Map(filePath+"/sample_map1.xml");
    Game game = new Game(gameCallback, properties, map);
}
