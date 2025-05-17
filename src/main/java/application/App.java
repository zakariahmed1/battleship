package application;

//Entry point of the application
public class App {
    public static void main(String[] args) {
        GameManager game = new GameManager();
        game.initializeGame();
        game.startGame();
    }
}
