package src.game;

import src.matachi.mapeditor.editor.Controller;
import src.matachi.mapeditor.editor.Editor;

import java.io.File;

public class Driver {
    public static final String DEFAULT_PROPERTIES_PATH = "properties/test2.properties";

    /**
     * Starting point
     * @param args the command line arguments
     */

    public static void main(String args[]) {
        String mode = null;
        String filePath = null;
        if (args.length > 0) {
            filePath = args[0];
            File file = new File(filePath);
            if (file.isDirectory()){
                // Folder as an argument
                // Start test mode on that folder
                System.out.println("folder provided as arg");
                mode = "TEST";
            } else if (file.isFile()){
                // Existing map as an argument
                // Start edit mode on that map
                System.out.println("map file provided as arg");
                mode = "EDIT";
            }
        } else {
            // No arguments
            // Start edit mode with no current map
            System.out.println("no args provided");
            mode = "EDIT";
        }
        new Controller(mode, filePath);   // open application

        // Comment previous code & uncomment the following in order to test a specific map without going through level/game checking
//        String propertiesPath = DEFAULT_PROPERTIES_PATH;
//        final Properties properties = PropertiesLoader.loadPropertiesFile(propertiesPath);
//        GameCallback gameCallback = new GameCallback();
//        new Game(gameCallback, properties, new Map("game/sample_map1.xml"));
    }
}
