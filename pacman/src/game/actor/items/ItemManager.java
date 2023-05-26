package src.game.actor.items;

import ch.aplu.jgamegrid.Location;
import src.game.Game;
import src.game.Map;
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

public class ItemManager {

    // ATTRIBUTES:
    private ArrayList<Location> pillAndItemLocations = new ArrayList<Location>();
    private ArrayList<Item> iceCubes = new ArrayList<Item>();
    private ArrayList<Item> goldPieces = new ArrayList<Item>();
    private ArrayList<Item> pills = new ArrayList<Item>();
    private ArrayList<Location> propertyPillLocations = new ArrayList<>();
    private ArrayList<Location> propertyGoldLocations = new ArrayList<>();
    private int maxPillsAndItems;

    /**
     * INSTANTIATES an instance of 'ItemManager'.
     */
    public ItemManager() { }

    /**
     * CALCULATES the number of pill & gold items on the boards.
     * @param game  Contains the details about the board
     * @return      The number of consumable items for 'PacActor'
     */
    public int countPillsAndItems(Game game) {

        // STEP 1: Loop through each of the cells on the board
        int pillsAndItemsCount = 0;
        for (int y = 0; y < game.getNumVertCells(); y++)
        {
            for (int x = 0; x < game.getNbHorzCells(); x++)
            {
                // STEP 2: Read the current cell
                Location location = new Location(x, y);
                int a = game.getMap().getCell(location);

                // CASE 3A: Found a pill
                if (a == Constants.PILL_TILE_CHAR) { // Pill
                    pillsAndItemsCount++;

                // CASE 3B: Found gold
                } else if (a == Constants.GOLD_TILE_CHAR) { // Gold
                    pillsAndItemsCount++;
                }
            }
        }

        // STEP 5: Return the number of consumable items
        return pillsAndItemsCount;
    }

    /**
     * PLACES an instance of a 'Pill' item on the board.
     * @param location  The coordinates of where to place the pill
     * @param game      The instance of the game to add the pill onto
     */
    public void putPill(Location location, Game game){
        Item pill = new Item(location, ItemType.Pill);
        pills.add(pill);
        game.addActor(pill, location);
    }

    /**
     * PLACES an instance of a 'Gold' item on the board.
     * @param location  The coordinates of where to place the gold item
     * @param game      The instance of the game to add the gold item onto
     */
    public void putGold(Location location, Game game){
        Item gold = new Item(location, ItemType.Gold);
        goldPieces.add(gold);
        game.addActor(gold, location);
    }

    /**
     * PLACES an instance of an 'Ice' item on the board.
     * @param location  The coordinates of where to place the ice item
     * @param game      The instance of the game to add the ice item onto
     */
    public void putIce(Location location, Game game){
        Item ice = new Item(location,ItemType.Ice);
        iceCubes.add(ice);
        game.addActor(ice, location);
    }

    /**
     * REMOVES an item from the board after it has been consumed by an instance of 'PacActor'
     * @param type      The 'ItemType' enumeration of the item to be removed
     * @param location  The location of the item to be removed
     */
    public void removeItem(ItemType type, Location location){

        // CASE A: Removing a GOLD item
        if (type.equals(ItemType.Gold)){
            for (Item gold : this.goldPieces){
                if (location.getX() == gold.getLocation().getX() && location.getY() == gold.getLocation().getY()) {
                    gold.hide();
                    gold.claim();
                    return;
                }
            }
        // CASE B: Removing an ICE item
        } else if(type.equals(ItemType.Ice)){
            for (Item ice : this.iceCubes){
                if (location.getX() == ice.getLocation().getX() && location.getY() == ice.getLocation().getY()) {
                    ice.hide();
                    ice.claim();
                    return;
                }
            }
        // CASE C: Removing a PILL
        } else if(type.equals(ItemType.Pill)){
            for (Item pill : this.pills){
                if (location.getX() == pill.getLocation().getX() && location.getY() == pill.getLocation().getY()) {
                    pill.hide();
                    pill.claim();
                    return;
                }
            }
        }
    }

    /**
     * ADDS the pill, gold & ice locations.
     * @param game  Used to get 'grid' of the game
     */
    public void setupPillAndItemsLocations(Game game) {

        // STEP : Loop through every location
        Map map = game.getMap();

        for (int y = 0; y < game.getNumVertCells(); y++)
        {
            for (int x = 0; x < game.getNumHorzCells(); x++)
            {
                // STEP 2: Extract the current location
                Location location = new Location(x, y);
                int a = map.getCell(location);

                // STEP 3: Found an item
                if (a == Constants.PILL_TILE_CHAR || a == Constants.GOLD_TILE_CHAR || a == Constants.ICE_TILE_CHAR) {
                    pillAndItemLocations.add(location);
                }
            }
        }
    }

    // GETTER & SETTER methods:
    public ArrayList<Location> getPillAndItemLocations() { return pillAndItemLocations; }
    public int getMaxPillsAndItems() { return maxPillsAndItems; }
    public ArrayList<Item> getGoldPieces() { return this.goldPieces; }
    public void setMaxPillsAndItems(Game game){
        maxPillsAndItems =  countPillsAndItems(game);
    }
    public ArrayList<Location> getPropertyPillLocations() { return propertyPillLocations; }
    public ArrayList<Location> getPropertyGoldLocations() { return propertyGoldLocations; }

    public Item getItemByLocation(Location location) {

        // STEP 1: Check through all items lists to find a match
        ArrayList<Item> items = new ArrayList<Item>();
        items.addAll(iceCubes);
        items.addAll(goldPieces);
        items.addAll(pills);

        // STEP 2: Linear search the item
        for (Item item: items) {

            // CASE 3A: The item was found
            if (item.getLocation().equals(location)) {
                return item;
            }
        }
        // CASE 3B: No item found
        return null;
    }
}