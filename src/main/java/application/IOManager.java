package application;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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
        System.out.println(" * total -> 6 cells");
    }
}