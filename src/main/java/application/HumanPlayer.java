package application;

import java.util.List;

public class HumanPlayer extends Player{
    private final IOManager io;

    public HumanPlayer(String name, IOManager io) {
        super(name);
        this.io = io;
    }

    @Override
    public Cell getAttack(Board enemyBoard)
    {
        return null;
    }

    @Override
    public void chooseFleet()
    {
        ;
    }

    @Override
    public String toString()
    {
        return "HumanPlayer{" +
                "name='" + name + '\'' +
                '}';
    }

}
