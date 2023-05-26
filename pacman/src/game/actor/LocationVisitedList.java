package src.game.actor;

import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;

/**
 * Type: New file
 * Team Name: Thursday 11:00am Team 1
 * Team Members:
 *      - Jiachen Si (1085839)
 *      - Natasha Chiorsac (1145264)
 *      - Jude Thaddeau Data (1085613)
 */

public interface LocationVisitedList {

    // ATTRIBUTES:
    int limit = 10;

    /**
     * CHECKS if a given location has been visited by an entity.
     * @param   location    The location to check if it has been visited
     * @param   visitedList An array of visited locations
     * @return  'true' if visited, 'false' otherwise
     */
    default boolean isVisited(Location location, ArrayList<Location> visitedList)
    {
        // STEP 1: Loop through all locations
        for (Location loc : visitedList)

            // CASE 2A: Location HAS been visited before
            if (loc.equals(location)) {
                return true;
            }
        // CASE 2B: Location has NOT been visited before
        return false;
    }

    /**
     * MARKS a location as visited.
     * @param location      The location to be marked as visited
     * @param visitedList   An array of visited locations
     */
    default void addVisitedList(Location location, ArrayList<Location> visitedList) {
        visitedList.add(location);
        if (visitedList.size() == limit)
            visitedList.remove(0);
    }
}
