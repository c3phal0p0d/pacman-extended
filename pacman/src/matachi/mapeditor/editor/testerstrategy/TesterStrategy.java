package src.matachi.mapeditor.editor.testerstrategy;

import src.matachi.mapeditor.editor.Editor;
import src.matachi.mapeditor.editor.checker.levelchecker.LevelChecker;

public interface TesterStrategy {

    LevelChecker levelChecker = LevelChecker.getInstance();

    void test(String filePath);

    default void changeMode(String filepath) {
        new Editor(filepath);
    }
}
