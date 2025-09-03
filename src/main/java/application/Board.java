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
    public String attackHandling(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            System.out.println("Invalid attack coordinates");
       return "invalid coordinates";
        }

        Cell cell = board[y][x];;  // make a cell with given coordinates
        if (cell.isAttacked()) {  // check if  cell was already attacked
            System.out.println("This cell was already attacked");
            return "cell already attacked";
        }

        cell.attack(); //attack the cell at given coordinate
        if (cell.hasShip()) {// check if there is a ship there
            Ship ship = cell.getShip(); // if there is a ship at that cell make ship ob,j
            ship.registerHit(cell);
            System.out.println("Hit!");
            if (ship.isSunk()) {
                System.out.println("Ship sunk!");
                return "hit and sunk";
            }
            return "hit";
        } else {
            System.out.println("Miss!");
            return "miss";
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
        return 0;
    }

    public int getHeigth() {
        return 0;
    }
}