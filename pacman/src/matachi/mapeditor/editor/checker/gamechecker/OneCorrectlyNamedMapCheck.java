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
    public boolean check(String filePath) {
        File file = new File(filePath);
        if(file.isFile()) {
            String filename = file.getName();
            if (filename.endsWith(".xml")&&Character.isDigit(filename.charAt(0))){
                return true;
            }
        }
        else if(file.isDirectory()) {
            File[] mapFiles = file.listFiles();

            if (mapFiles != null) {
                for (File mapFile : mapFiles) {
                    String filename = mapFile.getName();
                    // is .xml file starting with an integer
                    if (filename.endsWith(".xml")&&Character.isDigit(filename.charAt(0))){
                        return true;
                    }
                }
            }
        }

        // log error
        String str = "[Game " + filePath + " – no maps found]";
        logCheckFailure(gameChecker.getFileWriter(), str);

        return false;
    }
}
