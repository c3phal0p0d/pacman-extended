package src.matachi.mapeditor.editor.checker.levelchecker;

import src.Map;
import src.matachi.mapeditor.editor.checker.Check;

import java.io.FileWriter;
import java.io.IOException;

public interface LevelCheck extends Check {

    boolean check(Map map);
}
