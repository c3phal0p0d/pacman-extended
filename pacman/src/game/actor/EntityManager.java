package src.game.actor;

import ch.aplu.jgamegrid.Location;
import src.game.Game;
import src.game.actor.items.Item;
import src.game.actor.items.ItemManager;
import src.game.actor.items.ItemManager;

import java.util.ArrayList;
import java.util.Properties;

/**
 * Type: New file
 * Team Name: Thursday 11:00am Team 1
 * Team Members:
 *      - Jiachen Si (1085839)
 *      - Natasha Chiorsac (1145264)
 *      - Jude Thaddeau Data (1085613)
 */


public class EntityManager {

    // ATTRIBUTES:
    private Properties properties;
    private ArrayList<Monster> monsters = new ArrayList<Monster>();
    private ItemManager itemManager;
    private PacActor pacActor;

    /**
     * INSTANTIATES an instance of 'EntityManager'.
     * @param game          The specifications of the board
     * @param itemManager   An instance of the 'ItemManager'
     */
    public EntityManager(Game game, ItemManager itemManager) {

        this.properties = game.getProperties();
        this.itemManager = itemManager;

        // STEP 1: Initialise monsters
        createTroll(game);
        createTX5(game);
    }

    /**
     * CREATES an instance of 'PacActor'.
     * @param game  The game the new 'PacActor' instance will reside in
     */
    public void createPacActor(Game game) {

        // STEP 1: Create the 'PacActor' instance
        pacActor = new PacActor(game);

        // STEP 2: Setup automatic settings
        pacActor.setPropertyMoves(properties.getProperty("PacMan.move"));
        pacActor.setAuto(Boolean.parseBoolean(properties.getProperty("PacMan.isAuto")));
        game.addKeyRepeatListener(pacActor.getPlayerController());
        pacActor.setSlowDown(3);
    }

    /**
     * CREATES an instance of a 'Troll'.
     * @param game  The game the new 'Troll' instance will reside in
     */
    private void createTroll(Game game) {

        // STEP 1: Create the 'Troll'
        Troll troll = new Troll(game.getGameCallback(), game.getNumHorzCells(), game.getNumVertCells());

        // STEP 2: Initialise the Troll's locations
        String[] trollLocations = properties.getProperty("Troll.location").split(",");
        int trollX = Integer.parseInt(trollLocations[0]);
        int trollY = Integer.parseInt(trollLocations[1]);
        game.addActor(troll, new Location(trollX, trollY), Location.NORTH);
        monsters.add(troll);
    }

    /**
     * CREATES an instance of a 'TX5'.
     * @param game  The game the new 'TX5' instance will reside in
     */
    private void createTX5(Game game) {

        // STEP 1: Create the 'TX5'
        TX5 tx5 = new TX5(game.getGameCallback(), this, game.getNumHorzCells(), game.getNumVertCells());

        // STEP 2: Initialise the TX5's locations
        String[] tx5Locations = properties.getProperty("TX5.location").split(",");
        int tx5X = Integer.parseInt(tx5Locations[0]);
        int tx5Y = Integer.parseInt(tx5Locations[1]);
        game.addActor(tx5, new Location(tx5X, tx5Y), Location.NORTH);

        // STEP 3: Ensure TX-5 doesn't move for the first 5 seconds
        tx5.stopMoving(5);
        monsters.add(tx5);
    }

    /**
     * Sets up the random number generator of each ENTITY.
     * @param seed  The seed for the RNG
     */
    public void setSeed(int seed) {

        // STEP 1: Set up the seed for all monsters
        for (Monster m: monsters) {
            m.setSeed(seed);
        }
        // STEP 2: Set up the seed for 'PacActor'
        pacActor.setSeed(seed);
    }

    /**
     * The number of seconds to WAIT before act() method is invoked
     * @param interval  The number of seconds to wait
     */
    public void setSlowDown(int interval) {
        for (Monster m: monsters) {
            m.setSlowDown(interval);
        }
    }

    /**
     * FREEZES all monsters by anchoring them to their current location for 3 seconds.
     */
    public void freezeMonsters() {
        int FREEZE_TIME_INTERVAL = 3;
        for (Monster m: monsters) {
            m.stopMoving(FREEZE_TIME_INTERVAL);
        }
    }

    /**
     * INFURIATES monsters for 3 seconds time by making them move faster.
     */
    public void makeFurious() {
        int FURIOUS_TIME_INTERVAL = 3;
        for (Monster m: monsters) {
            m.makeFurious(FURIOUS_TIME_INTERVAL);
        }
    }

    /**
     * Used to STOP monsters from moving when the game is over.
     */
    public void stopMonsters() {
        for (Monster m: monsters) {
            m.setStopMoving(true);
        }
    }

    /**
     * CHECKS for collisions between pacman and all the monsters
     * @return 'true' if a collision occured, 'false' otherwise
     */
    public boolean hasThereBeenACollision() {

        // STEP 1: Check if any of the monsters made contact with the player
        for (Monster m: monsters) {

            // CASE 2A: Collision detected
            if (m.getLocation().equals(pacActor.getLocation())) {
                return true;
            }
        }
        // CASE 2B: Collision NOT detected
        return false;
    }

    /**
     * CHECKS if 'PacActor' has eaten all pills & gold items on the board (i.e. win test).
     * @return  'true' if all pill & gold items have been eaten, 'false' otherwise
     */
    public boolean hasEatenAllPills() {
        return pacActor.getNbPills() >= itemManager.getMaxPillsAndItems();
    }

    // GETTER & SETTER methods:
    public Location getPacActorLocation() {
        return pacActor.getLocation();
    }
    public void removePacActor() {
        pacActor.removeSelf();
    }
}
