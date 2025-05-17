package application;

import java.util.ArrayList;

//Abstract class for players(human and bots)
public abstract class Player {
    private String name = "";
    private Board playerBoard;
    private Board enemyBoard;
    private ArrayList<Ship> fleet;

    public Player(String name) {
        this.name = name;
        playerBoard = new Board();
        enemyBoard = new Board();
    }

}
