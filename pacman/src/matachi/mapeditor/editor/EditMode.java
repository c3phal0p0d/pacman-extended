package src.matachi.mapeditor.editor;

public class EditMode extends Mode {
    public EditMode(Controller controller) {
        super(controller);
        controller.init(Constants.MAP_WIDTH, Constants.MAP_HEIGHT);     // Opens up the editor
    }
}
