package src.game.autoplayer;

public class AutoPlayer {

    // Class Attributes:
    private AutoPlayerAlgorithm strategy;

    /**
     * The CONTEXT class for the strategy pattern (i.e. stores & selects the strategy to be used by the
     * `AutoPlayer` agent).
     * @param strategy  A concrete class that implements the `AutoPlayerAlgorithm` interface
     */
    public AutoPlayer(AutoPlayerAlgorithm strategy) {
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
    public boolean runStrategy() {
        return this.strategy.performAlgorithm();
    }
}
