package pl.lukaszparadylo.gamers;

import lombok.Getter;
import pl.lukaszparadylo.board.Board;
import pl.lukaszparadylo.gamers.Gamer;
import pl.lukaszparadylo.moves.FirstMoves;
import pl.lukaszparadylo.moves.RandomMove;
import pl.lukaszparadylo.moves.TheBestMoveFinder;

import java.util.Random;
@Getter
public class Robot2 extends Gamer {
    private Integer robotLevel;
    private Boolean nextMove;

    public Robot2(Character chosenMark, Integer robotLevel) {
        super("Robot " + new Random().nextInt(), chosenMark);
        if(robotLevel>3)this.robotLevel = 3;
        else if(robotLevel<=0)this.robotLevel = 1;
        else this.robotLevel=robotLevel;
        this.nextMove = false;
    }
    public Boolean makeMove(Board board){
        TheBestMoveFinder theBestMoveFinder = new TheBestMoveFinder(board);
        Integer[] theBestMove;
        if(board.getMoveNumber()<board.getBoardSize()){
            FirstMoves.makeMove(board, getChosenMark());
            return true;
        } else if(robotLevel==1){
            theBestMove = theBestMoveFinder.findTheBestMove(getChosenMark());
            if(theBestMove[0]==null||theBestMove[1]==null||theBestMove==null||theBestMove[2]==null||
                    board.getFields()[theBestMove[0]][theBestMove[1]]!='_') RandomMove.makeMove(board,getChosenMark());
            else board.getFields()[theBestMove[0]][theBestMove[1]]=getChosenMark();
            return true;
        }else if(robotLevel==2){
            if(new Random().nextInt(2)==1){
                theBestMove = theBestMoveFinder.findTheBestMove(getChosenMark());
                if(theBestMove[0]==null||theBestMove[1]==null||theBestMove==null||theBestMove[2]==null||
                        board.getFields()[theBestMove[0]][theBestMove[1]]!='_') RandomMove.makeMove(board,getChosenMark());
                else board.getFields()[theBestMove[0]][theBestMove[1]]=getChosenMark();
            }else {
                RandomMove.makeMove(board,getChosenMark());
            }
            return true;
        }else if(robotLevel==3){
            if(new Random().nextInt(3)==1){
                theBestMove = theBestMoveFinder.findTheBestMove(getChosenMark());
                if(theBestMove[0]==null||theBestMove[1]==null||theBestMove==null||theBestMove[2]==null||
                        board.getFields()[theBestMove[0]][theBestMove[1]]!='_') RandomMove.makeMove(board,getChosenMark());
                else board.getFields()[theBestMove[0]][theBestMove[1]]=getChosenMark();
            }else {
                RandomMove.makeMove(board,getChosenMark());
            }
            return true;
        }
        return false;
    }

    public Boolean getNextMove() {
        return nextMove;
    }

    @Override
    public void setNextMove(Boolean nextMove) {
        this.nextMove = nextMove;
    }
}
