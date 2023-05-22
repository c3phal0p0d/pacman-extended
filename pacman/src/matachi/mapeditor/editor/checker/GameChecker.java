package src.matachi.mapeditor.editor.checker;

import src.matachi.mapeditor.grid.Grid;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GameChecker {
    private final String logFilePath = "EditorLog.txt";
    private ArrayList<GameCheck> gameChecks;
    private FileWriter fileWriter;
    private boolean isGameValid = true;

    public GameChecker(){
        try {
            fileWriter = new FileWriter(new File(logFilePath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        gameChecks = new ArrayList<GameCheck>();
        gameChecks.add(new GameCheckA(fileWriter));
    }

    public void performChecks(String gameFolderFilePath){
        boolean isValid = true;
        for (GameCheck gameCheck : gameChecks){
            isValid = gameCheck.check(gameFolderFilePath);
            if (!isValid){
                isGameValid = false;
            }
        }
    }
}
