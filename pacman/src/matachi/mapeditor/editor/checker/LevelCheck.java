package src.matachi.mapeditor.editor.checker;

import src.Map;
import src.matachi.mapeditor.grid.Grid;

import java.io.FileWriter;
import java.io.IOException;

public abstract class LevelCheck {
    public abstract boolean check(Map map);
    private FileWriter fileWriter;

    public LevelCheck(FileWriter fileWriter){
        this.fileWriter = fileWriter;
    }

    public void logCheckFailure(String str) {
        try {
            fileWriter.write(str);
            fileWriter.write("\n");
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
