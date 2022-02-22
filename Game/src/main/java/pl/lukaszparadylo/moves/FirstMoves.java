package pl.lukaszparadylo.moves;

import pl.lukaszparadylo.board.Board;

import java.util.Random;

public class FirstMoves{
    public static Boolean makeMove(Board board, Character mark) {
        Random random = new Random();
        while(true){
            Integer row = random.nextInt(board.getBoardSize());
            Integer column = random.nextInt(board.getBoardSize());
            if(board.getFields()[row][column]=='_'){
                board.getFields()[row][column]=mark;
                return true;
            }
        }
    }
}
