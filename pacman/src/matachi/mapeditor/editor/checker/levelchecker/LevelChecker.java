package src.matachi.mapeditor.editor.checker.levelchecker;

import src.game.Map;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LevelChecker {
    private final String logFilePath = "EditorLog.txt";
    private ArrayList<LevelCheck> levelChecks;
    private FileWriter fileWriter;

    private static LevelChecker instance = null;

    private LevelChecker(){
        try {
            fileWriter = new FileWriter(new File(logFilePath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        levelChecks = new ArrayList<LevelCheck>();
        levelChecks.add(new OneStartingPointCheck(this));
        levelChecks.add(new PortalPairCheck(this));
        levelChecks.add(new TwoItemCheck(this));
    }

    public static LevelChecker getInstance() {
        if(instance == null) {
            instance = new LevelChecker();
        }
        return instance;
    }

    public boolean performChecks(Map map){
        boolean allChecksValid = true;
        for (LevelCheck levelCheck : levelChecks){
            boolean isValid = levelCheck.check(map);
            if (!isValid){
                allChecksValid = false;
            }
        }
        return allChecksValid;
    }

    public FileWriter getFileWriter() {
        return fileWriter;
    }
}
