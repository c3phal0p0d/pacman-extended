package src.matachi.mapeditor.editor;

public abstract class Mode {
    Controller controller;
    String filePath;

    public Mode(Controller controller, String filePath){
        this.controller = controller;
        this.filePath = filePath;
    }

}
