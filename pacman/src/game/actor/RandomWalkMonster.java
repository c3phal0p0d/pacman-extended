package src.game.actor;

import ch.aplu.jgamegrid.Location;
import src.game.utility.GameCallback;
/**
 * Type: New file
 * Team Name: Thursday 11:00am Team 1
 * Team Members:
 *      - Jiachen Si (1085839)
 *      - Natasha Chiorsac (1145264)
 *      - Jude Thaddeau Data (1085613)
 */

public abstract class RandomWalkMonster extends Monster {

    /**
     * INSTANTIATES a new instance of a 'RandomWalkMonster'.
     * @param gameCallback  Used to display behaviour of the game
     * @param type          The TYPE of the monster
     * @param numHorzCells  The number of HORIZONTAL cells on the board
     * @param numVertCells  The number of VERTICAL cells on the board
     */
    public RandomWalkMonster(GameCallback gameCallback, MonsterType type, int numHorzCells, int numVertCells) {
        super(gameCallback, type, numHorzCells, numVertCells);
    }

    /**
     * Handles the LOGIC for walking RANDOMLY.
     * @param   oldDirection  The location the monster was at before
     * @return  A new location of where the monster must navigate to
     */
    protected Location randomWalk(double oldDirection) {

        // STEP 1: Randomly generate a sign to choose direction
        int sign = randomiser.nextDouble() < 0.5 ? 1 : -1;
        setDirection(oldDirection);

        // STEP 2: Tilt towards a legal direction
        turn(sign * 90);
        Location next = getNextMoveLocation();

        // CASE 3A: The new direction is VALID
        if (canMove(next, getBackground(), numHorzCells, numVertCells))
        {
            setLocation(next);
        }
        // CASE 3B: The new direction is INVALID
        else
        {
            // STEP 5: Calculate an alternate direction
            setDirection(oldDirection);
            next = getNextMoveLocation();

            // STEP 6: Try to move forward
            if (canMove(next, getBackground(), numHorzCells, numVertCells))
            {
                setLocation(next);
            }
            // CASE 7: Cannot move forward
            else
            {
                // STEP 8: Calculate a new direction
                setDirection(oldDirection);

                // STEP 9: Try to turn right/left
                turn(-sign * 90);
                next = getNextMoveLocation();
                if (canMove(next, getBackground(), numHorzCells, numVertCells))
                {
                    setLocation(next);
                }
                // STEP 10: No valid locations, move to old location
                else
                {
                    setDirection(oldDirection);
                    turn(180);  // Turn backward
                    next = getNextMoveLocation();
                    setLocation(next);
                }
            }
        }
        // STEP 11: Return the next location
        return next;
    }

    /**
     * EXECUTES the random walk approach function.
     */
    protected void walkApproach()
    {
        double oldDirection = getDirection();
        randomWalk(oldDirection);
        gameCallback.monsterLocationChanged(this);
    }
}