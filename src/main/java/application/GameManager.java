package application;

//Controls the lifecycle of a game session
public class GameManager {
    Player player1;
    Player player2;
    IOManager IOManager;

    public GameManager() {
        IOManager = new IOManager();
        IOManager.introduceGame();
    }

    public void initializeGame() {

    }

    public void startGame() {

    }
}
