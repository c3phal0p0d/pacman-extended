package src.matachi.mapeditor.editor;

public abstract class Mode {
    Editor editor;
    String filePath;

    public Mode(Editor editor, String filePath){
        this.editor = editor;
        this.filePath = filePath;
    }

    public void updateGrid(int width, int height) {
        editor.getView().close();
        editor.init(width, height);
        editor.getView().setSize(width, height);
    }
}
