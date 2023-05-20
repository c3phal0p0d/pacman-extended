package src.matachi.mapeditor.editor.checker;

import src.matachi.mapeditor.grid.Grid;

import java.io.FileWriter;

/* Level check A. exactly one starting point for PacMan */
public class LevelCheckA extends LevelCheck {
    public LevelCheckA(FileWriter fileWriter) {
        super(fileWriter);
    }

    @Override
    public boolean check(Grid levelMap, String mapFilepath) {
        int count = 0;
        // Go through grid finding all PacMan's starting points & counting them up

        if (count==0){
            // log error
            logCheckFailure("Level " + mapFilepath + " - no start for PacMan");
            return false;
        }
        else if (count>1){
            // log error: [Level 5_levelname.xml – more than one start for Pacman: (3,7); (8, 1); (5, 2)]
            logCheckFailure("Level " + mapFilepath + " - more than one start for PacMan");
            return false;
        }

        return true;
    }
}
