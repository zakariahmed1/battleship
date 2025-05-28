package application;

import java.util.Optional;


public abstract class Player {

    protected final String name;
    protected final Board myBoard;

    public Player(String name) {
        this.name = getValidName(name);
        myBoard = new Board(10); //initializes empty board
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


    public String getName() {
        return name;
    }


    public boolean hasLost() {
        return myBoard.isAllshipsunk();
    }

    public Board getBoard() {return myBoard;};

    public Optional<Ship> defend(Cell cell) {
        //return board.receiveShot(cell);
        return null;
    }



    @Override
    public String toString()
    {
        return "Player{" +
                "name='" + name + '\'' +
                '}';
    }

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

}
