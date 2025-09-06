package com.test;

import application.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InputParserTest
{


    @DisplayName("test horizontal cell range")
    @Test
    void testHorizontalCoordinatesRange()
    {
        List<Cell> horizontal = new ArrayList<>();
        horizontal.add(new Cell(3, 5));
        horizontal.add(new Cell(2, 5));
        horizontal.add(new Cell(1, 5));

        assertEquals(horizontal, InputParser.getCellsFromRange(3, 5, 1, 5));
    }

    @DisplayName("Test vertical cell range")
    @Test
    void testVerticalCoordinatesRange()
    {
        List<Cell> vertical = new ArrayList<>();
        vertical.add(new Cell(5, 5));
        vertical.add(new Cell(5, 4));
        vertical.add(new Cell(5, 3));

        assertEquals(vertical, InputParser.getCellsFromRange(5, 5, 5, 3));
    }

    @DisplayName("Test diagonal cell range")
    @Test
    void testDiagonalCoordinatesRange()
    {
        List<Cell> diagonal = new ArrayList<>();
        diagonal.add(new Cell(6, 6));
        diagonal.add(new Cell(5, 5));
        diagonal.add(new Cell(4, 4));

        assertEquals(diagonal, InputParser.getCellsFromRange(6, 6, 4, 4));
    }

    @DisplayName("Test negatives in range")
    @Test
    void testNegativesInCoordinatesRange()
    {
        assertThrows(IllegalArgumentException.class,
                () -> InputParser.getCellsFromRange(-1,0,1,0));
    }

    @DisplayName("Test invalid diagonal cell range")
    @Test
    void testInvalidDiagonalCoordinatesRange()
    {
        assertThrows(IllegalArgumentException.class,
                () -> InputParser.getCellsFromRange(0,0,1,2));
    }


    @DisplayName("Test Command Exception")
    @Test
    void testInputStringThrowsCommandException()
    {
        Arrays.stream(Command.values()).forEach(command -> assertThrows(CommandException.class,
                () -> InputParser.throwIfInputIsCommand(command.name())));
    }

    @DisplayName("Test single coordinate-pair as valid input")
    @Test
    void testSingleCoordinateValidInput() {
        Cell expected = new Cell(5,5);
        Cell result = InputParser.parseCoordinates("5,5");
        assertEquals(expected, result);
    }

    @DisplayName("Test single coordinate-pair as invalid input")
    @Test
    void testSingleCoordinatesInvalidInput() {
        String[] checks = {"-1,3", "3-4", null, ""};
        for (String check : checks) {
            assertThrows(IllegalArgumentException.class,
                    () -> InputParser.parseCoordinates(check));
        }
    }

    @DisplayName("Test parsing a correct string of a coordinate range")
    @Test
    void testCoordinateRangeValidInput() {
        List<Cell> expected = List.of(new Cell(1,2), new Cell(2,2), new Cell(3,2));
        String input = "1,2-3,2";
        assertEquals(expected,InputParser.parseCoordinatesRange(input));
    }

    @DisplayName("Test parsing a incorrect string of a coordinate range")
    @Test
    void testCoordinateRangeInvalidInput() {
        String[] input = {"0,0-1,2", "9,7-99", "-9,7-9,9", "-5,0", "3"};

        for (String test : input) {
            assertThrows(IllegalArgumentException.class,
                    () -> InputParser.parseCoordinatesRange(test));
        }
    }

    @DisplayName("Test a valid ship selection")
    @Test
    void testValidShipSelection() {
        int index = ShipBuilder.getNumberOfShipTypes(); //valid indexes from 1 to numbersOfShipTypes
        Ship expectedShip = ShipBuilder.create(index-1);
        assertEquals(expectedShip.getClass(), InputParser.parseShip(""+index).getClass());
    }

    @DisplayName("Test an invalid ship selection")
    @Test
    void testInvalidShipSelection() {
        assertThrows(IllegalArgumentException.class,
                () -> InputParser.parseShip(""+-1));
        assertThrows(IllegalArgumentException.class,
                () -> InputParser.parseShip(""+ShipBuilder.getNumberOfShipTypes()+1));
    }

}
