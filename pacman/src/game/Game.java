// PacMan.java
// Simple PacMan implementation
package src.game;

import ch.aplu.jgamegrid.*;
import src.matachi.mapeditor.editor.Constants;
import src.utility.GameCallback;

import java.awt.*;
import java.util.ArrayList;
import java.util.Properties;

public class Game extends GameGrid
{
  private static int nbHorzCells = 20;
  private static int nbVertCells = 11;
  protected Map map;

  protected PacActor pacActor = new PacActor(this);
  private Monster troll = new Monster(this, MonsterType.Troll);;
  private Monster tx5 = new Monster(this, MonsterType.TX5);;

  private ArrayList<Location> pillAndItemLocations = new ArrayList<Location>();
  private ArrayList<Actor> iceCubes = new ArrayList<Actor>();
  private ArrayList<Actor> goldPieces = new ArrayList<Actor>();
  private GameCallback gameCallback;
  private Properties properties;
  private int seed = 30006;
  private ArrayList<Location> propertyPillLocations = new ArrayList<>();
  private ArrayList<Location> propertyGoldLocations = new ArrayList<>();
  private boolean isPlayerAlive = true;

  public Game(GameCallback gameCallback, Properties properties, Map map)
  {
    //Setup game
    super(map.getWidth(), map.getHeight(), 20, false);
    this.gameCallback = gameCallback;
    this.properties = properties;
    this.map = map;
    nbHorzCells = map.getWidth();
    nbVertCells = map.getHeight();
    setSimulationPeriod(100);
    setTitle("[PacMan in the Multiverse]");

    //Setup for auto test
    pacActor.setPropertyMoves(properties.getProperty("PacMan.move"));
    pacActor.setAuto(Boolean.parseBoolean(properties.getProperty("PacMan.isAuto")));
    loadPillAndItemsLocations();

    GGBackground bg = getBg();
    drawGrid(bg);

    //Setup Random seeds
    seed = Integer.parseInt(properties.getProperty("seed"));
    pacActor.setSeed(seed);

    addKeyRepeatListener(pacActor);
    setKeyRepeatPeriod(150);
    pacActor.setSlowDown(3);

    troll.setSeed(seed);
    troll.setSlowDown(3);
    tx5.setSeed(seed);
    tx5.setSlowDown(3);
    tx5.stopMoving(5);

    // Go through grid finding all PacMan's starting points & add them to array
//    ArrayList<Integer> monster_tiles = new ArrayList<Integer>();
//    for (int y=0; y<map.getHeight(); y++) {
//      for (int x = 0; x < map.getWidth(); x++) {
//        if (map.getTile(x, y)==Constants.TROLL_TILE_CHAR){
//          monster_tiles.add(Constants.TROLL_TILE_CHAR);
//        }
//        else if(map.getTile(x, y)==Constants.TX5_TILE_CHAR) {
//          monster_tiles.add(Constants.TX5_TILE_CHAR);
//        }
//      }
//    }
//
//    ArrayList<Monster> monsters = new ArrayList<Monster>();
//    // Map has monsters, initialize them
//    if(monster_tiles.size() > 0) {
//      for(int tile: monster_tiles) {
//        if(tile == Constants.TROLL_TILE_CHAR)
//        {
//          Monster troll = new Monster(this, MonsterType.Troll);
//
//        }
//        else if(tile == Constants.TX5_TILE_CHAR) {
//          Monster tx5 = new Monster(this, MonsterType.TX5);
//
//          monsters.add(tx5);
//        }
//      }
//    }

    setupActorLocations();

    //Run the game
    doRun();
    show();
    // Loop to look for collision in the application thread
    // This makes it improbable that we miss a hit
    boolean hasPacmanBeenHit = false; // Always false when there are no monsters
    boolean hasPacmanEatAllPills;
    setupPillAndItemsLocations();
    int maxPillsAndItems = countPillsAndItems();
    
    do {

      hasPacmanBeenHit = troll.getLocation().equals(pacActor.getLocation())
              || tx5.getLocation().equals(pacActor.getLocation());
      hasPacmanEatAllPills = pacActor.getNbPills() >= maxPillsAndItems;
      delay(10);
    } while(!hasPacmanBeenHit && !hasPacmanEatAllPills);
    delay(120);

    Location loc = pacActor.getLocation();
    troll.setStopMoving(true);
    tx5.setStopMoving(true);
    pacActor.removeSelf();

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
    closeGame(); // Game is finished, dispose frame
  }

  public GameCallback getGameCallback() {
    return gameCallback;
  }

  private void setupActorLocations() {
    for (int y=0; y<map.getHeight(); y++) {
      for (int x = 0; x < map.getWidth(); x++) {
        if (map.getTile(x, y) == Constants.TROLL_TILE_CHAR) {
          addActor(troll, new Location(x, y), Location.NORTH);
        } else if (map.getTile(x, y) == Constants.TX5_TILE_CHAR) {
          addActor(tx5, new Location(x, y), Location.NORTH);
        } else if (map.getTile(x, y) == Constants.PAC_TILE_CHAR) {
          addActor(pacActor, new Location(x, y));
        }
      }
    }
  }

