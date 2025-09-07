package com.test;

import application.HumanPlayer;
import application.IOManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class PlayerTest
{
    static IOManager io;

    @BeforeAll
    static void initializeIO() {
        io = new IOManager();
    }

    @AfterAll
    static void closeIO() {
        //io.close();
    }

    @DisplayName("Test valid player name")
    @Test
    void testValidPlayerName() {
        HumanPlayer player = new HumanPlayer("Philipp", io);
    }

    @DisplayName("Test empty player name")
    @Test
    void testPlayerNameEmptyOrNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new HumanPlayer(null, io));
        assertThrows(IllegalArgumentException.class,
                () -> new HumanPlayer("", io));
    }

    @DisplayName("Test player name too long")
    @Test
    void testPlayerNameTooLong() {
        assertThrows(IllegalArgumentException.class,
                () -> new HumanPlayer("ThisIsATestPlayerName", io));
    }

    @DisplayName("Test player name with whitespaces")
    @Test
    void testPlayerNameWhiteSpaces() {
        assertThrows(IllegalArgumentException.class,
                () -> new HumanPlayer("In v a lid",io));
    }


}
