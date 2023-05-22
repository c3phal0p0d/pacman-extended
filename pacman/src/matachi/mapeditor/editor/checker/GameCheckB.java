package src.matachi.mapeditor.editor.checker;

import src.Map;
import src.matachi.mapeditor.grid.Grid;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;

/* Game check B. the sequence of map files well-defined, that is, is there only one map file named with a particular number */
public class GameCheckB extends GameCheck {

    public GameCheckB(FileWriter fileWriter) {
        super(fileWriter);
    }

    @Override
    public boolean check(String gameFolderFilePath) {
        File gameFolder = new File(gameFolderFilePath);
        File[] mapFiles = gameFolder.listFiles();
        HashMap<Integer, ArrayList> levelMapsByNumber = new HashMap<Integer, ArrayList>();
        if (mapFiles != null) {
            for (File mapFile : mapFiles) {
                String filename = mapFile.getName();
                if (filename.endsWith(".xml")&&Character.isDigit(filename.charAt(0))){  // valid map file
                    int level = filename.charAt(0);
                    if (levelMapsByNumber.get(level)==null){
                        levelMapsByNumber.put(level, new ArrayList<String>());
                        levelMapsByNumber.get(level).add(filename);
                    } else {
                        levelMapsByNumber.get(level).add(filename);
                    }
                }
            }
        }

        for (ArrayList array : levelMapsByNumber.values()){
            if (array.size()>1){
                StringBuilder str = new StringBuilder("Game " + gameFolderFilePath + " – – multiple maps at same level: ");
                for (Object filename : array){
                    String positionStr = String.format("%s; ", filename.toString());
                    str.append(positionStr);
                }
                logCheckFailure(str.toString());;
                return false;
            }
        }

        return true;
    }
}
