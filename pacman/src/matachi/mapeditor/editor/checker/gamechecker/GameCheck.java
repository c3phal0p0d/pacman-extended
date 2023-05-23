package src.matachi.mapeditor.editor.checker.gamechecker;

import src.matachi.mapeditor.editor.checker.Check;

public interface GameCheck extends Check {
    boolean check(String gameFolderFilePath);
}
