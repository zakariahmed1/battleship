package application;
import java.util.ArrayList;
import java.util.Iterator;

public class Board {
    private final int SIZE = 10;
    private final Cell[][] board;
    Ship ship;
    ArrayList<Cell> coordinatesShip = new ArrayList<>();
    ArrayList<Ship> ships = new ArrayList<>(); // To keep track of all ships on the board

    public Board(int size) {
        board = new Cell[size][size];
        for (int i = 0; i < size; i++) {  // Fixed: changed <= to <
            for (int j = 0; j < size; j++) {  // Fixed: changed <= to <
                board[i][j] = new Cell(i, j);  // Initialize with coordinates
            }
        }
    }

    public boolean placeShip(Ship ship) {
        if (!isValidPlacement(ship)) {
            System.out.println("Invalid ship placement");
            return false;
        }

        if (ship.getSize() > 3 || ship.getSize() <= 0) {
            System.out.println("error size of the ship is too large or too small ");
            return false;
        } else {
            coordinatesShip.addAll(ship.getCoordinates());

            for (int i = 0; i < coordinatesShip.size(); i++) {  // Fixed: changed <= to <
                Cell coordinatecell = coordinatesShip.get(i);
                board[coordinatecell.x][coordinatecell.y].placeShip(ship);
            }
            ships.add(ship);  // Add the ship to the list of ships on the board
        }
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
                    board[cell.x][cell.y].ship = null;
                }
                iterator.remove();
            }
        }
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

    // Process an attack at given position
    public boolean attackHandling(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            System.out.println("Invalid attack coordinates");
            return false;
        }

        Cell cell = board[x][y];
        if (cell.isAttacked()) {
            System.out.println("This cell was already attacked");
            return false;
        }

        cell.attack();
        if (cell.hasShip()) {
            Ship ship = cell.getShip();
            ship.registerHit(cell);
            System.out.println("Hit!");
            if (ship.isSunk()) {
                System.out.println("Ship sunk!");
            }
            return true;
        } else {
            System.out.println("Miss!");
            return false;
        }
    }

    // Check if all ships on the board are sunk
    public boolean isAllshipsunk() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }

    // Verify if a ship can be placed
    public boolean isValidPlacement(Ship ship) {
        for (Cell cell : ship.getCoordinates()) {
            int x = cell.x;
            int y = cell.y;

            // Check if out of bounds
            if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
                return false;
            }

            // Check if cell already has a ship
            if (board[x][y].hasShip()) {
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
}