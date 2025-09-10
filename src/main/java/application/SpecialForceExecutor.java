package application;

public interface SpecialForceExecutor
{
    /**
     * Skips the following round of the current player
     */
    public void skipRound();

    /**
     * After a ship was hit, perform a random counter-attack
     */
    public void randomCounterAttack();
}
