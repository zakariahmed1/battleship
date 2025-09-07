package application;

import java.util.List;

public class HumanPlayer extends Player{
    private IOManager io;

    public HumanPlayer(String name, IOManager io) {
        super(name);
        this.io = io;
    }

    @Override
    public Cell getAttack(Board enemyBoard) throws CommandException
    {
        io.waitForPlayerResponse(name + " press a key to continue!");
        return io.inputAttack(myBoard.VisualizeBoard(), enemyBoard.VisualizeEnemyBoard()); //no validation done, because the handleAttack method in the board class performs already checks
    }


    @Override
    public void chooseFleet() throws CommandException
    {
        io.waitForPlayerResponse(name + " press a key to continue");
        while (!isReady()) {
            Ship ship = io.inputShip(myBoard.VisualizeBoard()); //returns a valid ship or throws CommandException
            if (canPlaceShip(ship)) { //checks if ship size is too large
                List<Cell> coordinates = io.inputShipPlacementCoordinates();
                try {
                    ship.setCoordinates(coordinates);
                    addShip(ship);
                }
                catch (IllegalArgumentException e) {
                    io.print(e.getMessage());
                }
            }
            else {
                io.print("Ship too large! You only have "+MAX_FLEET_CELLS+" cells!");
            }
        }
        io.drawBoard(myBoard.VisualizeBoard());
        io.waitForPlayerResponse(name+ " press a key to let the other player play!");
    }


    @Override
    public String toString()
    {
        return "HumanPlayer{" +
                "name='" + name + '\'' +
                '}';
    }

}
