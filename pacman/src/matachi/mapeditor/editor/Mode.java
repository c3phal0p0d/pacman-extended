package src.matachi.mapeditor.editor;

import src.matachi.mapeditor.editor.checker.GameCheck;
import src.matachi.mapeditor.editor.checker.GameChecker;
import src.matachi.mapeditor.editor.checker.LevelChecker;

public abstract class Mode {
    Controller controller;

    public Mode(Controller controller){
        this.controller = controller;
    }


}
