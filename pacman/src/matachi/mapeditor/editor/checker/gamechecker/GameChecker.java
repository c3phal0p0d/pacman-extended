package src.matachi.mapeditor.editor.checker.gamechecker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GameChecker {
    private final String logFilePath = "EditorLog.txt";
    private ArrayList<GameCheck> gameChecks;
    private FileWriter fileWriter;

    private static GameChecker instance = null;

    private GameChecker(){
        try {
            fileWriter = new FileWriter(new File(logFilePath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        gameChecks = new ArrayList<>();
        gameChecks.add(new OneCorrectlyNamedMapCheck(this));
        gameChecks.add(new MapUniqueNumberCheck(this));
    }

    public static GameChecker getInstance() {
        if(instance == null) {
            instance = new GameChecker();
        }
        return instance;
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

    public FileWriter getFileWriter() {
        return fileWriter;
    }
}
