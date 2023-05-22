package src.matachi.mapeditor.editor.checker;

import src.Map;

import java.io.FileWriter;

/* Check for each Gold and Pill being accessible to PacMan from the starting point, ignoring monsters but accounting for valid portals */
public class PacmanAccessibleCheck extends LevelCheck {
    public PacmanAccessibleCheck(FileWriter fileWriter) {
        super(fileWriter);
    }

    @Override
    public boolean check(Map map) {
        // TODO
        return true;
    }
}
