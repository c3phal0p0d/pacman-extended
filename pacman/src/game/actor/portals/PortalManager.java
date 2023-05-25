package src.game.actor.portals;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;
import src.game.Game;
import src.game.Map;
import src.game.actor.Monster;
import src.game.actor.PacActor;
import src.matachi.mapeditor.editor.Constants;

import javax.sound.sampled.Port;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class PortalManager {

    private ArrayList<Portal> portals = new ArrayList<>();
    private ArrayList<Portal> justTeleportedPortals = new ArrayList<>();
    private ArrayList<Actor> entities;

    private boolean isTherePortals = true;

    public PortalManager(Game game, Map map) {
        // Initialize Portals
        initializePortals(game, map);
    }

    private void initializePortals(Game game, Map map) {
        int width = map.getWidth();
        int height = map.getHeight();
        ArrayList<Integer> portalChars = new ArrayList<>();
        class SinglePortal {

            int type;
            Portal portal;
            public SinglePortal(int type, Location location) {
                this.type = type;
                portal = makePortal(type, location);
            }
        }

        ArrayList<SinglePortal> unpairedPortals = new ArrayList<>();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                // STEP 2: Extract the current location
                Location location = new Location(x, y);
                int a = map.getCell(location);

                if(a == Constants.PORTAL_YELLOW_TILE_CHAR || a == Constants.PORTAL_DARK_GOLD_TILE_CHAR ||
                        a == Constants.PORTAL_DARK_GRAY_TILE_CHAR || a == Constants.PORTAL_WHITE_TILE_CHAR ) {
                        if(!portalChars.contains(a)) { // First of a pair
                            portalChars.add(a);
                            SinglePortal single = new SinglePortal(a, location);
                            unpairedPortals.add(single);
                        }
                        else { // Connect to paired portal
                            for (SinglePortal unpaired: unpairedPortals) {
                                if(a == unpaired.type) { // Matching single portal found
                                    // Make new portal and pair portals together
                                    Portal p1 = unpaired.portal;
                                    Portal p2 = makePortal(a, location);
                                    p1.setPartner(p2);
                                    p2.setPartner(p1);
                                    portals.add(p1);
                                    portals.add(p2);
                                    game.addActor(p1, p1.getLocation());
                                    game.addActor(p2, p2.getLocation());
                                }
                            }
                        }
                }
            }
        }

        if(portals.size() == 0) { // No portals
            isTherePortals = false;
        }
    }

    // Must be called after the creation of the monster manager so that entities are rendered on top of the portals
    public void setEntities(Game game) {
        this.entities = game.getEntityManager().getEntities();
    }

    // Checks if any entity has come into contact with a portal and teleports them
    public void hasThereBeenATeleport(Game game) {
        for(Portal portal: portals) {
            boolean portalIsActive = true;

            for(Actor actor: entities) {
                // Check for collision

                // Collision detected and the player hasn't just teleported using this portal
                boolean justTeleported = justTeleportedPortals.contains(portal);
                if(portal.getLocation().equals(actor.getLocation()) && !justTeleported) {
                    System.out.println("TELEPORT THAT SHIT");

                    // Teleport partner
                    Location newLocation = portal.getTeleportLocation(portal);
                    actor.setLocation(newLocation);

                    // Turn off partner portal
                    justTeleportedPortals.add(portal.getPartner());

                    // Game callback
                    if(actor instanceof PacActor) {
                        game.getGameCallback().pacManLocationChanged(newLocation, ((PacActor) actor).getScore(),
                                ((PacActor) actor).getNbPills());
                    } else {
                        game.getGameCallback().monsterLocationChanged(((Monster) actor));
                    }
                }
                // Only reactivate portal when there are no entities on it
                else if(portal.getLocation().equals(actor.getLocation())) {
                    portalIsActive = false;
                }
            }
            if(portalIsActive) {
                justTeleportedPortals.remove(portal);
            }
        }
    }

    private Portal makePortal(int type, Location location) {
        Portal newPortal = null;

        switch(type) {
            case Constants.PORTAL_YELLOW_TILE_CHAR:
                newPortal = new YellowPortal(location);
                break;
            case Constants.PORTAL_DARK_GOLD_TILE_CHAR:
                newPortal = new DarkGoldPortal(location);
                break;
            case Constants.PORTAL_WHITE_TILE_CHAR:
                newPortal = new WhitePortal(location);
                break;
            case Constants.PORTAL_DARK_GRAY_TILE_CHAR:
                newPortal = new DarkGrayPortal(location);
                break;
        }
        return newPortal;
    }

    // Getter method
    public boolean getIsTherePortals() { return isTherePortals; }
}
