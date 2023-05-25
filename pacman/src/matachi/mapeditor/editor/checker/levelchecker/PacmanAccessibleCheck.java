package src.matachi.mapeditor.editor.checker.levelchecker;

import src.game.Map;

/* Check for each Gold and Pill being accessible to PacMan from the starting point, ignoring monsters but accounting for valid portals */
public class PacmanAccessibleCheck implements LevelCheck {

    private LevelChecker levelChecker;

    public PacmanAccessibleCheck(LevelChecker levelChecker) {
        this.levelChecker = levelChecker;
    }

    @Override
    public boolean check(Map map) {
        // TODO
        return true;
    }
}
