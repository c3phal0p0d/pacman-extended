package src.game.actor;

import ch.aplu.jgamegrid.Location;
import src.game.utility.GameCallback;

import java.util.ArrayList;

/**
 * Type: New file
 * Team Name: Thursday 11:00am Team 1
 * Team Members:
 *      - Jiachen Si (1085839)
 *      - Natasha Chiorsac (1145264)
 *      - Jude Thaddeau Data (1085613)
 */

public class TX5 extends RandomWalkMonster implements LocationVisitedList {

    // ATTRIBUTES:
    private ArrayList<Location> visitedList = new ArrayList<Location>();
    private EntityManager entityManager;

    /**
     * INSTANTIATES a new instance of 'TX5'.
     * @param gameCallback      Used to display behaviour of the game
     * @param entityManager     An instance of the Entity manager
     * @param numHorzCells      The number of HORIZONTAL cells on the board
     * @param numVertCells      The number of VERTICAL cells on the board
     */
    public TX5 (GameCallback gameCallback, EntityManager entityManager, int numHorzCells, int numVertCells) {
        super(gameCallback, MonsterType.TX5, numHorzCells, numVertCells);
        this.entityManager = entityManager;
    }

    /**
     * TX5's walk approach aggressively follows the 'PacActor'.
     */
    protected void walkApproach() {

        // STEP 1: Calculate the compass direction of 'PacActor'
        Location pacLocation = entityManager.getPacActorLocation();
        double oldDirection = getDirection();
        Location.CompassDirection compassDir = getLocation().get4CompassDirectionTo(pacLocation);
        Location next = getLocation().getNeighbourLocation(compassDir);
        setDirection(compassDir);

        // CASE 2A: Can follow PacActor
        if (!isVisited(next, visitedList) && canMove(next, getBackground(), numHorzCells, numVertCells)) {
            setLocation(next);

        // CASE 2B: Behaves like a Troll
        } else {
            next = randomWalk(oldDirection);
        }
        // STEP 3: Update the set of visited locations
        gameCallback.monsterLocationChanged(this);
        addVisitedList(next, visitedList);
    }
}
