package src.game.autoplayer;

public interface AutoPlayerAlgorithm {

    /***
     * EXECUTES the given `AutoPlayerAlgorithm` strategy pattern.
     * @return  `true` if the algorithm found an action to
     *          perform, `false` otherwise
     */
    public boolean performAlgorithm();
}
