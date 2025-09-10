package application;
import application.entities.Ship;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Board {

    private final int SIZE = 10;
    private final Cell[][] board;
    Random random = new Random();

    int max=9;
    int min=1;
    int randomX=min + (max - min) * random.nextInt();
    int randomy=min + (max - min) *random.nextInt();
    Ship ship;

    ArrayList<Cell> coordinatesShip = new ArrayList<>();
    ArrayList<Ship> ships = new ArrayList<>(); // To keep track of all ships on the board

    public Board(int size) {
        board = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new Cell(j, i);}
        }
    }

    public boolean placeShip(Ship ship) {
        ArrayList<Cell> coordinatesShip = new ArrayList<>();
        if (!isValidPlacement(ship)) {
            System.out.println("Invalid ship placement");
            return false;
        }

        if (ship.getSize() > 3 || ship.getSize() <= 0) {
            System.out.println("error size of the ship is too large or too small ");
            return false;
        } else {
            coordinatesShip.addAll(ship.getCoordinates()); // put the ship coordinates in a l,ist

            for (int i = 0; i < coordinatesShip.size(); i++) { // l,oop through all the coordinates
                Cell coordinatecell = coordinatesShip.get(i); // make an ob,ject of coorrdinateship and assign it each time the iterator
                board[coordinatecell.y][coordinatecell.x].placeShip(ship); // place ship at the given coordinates
            }
            ships.add(ship);  // Add the ship to the list of ships on the board
        }
        return true;
    }
    public boolean placeRandomships(Ship ship){


        return true;
    }

    // Remove all eliminated ships from the board
    public void removeEliminatedShips() {
        Iterator<Ship> iterator = ships.iterator();
        while (iterator.hasNext()) {
            Ship ship = iterator.next();
            if (ship.isSunk()) {
                // Remove the ship from all cells
                for (Cell cell : ship.getCoordinates()) {
                    board[cell.y][cell.x].ship = null;
                }
                iterator.remove();
            }
        }
    }
public void Timer(){

}
    // Visualize the board (return a matrix representation)
    public String[][] VisualizeBoard() {
        String[][] visualization = new String[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j].hasShip()) {
                    if (board[i][j].isAttacked()) {
                        visualization[i][j] = "X"; // Hit ship
                    } else {
                        visualization[i][j] = "S"; // Ship
                    }
                } else {
                    if (board[i][j].isAttacked()) {
                        visualization[i][j] = "O"; // Miss
                    } else {
                        visualization[i][j] = "."; // Empty water
                    }
                }
            }
        }
        return visualization;
    }
    public String [][] VisualizeEnemyBoard(){
        String[][] visualization = new String[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j].hasShip()) {
                    if (board[i][j].isAttacked()) {
                        visualization[i][j] = "X"; // Hit ship
                    } else {
                        visualization[i][j] = "."; // Ship
                    }
                } else {
                    if (board[i][j].isAttacked()) {
                        visualization[i][j] = "O"; // Miss
                    } else {
                        visualization[i][j] = "."; // Empty water
                    }
                }
            }
        }
        return visualization;

    }

    // Process an attack at given position
    public AttackResult recordDefense(Cell cell) {
        int x = cell.getX();
        int y = cell.getY();

        // Check bounds
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            System.out.println("Invalid attack coordinates");
            return AttackResult.INVALID;
        }

        Cell target = board[y][x];

        // Already attacked?
        if (target.isAttacked()) {
            System.out.println("This cell was already attacked");
            return AttackResult.ALREADY_ATTACKED;
        }

        // Mark attacked
        target.attack();

        if (target.hasShip()) {
            Ship ship = target.getShip();
            ship.registerHit(cell);

            System.out.println("Hit!");
            if (ship.isSunk()) {
                System.out.println("Ship sunk!");
                return AttackResult.SUNK;
            }
            return AttackResult.HIT;
        } else {
            System.out.println("Miss!");
            return AttackResult.MISS;
        }
    }


    // Check if all ships on the board are sunk
    public boolean isAllshipsunk() {
        for (Ship ship : ships) { //iterate through the ships
            if (!ship.isSunk()) { // if ship is not sunk return false
                return false;
            }
        }
        return true;
    }

    // Verify if a ship can be placed
    public boolean isValidRandomPlacement(Ship ship){
        for ( Cell cell: ship.getCoordinates()){
            int x = cell.x+randomX;
            int y = cell.y+randomy;
            if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
                return false;
            }

            // Check if cell already has a ship
            if (board[y][x].hasShip()) {
                return false;
            }

        } return true;
    }
    public boolean isValidPlacement(Ship ship) {
        for (Cell cell : ship.getCoordinates()) {
            int x = cell.x;
            int y = cell.y;

            // Check if out of bounds
            if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
                return false;
            }

            // Check if cell already has a ship
            if (board[y][x].hasShip()) {
                return false;
            }

            // Check adjacent cells (optional, for no touching ships rule)
            for (int i = Math.max(0, x-1); i <= Math.min(SIZE-1, x+1); i++) {
                for (int j = Math.max(0, y-1); j <= Math.min(SIZE-1, y+1); j++) {
                    if (board[i][j].hasShip()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // Getter for the board (alternative to VisualizeBoard)
    public Cell[][] getBoard() {
        return board;
    }

    public int getWidth() {
        return board[0].length;  //num of columns
    }

    public int getHeight() {
        return board.length;    // num of rows
    }

    public boolean wasAttacked(Cell cell) {
        // checks for valid coordinates
        if (cell.getX() < 0 || cell.getX() >= getWidth()
                || cell.getY() < 0 || cell.getY() >= getHeight()) {
            throw new IllegalArgumentException("Cell is out of board bounds: " + cell);
        }
        return board[cell.getY()][cell.getX()].isAttacked();
    }
}