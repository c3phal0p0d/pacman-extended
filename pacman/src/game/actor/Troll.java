package src.game.actor;

import src.game.utility.GameCallback;

/**
 * Type: New file
 * Team Name: Thursday 11:00am Team 1
 * Team Members:
 *      - Jiachen Si (1085839)
 *      - Natasha Chiorsac (1145264)
 *      - Jude Thaddeau Data (1085613)
 */

public class Troll extends RandomWalkMonster{

    /**
     * INSTANTIATES a new instance of 'Troll'.
     * @param gameCallback Used to display behaviour of the game
     * @param numHorzCells The number of HORIZONTAL cells on the board
     * @param numVertCells The number of VERTICAL cells on the board
     */
    public Troll(GameCallback gameCallback, int numHorzCells, int numVertCells) {
        super(gameCallback, MonsterType.Troll, numHorzCells, numVertCells);
    }
}