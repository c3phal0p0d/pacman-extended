package src.matachi.mapeditor.editor.checker.gamechecker;

import java.io.File;
import java.io.FileWriter;

/* Checks for at least one correctly named map file in the folder */
public class OneCorrectlyNamedMapCheck implements GameCheck {

    private GameChecker gameChecker;

    public OneCorrectlyNamedMapCheck(GameChecker gameChecker) {
        this.gameChecker = gameChecker;
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
        logCheckFailure(gameChecker.getFileWriter(), str);

        return false;
    }
}
