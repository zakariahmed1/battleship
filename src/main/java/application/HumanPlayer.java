package application;

import java.util.List;

public class HumanPlayer extends Player{

    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public Cell getAttack()
    {
        return null;
    }

    @Override
    public List<Ship> getFleet()
    {
        return null;
    }

    @Override
    public String toString()
    {
        return "HumanPlayer{" +
                "name='" + name + '\'' +
                '}';
    }

}
