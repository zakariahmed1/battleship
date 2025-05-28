package com.test;

import application.HumanPlayer;
import application.IOManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import application.Cell;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IOManagerTest
{

    static IOManager io;

    @BeforeAll
    static void initializeIO()
    {
        io = new IOManager();
    }

    @AfterAll
    static void closeIO()
    {
        //io.close()
    }


    @DisplayName("test horizontal cell range")
    @Test
    void testHorizontalCoordinatesRange()
    {
        List<Cell> horizontal = new ArrayList<>();
        horizontal.add(new Cell(3, 5));
        horizontal.add(new Cell(2, 5));
        horizontal.add(new Cell(1, 5));

        assertEquals(io.getCellsFromRange(3, 5, 1, 5), horizontal);
    }

    @DisplayName("Test vertical cell range")
    @Test
    void testVerticalCoordinatesRange()
    {
        List<Cell> vertical = new ArrayList<>();
        vertical.add(new Cell(5, 5));
        vertical.add(new Cell(5, 4));
        vertical.add(new Cell(5, 3));

        assertEquals(io.getCellsFromRange(5, 5, 5, 3), vertical);
    }

    @DisplayName("Test diagonal cell range")
    @Test
    void testDiagonalCoordinatesRange()
    {
        List<Cell> diagonal = new ArrayList<>();
        diagonal.add(new Cell(6, 6));
        diagonal.add(new Cell(5, 5));
        diagonal.add(new Cell(4, 4));

        assertEquals(io.getCellsFromRange(6, 6, 4, 4), diagonal);
    }


    @DisplayName("Test to large cell range")
    @Test
    void testTooLargeCoordinatesRange()
    {
        assertThrows(IllegalArgumentException.class,
                () -> io.getCellsFromRange(0, 0, 20, 20));
    }


}
