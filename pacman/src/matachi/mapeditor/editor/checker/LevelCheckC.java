package src.matachi.mapeditor.editor.checker;

import src.matachi.mapeditor.grid.Grid;

import java.io.FileWriter;
import java.util.ArrayList;

/* Level check C. at least two Gold and Pill in total */
public class LevelCheckC extends LevelCheck {
    public LevelCheckC(FileWriter fileWriter) {
        super(fileWriter);
    }

    @Override
    public boolean check(Grid levelMap, String mapFilePath) {
        int goldAndPillCount = 0;

        // Go through grid finding all squares with gold and pills, and counting them up
        for (int y=0; y<levelMap.getHeight(); y++) {
            for (int x = 0; x < levelMap.getWidth(); x++) {
                if (levelMap.getTile(x, y)=='c'||levelMap.getTile(x, y)=='d'){   // Gold or Pill
                    goldAndPillCount++;
                }
            }
        }

        if (goldAndPillCount<2){
            // log error
            String str = "Level " + mapFilePath + " - less than 2 Gold and Pill";
            logCheckFailure(str);
            return false;
        }

        return true;
    }
}
