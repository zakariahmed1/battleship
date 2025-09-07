package application;

import java.util.*;

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
    private Player currentCounterAttackPlayer;
    private final IOManager io;
    private static GameManager gm; //singleton instance
    private Queue<SpecialForce> specialCounterForceQueue; //queue for counter-attacks
    private boolean gameInitialized;


    private boolean gameOver;

    private GameManager() {
        io = new IOManager();
        io.introduceGame();
    }

    public void initializeGame() {
        specialCounterForceQueue = new LinkedList<SpecialForce>();
        player1 = getNewPlayer(false, true); //first player cannot be a bot
        player2 = getNewPlayer(true, false); //second player can be a bot
        currentPlayer = player1;
        gameOver = false;
        setupBoards();
        gameInitialized = true;
    }

    /*
     * Game Loop
     */
    public void startGame() {

        if (!gameInitialized)
        {
            io.print("Please initialize the game first!");
            return;
        }

        while (!gameOver) {
            playTurn();
            if (!gameOver) {
                //don't let the other player see the currents players board - clear the view first
                io.waitForPlayerResponse("Press enter to continue");
                changePlayer();
            }
        }
        io.announceWinner(getWinningPlayer());
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

    /*
     * sets up the fleet for the 2 players
     */
    private void setupBoards() {
        io.setupFleetMessage();
        chooseFleet(player1);
        chooseFleet(player2);
    }
    

    /*
     * let the player choose and place the fleet
     */
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

    /*
     * Handle commands a player could enter
     */
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

    /*
     * resets the game state and restarts it
     */
    private void restartGame() {
        player1 = null;
        player2 = null;
        currentPlayer = null;
        currentCounterAttackPlayer = null;
        specialCounterForceQueue = null;
        gameOver = false;
        gameInitialized = false;
        initializeGame();
        startGame();
    }

    /*
     * return opposite player to the one in the parameters
     */
    private Player getOpponent(Player currentPlayer) {
        return currentPlayer.equals(player1) ? player2 : player1;
    }

    /*
     * change the current player to the other player
     */
    private void changePlayer() {
        currentPlayer = getOpponent(currentPlayer);
    }

    /*
     * Get the attacking cell from a player, handle the attack and perform the special-force counter attacks if present
     */
    private void playTurn() {
        if (currentPlayer.hasToSkip()) {
            currentPlayer.skipRounds(false);
            io.print(currentPlayer.getName()+" you are skipping this round!");
        }
        else
        {
            handlePlayerTurn();
            checkGameOver();
            //if game is not over, perform counter-attacks if any
            if (!gameOver)
            {
                currentCounterAttackPlayer = getOpponent(currentPlayer);
                dequeueSpecialCounterForce();
            }
        }
    }

    /*
     * Asks the player for an attacking cell, performs the attack on the defending player.
     * Lets the player retry if the cell was invalid
     */
    private void handlePlayerTurn() {

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
    }

    /*
     * Checks the result of the attack.
     * If it was an invalid cell, or the cell was already revealed, throw an exception
     */
    private void handleAttack(Player defender, Cell coordinates) {
        String attackResult = defender.defend(coordinates);

        switch (attackResult) {
            case "invalid coordinates" -> { throw new IllegalArgumentException("");} // msg already printed in board
            case "cell already attacked" -> { throw new IllegalArgumentException("");} // msg already printed in board
        }
    }

    /*
     * set attribute gameOver to true if any player has lost
     */
    private void checkGameOver() {
        if (player1.hasLost() || player2.hasLost())
            gameOver = true;
    }

    /*
     * returns the winning player
     */
    private Player getWinningPlayer()
    {
        return player1.hasLost() ? player2 : player1;
    }

    /*
     * execute all the enqueued special-force counter-attacks
     */
    private void dequeueSpecialCounterForce() {
        while (!specialCounterForceQueue.isEmpty() && !gameOver) {
            specialCounterForceQueue.poll().performAction();
            checkGameOver();
        }
    }

    /**
     * @return the current instance of this object as a SpecialForceExectutor
     */

    public static GameManager getInstance() {
        if (null == gm) {
            gm = new GameManager();
        }
        return gm;
    }

    /**
     * enqueue a special force counter-attack that should be executed after the current round.
     * All the enqueued counter-attacks are being invoked after the current round.
     */
    public void enqueueSpecialCounterForce(SpecialForce specialForces)
    {
        if (specialForces != null)
        {
            specialCounterForceQueue.add(specialForces);
        }
    }


    @Override
    public void skipRound() {
        io.print(getOpponent(currentCounterAttackPlayer).getName()+ ", you hit a ship with a special force! You will skip the next round!");
        getOpponent(currentCounterAttackPlayer).skipRounds(true);
        currentCounterAttackPlayer = getOpponent(currentCounterAttackPlayer); //not needed, no more counterattacks
    }

    @Override
    public void randomCounterAttack()
    {
        Player attacker = currentCounterAttackPlayer;
        Player defender = getOpponent(attacker);

        io.print(defender.getName() + ", you hit a ship that is going to make a random counter attack, be prepared!");
        Cell shot = new Cell(0, 0); //todo get random attack cell from bot
        io.print(attacker.getName() + " is attacking cell at x: " + shot.getX() + " y: " + shot.getY());

        try {
            handleAttack(defender, shot);
        }
        catch (IllegalArgumentException e)
        {
            // dont react if already revealed cell was hit
        }

        //show actual current players board, never the counter-attacking players board
        io.print(currentPlayer.getName() + " you current board: ");
        io.drawBoard(currentPlayer.getBoard().VisualizeBoard());

        //change the player for possible next counter-attack
        currentCounterAttackPlayer = defender;
    }
}
