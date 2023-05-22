package src.matachi.mapeditor.editor.checker;

import src.Map;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LevelChecker {
    private final String logFilePath = "EditorLog.txt";
    private ArrayList<LevelCheck> levelChecks;
    private FileWriter fileWriter;

    public LevelChecker(){
        try {
            fileWriter = new FileWriter(new File(logFilePath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        levelChecks = new ArrayList<LevelCheck>();
        levelChecks.add(new OneStartingPointCheck(fileWriter));
        levelChecks.add(new PortalPairCheck(fileWriter));
        levelChecks.add(new TwoItemCheck(fileWriter));
    }

    public boolean performChecks(Map map){
        for (LevelCheck levelCheck : levelChecks){
            boolean isValid = levelCheck.check(map);
            if (!isValid){
                return false;
            }
        }
        return true;
    }
}
