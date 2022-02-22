package pl.lukaszparadylo.judge;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.lukaszparadylo.board.Board;
import pl.lukaszparadylo.gamers.Gamer;
import pl.lukaszparadylo.scanner.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class Judge2Test {

    Board board;
    Gamer gamer1;
    Gamer gamer2;
    Judge2 judge2;

    @BeforeEach
    public void setValues(){
        board = new Board(3);
        gamer1 = new Gamer("Gamer1",'X');
        gamer2 = new Gamer("Gamer2",'O');
        judge2 = new Judge2(board);
    }

    @Test
    void checkWhoWon() {
        gamer1.makeMove(board,1);
        gamer1.makeMove(board, 2);
        gamer1.makeMove(board, 3);
        String whoWon = judge2.checkWhoWon();
        setValues();
        String whoWon2 = judge2.checkWhoWon();
        Assertions.assertEquals("X", whoWon);
        Assertions.assertEquals("draw", whoWon2);
    }
}