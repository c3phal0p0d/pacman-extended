package src.game.autoplayer;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.*;
import src.game.actor.PacActor;

import java.util.ArrayList;

public class GreedyItemAlgorithm implements AutoPlayerAlgorithm {

    /**
     * INSTANTIATES an instance of `GreedyItemAlgorithm`.
     */
    public GreedyItemAlgorithm () {}

    /**
     * EXECUTES the `AutoPlayer` by greedily moving towards the closest item
     * @param   player The agent the player is relinquishing control of
     * @return  A boolean where `true`
     */
    @Override
    public boolean performAlgorithm(PacActor player) {

        // STEP 1: Get a list of all the valid neighbour locations
        ArrayList<Location> neighbours = this.getValidNeighbours(player);

        // STEP 2: Check if there are no neighbour cells
        if ( neighbours == null) {
            return false;
        }
        // STEP 3: Move to the neighbour location if it has an item

        // STEP 4: Check which neighbour is closer to THEIR closest pill

        // STEP 5: Move to the neighbour closet to it's pill

        return true;
    }

    /**
     * GENERATES an ArrayList of the player's valid neighbour locations.
     * @param   player  The player to determine the neighbours of
     * @return  An ArrayList of neighbour locations or null if there are no
     *          neighbour locations
     */
    public ArrayList<Location> getValidNeighbours(PacActor player) {

        // STEP 1: Initialise the arrayList
        ArrayList<Location> neighbours = new ArrayList<>();

        // STEP 2: Go through each neighbour & add them to the list if they are valid
        Location neighbour;
        for (Location.CompassDirection dir: Location.CompassDirection.values()) {

            // STEP 3: Only add the neighbour if the player can move to that location
            neighbour = player.getLocation().getNeighbourLocation(dir);
            if (player.canMove(neighbour, player.getBackground(),
                    player.getNbHorzCells(), player.getNbVertCells())) {
                neighbours.add(neighbour);
            }
        }
        // CASE 4A: If the there are no neighbours the map is invalid
        if (neighbours.isEmpty()) {
            return null;
        }
        // CASE 4B: If there are neighbours, the map is valid
        return neighbours;
    }
}
