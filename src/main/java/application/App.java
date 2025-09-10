package application;

//Entry point of the application
public class App {
    public static void main(String[] args) {
        GameManager game = GameManager.getInstance();
        game.initializeGame();
        game.startGame();
    }
}
