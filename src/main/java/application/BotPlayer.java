package application;

import java.util.List;

public class BotPlayer extends Player
{
    public BotPlayer() {
        super("Bot");
    }

    public BotPlayer(String name) {
        super(name);
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
        return "BotPlayer{" +
                "name='" + name + '\'' +
                '}';
    }

}
