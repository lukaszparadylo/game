package pl.lukaszparadylo.gamers;

import lombok.Getter;
import pl.lukaszparadylo.board.Board;

@Getter
public class Gamer {
    private String name;
    private Integer score;
    private Character chosenMark;
    private Boolean nextMove;

    public Gamer(String name, Character chosenMark) {
        this.name = name;
        this.chosenMark = chosenMark;
        this.score = 0;
        this.nextMove = false;
    }
    public Boolean makeMove(Board board, Integer position) {
        if(position>0&&position<= board.getBoardSize()* board.getBoardSize()){
            board.setFields(position, getChosenMark());
            return true;
        }
        else return false;
    }
    public void addPoints(Integer score) {
        this.score += score;
    }

    public Boolean getNextMove() {
        return nextMove;
    }
    public void setNextMove(Boolean nextMove) {
        this.nextMove = nextMove;
    }

    public Boolean makeMove(Board board) {
        return null;
    }
}
