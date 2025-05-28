package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

//Manages input/outputs for the application
public class IOManager {
    Scanner scanner;

    public IOManager() {
        scanner = new Scanner(System.in);
    }

    // prints welcome message and short rule description
    public void introduceGame() {
        System.out.println("Welcome to CLI Battleship!\n");
        System.out.println("Prepare for a head-to-head naval battle between two players.");
        System.out.println(
                "Each of you will command a fleet and take turns launching attacks to sink the enemy ships.\n");
        System.out.println("Fleet Rules:");
        System.out.println("- Ships can be placed horizontally or vertically.");
        System.out.println("- Ships cannot overlap or go out of bounds.\n");
        System.out.println("How to Play:");
        System.out.println("- Players take turns entering coordinates to fire at the enemy grid.");
        System.out.println("- You’ll be told if it’s a hit or a miss—and when a ship is sunk.");
        System.out.println("- The first to sink all enemy ships wins the battle!");
        System.out.println();
    }

    // clears the terminal and siplays a message with the name of the player
    // supposed to play next
    public void changePlayer(Player current) {
        clearTerminal();
        System.out.println(current.getName() + " it's your turn now!");
    }

    // clears the terminal
    private void clearTerminal() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Displays a winning message with the winners name
    public void announceWinner(Player winner) {
        System.out.println("Conratulations, " + winner.getName() + " has won the game.");
    }

    // asks the user to input player names and returns a list of names
    public List<String> inputPlayers() {
        System.out.println(
                "Let's start by setting up players names. If you wish to play against a bot type in only your name, if you are two players make sure the two names are separated by a comma.");
        String in = scanner.nextLine();
        return Arrays.stream(in.split(","))
                .map(n -> n.trim())
                .filter(n -> !n.isEmpty())
                .toList();
    }

    // displays a message explaining rules to setup the fleet
    public void setupFleetMessage() {
        System.out.println("Perfect, now it's time to set up your fleet.");
        System.out.println("Each player has a 6x7 grid to place their fleet and track enemy fire.");
        System.out.println("Build your fleet using up to 6 total cells. See the example below:");
        System.out.println("  * Small Ship (1 cell)");
        System.out.println("  * Medium Ship (2 cells)");
        System.out.println("  * Large Ship (3 cells)");
        System.out.println("  * total -> 6 cells");
    }

    /**
     * Lets the player choose a ship from all the possible supported ships.
     * @param board the 2D string representation of the board.
     * @return the chosen ship instance.
     */
    public Ship inputShip(String[][] board) {
        System.out.println();
        System.out.println("Your current board: ");
        drawBoard(board);
        System.out.println();
        System.out.println(ShipBuilder.getShipsDescription());
        System.out.println("Enter your choice (for example 1): ");

        Ship ship = null;

        do {
            try {
                int shipId = Integer.parseInt(scanner.nextLine());
                ship =  ShipBuilder.create(shipId);
            }
            catch (NumberFormatException e) {
                System.out.println("Pleaser enter an integer!");
            }
            catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (ship == null);

        return ship;
    }


    /**
     * Draws the board on the terminal
     * @param board the 2D string representation of a board.
     */
    public void drawBoard(String[][] board) {

        System.out.printf("%-6s", "");
        IntStream.range(1,board[0].length+1).forEach(num -> System.out.printf("%-3d",num));
        System.out.println();
        System.out.printf("%-6s", "");
        IntStream.range(1,board[0].length+1).forEach(num -> System.out.printf("%-3s","__"));

        System.out.println();
        int counter = 1;
        for (String[] row : board) {
            System.out.printf("%-3d%-3s",counter, "|");
            Arrays.stream(row).forEach(sym -> System.out.printf("%-3s", sym));
            System.out.println();
            counter++;
        }
    }

    /**
     * Asks a human player for the coordinates of a ship, as long until he enters valid formatted coordinates, where the
     * size of the ship does not exceen the maximum possible ship size, in the following format:
     *
     * For a single cell: x,y
     * For a range of cells: x1,y1-x2,y2
     *
     * @return a list of cells corrspnding to the coordinates (or range).
     */
    public List<Cell> inputShipPlacementCoordinates() {
        System.out.println("Enter coordinates in the format x,y or x,y-x,y: ");
        List<Cell> cells = null;
        do {
            String[] fromTo = scanner.nextLine().split("-");
            if (fromTo.length <= 2) {
                int x1, y1, x2, y2;
                try {
                    x1 = parseXCoordinate(fromTo[0]);
                    y1 = parseYCoordinate(fromTo[0]);
                    if (fromTo.length == 2) {
                        x2 = parseXCoordinate(fromTo[1]);
                        y2 = parseYCoordinate(fromTo[2]);
                    }
                    else {
                        x2 = x1;
                        y2 = y1;
                    }
                    cells = getCellsFromRange(x1,y1,x2,y2);
                }
                catch (NumberFormatException e) {
                    System.out.println("Please enter valid coordinates in the format x1,y1 or x1,y1-x2,y2 for a range!");
                }
                catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        } while (cells == null);
        return cells;
    }

    /*
     * parses the x coordinate out of the following format: x,y
     */
    private int parseXCoordinate(String coord) throws NumberFormatException {
        return Integer.parseInt(coord.split(",")[0]);
    }

    /*
     * parses the y coordinate out of the following format: x,y
     */
    private int parseYCoordinate(String coord) {
        return Integer.parseInt(coord.split(",")[1]);
    }

    /**
     * Creates a List of cells out of the given coordinate range
     * @param x1 and y1 are the starting point, x2 and y2 the end of the range
     * @return a List of Cells created from the range.
     */
    public List<Cell> getCellsFromRange(int x1, int y1, int x2, int y2) {
        int deltaX = x2==x1 ? 0 : ((x2 < x1) ? -1 : 1) ;
        int deltaY = y2==y1 ? 0 : ((y2 < y1) ? -1 : 1);

        if (Integer.max(Math.abs(x2-x1),Math.abs(y2-y1)) > ShipBuilder.getMaxSupportedShipSize())
            throw new IllegalArgumentException("Range too high!");

        List<Cell> range = new ArrayList<>();

        while (x1 != x2 || y1 != y2) {
            range.add(new Cell(x1,y1));
            x1 += deltaX;
            y1 += deltaY;
        }
        range.add(new Cell(x1,y1));
        return range;
    }


}