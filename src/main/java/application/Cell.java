package application;

import application.entities.Ship;

public class Cell {
    Ship ship;
    int x;
    int y;
    boolean attacked;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.attacked = false;
    }

    public int getX(){ return x; }
    public int getY() { return y;}
    public void setX(int x){
        this.x=x;
    }
    public void setY(int y){
        this.y=y;
    }
    public Cell() {
        this.attacked = false;
    }

    public void placeShip(Ship ship) {
        this.ship = ship; //assign a ship to the cell
    }

    public boolean hasShip() {
        return ship != null; //return null if there is no ship assigned to the cell
    }

    public boolean isAttacked() {
        return attacked;
    }
    public boolean hasShip(Ship ship1){
        return ship1 !=null;
    }
    public void attack() {
        this.attacked = true;
        if (hasShip()) {
            ship.registerHit(this);
        }
    }

    public Ship getShip() {
        return ship;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cell cell = (Cell) obj;
        return x == cell.x && y == cell.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }
}