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
        // ask the player for coordinates
        return null;
    }



    @Override
    public void chooseFleet()
    {
        //todo LOOP until all ships placed
        //todo should we count here, or let the board decide?
        //ask the player for the ships
        Ship ship = io.inputShip(myBoard.VisualizeBoard());
        List<Cell> coordinates = io.inputShipPlacementCoordinates();
        try {
            //let the ship and board check if a ship of this type and coordinates can be added to the board.
            ship.setCoordinates(coordinates);
            myBoard.placeShip(ship); //todo check if true?
            //or maybe myBoard.isValidPlacement();
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
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
