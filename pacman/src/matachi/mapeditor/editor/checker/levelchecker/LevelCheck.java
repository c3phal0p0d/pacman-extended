package src.matachi.mapeditor.editor.checker.levelchecker;

import src.matachi.Map;
import src.matachi.mapeditor.editor.checker.Check;

public interface LevelCheck extends Check {

    boolean check(Map map);
}
