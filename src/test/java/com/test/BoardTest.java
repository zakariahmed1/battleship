package com.test;

import application.Board;
import org.junit.jupiter.api.BeforeEach;

public class BoardTest {

    private Board board;

    @BeforeEach
    void setUp(){
        board = new Board(10);
    }
}
