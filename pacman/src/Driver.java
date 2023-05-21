package src;

import src.utility.GameCallback;
import src.utility.PropertiesLoader;
import src.matachi.mapeditor.editor.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class Driver {
    public static final String DEFAULT_PROPERTIES_PATH = "properties/test2.properties";

    /**
     * Starting point
     * @param args the command line arguments
     */

    public static void main(String args[]) {
        String propertiesPath = DEFAULT_PROPERTIES_PATH;
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
        new Controller(mode, filePath);   // open editor
    }
}
