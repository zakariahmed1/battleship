package application.entities;

import application.*;
import application.io.CommandException;

import java.util.*;

public class BotPlayer extends Player
{

    private Random random = new Random();
    public BotPlayer() {
        super(generateName());
    }

    public BotPlayer(String name) {
        super(name);
    }

    private Queue<Cell> targetQ = new LinkedList<>(); // cells to try next


    @Override
    public Cell getAttack(Board enemyBoard) throws CommandException {

        // if we already have target cells in queue (hunt mode)
        while(!targetQ.isEmpty()){
            Cell target = targetQ.poll();
            if (!enemyBoard.wasAttacked(target)){
                return target;    // attacks next cell
            }
        }

        // else: random shot
        while(true) {

            int x = random.nextInt(enemyBoard.getWidth());
            int y = random.nextInt(enemyBoard.getHeight());
            Cell target = new Cell(x, y);

            // makes sure the cell is still hidden
            if(!enemyBoard.wasAttacked(target)) {
                return target;
            }
        }
    }

    // enqueues neighbours after a hit
    public void enqueueNeighbors(Cell cell, Board enemyBoard){
        int x = cell.getX();
        int y = cell.getY();

        // up
        if (y > 0) targetQ.add(new Cell(x, y - 1));

        // down
        if (y < enemyBoard.getHeight() - 1)targetQ.add(new Cell(x, y +1));

        // left
        if (x > 0) targetQ.add(new Cell(x - 1, y));

        // right
        if (x < enemyBoard.getWidth() - 1) targetQ.add(new Cell(x + 1, y));
    }



    @Override
    public void chooseFleet() throws CommandException {
        // creates the ships and placements
        while (!isReady()){
            Ship ship = randomShip(); // picks a random ship type

            if (canPlaceShip(ship)) {
                try {
                    List<Cell> coordinates = generateRandomPlacement(ship.getSize());

                    ship.setCoordinates(coordinates);
                    addShip(ship);
                }    catch(IllegalArgumentException e)  {

                    //placement failed, try again
                }
            } else {
                // ship too large
                // skip and retry with another ship
            }
        }
    }



    //the name of the method speaks for itself
    private List<Cell> generateRandomPlacement(int size) {
        List<Cell> cells = new ArrayList<>();

        boolean horizontal = random.nextBoolean();
        int boardWidth = myBoard.getWidth();
        int boardHeight = myBoard.getHeight();

        if (horizontal) {
            int y = random.nextInt(boardHeight);
            int x = random.nextInt(boardWidth - size +1);

            for (int i = 0; i < size; i++) {
                cells.add(new Cell(x + i, y));
            }
        }
        return cells;
    }

    private Ship randomShip() {

        //randomly pick a ship type
        int type = random.nextInt(5);
        switch (type) {
            /*case 0: return new Carrier();
            case 1: return new Battleship();*/
            case 2: return new Cruiser();
            case 3: return new Submarine();
            default: return new Destroyer();
        }
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
