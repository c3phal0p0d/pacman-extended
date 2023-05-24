package src.matachi.mapeditor.editor.testerstrategy;

import src.Game;
import src.Map;
import src.matachi.mapeditor.editor.Editor;
import src.utility.GameCallback;
import src.utility.PropertiesLoader;

import java.io.File;
import java.util.Properties;

import static src.Driver.DEFAULT_PROPERTIES_PATH;

public class SingleMapTesterStrategy extends TesterStrategy {


    @Override
    public void test(String filePath) {
        // Open File
        File file = new File(filePath);

        // Convert .xml file to Map
        Map map = new Map(file.getName(), file.getPath());

        // Apply level checking
        boolean isLevelValid = super.levelChecker.performChecks(map);

        // If checks succeed, run game with that level map
        if (isLevelValid){
            System.out.println("Level checks succeeded");
            String propertiesPath = DEFAULT_PROPERTIES_PATH;
            final Properties properties = PropertiesLoader.loadPropertiesFile(propertiesPath);
            GameCallback gameCallback = new GameCallback();
            new Game(gameCallback, properties, map);

            // After test is done, return to edit mode with map open
            changeMode(filePath);
        } else {
            System.out.println("Failed game & level checks");

            // If checks fail, return to edit mode with map open
            changeMode(filePath);
        }
    }
}
