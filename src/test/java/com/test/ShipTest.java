package com.test;

import application.Cell;
import application.entities.Ship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ShipTest {

    private Ship ship;
    private List<Cell> coords;

    @BeforeEach
    void setUp() {

        ship = new Ship(3);
        coords = Arrays.asList(new Cell(0,0), new Cell(0, 1), new Cell(0,2));
    }

    @Test
    @DisplayName("Constructor initializes size, health and sunk status correctly")
    void testConstructorInitializesCorrectly(){

        assertEquals(3, ship.getSize());
        assertEquals(3, ship.getRemainigHealth());
        assertFalse(ship.isSunk());
    }


    @Test
    @DisplayName("Setting and getting coordinates correctly")
    void testSetAndGetCoordinates(){
        ship.setCoordinates(coords);
        assertEquals(coords, ship.getCoordinates());
    }

    @Test
    @DisplayName("Setting coordinates with a size mismatch must throw an exception")
    void testSetCoordinatesThrowsExceptionWhenSizeMismatch(){

        List<Cell> wrongCoords = Arrays.asList(new Cell(1, 1), new Cell(1,2));
        assertThrows(IllegalArgumentException.class, () -> ship.setCoordinates(wrongCoords));
    }

    @Test
    @DisplayName("Registering a hit on a valid cell must reduce remaining health")
    void testRegisterHitMarksCorrectSegment(){

        ship.setCoordinates(coords);
        ship.registerHit(new Cell(0,1));
        assertEquals(2,ship.getRemainigHealth());
        assertFalse(ship.isSunk());
    }

    @Test
    @DisplayName("Registering a hit on a non-occupied cell must not change health")
    void testRegisterDoesNothingIfCellNotPartOfShip(){

        ship.setCoordinates(coords);
        ship.registerHit(new Cell(5,5));
        assertEquals(3,ship.getRemainigHealth());
        assertFalse(ship.isSunk());
    }


    @Test
    @DisplayName("Registering a hit before setting coordinates must throw an exception")
    void testRegisterHitThrowsExceptionWhenCoordinatesNotSet(){

        assertThrows(IllegalStateException.class, () -> ship.registerHit(new Cell(0,0)));
    }

    @Test
    @DisplayName("toString() must include size and hit status")
    void testToStringContainsSizeAndHits(){

        String result = ship.toString();
        assertTrue(result.contains("size = 3"));
        assertTrue(result.contains("hits"));
    }

    @Test
    @DisplayName("Ship must be sunk only after all segments are hit")
    void testIsSunkAfterAllHits(){

        ship.setCoordinates(coords);

        for (Cell c : coords){
            ship.registerHit(c);
        }

        assertTrue(ship.isSunk());
        assertEquals(0,ship.getRemainigHealth());
    }

    @Test
    @DisplayName("Occupies() must correctly report if ship covers a cell")
    void testOccupiesReturnsCorrectly(){

        ship.setCoordinates(coords);
        assertTrue(ship.occupies(new Cell(0,0)));
        assertFalse(ship.occupies(new Cell(1,1)));
    }


}
