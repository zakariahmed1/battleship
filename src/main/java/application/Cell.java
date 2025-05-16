package application;

public class Cell {
    Ship ship;
    boolean attacked ;

    public void placeShip(Ship ship){
        this.ship = ship;
    }
    public boolean hasShip(){

        return ship!=null;
    }
    public boolean isAttacked(){
        return true;
    }public void attack ( ){
    }
    public Ship getShip(){
        return ship;

    }
}
