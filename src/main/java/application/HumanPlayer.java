package application;

import java.util.ArrayList;
import java.util.List;

public class HumanPlayer extends Player{
    private IOManager io;

    public HumanPlayer(String name, IOManager io) {
        super(name);
        this.io = io;
    }

    @Override
    public Cell getAttack(Board enemyBoard)
    {
        // ask the player for coordinates
        return null;
    }


    @Override
    public void chooseFleet()
    {
        //TODO maybe better board checks adding
        while (!allShipsPlaced()) {

            Ship ship = io.inputShip(myBoard.VisualizeBoard());
            if (canPlaceShip(ship)) {
                List<Cell> coordinates = io.inputShipPlacementCoordinates();
                try {
                    ship.setCoordinates(coordinates);
                    if (myBoard.placeShip(ship)) {
                        fleet.add(ship);
                    }
                }
                catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
            else {
                System.out.println("Ship too large! You only have 6 cells!");
            }
        }
    }


    @Override
    public String toString()
    {
        return "HumanPlayer{" +
                "name='" + name + '\'' +
                '}';
    }

}
