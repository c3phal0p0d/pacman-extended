package src.matachi.mapeditor.editor.checker.levelchecker;

import src.matachi.Map;

import java.util.ArrayList;

/* Checks that there are exactly two tiles for each portal appearing on the map */
public class PortalPairCheck implements LevelCheck {

    private LevelChecker levelChecker;

    public PortalPairCheck(LevelChecker levelChecker) {
        this.levelChecker = levelChecker;
    }

    @Override
    public boolean check(Map map) {
        int whiteCount = 0;
        int yellowCount = 0;
        int darkGoldCount = 0;
        int darkGrayCount = 0;
        ArrayList<int[]> whitePositions = new ArrayList<>();
        ArrayList<int[]> yellowPositions = new ArrayList<>();
        ArrayList<int[]> darkGoldPositions = new ArrayList<>();
        ArrayList<int[]> darkGrayPositions = new ArrayList<>();

        for (int y=0; y<map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                int[] position = new int[2];
                position[0] = x;
                position[1] = y;
                if (map.getTile(x, y) == 'i'){ // White portal
                    whitePositions.add(position);
                    whiteCount++;
                } else if (map.getTile(x, y) == 'j'){  // Yellow portal
                    yellowPositions.add(position);
                    yellowCount++;
                } else if (map.getTile(x, y) == 'k'){  // Dark gold portal
                    darkGoldPositions.add(position);
                    darkGoldCount++;
                } else if (map.getTile(x, y) == 'l'){  // Dark gray portal
                    darkGrayPositions.add(position);
                    darkGrayCount++;
                }
            }
        }

        if (whiteCount>0&&whiteCount!=2){
            // log error
            StringBuilder str = new StringBuilder("Level " + map.getName() + " - portal White count is not 2: ");
            for (int i=0; i<whitePositions.size(); i++){
                int[] position = whitePositions.get(i);
                String positionStr;
                if (i==whitePositions.size()-1){
                    positionStr = String.format("(%d,%d)", position[0]+1, position[1]+1);
                } else {
                    positionStr = String.format("(%d,%d); ", position[0]+1, position[1]+1);
                }
                str.append(positionStr);
            }
            logCheckFailure(levelChecker.getFileWriter(), str.toString());
            return false;
        }

        if (yellowCount>0&&yellowCount!=2){
            // log error
            StringBuilder str = new StringBuilder("Level " + map.getName() + " - portal Yellow count is not 2: ");
            for (int i=0; i<yellowPositions.size(); i++){
                int[] position = yellowPositions.get(i);
                String positionStr;
                if (i==yellowPositions.size()-1){
                    positionStr = String.format("(%d,%d)", position[0]+1, position[1]+1);
                } else {
                    positionStr = String.format("(%d,%d); ", position[0]+1, position[1]+1);
                }
                str.append(positionStr);
            }
            logCheckFailure(levelChecker.getFileWriter(), str.toString());
            return false;
        }

        if (darkGoldCount>0&&darkGoldCount!=2){
            // log error
            StringBuilder str = new StringBuilder("Level " + map.getName() + " - portal Dark Gold count is not 2: ");
            for (int i=0; i<darkGoldPositions.size(); i++){
                int[] position = darkGoldPositions.get(i);
                String positionStr;
                if (i==darkGoldPositions.size()-1){
                    positionStr = String.format("(%d,%d)", position[0]+1, position[1]+1);
                } else {
                    positionStr = String.format("(%d,%d); ", position[0]+1, position[1]+1);
                }
                str.append(positionStr);
            }
            logCheckFailure(levelChecker.getFileWriter(), str.toString());
            return false;
        }

        if (darkGrayCount>0&&darkGrayCount!=2){
            // log error
            StringBuilder str = new StringBuilder("Level " + map.getName() + " - portal Dark Gray count is not 2: ");
            for (int i=0; i<darkGrayPositions.size(); i++){
                int[] position = darkGrayPositions.get(i);
                String positionStr;
                if (i==darkGrayPositions.size()-1){
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
