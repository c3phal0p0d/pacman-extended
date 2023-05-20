package src.matachi.mapeditor.editor.checker;

import src.matachi.mapeditor.grid.Grid;

import java.io.FileWriter;
import java.io.IOException;

public abstract class GameCheck {
    public abstract boolean check(Grid levelMap, String gameFolderFilePath);
    private FileWriter fileWriter;

    public GameCheck(FileWriter fileWriter){
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
