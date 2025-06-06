package application;

import java.util.Random;

public class BotPlayer extends Player {
    public BotPlayer() {
        super(generateName());
    }

    public BotPlayer(String name) {
        super(name);
    }

    @Override
    public Cell getAttack(Board enemyBoard) {
        // create an attack based on the enemyBoard current situation
        return null;
    }

    @Override
    public void chooseFleet() {
        // creates the ships
    }

    @Override
    public String toString() {
        return "BotPlayer{" +
                "name='" + name + '\'' +
                '}';
    }

    public static String generateName() {
        String[] names = { "Jeff", "Admiral Byte", "Captain Motherboard", "Commander Enum", "Lieutenant Maven" };
        Random random = new Random();
        return names[random.nextInt(names.length)];
    }

}
