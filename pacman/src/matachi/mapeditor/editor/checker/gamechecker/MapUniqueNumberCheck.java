package src.matachi.mapeditor.editor.checker.gamechecker;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

/* Checks that the sequence of map files is well-defined, that is, is there only one map file named with a particular number */
public class MapUniqueNumberCheck implements GameCheck {

    private GameChecker gameChecker;

    public MapUniqueNumberCheck(GameChecker gameChecker) {
        this.gameChecker = gameChecker;
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
                for (int i=0; i<array.size(); i++){
                    Object filename = array.get(i);
                    String positionStr;
                    if (i==array.size()-1){
                        positionStr = String.format("%s; ", filename.toString());
                    }
                    else {
                        positionStr = String.format("%s", filename.toString());
                    }
                    str.append(positionStr);
                }
                logCheckFailure(gameChecker.getFileWriter(), str.toString());;
                return false;
            }
        }

        return true;
    }
}
