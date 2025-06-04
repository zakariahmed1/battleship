package application;

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
        return io.inputAttack(enemyBoard.VisualizeEnemyBoard()); //no validation done, because the handleAttack method in the board class performs already checks
    }


    @Override
    public void chooseFleet()
    {
        //TODO maybe better board checks adding
        while (!allShipsPlaced()) {

            Ship ship = io.inputShip(myBoard.VisualizeBoard()); //does return a valid ship
            if (canPlaceShip(ship)) {
                List<Cell> coordinates = io.inputShipPlacementCoordinates();
                try {
                    ship.setCoordinates(coordinates);
                    if (myBoard.placeShip(ship)) {
                        fleet.add(ship); //redundant until board adapts
                    }
                }
                catch (IllegalArgumentException e) {
                    io.print(e.getMessage());
                }
            }
            else {
                io.print("Ship too large! You only have 6 cells!");
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
