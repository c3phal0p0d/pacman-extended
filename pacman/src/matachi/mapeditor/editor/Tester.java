package src.matachi.mapeditor.editor;

import src.matachi.mapeditor.editor.checker.gamechecker.GameChecker;
import src.matachi.mapeditor.editor.checker.levelchecker.LevelChecker;
import src.matachi.mapeditor.editor.testerstrategy.TesterStrategy;

public class Tester {
    LevelChecker levelChecker;
    GameChecker gameChecker;
    TesterStrategy testerStrategy;

    public Tester(String filePath, TesterStrategy testerStrategy) {

        this.gameChecker = GameChecker.getInstance();
        this.levelChecker = LevelChecker.getInstance();
        this.testerStrategy = testerStrategy;

        // Apply Game Checks
        boolean isGameValid = gameChecker.performChecks(filePath);

        // Game is valid, load and test the maps
        if(isGameValid) {
            System.out.println("Game checks succeeded");
            testerStrategy.test(filePath);
        }
        // Game is invalid, go back to editor
        else {
            System.out.println("Failed game checks");

            // If checks fail, return to edit mode with no map
            testerStrategy.changeMode(null);
        }

    }
}