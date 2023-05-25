package src.matachi.mapeditor.editor;

import src.matachi.mapeditor.editor.checker.gamechecker.GameChecker;
import src.matachi.mapeditor.editor.checker.levelchecker.LevelChecker;
import src.matachi.mapeditor.editor.testerstrategy.TesterStrategy;

public class Tester {
    LevelChecker levelChecker;
    GameChecker gameChecker;
    TesterStrategy testerStrategy;

    public Tester(String filePath, TesterStrategy testerStrategy) {

        this.gameChecker = GameChecker.getInstance();
        this.levelChecker = LevelChecker.getInstance();
        this.testerStrategy = testerStrategy;

        // Apply Game Checks
        boolean isGameValid = gameChecker.performChecks(filePath);

        // Game is valid, load and test the maps
        if(isGameValid) {
            System.out.println("Game checks succeeded");
            testerStrategy.test(filePath);
        }
        // Game is invalid, go back to editor
        else {
            System.out.println("Failed game checks");

            // If checks fail, return to edit mode with no map
            testerStrategy.changeMode(null);
        }

    }
}

/*

// Game folder is being tested
        if (file.isDirectory()){
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
                changeMode(new Editor(editor, null));

            } else {
                System.out.println("Failed game & level checks");

                // If checks fail, return to edit mode with no map
                changeMode(new Editor(editor, null));
            }
        }

        // Single level map is being tested
        else if (file.isFile()){
            // Convert .xml file to Map
            Map map = new Map(file.getName(), file.getPath());

            // Apply level checking
            boolean isLevelValid = editor.getLevelChecker().performChecks(map);

            // If checks succeed, run game with that level map
            if (isLevelValid){
                System.out.println("Level checks succeeded");
                String propertiesPath = DEFAULT_PROPERTIES_PATH;
                final Properties properties = PropertiesLoader.loadPropertiesFile(propertiesPath);
                GameCallback gameCallback = new GameCallback();
                new Game(gameCallback, properties, map);

                // After test is done, return to edit mode with map open
                editor.changeMode(new Editor(editor, filePath));
            } else {
                System.out.println("Failed game & level checks");

                // If checks fail, return to edit mode with map open
                editor.changeMode(new Editor(editor, filePath));
            }
        }
 */
