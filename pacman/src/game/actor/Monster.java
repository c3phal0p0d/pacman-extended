// Monster.java
// Used for PacMan
package src.game.actor;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;
import src.game.utility.GameCallback;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Type: Modified file
 * Team Name: Thursday 11:00am Team 1
 * Team Members:
 *      - Jiachen Si (1085839)
 *      - Natasha Chiorsac (1145264)
 *      - Jude Thaddeau Data (1085613)
 */

public abstract class Monster extends Actor implements CanMove
{
    // ATTRIBUTES:
    protected MonsterType type;
    private boolean stopMoving = false;
    protected Random randomiser = new Random(0);

    protected GameCallback gameCallback;
    private boolean isFurious = false;
    protected int numHorzCells;
    protected int numVertCells;
    private boolean isFrozen = false;

    /**
     * INSTANTIATES an instance of 'Monster'.
     * @param gameCallback  Used to display behaviour of the game
     * @param type          The type of the monster
     * @param numHorzCells  The number of HORIZONTAL cells on the board
     * @param numVertCells  The number of VERTICAL cells on the board
     */
    public Monster(GameCallback gameCallback, MonsterType type, int numHorzCells, int numVertCells)
    {
        super("sprites/" + type.getImageName());
        this.type = type;
        this.gameCallback = gameCallback;
        this.numHorzCells = numHorzCells;
        this.numVertCells = numVertCells;
    }

    /**
     * EXECUTES the monster's board navigation logic every jgamegrid simulation loop
     */
    public void act()
    {
        // CASE A: 'PacActor' eats an ice item
        if (stopMoving) {
            return;
        }
        // CASE B: 'PacActor' eats a gold item
        if (isFurious) {
            furiousWalkApproach();

        // CASE C: Regular monster navigation algorithm
        } else {
            walkApproach();
        }
        setHorzMirror(!(getDirection() > 150) || !(getDirection() < 210));
    }

    /**
     * EXECUTES the logic of when monsters move furiously as a response to 'PacActor' eating a gold item.
     */
    protected void furiousWalkApproach() {

        // STEP 1: Move accordingly
        walkApproach();

        // CASE 2A: Attempt to travel the same direction again
        Location next = getNextMoveLocation();
        if (canMove(next, getBackground(), numHorzCells, numVertCells)) {
            setLocation(next);

        // CASE 2B: Walk normally
        } else {
            walkApproach();
        }
    }

    /**
     * HANDLES the walking logic for each monster type.
     */
    protected abstract void walkApproach();

    /**
     * PAUSES the monsters on the current cell due to 'PacActor' eating an ICE item.
     * @param seconds   The number of seconds to pause the monsters for
     */
    public void stopMoving(int seconds) {

        // STEP 1: Update 'moving' flag
        this.stopMoving = true;
        this.isFrozen = true;

        // STEP 2: Instantiate & run the timer for the desired number of seconds
        Timer timer = new Timer(); // Instantiate Timer Object
        int SECOND_TO_MILLISECONDS = 1000;
        final Monster monster = this;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                monster.stopMoving = false;
                monster.isFrozen = false;
            }
        }, seconds * SECOND_TO_MILLISECONDS);
    }

    /**
     * Makes the MONSTERS move faster for a desired number of seconds
     * @param seconds   The number of seconds to keep the monsters in a furious state
     */
    public void makeFurious(int seconds) {

        // STEP 1: Only freeze monster if NOT frozen
        if(!isFrozen) {
            this.isFurious = true;

            // STEP 2: Instantiate Timer Object
            Timer timer = new Timer();
            int SECOND_TO_MILLISECONDS = 1000;
            final Monster monster = this;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    monster.isFurious = false;
                }
            }, seconds * SECOND_TO_MILLISECONDS);
        }
    }

    // GETTER & SETTER methods:
    public MonsterType getType() {
        return type;
    }
    public void setSeed(int seed) {
        randomiser.setSeed(seed);
    }
    public void setStopMoving(boolean stopMoving) {
        this.stopMoving = stopMoving;
    }
}