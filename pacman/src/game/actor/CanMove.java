package src.game.actor;

import ch.aplu.jgamegrid.GGBackground;
import ch.aplu.jgamegrid.Location;

import java.awt.*;

public interface CanMove {

    /**
     * CHECKS if 'PacActor' can move towards a given location.
     * @param   location    The location to check if 'PacActor' can move into
     * @return  'true' if the move is possible, 'false' otherwise
     */
    default boolean canMove(Location location, GGBackground bg, int numHorzCells, int numVertCells)
    {
        // Checks if the player can traverse to the given tile.
        Color c = bg.getColor(location);
        if ( c.equals(Color.gray) || location.getX() >= numHorzCells
                || location.getX() < 0 || location.getY() >= numVertCells || location.getY() < 0)
            // Tile is grey or is outside the board
            return false;
        else
            return true;
    }
}
