package src.game.actor;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;
import src.game.Game;
import src.game.Map;
import src.game.actor.items.ItemManager;
import src.game.autoplayer.AutoPlayer;
import src.game.autoplayer.AutoPlayerAlgorithm;
import src.matachi.mapeditor.editor.Constants;

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
    public EntityManager(Game game, ItemManager itemManager, Properties properties, Map map, AutoPlayerAlgorithm strategy) {

        this.properties = properties;
        this.itemManager = itemManager;

        // STEP 1: Find and initialize map monsters
        initializeMapEntities(game, map, strategy);
    }

    /**
     * Searches the map object to find the monsters on the map and adds them to an array of monsters
     * @param map
     */
    private void initializeMapEntities(Game game, Map map, AutoPlayerAlgorithm strategy) {
        int width = map.getWidth();
        int height = map.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // STEP 2: Extract the current location
                Location location = new Location(x, y);
                int a = map.getCell(location);

                // STEP 3: Found an entity
                // CASE A: Found a Troll
                if(a == Constants.TROLL_TILE_CHAR) {
                    createTroll(game, location);
                }
                // CASE B: Found a TX5
                else if(a == Constants.TX5_TILE_CHAR) {
                    createTX5(game, location);
                }
                else if(a == Constants.PAC_TILE_CHAR) {
                    createPacActor(game, location, strategy);
                }
            }
        }
    }

    /**
     * CREATES an instance of 'PacActor'.
     * @param game  The game the new 'PacActor' instance will reside in
     */
    public void createPacActor(Game game, Location location, AutoPlayerAlgorithm strategy) {

        // STEP 1: Create the 'PacActor' instance
        pacActor = new PacActor(game, location, strategy);

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
    private void createTroll(Game game, Location location) {

        // STEP 1: Create the 'Troll'
        Troll troll = new Troll(game.getGameCallback(), game.getNumHorzCells(), game.getNumVertCells());

        // STEP 2: Initialise the Troll's locations
        game.addActor(troll, location, Location.NORTH);
        monsters.add(troll);
    }

    /**
     * CREATES an instance of a 'TX5'.
     * @param game  The game the new 'TX5' instance will reside in
     */
    private void createTX5(Game game, Location location) {

        // STEP 1: Create the 'TX5'
        TX5 tx5 = new TX5(game.getGameCallback(), this, game.getNumHorzCells(), game.getNumVertCells());

        // STEP 2: Initialise the TX5's locations
        game.addActor(tx5, location, Location.NORTH);

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

    public ArrayList<Actor> getEntities() {
        ArrayList<Actor> entities = new ArrayList<>();
        for(Monster monster: monsters) {
            entities.add(monster);
        }
        entities.add(pacActor);
        return entities;
    }
}
