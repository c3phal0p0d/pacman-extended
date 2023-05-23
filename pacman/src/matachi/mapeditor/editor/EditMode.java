package src.matachi.mapeditor.editor;

import org.jdom.Element;
import src.Map;
import src.matachi.mapeditor.grid.Grid;
import src.matachi.mapeditor.grid.GridView;

import java.io.File;
import java.util.List;

public class EditMode extends Mode {
    public EditMode(Controller controller, String filePath) {
        super(controller, filePath);
        controller.init(Constants.MAP_WIDTH, Constants.MAP_HEIGHT);     // Opens up the editor
        if (filePath!=null){
            loadMapIntoEditor(filePath, controller.getModel(), controller.getGrid());
        }
    }

    public void loadMapIntoEditor(String filePath, Grid model, GridView grid){
        File mapFIle = new File(filePath);
        Map map = new Map(mapFIle.getName(), filePath);

        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                model.setTile(x, y, map.getTile(x, y));
                grid.redrawGrid();
            }
        }

    }
}
