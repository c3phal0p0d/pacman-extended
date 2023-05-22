package src.matachi.mapeditor.editor.checker;

import src.Map;
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
        levelChecks.add(new LevelCheckB(fileWriter));
        levelChecks.add(new LevelCheckC(fileWriter));
    }

    public void performChecks(Map map){
        boolean isValid = true;
        for (LevelCheck levelCheck : levelChecks){
            isValid = levelCheck.check(map);
            if (!isValid){
                isLevelValid = false;
            }
        }
    }
}
