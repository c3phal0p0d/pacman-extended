// PacMan.java
// Simple PacMan implementation
package src.game;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GGBackground;
import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;
import src.game.actor.EntityManager;
import src.game.actor.items.ItemManager;
import src.game.actor.portals.PortalManager;
import src.game.autoplayer.AutoPlayerAlgorithm;
import src.game.autoplayer.GreedyItemAlgorithm;
import src.game.utility.GameCallback;
import src.matachi.Map;

import java.awt.*;
import java.util.Properties;

/**
 * Type: Modified file
 * Team Name: Thursday 11:00am Team 1
 * Team Members:
 *      - Jiachen Si (1085839)
 *      - Natasha Chiorsac (1145264)
 *      - Jude Thaddeau Data (1085613)
 */

public class Game extends GameGrid
{
    // ATTRIBUTES:
    private final static int nbHorzCells = 20;
    private final static int nbVertCells = 11;
    private Map map;
    private EntityManager entityManager;
    private ItemManager itemManager;
    private PortalManager portalManager;
    private GameCallback gameCallback;
    private Properties properties;
    private int seed = 30006;
    private boolean isPlayerAlive = true;

    /**
     * INSTANTIATES an instance of the 'Game' class.
     * @param gameCallback  Used to ensure terminal output is correct
     * @param properties    The CUSTOM settings of the game
     */
    public Game(GameCallback gameCallback, Properties properties, Map map)
    {
        // STEP 1: Setup game
        super(nbHorzCells, nbVertCells, 20, false);
        this.gameCallback = gameCallback;
        this.properties = properties;
        this.map = map;
        setSimulationPeriod(100);
        setTitle("[PacMan in the TorusVerse]");

        // STEP 2: Setup Components
        itemManager = new ItemManager();
        itemManager.setupPillAndItemsLocations(this);

        // STEP 3: Setup for auto test
        //itemManager.loadPillAndItemsLocations(properties);
        itemManager.setMaxPillsAndItems(this);

        // STEP 4: Draw grid
        GGBackground bg = getBg();
        map.drawMap(this, bg);

        // STEP 5: Initialise portals
        portalManager = new PortalManager(this, map);

        // STEP 6: Initialise entities
        createEntityManager(seed, this.map, new GreedyItemAlgorithm());
        // Must be called after creating monster manager so that entities are rendered on top of portals
        portalManager.setEntities(this);


        // STEP 7: Setup Random seeds
        seed = Integer.parseInt(properties.getProperty("seed"));
        setKeyRepeatPeriod(150);

        // STEP 8: Run the game
        doRun();
        show();

        // STEP 9: Loop to look for collision in the application thread
        boolean hasPacmanBeenHit;
        boolean hasPacmanEatAllPills;
        boolean hasPortals = portalManager.getIsTherePortals();
        do {
            hasPacmanBeenHit = entityManager.hasThereBeenACollision();
            hasPacmanEatAllPills = entityManager.hasEatenAllPills();
            if(hasPortals) {
                portalManager.hasThereBeenATeleport(this);
            }
            delay(10);
        } while(!hasPacmanBeenHit && !hasPacmanEatAllPills);

        // STEP 10: Game is finished, terminate the entities
        delay(120);
        Location loc = entityManager.getPacActorLocation();
        entityManager.stopMonsters();
        entityManager.removePacActor();

        // STEP 11: Display the outcome of the game
        String title = "";
        if (hasPacmanBeenHit) {
            bg.setPaintColor(Color.red);
            title = "GAME OVER";
            addActor(new Actor("sprites/explosion3.gif"), loc);
            killPlayer();
        } else if (hasPacmanEatAllPills) {
            bg.setPaintColor(Color.yellow);
            title = "YOU WIN";
        }
        setTitle(title);
        gameCallback.endOfGame(title);
        doPause();
        delay(1000);
        closeGame();
    }

    /**
     * INSTANTIATES an instance of the Entity Manager.
     * @param seed  RNG seed for 'PacActor'
     */
    private void createEntityManager(int seed, Map map, AutoPlayerAlgorithm strategy) {
        entityManager = new EntityManager(this, itemManager, properties, map, strategy);
        entityManager.setSlowDown(3);
    }

    // GETTER methods:
    public Map getMap() {
        return map;
    }
    public Properties getProperties() {
        return properties;
    }
    public int getNumHorzCells(){ return this.nbHorzCells; }
    public int getNumVertCells(){ return this.nbVertCells; }
    public GameCallback getGameCallback() { return gameCallback; }
    public ItemManager getItemManager(){
        return itemManager;
    }
    public EntityManager getEntityManager() {
        return entityManager;
    }
    public PortalManager getPortalManager() { return this.portalManager; }

    public void closeGame() {
        this.getFrame().dispose();
    }

    private void killPlayer() {
        this.isPlayerAlive = false;
    }

    public boolean getIsPlayerAlive() {
        return this.isPlayerAlive;
    }
}
