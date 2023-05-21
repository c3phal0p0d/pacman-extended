package src.matachi.mapeditor.editor.checker;

import src.matachi.mapeditor.grid.Grid;

import java.io.FileWriter;
import java.util.ArrayList;

/* Level check D. each Gold and Pill is accessible to PacMan from the starting point, ignoring monsters but accounting for valid portals */
public class LevelCheckD extends LevelCheck {
    public LevelCheckD(FileWriter fileWriter) {
        super(fileWriter);
    }

    @Override
    public boolean check(Grid levelMap, String mapFilePath) {
        return false;
    }
}
