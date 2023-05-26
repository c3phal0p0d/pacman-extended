package src.game.autoplayer;

import src.game.actor.PacActor;
import src.game.Map;
import src.matachi.mapeditor.editor.checker.levelchecker.LevelChecker;

public class AutoPlayer {

    // Class Attributes:
    private PacActor player;
    private AutoPlayerAlgorithm strategy;

    /**
     * The CONTEXT class for the strategy pattern (i.e. stores & selects the strategy to be used by the
     * `AutoPlayer` agent).
     * @param player    The agent the player is relinquishing control of
     * @param strategy  A concrete class that implements the `AutoPlayerAlgorithm` interface
     */
    public AutoPlayer(PacActor player, AutoPlayerAlgorithm strategy, Map map) {
        this.player = player;
        this.strategy = strategy;
    }

    /**
     * ASSIGNS a NEW strategy to the `AutoPlayer` agent.
     * @param strategy  The algorithm to replace the existing `AutoPlayer` strategy during runtime
     */
    public void setStrategy(AutoPlayerAlgorithm strategy) {
        this.strategy = strategy;
    }

    /**
     * EXECUTES the strategy.
     * @return  A boolean where `true` indicates a move was found OR `false` otherwise
     */
    public boolean runStrategy(Map map, LevelChecker levelChecker) {
        return this.strategy.performAlgorithm(this.player, map, levelChecker);
    }
}
