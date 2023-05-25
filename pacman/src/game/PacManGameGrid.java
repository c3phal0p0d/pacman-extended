// PacGrid.java
package src.game;

import ch.aplu.jgamegrid.GGBackground;
import ch.aplu.jgamegrid.Location;

import java.awt.*;

/**
 * Type: Modified file
 * Team Name: Thursday 11:00am Team 1
 * Team Members:
 *      - Jiachen Si (1085839)
 *      - Natasha Chiorsac (1145264)
 *      - Jude Thaddeau Data (1085613)
 */

public class PacManGameGrid
{
    // ATTRIBUTES:
    private int nbHorzCells;
    private int nbVertCells;
    private int[][] mazeArray;

    /**
     * INSTANTIATES an instance of 'PacManGameGrid'.
     * @param nbHorzCells   The number of HORIZONTAL cells on the board
     * @param nbVertCells   The number of VERTICAL cells on the board
     */
    public PacManGameGrid(int nbHorzCells, int nbVertCells)
    {
        // STEP 1: Assign the data to relevant attributes
        this.nbHorzCells = nbHorzCells;
        this.nbVertCells = nbVertCells;
        mazeArray = new int[nbVertCells][nbHorzCells];
        String maze =
                "xxxxxxxxxxxxxxxxxxxx" + // 0
                        "x....x....g...x....x" + // 1
                        "xgxx.x.xxxxxx.x.xx.x" + // 2
                        "x.x.......i.g....x.x" + // 3
                        "x.x.xx.xx  xx.xx.x.x" + // 4
                        "x......x    x......x" + // 5
                        "x.x.xx.xxxxxx.xx.x.x" + // 6
                        "x.x......gi......x.x" + // 7
                        "xixx.x.xxxxxx.x.xx.x" + // 8
                        "x...gx....g...x....x" + // 9
                        "xxxxxxxxxxxxxxxxxxxx";// 10


        // STEP 2: Copy maze structure into integer array
        for (int i = 0; i < nbVertCells; i++)
        {
            for (int k = 0; k < nbHorzCells; k++) {
                int value = toInt(maze.charAt(nbHorzCells * i + k));
                mazeArray[i][k] = value;
            }
        }
    }

    /**
     * GET the cell at a specified location on the grid.
     * @param   location  The cell's coordinates
     * @return  An INTEGER where 0 represents a Wall, 1 for a PILL, 2 for EMPTY space, 3 for GOLD items & 4 for
     *          ICE items.
     */
    public int getCell(Location location)
    {
        return mazeArray[location.y][location.x];
    }

    /**
     * CONVERTS a character representing an element in the maze to a corresponding integer
     * @param   c     A cell in the 'mazeArray'
     * @return  An INTEGER where 0 represents a Wall, 1 for a PILL, 2 for EMPTY space, 3 for GOLD items & 4 for
     *          ICE items.
     */
    private int toInt(char c)
    {
        if (c == 'x') // Maze Wall
            return 0;
        if (c == '.') // Pill
            return 1;
        if (c == ' ') // Empty
            return 2;
        if (c == 'g') // Gold
            return 3;
        if (c == 'i') // Ice
            return 4;
        return -1;    // Not Recognised
    }

    /**
     * DRAWS the game grid.
     * @param game  The 'Game' instance to draw the grid onto
     * @param bg    The background of the game grid
     */
    public void drawGrid(Game game, GGBackground bg)
    {
        // STEP 1: Loop through each location on the board
        bg.clear(Color.gray);
        bg.setPaintColor(Color.white);
        for (int y = 0; y < nbVertCells; y++)
        {
            for (int x = 0; x < nbHorzCells; x++)
            {
                // STEP 2: Extract the location
                bg.setPaintColor(Color.white);
                Location location = new Location(x, y);
                int a = getCell(location);

                // CASE 3A: Place a WALL
                if (a > 0)
                    bg.fillCell(location, Color.lightGray);

                // CASE 3B: Place a PILL
                if (a == 1 && game.getItemManager().getPropertyPillLocations().size() == 0) {
                    game.getItemManager().putPill(location, game);

                // CASE 3C: Place a GOLD item
                } else if (a == 3 && game.getItemManager().getPropertyGoldLocations().size() == 0) {
                    game.getItemManager().putGold(location, game);

                // CASE 3D: Place an ICE item
                } else if (a == 4) {
                    game.getItemManager().putIce(location, game);
                }
            }
        }
        for (Location location : game.getItemManager().getPropertyPillLocations()) {
            game.getItemManager().putPill(location, game);
        }
        for (Location location : game.getItemManager().getPropertyGoldLocations()) {
            game.getItemManager().putGold(location, game);
        }
    }
}