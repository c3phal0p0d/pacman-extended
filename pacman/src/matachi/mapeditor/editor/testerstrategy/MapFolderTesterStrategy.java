package src.matachi.mapeditor.editor.testerstrategy;

import src.game.Game;
import src.matachi.Map;
import src.game.utility.GameCallback;
import src.game.utility.PropertiesLoader;
import src.matachi.mapeditor.editor.checker.gamechecker.GameChecker;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Properties;

import static src.Driver.DEFAULT_PROPERTIES_PATH;

public class MapFolderTesterStrategy implements TesterStrategy {

    private int currentGameIndex = 0;
    private int maxMapIndex;
    private ArrayList<Map> maps = new ArrayList<>();
    private Properties properties;

    private GameChecker gameChecker = GameChecker.getInstance();

    @Override
    public void test(String filePath) {

        // Apply Game Checks
        boolean isGameValid = gameChecker.performChecks(filePath);

        // Game is invalid, return to editor
        if(!isGameValid) {
            changeMode(null);
            return;
        }

        // Open File
        File file = new File(filePath);

        // Convert .xml files to Maps
        File gameFolder = file;
        File[] mapFiles = gameFolder.listFiles();
        sortByNumber(mapFiles);

        if (mapFiles != null) {
            for (File mapFile : mapFiles) {
                if (mapFile.getName().endsWith(".xml")&&Character.isDigit(mapFile.getName().charAt(0))){  // should only test maps with valid file names
                    maps.add(new Map(mapFile.getName(), mapFile.getPath()));
                }
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

    // Sorts array of map files by file names
    // Referenced from https://stackoverflow.com/questions/16898029/how-to-sort-file-names-in-ascending-order
    public void sortByNumber(File[] files) {
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                int n1 = extractNumber(o1.getName());
                int n2 = extractNumber(o2.getName());
                return n1 - n2;
            }

            private int extractNumber(String name) {
                int len = name.length();
                int firstCharIndex = len;
                for(int i = 0; i < len; i++) {
                    if(!Character.isDigit(name.charAt(i))) {
                        firstCharIndex = i;
                        break;
                    }
                }

                int i = 0;
                try {
                    int start = 0;
                    String number = name.substring(start, firstCharIndex);
                    i = Integer.parseInt(number);
                } catch(Exception e) {
                    i = 0; // if filename does not match the format
                    // then default to 0
                }
                return i;
            }
        });

        for(File f : files) {
            System.out.println(f.getName());
        }
    }
}
