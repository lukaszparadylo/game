package pl.lukaszparadylo.gamers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.lukaszparadylo.board.Board;

import static org.junit.jupiter.api.Assertions.*;

class Robot2Test {

    private Gamer gamer;
    private Robot2 robot;
    private Board board;

    @BeforeEach
    public void setValues(){
        gamer = new Gamer("Name",'O');
        robot = new Robot2('X',1);
        board = new Board(3);
    }

    @Test
    void makeMove() {
        gamer.makeMove(board,1);
        gamer.makeMove(board,3);
        robot.makeMove(board);
        board.incrementMoveNumber();
        robot.makeMove(board);
        board.incrementMoveNumber();
        robot.makeMove(board);
        board.incrementMoveNumber();
        board.showBoard();
        Integer amountOfRobotsMark = 0;
        for (Character[] i : board.getFields()){
            for (Character j : i){
                if(j=='X')amountOfRobotsMark++;
            }
        }
        Assertions.assertEquals(3, amountOfRobotsMark);

    }
    @Test
    public void robotLevelTest(){
        Integer currentRobotsLevel = robot.getRobotLevel();
        robot = new Robot2('X',3);
        Integer changedRobotsLevel = robot.getRobotLevel();
        robot = new Robot2('X', 33);
        Integer wrongRobotsLevel = robot.getRobotLevel();
        robot = new Robot2('X', 0);
        Integer robotLevel0 = robot.getRobotLevel();

        Assertions.assertEquals(1, currentRobotsLevel);
        Assertions.assertEquals(3, changedRobotsLevel);
        Assertions.assertEquals(3, wrongRobotsLevel);
        Assertions.assertEquals(1, robotLevel0);
    }
}