package pl.lukaszparadylo.judge;

import pl.lukaszparadylo.board.Board;
import pl.lukaszparadylo.scanner.Scanner;

public class Judge2 {
    public static final String DRAW = "draw";
    public static final Integer EMPTY = 0;
    public static final Integer CROSS = 1;
    public static final Integer CIRCLE = 2;
    private Board board;
    private Scanner scanner;
    private Character[] availableFigures;

    public Judge2(Board board) {
        this.board = board;
        this.availableFigures = new Character[]{'_','X','O'};
        this.scanner = new Scanner();
    }
    public String checkWhoWon(){
        if(scanner.getDataFromBackSlash(board.getFields())[CROSS]==board.getBoardSize())
            return availableFigures[CROSS].toString();
        if(scanner.getDataFromForwardSlash(board.getFields())[CROSS]==board.getBoardSize())
            return availableFigures[CROSS].toString();
        if(scanner.getDataFromBackSlash(board.getFields())[CIRCLE]==board.getBoardSize())
            return availableFigures[CIRCLE].toString();
        if(scanner.getDataFromForwardSlash(board.getFields())[CIRCLE]==board.getBoardSize())
            return availableFigures[CIRCLE].toString();

        for(int i = 0;i<board.getBoardSize(); i++){
            scanner.getDataFromColumn(board.getFields(), i);
            if(scanner.getDataFromColumn(board.getFields(), i)[CROSS]==board.getBoardSize())
                return availableFigures[CROSS].toString();
            if(scanner.getDataFromColumn(board.getFields(), i)[CIRCLE]==board.getBoardSize())
                return availableFigures[CIRCLE].toString();
            if(scanner.getDataFromRows(board.getFields(), i)[CROSS]==board.getBoardSize())
                return availableFigures[CROSS].toString();
            if(scanner.getDataFromRows(board.getFields(), i)[CIRCLE]==board.getBoardSize())
                return availableFigures[CIRCLE].toString();
        }
        return DRAW;
    }
}
