package src.game.actor.portals;

public enum PortalType {
    DarkGold,
    White,
    DarkGray,
    Yellow;

    /**
     * EXTRACTS the filepath of the item's sprite.
     * @return the item sprite filepath
     */
    public String getImageName() {
        switch (this) {
            case DarkGold: return "sprites/portalDarkGold.png";
            case White: return "sprites/portalWhite.png";
            case DarkGray: return "sprites/portalDarkGray.png";
            case Yellow: return "sprites/portalYellow.png";
            default: {
                assert false;
            }
        }
        return null;
    }
}
