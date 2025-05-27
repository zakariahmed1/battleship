package application;

import java.util.ArrayList;
import java.util.List;

//Controls the lifecycle of a game session
public class GameManager {
    private Player player1;
    private Player player2;
    private final IOManager io;

    public GameManager() {
        io = new IOManager();
        io.introduceGame();
    }

    // initializes the game by setting up Players
    public void initializeGame() {
        List<String> playersName = new ArrayList<>(io.inputPlayers());
        player1 = new HumanPlayer(playersName.get(0), io);
        if (playersName.size() == 1) {// if only one name was in the input initialize one player as bot
            player2 = new BotPlayer();
        } else {
            player2 = new HumanPlayer(playersName.get(1), io);
        }
        setupBoards();
    }

    // manages the game loop
    public void startGame() {
        Player winner = null;
        Player currentPlayer = player1;
        while (winner == null) {
            winner = playTurn(currentPlayer);
            currentPlayer = getOpponent(currentPlayer);
            io.changePlayer(currentPlayer);
        }
        io.announceWinner(winner);
    }

    // sets up the fleet for the 2 players
    private void setupBoards() {
        io.setupFleetMessage();
        player1.chooseFleet();// selection should already be validated
        io.changePlayer(player2);
        player2.chooseFleet();
        io.changePlayer(player1);
    }

    // return opposite player to the one in the parameters
    private Player getOpponent(Player currentPlayer) {
        return currentPlayer.equals(player1) ? player2 : player1;
    }

    // handles a single turn, get shot coordinated and handles attack, finally it
    // checks for a win condition
    private Player playTurn(Player currentPlayer) {
        Cell shot = currentPlayer.getAttack(null); // this method should have no parameter, it asks the player to input
                                                   // his shot coordinates and validates them
        Player enemyPlayer = getOpponent(currentPlayer);
        handleAttack(currentPlayer, enemyPlayer, shot);
        if (enemyPlayer.hasLost()) {
            return currentPlayer;
        }
        return null;
    }

    // handles the attack logic
    private void handleAttack(Player attacker, Player defender, Cell coordinates) {
        // Cell.Type shotResoult = defender.recordDefense(coordinates); updates the
        // defender board and stores the returned shot resoult
        // attacker.recordAttack(coordinates); updates the player board with the cells
        // he has already attacked
        // io.displayShot(shotResoult); displays the resoult of the shot
    }
}
