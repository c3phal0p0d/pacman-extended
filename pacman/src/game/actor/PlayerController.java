package src.game.actor;

import ch.aplu.jgamegrid.GGKeyRepeatListener;
import ch.aplu.jgamegrid.Location;

import java.awt.event.KeyEvent;

/**
 * Type: New file
 * Team Name: Thursday 11:00am Team 1
 * Team Members:
 *      - Jiachen Si (1085839)
 *      - Natasha Chiorsac (1145264)
 *      - Jude Thaddeau Data (1085613)
 */

public class PlayerController implements GGKeyRepeatListener {

    // ATTRIBUTES:
    private PacActor pacActor;

    /**
     * INSTANTIATES an instance of 'PlayerController' that is responsible for controlling 'PacActor'.
     * @param pacActor  An instance of 'PacActor' that is to be controlled by the user
     */
    public PlayerController(PacActor pacActor){
        this.pacActor = pacActor;
    }

    /**
     * READS & EXECUTES keyboard input supplied by the player
     * @param keyCode   The keyboard value being read
     */
    public void keyRepeated(int keyCode)
    {
        // STEP 1: Auto mode does not require keyboard input
        if (pacActor.getAuto()) {
            return;
        }
        // STEP 2: Only perform keyboard moves if 'PacActor' exists
        if (pacActor.isRemoved())
            return;

        // STEP 3: Process instances of valid keyboard movements
        Location next = null;
        switch (keyCode)
        {
            // CASE 4A: Move left
            case KeyEvent.VK_LEFT:
                next = pacActor.getLocation().getNeighbourLocation(Location.WEST);
                pacActor.setDirection(Location.WEST);
                break;

            // CASE 4B: Move up
            case KeyEvent.VK_UP:
                next = pacActor.getLocation().getNeighbourLocation(Location.NORTH);
                pacActor.setDirection(Location.NORTH);
                break;

            // CASE 4C: Move right
            case KeyEvent.VK_RIGHT:
                next = pacActor.getLocation().getNeighbourLocation(Location.EAST);
                pacActor.setDirection(Location.EAST);
                break;

            // CASE 4D: Move down
            case KeyEvent.VK_DOWN:
                next = pacActor.getLocation().getNeighbourLocation(Location.SOUTH);
                pacActor.setDirection(Location.SOUTH);
                break;
        }
        // STEP 5: Execute the move if it is valid
        if (next != null && pacActor.canMove(next, pacActor.getBackground(),
                                             pacActor.getNbHorzCells(), pacActor.getNbVertCells()))
        {
            pacActor.setLocation(next);
            pacActor.eatPill(next);
        }
    }
}