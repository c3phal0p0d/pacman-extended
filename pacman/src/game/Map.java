package src.game;

import ch.aplu.jgamegrid.GGBackground;
import ch.aplu.jgamegrid.Location;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import src.matachi.mapeditor.editor.Constants;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

public class Map {
    String filePath;
    String name;
    private int width;
    private int height;
    char[][] grid;

    public Map(String name, String filePath){
        this.name = name;
        this.filePath = filePath;
        System.out.println(filePath);
        System.out.println(name);
        convertFromXML(filePath);
    }

    public void convertFromXML(String filePath){
        SAXBuilder builder = new SAXBuilder();
        try {
            File file = new File(filePath);
            BufferedReader in;
            FileReader reader = null;

            Document document;
            if (file.canRead() && file.exists()) {
                document = builder.build(file);

                Element rootNode = document.getRootElement();

                List sizeList = rootNode.getChildren("size");
                Element sizeElem = (Element) sizeList.get(0);
                int height = Integer.parseInt(sizeElem.getChildText("height"));
                int width = Integer.parseInt(sizeElem.getChildText("width"));
                this.width = width;
                this.height = height;
                grid = new char[width][height];

                List rows = rootNode.getChildren("row");
                for (int y = 0; y < rows.size(); y++) {
                    Element cellsElem = (Element) rows.get(y);
                    List cells = cellsElem.getChildren("cell");

                    for (int x = 0; x < cells.size(); x++) {
                        Element cell = (Element) cells.get(x);
                        String cellValue = cell.getText();

                        char tileChar = switch (cellValue) {
                            case "PathTile" -> Constants.PATH_TILE_CHAR;
                            case "WallTile" -> Constants.WALL_TILE_CHAR;
                            case "PillTile" -> Constants.PILL_TILE_CHAR;
                            case "GoldTile" -> Constants.GOLD_TILE_CHAR;
                            case "IceTile" -> Constants.ICE_TILE_CHAR;
                            case "PacTile" -> Constants.PAC_TILE_CHAR;
                            case "TrollTile" -> Constants.TROLL_TILE_CHAR;
                            case "TX5Tile" -> Constants.TX5_TILE_CHAR;
                            case "PortalWhiteTile" -> Constants.PORTAL_WHITE_TILE_CHAR;
                            case "PortalYellowTile" -> Constants.PORTAL_YELLOW_TILE_CHAR;
                            case "PortalDarkGoldTile" -> Constants.PORTAL_DARK_GOLD_TILE_CHAR;
                            case "PortalDarkGrayTile" -> Constants.PORTAL_DARK_GRAY_TILE_CHAR;
                            default -> '0';
                        };
                        setTile(x, y, tileChar);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void drawMap(Game game, GGBackground bg)
    {
        // STEP 1: Loop through each location on the board
        bg.clear(Color.gray);
        bg.setPaintColor(Color.white);
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                // STEP 2: Extract the location
                bg.setPaintColor(Color.white);
                Location location = new Location(x, y);
                int c = getCell(location);

                // Tile that isn't a wall, make it a path tile
                if (c != Constants.WALL_TILE_CHAR) {
                    bg.fillCell(location, Color.lightGray);
                }

                // CASE 3A: Place a PILL
                if (c == Constants.PILL_TILE_CHAR) {
                    game.getItemManager().putPill(location, game);


                // CASE 3B: Place a GOLD item
                } else if (c == Constants.GOLD_TILE_CHAR) {
                    game.getItemManager().putGold(location, game);

                // CASE 3C: Place an ICE item
                } else if (c == Constants.ICE_TILE_CHAR) {
                    game.getItemManager().putIce(location, game);
                }
            }
        }
//        for (Location location : game.getItemManager().getPropertyPillLocations()) {
//            game.getItemManager().putPill(location, game);
//        }
//        for (Location location : game.getItemManager().getPropertyGoldLocations()) {
//            game.getItemManager().putGold(location, game);
//        }
    }

    public int getCell(Location location)
    {
        return grid[location.x][location.y];
    }

    public char getTile(int x, int y){
        return grid[x][y];
    }

    public void setTile(int x, int y, char tileChar){
        grid[x][y] = tileChar;
    }

    public String getFilePath(){
        return filePath;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }
}
