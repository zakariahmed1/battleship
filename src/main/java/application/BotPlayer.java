package application;

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
        // create an attack based on the enemyBoard
        return null;
    }

    @Override
    public void chooseFleet()
    {
        //creates the ships
    }

    @Override
    public String toString()
    {
        return "BotPlayer{" +
                "name='" + name + '\'' +
                '}';
    }

}
