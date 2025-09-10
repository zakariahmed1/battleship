package application;


/** The enum is used to take the outcome of an attack in a way
// that Board, GameManager, and players can communicate results
// without relying on strings that are prone to errors. */
public enum AttackResult {
    MISS,
    HIT,
    SUNK,
    INVALID,
    ALREADY_ATTACKED
}
