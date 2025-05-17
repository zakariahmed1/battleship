package application;

import java.util.List;

public abstract class Player {

    protected final String name;
    protected final Board myBoard;

    public Player(String name) {
        this.name = getValidName(name);
        myBoard = new Board(); //initializes empty board
    }

    public String getName() {
        return name;
    }

    public boolean hasLost() {
        return false;
    }


    public abstract Cell getAttack();

    public abstract List<Ship> getFleet();

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
