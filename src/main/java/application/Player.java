package application;

public abstract class Player {

    protected final String name;
    protected final Board myBoard;

    public Player(String name) {
        this.name = getValidName(name);
        myBoard = new Board(); //initializes empty board
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
        return false;
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

        if (name.length() > 10)
            throw new IllegalArgumentException("Name length must not exceed 10 characters");

        return name;
    }

}
