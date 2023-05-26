package src.matachi.mapeditor.editor;

import src.matachi.mapeditor.editor.checker.gamechecker.GameChecker;
import src.matachi.mapeditor.editor.checker.levelchecker.LevelChecker;
import src.matachi.mapeditor.editor.testerstrategy.TesterStrategy;

public class Tester {
    TesterStrategy testerStrategy;

    public Tester(String filePath, TesterStrategy testerStrategy) {

        this.testerStrategy = testerStrategy;
        testerStrategy.test(filePath);
    }
}