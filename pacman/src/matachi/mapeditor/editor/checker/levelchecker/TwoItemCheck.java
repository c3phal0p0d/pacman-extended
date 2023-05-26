package src.matachi.mapeditor.editor.checker.levelchecker;

import src.matachi.Map;

/* Checks that there are at least two Gold and Pill in total */
public class TwoItemCheck implements LevelCheck {

    private LevelChecker levelChecker;

    public TwoItemCheck(LevelChecker levelChecker) {
        this.levelChecker = levelChecker;
    }

    @Override
    public boolean check(Map map) {
        int goldAndPillCount = 0;

        // Go through grid finding all squares with gold and pills, and counting them up
        for (int y=0; y<map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                if (map.getTile(x, y)=='c'||map.getTile(x, y)=='d'){   // Gold or Pill
                    goldAndPillCount++;
                }
            }
        }

        if (goldAndPillCount<2){
            // log error
            String str = "Level " + map.getName() + " - less than 2 Gold and Pill";
            logCheckFailure(levelChecker.getFileWriter(), str);
            return false;
        }

        return true;
    }
}
