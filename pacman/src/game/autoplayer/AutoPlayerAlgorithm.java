package src.game.autoplayer;

import src.game.actor.PacActor;
import src.game.Map;
import src.matachi.mapeditor.editor.checker.levelchecker.LevelChecker;

public interface AutoPlayerAlgorithm {

    /***
     * EXECUTES the given `AutoPlayerAlgorithm` strategy pattern.
     * @param   player          The agent the player is relinquishing control of
     * @param   map             The map to check the validity of
     * @param   levelChecker    Used to check the validity of a map
     * @return  `true` if the algorithm found an action to
     *          perform, `false` otherwise
     */
    public boolean performAlgorithm(PacActor player, Map map, LevelChecker levelChecker);
}
