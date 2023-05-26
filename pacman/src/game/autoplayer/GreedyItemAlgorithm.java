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
        if (neighbours == null) {
            return false;
        }
        // STEP 3: Move to the neighbour location if it has an item
        Location itemLocation = this.checkNeighbourItem(player, neighbours);
        if (itemLocation != null) {
            player.setLocation(itemLocation);
            player.eatPill(itemLocation);
        }
        // STEP 4: Check which neighbour is closer to THEIR closest pill

        // STEP 5: Move to the neighbour who is closest to it's closest pill

        return true;
    }

    /**
     * AUXILIARY - GENERATES an ArrayList of the player's valid neighbour locations.
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

    /**
     * AUXILIARY - CHECKS if any of the player's neighbours stores an item.
     * @param   player      The player to check the neighbours of
     * @param   neighbours  The neighbour cells of the player
     * @return  An instance of a `Location` that holds an item, or `null` if no neighbours
     *          store an item
     */
    public Location checkNeighbourItem(PacActor player, ArrayList<Location> neighbours) {

        // STEP 1: Check if any of the locations store items
        for (Location neighbour: neighbours) {
            for (Location itemLocation: player.getItemManager().getPillAndItemLocations()) {
                if (neighbour.equals(itemLocation)) {
                    return itemLocation;
                }
            }
        }
        // STEP 2: No neighbours contain items
        return null;
    }
}
