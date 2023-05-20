package src.matachi.mapeditor.editor.checker;

import src.matachi.mapeditor.grid.Grid;

import java.io.FileWriter;
import java.util.ArrayList;

/* Level check D. each Gold and Pill is accessible to PacMan from the starting point, ignoring monsters but accounting for valid portals */
public class LevelCheckD extends LevelCheck {
    public LevelCheckD(FileWriter fileWriter) {
        super(fileWriter);
    }

    @Override
    public boolean check(Grid levelMap, String mapFilePath) {
        int count = 0;
        ArrayList<int[]> pacmanPositions = new ArrayList<>();

        // Go through grid finding all PacMan's starting points & add them to array
        for (int y=0; y<levelMap.getHeight(); y++) {
            for (int x = 0; x < levelMap.getWidth(); x++) {
                if (levelMap.getTile(x, y)=='f'){   // PacMan
                    int[] position = new int[2];
                    position[0] = x;
                    position[1] = y;
                    pacmanPositions.add(position);
                    count++;
                }
            }
        }

        if (count==0){
            // log error
            String str = "Level " + mapFilePath + " - no start for PacMan";
            logCheckFailure(str);
            return false;
        }
        else if (count>1){
            // log error: [Level 5_levelname.xml – more than one start for Pacman: (3,7); (8, 1); (5, 2)]
            StringBuilder str = new StringBuilder("Level " + mapFilePath + " - more than one start for PacMan: ");
            for (int[] position: pacmanPositions){
                String positionStr = String.format("(%d, %d); ", position[0], position[1]);
                str.append(positionStr);
            }
            logCheckFailure(str.toString());
            return false;
        }

        return true;
    }
}
