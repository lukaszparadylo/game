package pl.lukaszparadylo.gamers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.lukaszparadylo.board.Board;

public class GamersTest {

    private Gamer gamer1;
    private Gamer gamer2;
    private Board board;

    @BeforeEach
    public void resetAllValues(){
        gamer1 = new Gamer("Jan",'X');
        gamer2 = new Gamer("Jacek", 'O');
    }

    @Test
    public void makeMoveTest(){
        board = new Board(3);
        gamer1.makeMove(board,1);
        Assertions.assertEquals('X', board.getFields()[0][0]);
    }
    @Test
    public void getNameTest(){
        String name = gamer1.getName();
        Assertions.assertEquals(true, !name.isEmpty());
    }
    @Test
    public void scoreTest(){
        Integer scoreBeforeWon = gamer1.getScore();
        gamer1.addPoints(100);
        Integer scoreAfterWon = gamer1.getScore();
        gamer1.addPoints(100);
        Integer scoreAfter2Won = gamer1.getScore();

        Assertions.assertEquals(0,scoreBeforeWon);
        Assertions.assertEquals(100, scoreAfterWon);
        Assertions.assertEquals(200, scoreAfter2Won);
    }

}
