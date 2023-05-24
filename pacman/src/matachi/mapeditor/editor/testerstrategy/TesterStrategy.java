package src.matachi.mapeditor.editor.testerstrategy;

import src.matachi.mapeditor.editor.Editor;
import src.matachi.mapeditor.editor.checker.levelchecker.LevelChecker;

public abstract class TesterStrategy {

    protected LevelChecker levelChecker = LevelChecker.getInstance();

    public abstract void test(String filePath);

    public void changeMode(String filepath) {
        new Editor(filepath);
    }
}
