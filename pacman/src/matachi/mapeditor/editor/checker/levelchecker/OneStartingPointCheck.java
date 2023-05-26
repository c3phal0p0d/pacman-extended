package src.matachi.mapeditor.editor.checker.levelchecker;

import src.matachi.Map;
import src.matachi.mapeditor.editor.Constants;

import java.util.ArrayList;

/* Checks that there is exactly one starting point for PacMan */
public class OneStartingPointCheck implements LevelCheck {

    private LevelChecker levelChecker;
    public OneStartingPointCheck(LevelChecker levelChecker) {
        this.levelChecker = levelChecker;
    }

    @Override
    public boolean check(Map map) {
        int count = 0;
        ArrayList<int[]> pacmanPositions = new ArrayList<>();

        // Go through grid finding all PacMan's starting points & add them to array
        for (int y=0; y<map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                if (map.getTile(x, y)==Constants.PAC_TILE_CHAR){
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
            String str = "Level " + map.getName() + " - no start for PacMan";
            logCheckFailure(levelChecker.getFileWriter(), str);
            return false;
        }
        else if (count>1){
            // log error: [Level 5_levelname.xml – more than one start for Pacman: (3,7); (8, 1); (5, 2)]
            StringBuilder str = new StringBuilder("Level " + map.getName() + " - more than one start for PacMan: ");
            for (int i=0; i<pacmanPositions.size(); i++){
                int[] position = pacmanPositions.get(i);
                String positionStr;
                if (i==pacmanPositions.size()-1){
                    positionStr = String.format("(%d,%d)", position[0]+1, position[1]+1);
                } else {
                    positionStr = String.format("(%d,%d); ", position[0]+1, position[1]+1);
                }
                str.append(positionStr);
            }
            logCheckFailure(levelChecker.getFileWriter(), str.toString());
            return false;
        }

        return true;
    }
}
