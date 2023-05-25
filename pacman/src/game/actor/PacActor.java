// PacActor.java
// Used for PacMan
package src.game.actor;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;
import src.game.Game;
import src.game.actor.items.Item;
import src.game.actor.items.ItemManager;
import src.game.actor.items.ItemType;
import src.game.utility.GameCallback;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Type: Modified file
 * Team Name: Thursday 11:00am Team 1
 * Team Members:
 *      - Jiachen Si (1085839)
 *      - Natasha Chiorsac (1145264)
 *      - Jude Thaddeau Data (1085613)
 */

public class PacActor extends Actor implements LocationVisitedList, CanMove
{
    // ATTRIBUTES:
    private static final int nbSprites = 4;
    private int idSprite = 0;
    private int nbPills = 0;
    private int score = 0;
    private ArrayList<Location> visitedList = new ArrayList<Location>();
    private List<String> propertyMoves = new ArrayList<>();
    private int propertyMoveIndex = 0;
    private int seed;
    private Random randomiser = new Random();
    private PlayerController playerController;
    private String version;
    private EntityManager entityManager;
    private ItemManager itemManager;
    private GameCallback gameCallback;
    private int numHorzCells;
    private int numVertCells;
    private boolean isAuto = false;

    /**
     * INSTANTIATES an instance of 'PacActor'.
     * @param game Contains the information associated with 'PacActor' attributes
     */
    public PacActor(Game game, Location location)
    {
        // STEP 1: Assign attributes
        super(true, "sprites/pacpix.gif", nbSprites);  // Rotatable
        this.version = game.getProperties().getProperty("version");
        this.gameCallback = game.getGameCallback();
        this.numHorzCells = game.getNumHorzCells();
        this.numVertCells = game.getNumVertCells();
        this.entityManager = game.getEntityManager();
        this.itemManager = game.getItemManager();
        this.playerController = new PlayerController(this);

        // STEP 2: Set up locations
        game.addActor(this, location);
    }

    /**
     * Responsible for DIRECTING movement of Entities
     */
    public void act()
    {
        show(idSprite);
        idSprite++;
        if (idSprite == nbSprites)
            idSprite = 0;

        if (isAuto) {
            moveInAutoMode();
        }
        gameCallback.pacManLocationChanged(getLocation(), score, nbPills);
    }

    /**
     * CALCULATES the closest pill location the 'PacActor' is closest to.
     * @return  The 'Location' of the closest pill
     */
    private Location closestPillLocation() {
        int currentDistance = 1000;
        Location currentLocation = null;
        List<Location> pillAndItemLocations = itemManager.getPillAndItemLocations();
        for (Location location: pillAndItemLocations) {
            int distanceToPill = location.getDistanceTo(getLocation());
            if (distanceToPill < currentDistance) {
                currentLocation = location;
                currentDistance = distanceToPill;
            }
        }
        return currentLocation;
    }

    /**
     * HELPER function for making an AUTOMATED 'PacActor'
     */
    private void followPropertyMoves() {
        String currentMove = propertyMoves.get(propertyMoveIndex);
        switch(currentMove) {
            case "R":
                turn(90);
                break;
            case "L":
                turn(-90);
                break;
            case "M":
                Location next = getNextMoveLocation();
                if (canMove(next, getBackground(), numHorzCells, numVertCells)) {
                    setLocation(next);
                    eatPill(next);
                }
                break;
        }
        propertyMoveIndex++;
    }

    /**
     * LOGIC for moving the 'PacActor' automatically.
     */
    private void moveInAutoMode() {
        if (propertyMoves.size() > propertyMoveIndex) {
            followPropertyMoves();
            return;
        }
        Location closestPill = closestPillLocation();
        double oldDirection = getDirection();

        Location.CompassDirection compassDir =
                getLocation().get4CompassDirectionTo(closestPill);
        Location next = getLocation().getNeighbourLocation(compassDir);
        setDirection(compassDir);
        if (!isVisited(next, visitedList) && canMove(next, getBackground(), numHorzCells, numVertCells)) {
            setLocation(next);
        } else {
            // normal movement
            int sign = randomiser.nextDouble() < 0.5 ? 1 : -1;
            setDirection(oldDirection);
            turn(sign * 90);  // Try to turn left/right
            next = getNextMoveLocation();
            if (canMove(next, getBackground(), numHorzCells, numVertCells)) {
                setLocation(next);
            } else {
                setDirection(oldDirection);
                next = getNextMoveLocation();
                if (canMove(next, getBackground(), numHorzCells, numVertCells)) // Try to move forward
                {
                    setLocation(next);
                } else {
                    setDirection(oldDirection);
                    turn(-sign * 90);  // Try to turn right/left
                    next = getNextMoveLocation();
                    if (canMove(next, getBackground(), numHorzCells, numVertCells)) {
                        setLocation(next);
                    } else {
                        setDirection(oldDirection);
                        turn(180);  // Turn backward
                        next = getNextMoveLocation();
                        setLocation(next);
                    }
                }
            }
        }
        eatPill(next);
        addVisitedList(next, visitedList);
    }

    /**
     * Handles the LOGIC whenever 'PacActor' touches a consumable item.
     * @param location  The location of the consumable item
     */
    protected void eatPill(Location location)
    {
        // STEP 1: Get the location of the item to be eaten
        Item item = itemManager.getItemByLocation(location);

        // STEP 2: Only eat items that are present on the board
        if (item != null) {

            // CASE 3A: No item on the current location
            if(item.isClaimed()) {
                return;
            }
            ItemType type = item.getType();

            // CASE 3B: Item is a PILL item
            if (type.equals(ItemType.Pill))
            {
                nbPills++;
                score++;
                gameCallback.pacManEatPillsAndItems(location, "pills");
                itemManager.removeItem(ItemType.Pill, location);

            // CASE 3C: Item is a GOLD item
            } else if (type.equals(ItemType.Gold)) {
                nbPills++;
                score+= 5;
                getBackground().fillCell(location, Color.lightGray);
                gameCallback.pacManEatPillsAndItems(location, "gold");
                itemManager.removeItem(ItemType.Gold, location);
                if(version.equals("multiverse")) {
                    entityManager.makeFurious();
                }

            // CASE 3D: Item is an ICE item
            } else if (type.equals(ItemType.Ice)) {
                getBackground().fillCell(location, Color.lightGray);
                gameCallback.pacManEatPillsAndItems(location, "ice");
                itemManager.removeItem(ItemType.Ice, location);
                if(version.equals("multiverse")) {
                    entityManager.freezeMonsters();
                }
            }
        }
        // STEP 4: Update & display the current score
        String title = "[PacMan in the Multiverse] Current score: " + score;
        gameGrid.setTitle(title);
    }

    // GETTER & SETTER methods
    public int getNbPills() {
        return nbPills;
    }
    public int getScore() { return score; }
    public void setAuto(boolean auto) {
        isAuto = auto;
    }
    public boolean getAuto(){
        return isAuto;
    }
    public void setSeed(int seed) {
        this.seed = seed;
        randomiser.setSeed(seed);
    }
    public void setPropertyMoves(String propertyMoveString) {
        if (propertyMoveString != null) {
            this.propertyMoves = Arrays.asList(propertyMoveString.split(","));
        }
    }
    public PlayerController getPlayerController(){
        return playerController;
    }
}
