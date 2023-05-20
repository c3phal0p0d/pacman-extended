package src.matachi.mapeditor.editor.checker;

import src.matachi.mapeditor.grid.Grid;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LevelChecker {
    private final String logFilePath = "EditorLog.txt";
    private ArrayList<LevelCheck> levelChecks;
    private FileWriter fileWriter;
    private boolean isLevelValid = true;

    public LevelChecker(){
        try {
            fileWriter = new FileWriter(new File(logFilePath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        levelChecks = new ArrayList<LevelCheck>();
        levelChecks.add(new LevelCheckA(fileWriter));
    }

    public void performChecks(Grid levelMap, String mapFilepath){
        boolean isValid = true;
        for (LevelCheck levelCheck : levelChecks){
            isValid = levelCheck.check(levelMap, mapFilepath, logFilePath);
            if (!isValid){
                isLevelValid = false;
            }
        }
    }
}
