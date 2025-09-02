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
        waitForPlayerResponse("Player "+current.getName()+", press a key to continue!");
        System.out.println(current.getName() + " it's your turn now!");
    }

    public void waitForPlayerResponse(String message) {
        System.out.println(message);
        scanner.nextLine();
        clearTerminal();
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
    public String inputPlayers(boolean isFirstPlayer) { //todo commandexcpeion
        /*System.out.println(
                "Let's start by setting up players names. If you wish to play against a bot type in only your name, if you are two players make sure the two names are separated by a comma.");
        String in = scanner.nextLine();
        return Arrays.stream(in.split(","))
                .map(n -> n.trim())
                .filter(n -> !n.isEmpty())
                .toList();

         */
        if (isFirstPlayer) {
            System.out.println("Enter first player name: ");
        }
        else {
            System.out.println("Enter second player name or press enter for a bot type");
        }
        return scanner.nextLine();
    }

    // displays a message explaining rules to setup the fleet
    public void setupFleetMessage() {
        System.out.println("Perfect, now it's time to set up your fleet.");
        System.out.println("Each player has a 9x9 grid to place their fleet and track enemy fire.");
        System.out.println("Build your fleet using up to 6 total cells. See the example below:");
        System.out.println("  * Small Ship (1 cell)");
        System.out.println("  * Medium Ship (2 cells)");
        System.out.println("  * Large Ship (3 cells)");
        System.out.println("  * total -> 6 cells");
    }

    /**
     * Lets the player choose a ship from all the possible supported ships.
     *
     * @param board the 2D string representation of the board.
     * @return the chosen ship instance.
     */
    public Ship inputShip(String[][] board) throws CommandException{
        System.out.println();
        System.out.println("Your current board: ");
        drawBoard(board);
        System.out.println();
        System.out.println(ShipBuilder.getShipsDescription());
        System.out.println("Enter your choice (for example 1): ");

        Ship ship = null;

        do {
            try {
                ship = InputParser.parseShip(scanner.nextLine());
            }
            catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (ship == null);

        return ship;
    }


    /**
     * Draws the board on the terminal
     *
     * @param board the 2D string representation of a board.
     */
    public void drawBoard(String[][] board) {

        System.out.printf("%-6s", "");
        IntStream.range(0,board[0].length).forEach(num -> System.out.printf("%-3d",num));
        System.out.println();
        System.out.printf("%-6s", "");
        IntStream.range(0,board[0].length).forEach(num -> System.out.printf("%-3s","__"));

        System.out.println();
        int counter = 0;
        for (String[] row : board) {
            System.out.printf("%-3d%-3s",counter, "|");
            Arrays.stream(row).forEach(sym -> System.out.printf("%-3s", sym));
            System.out.println();
            counter++;
        }
    }

    /**
     * Prompts a human player to enter ship coordinates until a valid input is provided.
     * Delegates parsing and validation to InputParser.parseCoordinatesRange()
     *
     * Accepted formats:
     * Single cell: x,y
     * Range of cells: x1,y1-x2,y2
     *
     * @return a list of cells correspnding to the coordinates (or range).
     * @throws CommandException if the player entered a supported command
     */
    public List<Cell> inputShipPlacementCoordinates() throws CommandException {

        System.out.println("Enter coordinates in the format x,y or x,y-x,y: ");
        List<Cell> cells = null;
        do {
            try
            {
                cells = InputParser.parseCoordinatesRange(scanner.nextLine());
            }
            catch (IllegalArgumentException e) {
                System.out.println(e.getMessage()+ " Try again!");;
            }
        } while (cells == null);

        return cells;
    }

    /**
     * Prompts a human player to input coordinates for an attack.
     * Delegates parsing and validation to InputParser.parseCoordinates()
     *
     * @return the Cell to attack
     * @throws CommandException if the player entered a supported command
     */
    public Cell inputAttack(String[][] enemyBoard) throws CommandException{
        System.out.println("Current enemies board: ");
        drawBoard(enemyBoard);
        Cell attack = null;
        do {
            try {
                System.out.println("Enter the coordinates you want to attack: ");
                attack = InputParser.parseCoordinates(scanner.nextLine());
            }
            catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

        } while (attack == null);
        return attack;
    }

    /**
     * Prints the given error message on the terminal
     * @param error the error message
     */
    public void print(String error) {
        System.out.println(error);
    }

}