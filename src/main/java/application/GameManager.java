package application;

import java.util.Optional;

/**
 * The GameManager class manages the flow of a turn-based console game.
 * It is repsonsible for:
 *  - Handling player turns and switching between players,
 *  - Executing actions such as attacks.
 *  - Reacting to commands
 *  - Checking whether the game has ended
 */
public class GameManager implements SpecialForceExecutor {

    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private final IOManager io;

    private static SpecialForceExecutor specialForceExecutor;

    private boolean gameOver;

    public GameManager() {
        io = new IOManager();
        io.introduceGame();
        specialForceExecutor = this;
    }

    public void initializeGame() {
        player1 = getNewPlayer(false, true); //first player cannot be a bot
        player2 = getNewPlayer(true, false); //second player can be a bot
        currentPlayer = player1;
        gameOver = false;
        setupBoards();
    }

    // Game loop
    public void startGame() {
        while (!gameOver) {
            if (currentPlayer.hasToSkip()) {
                currentPlayer.skipRounds(false);
                io.print(currentPlayer.getName()+" you are skipping this round!");
            }
            else
            {
                playTurn();
            }
            if (!gameOver) {
                //don't let the other player see the currents players board - clear the view first
                io.waitForPlayerResponse("Press enter to continue");
                changePlayer();
            }
        }
        io.announceWinner(currentPlayer);
    }

    /*
     * Asks the player for his name and generates a new HumanPlayer instance.
     * If the player enters an empty name, it will generate a BotPlayer instance.
     * The first player must be a human player.
     */
    private Player getNewPlayer(boolean botAllowed, boolean firstPlayer) {
        Player player = null;

        do {
            try
            {
                String playerName = io.askPlayerName(firstPlayer);
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

    // sets up the fleet for the 2 players
    private void setupBoards() {
        io.setupFleetMessage();
        chooseFleet(player1);
        chooseFleet(player2);
    }
    

    // let the player choose and place the fleet
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

    //Handle commands a player could enter
    private void handleCommand(Command command) {
        switch (command) {
            case EXIT -> {
                io.print("Exiting game...");
                io.close();
                System.exit(0);
            }
            case RESTART -> {
                io.print("Restarting game");
                restartGame();
            }
            case SHOWBOARDS -> {
                io.print("Your current board: ");
                io.drawBoard(currentPlayer.getBoard().VisualizeBoard());
                io.print("Your enemy's board: ");
                io.drawBoard(getOpponent(currentPlayer).getBoard().VisualizeEnemyBoard());
            }
            case HELP -> {
                io.printHelp();
            }
        }
    }

    // resets the game state and restarts the game
    private void restartGame() {
        player1 = null;
        player2 = null;
        currentPlayer = null;
        gameOver = false;
        initializeGame();
        startGame();
    }

    // return opposite player to the one in the parameters
    private Player getOpponent(Player currentPlayer) {
        return currentPlayer.equals(player1) ? player2 : player1;
    }

    // change the current player to the other player
    private void changePlayer() {
        currentPlayer = getOpponent(currentPlayer);
    }

    /*
     * Asks the player for an attacking cell, performs the attack on the defending player.
     * If the defending player has lost, the game is over.
     */
    private void playTurn() {

        boolean validAttack = false;
        Player enemyPlayer = getOpponent(currentPlayer);

        do {
            try
            {
                Cell shot = currentPlayer.getAttack(enemyPlayer.getBoard());
                handleAttack(enemyPlayer, shot);
                validAttack = true;
            }
            catch (IllegalArgumentException e) {
                io.print(e.getMessage());
            }
            catch (CommandException e) {
                handleCommand(e.getCommand());
            }
        } while (!validAttack);

        checkGameOver(enemyPlayer);
    }

    // stop the game if game over
    private void checkGameOver(Player defender) {
        if (defender.hasLost())
            gameOver = true;
    }

    // handles the attack
    private void handleAttack(Player defender, Cell coordinates) {
        String attackResult = defender.defend(coordinates);

        //nothing to do, error outputs already done in board

        /*switch (attackResult) {
            case "invalid coordinates" -> { throw new IllegalArgumentException("Coordinates " +coordinates+ " are invalid! Try again!");} // throw exception
            case "cell already attacked" -> { throw new IllegalArgumentException("Coordinates "+coordinates+" already revealed! Try again!");} //throw exception
           // case "hit and sunk" -> {io.print("Hit and sunk!");} //already done by board
           // case "hit" -> {io.print("Hit!");} //already done by board
           // case "miss" -> {io.print("miss!");} //already done by board
        }*/
    }

    @Override
    public void skipRound() {
        io.print("You hit a ship with a special force! You will skip the next round!");
        currentPlayer.skipRounds(true);
    }

    @Override
    public void randomCounterAttack() {
        //print output that we are going to attack
        //change player? for redunand calls?
        //handleAttack
        //change player back?
    }

    /**
     * @return the current instance of this object as a SpecialForceExectutor
     */
    public static Optional<SpecialForceExecutor> getSpecialForceExecutorInstance() {
        return Optional.of(specialForceExecutor);
    }

}
