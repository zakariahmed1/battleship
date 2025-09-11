package com.test;

import application.Board;
import application.Cell;
import application.entities.Ship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class BoardTest {

    private Board board;

    @BeforeEach
    @DisplayName("Initialize a fresh Board before each test")
    void setUp(){
        board = new Board(10);
    }

    private Ship createShipWithCells(int size, int startX, int startY){

        Ship ship = new Ship(size);
        List<Cell> coords = new ArrayList<>();

        for(int i = 0; i < size; i++){
            coords.add(new Cell(startX + i, startY)); //horizontal placement
        }
        ship.setCoordinates(coords);
        return ship;
    }

    @Test
    @DisplayName("Board must initialize with correct dimensions")
    void testBoardInitialization(){
        assertNotNull(board, "Board object must not be 0 after construction");
    }

    @Test
    @DisplayName("placeShip() must succed for a valid ship placement")
    void testPlaceShipValid(){

        Ship ship = createShipWithCells(3,0,0);
        boolean result = board.placeShip(ship);
        assertTrue(result, "Expected placeShip to succeed to a valid ship");
    }

    @Test
    @DisplayName("placeShip() must fail for a ship with invalid coords")
    void testPlaceShipInvalid(){

        Ship ship = createShipWithCells(3,9,0);
        boolean result = board.placeShip(ship);
        assertFalse(result, "Expected placeShip to fail for a ship that doesn't fit");
    }
}
