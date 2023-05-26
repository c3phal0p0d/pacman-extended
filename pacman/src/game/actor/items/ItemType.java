package src.game.actor.items;

/**
 * Type: New file
 * Team Name: Thursday 11:00am Team 1
 * Team Members:
 *      - Jiachen Si (1085839)
 *      - Natasha Chiorsac (1145264)
 *      - Jude Thaddeau Data (1085613)
 */

public enum ItemType {
    Gold,
    Ice,
    Pill;

    /**
     * EXTRACTS the filepath of the item's sprite.
     * @return the item sprite filepath
     */
    public String getImageName() {
        switch (this) {
            case Gold: return "sprites/gold.png";
            case Ice: return "sprites/ice.png";
            case Pill: return "sprites/pill.png";
            default: {
                assert false;
            }
        }
        return null;
    }
}
