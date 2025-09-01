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
        /*List<String> playersName = new ArrayList<>(io.inputPlayers());
        player1 = new HumanPlayer(playersName.get(0), io);
        if (playersName.size() == 1) {// if only one name was in the input initialize one player as bot
            player2 = new BotPlayer();
        } else {
            player2 = new HumanPlayer(playersName.get(1), io);
        }
        setupBoards();*/

        player1 = getNewPlayer(false, true);
        player2 = getNewPlayer(true, false);
        setupBoards();

    }

    private Player getNewPlayer(boolean botAllowed, boolean firstPlayer) {
        Player player = null;

        do {
            try
            {
                String playerName = io.inputPlayers(firstPlayer);
                if (playerName.isEmpty()) {
                    if (botAllowed)
                        player = new BotPlayer("Bot");
                    else
                        io.print("First player cannot be a bot");
                }
                else {
                    player = new HumanPlayer(playerName, io);
                }
            }
            catch (IllegalArgumentException e) {
                io.print(e.getMessage());
            }
        } while (player == null);

        return player;
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
        //player1.chooseFleet();
        chooseFleet(player1);
        io.changePlayer(player2);
        //player2.chooseFleet();
        chooseFleet(player2);
        io.changePlayer(player1);
    }

    private void chooseFleet(Player player) {
        while (!player.isReady()) {
            try {
                player.chooseFleet();
            }
            catch (CommandException e) {
                handleCommand(e.getCommand());
            }
        }
    }

    private void handleCommand(Command command) {
        switch (command) {
            case EXIT -> {
                io.print("Exiting game...");
                System.exit(0);
            }
            case RESTART -> {
                io.print("Restarting game");
                restartGame();
            }
        }
    }

    private void restartGame() {
        player1 = null;
        player2 = null;
        initializeGame();
        startGame();
    }

    // return opposite player to the one in the parameters
    private Player getOpponent(Player currentPlayer) {
        return currentPlayer.equals(player1) ? player2 : player1;
    }

    // handles a single turn, get shot coordinated and handles attack, finally it
    // checks for a win condition
    private Player playTurn(Player currentPlayer) {
        Player enemyPlayer = getOpponent(currentPlayer);
//        Cell shot = currentPlayer.getAttack(enemyPlayer.getBoard()); // this method should have no parameter, it asks the player to input
                                                   // his shot coordinates and validates them

        boolean validAttack = false;
        do {
            try
            {
                Cell shot = currentPlayer.getAttack(enemyPlayer.getBoard());
                handleAttack(currentPlayer, enemyPlayer, shot);
                validAttack = true;
            }
            catch (IllegalArgumentException e) {
                io.print(e.getMessage());
            }
            catch (CommandException e) {
                handleCommand(e.getCommand());
            }
        }while (!validAttack);
        //handleAttack(currentPlayer, enemyPlayer, shot);
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

        String attackResult = defender.recordDefense(coordinates);

        switch (attackResult) {
            case "invalid coordinates" -> { throw new IllegalArgumentException("Coordinates " +coordinates+ " are invalid! Try again!");} // throw exception
            case "cell already attacked" -> { throw new IllegalArgumentException("Coordinates "+coordinates+" already revealed! Try again!");} //throw exception
           // case "hit and sunk" -> {io.print("Hit and sunk!");} //already done by board
           // case "hit" -> {io.print("Hit!");} //already done by board
           // case "miss" -> {io.print("miss!");} //already done by board
        }
    }
}
