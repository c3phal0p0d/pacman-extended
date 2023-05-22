package src.matachi.mapeditor.editor.checker;

import java.io.File;
import java.io.FileWriter;

/* Game check A. at least one correctly named map file in the folder */
public class OneCorrectlyNamedMapCheck extends GameCheck {

    public OneCorrectlyNamedMapCheck(FileWriter fileWriter) {
        super(fileWriter);
    }

    @Override
    public boolean check(String gameFolderFilePath) {
        File gameFolder = new File(gameFolderFilePath);
        File[] mapFiles = gameFolder.listFiles();
        if (mapFiles != null) {
            for (File mapFile : mapFiles) {
                String filename = mapFile.getName();
                if (filename.endsWith(".xml")&&Character.isDigit(filename.charAt(0))){  // is .xml file starting with an integer
                    return true;
                }
            }
        }
        // log error
        String str = "Game " + gameFolderFilePath + " – no maps found";
        logCheckFailure(str);

        return false;
    }
}
