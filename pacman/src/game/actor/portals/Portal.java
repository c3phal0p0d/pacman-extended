package src.game.actor.portals;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;
import src.game.actor.MonsterType;

public abstract class Portal extends Actor {
    private Location location;
    private Portal partner;

    public Portal(Location location, PortalType type){
        super(type.getImageName());
        this.location = location;
        this.partner = null;
    }

    public void setPartner(Portal partner) {
        this.partner = partner;
    }

    public Portal getPartner() {
        return partner;
    }

    public Location getLocation() {
        return location;
    }

    public Location getTeleportLocation(Portal portal) {
        return portal.getPartner().getLocation();
    }
}
