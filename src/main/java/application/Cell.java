package application;

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

    public Cell() {
        this.attacked = false;
    }

    public void placeShip(Ship ship) {
        this.ship = ship;
    }

    public boolean hasShip() {
        return ship != null;
    }

    public boolean isAttacked() {
        return attacked;
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