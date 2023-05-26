// PacActor.java
// Used for PacMan
package src.game.actor;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;
import src.game.Game;
import src.game.actor.items.Item;
import src.game.actor.items.ItemManager;
import src.game.actor.items.ItemType;
import src.game.actor.portals.PortalManager;
import src.game.autoplayer.AutoPlayer;
import src.game.autoplayer.AutoPlayerAlgorithm;
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
    private PlayerController playerController;
    private String version;
    private EntityManager entityManager;
    private ItemManager itemManager;
    private PortalManager portalManager;
    private GameCallback gameCallback;
    private boolean isAuto = false;

    private AutoPlayer autoPlayer;

    /**
     * INSTANTIATES an instance of 'PacActor'.
     * @param game Contains the information associated with 'PacActor' attributes
     */
    public PacActor(Game game, Location location, AutoPlayerAlgorithm strategy)
    {
        // STEP 1: Assign attributes
        super(true, "sprites/pacpix.gif", nbSprites);  // Rotatable
        this.version = game.getProperties().getProperty("version");
        this.gameCallback = game.getGameCallback();
        this.entityManager = game.getEntityManager();
        this.itemManager = game.getItemManager();
        this.portalManager = game.getPortalManager();
        this.playerController = new PlayerController(this);

        // STEP 2: Set up locations
        game.addActor(this, location);
        this.autoPlayer = new AutoPlayer(this, strategy);
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
     * LOGIC for moving the 'PacActor' automatically.
     */
    private void moveInAutoMode() {
        this.autoPlayer.runStrategy();
    }

    /**
     * Handles the LOGIC whenever 'PacActor' touches a consumable item.
     * @param location  The location of the consumable item
     *
     * @apiNote CHANGES from `protected` to public
     */
    public void eatPill(Location location)
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
                if(version.equals("torusverse")) {
                    entityManager.makeFurious();
                }

            // CASE 3D: Item is an ICE item
            } else if (type.equals(ItemType.Ice)) {
                getBackground().fillCell(location, Color.lightGray);
                gameCallback.pacManEatPillsAndItems(location, "ice");
                itemManager.removeItem(ItemType.Ice, location);
                if(version.equals("toruseverse")) {
                    entityManager.freezeMonsters();
                }
            }
        }
        // STEP 4: Update & display the current score
        String title = "[PacMan in the TorusVerse] Current score: " + score;
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

    public PlayerController getPlayerController(){
        return playerController;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }
    public PortalManager getPortalManager() {
        return portalManager;
    }
}
