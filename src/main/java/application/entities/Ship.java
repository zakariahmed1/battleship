package application.entities;

import application.Cell;

import java.util.List;

public class Ship {

    protected int size;  //size of the ship
    protected boolean[] hits;  //hit status for each segment
    protected List<Cell> coordinates;   //coordinates of the ship of the board

    public Ship(int size){
        this.size = size;
        this.hits = new boolean[size]; //false by default (no hits)
    }


    //return the ship size
    public int getSize(){
        return size;
    }


    //Set coordinates of the ship on the board
    public void setCoordinates(List<Cell> coordinates){
        if(coordinates.size() != size){
            throw new IllegalArgumentException("Coordinates size must match ship size");
        }
        this.coordinates = coordinates;
    }

    //Get the coordinates of the ship
    public List<Cell> getCoordinates(){
        return coordinates;
    }


    //check if the ship is fully sunk (all segments hit)
    public boolean isSunk(){
        for (boolean hit: hits){
            if (!hit){
                return false;
            }
        } return true;
    }

    //register a hit on the ship at the given cell
    public void registerHit(Cell cell){

        if (coordinates == null){
            throw new IllegalStateException("Coordinates aren't set for ship");
        }
        for (int i = 0; i < size; i++){
            if (coordinates.get(i).equals(cell)){
                hits[i] = true;
                return;
            }
        }
    }

    //check if the ship is fully sunk
    public boolean occupies(Cell cell){

        if (coordinates == null) return false;
        return coordinates.contains(cell);
    }

    //get how many segments aren't hit
    public int getRemainigHealth(){

        int count = 0;

        for (boolean hit : hits){
            if (!hit) count++;
        } return count;
    }


    @Override
    public String toString() {
        return "Ship (size = " + size + ", hits = " + java.util.Arrays.toString(hits) + " )";
    }
}
