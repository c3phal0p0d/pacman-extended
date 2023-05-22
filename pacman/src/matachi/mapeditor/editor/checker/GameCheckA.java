package src.matachi.mapeditor.editor.checker;

import src.matachi.mapeditor.grid.Grid;

import java.io.FileWriter;

/* Game check A. at least one correctly named map file in the folder */
public class GameCheckA extends GameCheck {

    public GameCheckA(FileWriter fileWriter) {
        super(fileWriter);
    }

    @Override
    public boolean check(String gameFolderFilePath) {
        return false;
    }
}
