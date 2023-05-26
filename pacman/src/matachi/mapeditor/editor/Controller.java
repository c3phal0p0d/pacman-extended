package src.matachi.mapeditor.editor;

import src.matachi.mapeditor.editor.testerstrategy.MapFolderTesterStrategy;
import src.matachi.mapeditor.editor.testerstrategy.SingleMapTesterStrategy;

import java.io.File;
import java.util.Objects;

public class Controller {

    /*
    The purpose of this class is to start the app in the specified mode
     */

    /**
     * @param modeStr
     * The mode to open with
     * @param filePath
     * The file path given
     */

    public Controller(String modeStr, String filePath) {
        chooseMode(modeStr, filePath);
    }

    public void chooseMode(String modeStr, String filePath) {
        if (Objects.equals(modeStr, "TEST")){ // TEST MODE

            System.out.println("Test mode");

            File file = new File(filePath);
            if(file.isFile()) {
                new Tester(filePath, new SingleMapTesterStrategy()); // Open Tester
            }
            else if(file.isDirectory()) {
                new Tester(filePath, new MapFolderTesterStrategy()); // Open Tester
            }
        } else if (Objects.equals(modeStr, "EDIT")){ // EDIT MODE

            System.out.println("Edit mode");
            new Editor(filePath);
        }
    }
}
