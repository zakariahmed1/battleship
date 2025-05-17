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
    public Cell getAttack()
    {
        return null;
    }

    @Override
    public List<Ship> getFleet()
    {
        return null;
    }

}
