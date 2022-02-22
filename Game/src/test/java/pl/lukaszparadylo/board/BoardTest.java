package pl.lukaszparadylo.board;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.lukaszparadylo.exceptions.IllegalBoardSize;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {


    private Board board = new Board(0);
    private Board board1 = new Board(1);
    private Board board3 = new Board(3);
    private Board board5 = new Board(5);

    @AfterEach
    public void resetValues(){
        board = new Board(0);
        board1 = new Board(1);
        board3 = new Board(3);
        board5 = new Board(5);
    }
    @Test
    void incrementMoveNumber() {
        Integer moveNumberBefore = board.getMoveNumber();
        board.incrementMoveNumber();
        Integer moveNumberAfterIncrement = board.getMoveNumber();
        Assertions.assertEquals(0, moveNumberBefore);
        Assertions.assertEquals(1, moveNumberAfterIncrement);
    }

    @Test
    void setFields() {
        board.setFields(2,'K');
        Character getMark = board.getFields()[0][0];
        Assertions.assertEquals('K', getMark);
    }

    @Test
    void showBoard() {
        board.showBoard();
    }

    @Test
    void getBoardSize() {
        Integer boardSize = board.getBoardSize();
        Integer board1Size = board1.getBoardSize();
        Integer board3Size = board3.getBoardSize();
        Integer board5Size = board5.getBoardSize();

        Assertions.assertEquals(1,boardSize);
        Assertions.assertEquals(1,board1Size);
        Assertions.assertEquals(3,board3Size);
        Assertions.assertEquals(5,board5Size);
    }

    @Test
    void getFields() {
        Character[][] getFields = board.getFields();
        for (Character[] i : getFields){
            for (Character j : i){
                Assertions.assertEquals('_',j);
            }
        }
        Character[][] getFields3 = board3.getFields();
        for (Character[] i : getFields){
            for (Character j : i){
                Assertions.assertEquals('_',j);
            }
        }
    }

    @Test
    void getMoveNumber() {
        board.incrementMoveNumber();
        Integer moveNumber = board.getMoveNumber();
        Assertions.assertEquals(1, moveNumber);
    }
}