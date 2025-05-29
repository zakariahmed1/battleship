package application;

import java.util.ArrayList;
import java.util.List;


public abstract class Player {

    protected final String name;
    protected final Board myBoard;
    protected final List<Ship> fleet; //ambiuous...already in board
    protected final int MAX_FLEET_CELLS = 6;


    public Player(String name) {
        this.name = getValidName(name);
        myBoard = new Board(10); //initializes empty board
        fleet = new ArrayList<>(); //empty fleet -> ambigous, but currently the board gives too less infos
    }

    /**
     * @param enemyBoard the Board of the enemy
     * @return the Cell that contains the coordinates of the attack
     */
    public abstract Cell getAttack(Board enemyBoard);

    /**
     * Sets up the initial fleet for this player and adds it to the board
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

        if (name.length() > 15)
            throw new IllegalArgumentException("Name length must not exceed 15 characters");

        if (name.matches(".*\\s+.*"))
            throw new IllegalArgumentException("Name must not contain whitespaces");

        return name;
    }

    /**
     * @return true, if no more ships can be placed according to the maximum allowed size of cells, false otherwise.
     */
    public boolean allShipsPlaced() {
        return getOccupiedCellsSize() == MAX_FLEET_CELLS;
    }

    /**
     * @return the current amount of cells occupied by ships.
     */
    public int getOccupiedCellsSize() {
        return fleet.stream().mapToInt(ship -> ship.getSize()).sum();
    }

    /**
     * Checks whether the given ship can be placed on the board, without exceeding the limit for cells
     * reserved for ships.
     *
     * @param ship the ship to be placed
     * @return true if placing the ship stays within the allowed ship cell limit; false otherwise
     */
    public boolean canPlaceShip(Ship ship) {
        return ship.getSize()+getOccupiedCellsSize() <= MAX_FLEET_CELLS;
    }

    //according to
    public boolean recordDefense(Cell coordinates) {
        //todo maybe board could throw an exception if not valid...
        return myBoard.attackHandling(coordinates.x, coordinates.y); //according to current board logic
    }

}