  private int countPillsAndItems() {
    int pillsAndItemsCount = 0;
    for (int y = 0; y < nbVertCells; y++)
    {
      for (int x = 0; x < nbHorzCells; x++)
      {
        Location location = new Location(x, y);
        int a = map.getTile(location.x, location.y);
        if (a == Constants.PILL_TILE_CHAR && propertyPillLocations.size() == 0) { // Pill
          pillsAndItemsCount++;
        } else if (a == Constants.GOLD_TILE_CHAR && propertyGoldLocations.size() == 0) { // Gold
          pillsAndItemsCount++;
        }
      }
    }
    if (propertyPillLocations.size() != 0) {
      pillsAndItemsCount += propertyPillLocations.size();
    }

    if (propertyGoldLocations.size() != 0) {
      pillsAndItemsCount += propertyGoldLocations.size();
    }

    return pillsAndItemsCount;
  }

  public ArrayList<Location> getPillAndItemLocations() {
    return pillAndItemLocations;
  }

  private void loadPillAndItemsLocations() {
    for (int y=0; y<map.getHeight(); y++){
      for (int x=0; x<map.getWidth(); x++){
        if (map.getTile(x, y)== Constants.PILL_TILE_CHAR){
          propertyPillLocations.add(new Location(x, y));
        } else if (map.getTile(x, y)== Constants.GOLD_TILE_CHAR){
          propertyGoldLocations.add(new Location(x, y));
        }
      }
    }
  }

  private void setupPillAndItemsLocations() {
    for (int y = 0; y < nbVertCells; y++)
    {
      for (int x = 0; x < nbHorzCells; x++)
      {
        Location location = new Location(x, y);
        int a = map.getTile(location.x, location.y);
        if (a == Constants.PILL_TILE_CHAR && propertyPillLocations.size() == 0) {
          pillAndItemLocations.add(location);
        }
        if (a == Constants.GOLD_TILE_CHAR &&  propertyGoldLocations.size() == 0) {
          pillAndItemLocations.add(location);
        }
        if (a == Constants.ICE_TILE_CHAR) {
          pillAndItemLocations.add(location);
        }
      }
    }

    if (propertyPillLocations.size() > 0) {
      for (Location location : propertyPillLocations) {
        pillAndItemLocations.add(location);
      }
    }
    if (propertyGoldLocations.size() > 0) {
      for (Location location : propertyGoldLocations) {
        pillAndItemLocations.add(location);
      }
    }
  }

  private void drawGrid(GGBackground bg)
  {
    bg.clear(Color.gray);
    bg.setPaintColor(Color.white);
    for (int y = 0; y < nbVertCells; y++)
    {
      for (int x = 0; x < nbHorzCells; x++)
      {
        bg.setPaintColor(Color.white);
        Location location = new Location(x, y);
        int a = map.getTile(location.x, location.y);
        if (a != Constants.WALL_TILE_CHAR)
          bg.fillCell(location, Color.lightGray);
        if (a == Constants.PILL_TILE_CHAR && propertyPillLocations.size() == 0) { // Pill
          putPill(bg, location);
        } else if (a == Constants.GOLD_TILE_CHAR && propertyGoldLocations.size() == 0) { // Gold
          putGold(bg, location);
        } else if (a == Constants.ICE_TILE_CHAR) {
          putIce(bg, location);
        }
      }
    }

    for (Location location : propertyPillLocations) {
      putPill(bg, location);
    }

    for (Location location : propertyGoldLocations) {
      putGold(bg, location);
    }
  }

  private void putPill(GGBackground bg, Location location){
    bg.fillCircle(toPoint(location), 5);
  }

  private void putGold(GGBackground bg, Location location){
    bg.setPaintColor(Color.yellow);
    bg.fillCircle(toPoint(location), 5);
    Actor gold = new Actor("sprites/gold.png");
    this.goldPieces.add(gold);
    addActor(gold, location);
  }

  private void putIce(GGBackground bg, Location location){
    bg.setPaintColor(Color.blue);
    bg.fillCircle(toPoint(location), 5);
    Actor ice = new Actor("sprites/ice.png");
    this.iceCubes.add(ice);
    addActor(ice, location);
  }

  public void removeItem(String type,Location location){
    if(type.equals("gold")){
      for (Actor item : this.goldPieces){
        if (location.getX() == item.getLocation().getX() && location.getY() == item.getLocation().getY()) {
          item.hide();
        }
      }
    }else if(type.equals("ice")){
      for (Actor item : this.iceCubes){
        if (location.getX() == item.getLocation().getX() && location.getY() == item.getLocation().getY()) {
          item.hide();
        }
      }
    }
  }

  public int getNumHorzCells(){
    return this.nbHorzCells;
  }
  public int getNumVertCells(){
    return this.nbVertCells;
  }

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
