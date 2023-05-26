package src.game.autoplayer;

import src.game.actor.PacActor;

public interface AutoPlayerAlgorithm {

    /***
     * EXECUTES the given `AutoPlayerAlgorithm` strategy pattern.
     * @param   player The agent the player is relinquishing control of
     * @return  `true` if the algorithm found an action to
     *          perform, `false` otherwise
     */
    public boolean performAlgorithm(PacActor player);
}
