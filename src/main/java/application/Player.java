package application;


import java.util.ArrayList;
import java.util.List;

public abstract class Player {

    protected final String name;
    protected final Board myBoard;
    protected final int MAX_FLEET_CELLS = 6;
    private int occupiedCells;
    private boolean skipRound;

    protected List<Ship> ships = new ArrayList<>();


    public Player(String name) {
        this.name = getValidName(name);
        myBoard = new Board(10); //initializes empty board
        occupiedCells = 0;
    }

    /**
     * @param enemyBoard the Board of the enemy
     * @return the Cell that contains the coordinates of the attack
     */
    public abstract Cell getAttack(Board enemyBoard);

    /**
     * Sets up the initial fleet for this player and adds it to the board
     * @return true, if all ships placed
     */
    public abstract void chooseFleet();


    /**
     * @return the name of a player
     */
    public String getName() {
        return name;
    }

    /**
     * @return true if this player has no more living ships, otherwise false.
     */
    public boolean hasLost() {
        return myBoard.isAllshipsunk();
    }

    /**
     * @return the string representation of a player
     */
    @Override
    public String toString()
    {
        return "Player{" +
                "name='" + name + '\'' +
                '}';
    }

    //checks if the player name meets certain requirements
    private String getValidName(String name)
    {
        if (name == null)
            throw new IllegalArgumentException("Name must bet set");

        name = name.trim();
        if (name.isEmpty())
            throw new IllegalArgumentException("Name must not be empty");

        if (name.length() > 20)
            throw new IllegalArgumentException("Name length must not exceed 15 characters");

        if (name.matches(".*\\s+.*"))
            throw new IllegalArgumentException("Name must not contain whitespaces");

        return name;
    }

    /**
     * @return true, if all ships placed, false otherwise.
     */
    public boolean isReady() {
        return occupiedCells == MAX_FLEET_CELLS;
    }

    public List<Ship> getShips(){
        return ships;
    }

    /*
     * adds the ship to the board
     */
    protected void addShip(Ship ship) {
        if (myBoard.placeShip(ship)) {
            occupiedCells+=ship.getSize();
        }
    }


    /**
     * Checks whether the given ship can be placed on the board, without exceeding the limit for cells
     * reserved for ships.
     *
     * @param ship the ship to be placed
     * @return true if placing the ship stays within the allowed ship cell limit; false otherwise
     */
    public boolean canPlaceShip(Ship ship) {
        return (ship.getSize()+occupiedCells) <= MAX_FLEET_CELLS;
    }


    /**
     * Defends an attack on the given coordinates
     * @param cell the cell (coordinates) of an attack
     * @return the result of the attack
     */
    public String defend(Cell cell) {
        //todo maybe board could throw an exception if not valid...
        return myBoard.attackHandling(cell.x, cell.y); //according to current board logic
    }

    /**
     * Enables or disables skipping rounds for this player
     * @param skip true to skip the following rounds, false to resume back to normal
     */
    public void skipRounds(boolean skip) {
        skipRound = skip;
    }

    /**
     * @return true, if this player has to skip its round
     */
    public boolean hasToSkip() {
        return skipRound;
    }

    /**
     * @return the players board instance
     */
    public Board getBoard() {
        return myBoard;
    }

}
