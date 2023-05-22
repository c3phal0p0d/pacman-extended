package src.matachi.mapeditor.editor.checker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GameChecker {
    private final String logFilePath = "EditorLog.txt";
    private ArrayList<GameCheck> gameChecks;
    private FileWriter fileWriter;

    public GameChecker(){
        try {
            fileWriter = new FileWriter(new File(logFilePath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        gameChecks = new ArrayList<GameCheck>();
        gameChecks.add(new OneCorrectlyNamedMapCheck(fileWriter));
        gameChecks.add(new MapUniqueNumberCheck(fileWriter));
    }

    public boolean performChecks(String gameFolderFilePath){
        for (GameCheck gameCheck : gameChecks){
            boolean isValid = gameCheck.check(gameFolderFilePath);
            if (!isValid){
                return false;
            }
        }
        return true;
    }
}
