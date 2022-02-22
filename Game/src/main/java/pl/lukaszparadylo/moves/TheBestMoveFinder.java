package pl.lukaszparadylo.moves;

import pl.lukaszparadylo.board.Board;

public class TheBestMoveFinder {
    private Integer[] theBestScore;
    private Integer score;
    private Integer numberOfMark;
    private Integer secondMark;
    private Board board;

    public TheBestMoveFinder(Board board) {
        this.theBestScore = new Integer[3];
        this.board = board;
    }
    public Integer[] findTheBestMove(Character mark){
        this.score = 0;
        if(mark=='X') {
            numberOfMark = 4;
            secondMark = 5;
        }
        else {
            numberOfMark = 5;
            secondMark = 4;
        }
        findTheBestMoveByBackSlash(board.getFields());
        findTheBestMoveByForwardSlash(board.getFields());
        findTheBestMoveByRows(board.getFields());
        findTheBestMoveByColumns(board.getFields());
        if(theBestScore[0]==null||theBestScore[1]==null||theBestScore==null||theBestScore[2]==null){
/*            findTheBestMoveByBackSlash(board.getFields());
            findTheBestMoveByForwardSlash(board.getFields());
            findTheBestMoveByRows(board.getFields());
            findTheBestMoveByColumns(board.getFields());*/
        }
        return theBestScore;
    }
    private void findTheBestMoveByRows(Character[][] table){
        Integer tempScore=0;
        for (int i = 0; i < table.length; i++) {
            Integer[] rowData = {0,0,0,0,0,0};

            for (int j = 0; j < table.length; j++) {
                if(table[i][j]=='_') rowData[3]++;
                if(table[i][j]=='X') rowData[4]++;
                if(table[i][j]=='O') rowData[5]++;
            }

            if(rowData[3]>0&&rowData[numberOfMark]>=0&&rowData[secondMark]==0){
                tempScore=rowData[numberOfMark]+rowData[3];
            }
            else if(rowData[3]==1&&rowData[secondMark]>0&&rowData[numberOfMark]==0){
                tempScore=rowData[secondMark]+rowData[3];
            }else {
                tempScore=rowData[3];
            }
            if(tempScore>score) {
                score = tempScore;
                for (int j = 0; j < table.length; j++) {
                    if(table[i][j]=='_') {
                        rowData[1] = i;
                        rowData[2] = j;
                        theBestScore[0] = rowData[1];
                        theBestScore[1] = rowData[2];
                        theBestScore[2] = score;
                        break;
                    }
                }
            }
        }
    }
    private void findTheBestMoveByColumns(Character[][] table){
        Integer tempScore = 0;
        for (int i = 0; i < table.length; i++) {
            Integer[] columnData = {0,0,0,0,0,0};
            for (int j = 0; j < table.length; j++) {
                if(table[j][i]=='_') columnData[3]++;
                if(table[j][i]=='X') columnData[4]++;
                if(table[j][i]=='O') columnData[5]++;
            }
            //Integer tempScore = (columnData[3])+(columnData[numberOfMark])-(columnData[secondMark]);
            if(columnData[3]>0&&columnData[numberOfMark]>=0&&columnData[secondMark]==0){
                tempScore=columnData[numberOfMark]+columnData[3];
            }
            else if(columnData[3]==1&&columnData[secondMark]>0&&columnData[numberOfMark]==0){
                tempScore=columnData[secondMark]+columnData[3];
            }else {
                tempScore=columnData[3];
            }
            if(tempScore>score) {
                score = tempScore;
                for (int j = 0; j < table.length; j++) {
                    if(table[j][i]=='_') {
                        columnData[1] = i;
                        columnData[2] = j;
                        theBestScore[0] = columnData[1];
                        theBestScore[1] = columnData[2];
                        theBestScore[2] = score;
                        break;
                    }
                }

            }
        }
    }
    private void findTheBestMoveByBackSlash(Character[][] table){
            Integer tempScore = 0;
            Integer[] columnData = {0,0,0,0,0,0};
            for (int j = 0; j < table.length; j++) {
                if(table[j][j]=='_') columnData[3]++;
                if(table[j][j]=='X') columnData[4]++;
                if(table[j][j]=='O') columnData[5]++;
            }
            //Integer tempScore = (columnData[3])+(columnData[numberOfMark])-(columnData[secondMark]);
            if(columnData[3]>0&&columnData[numberOfMark]>=0&&columnData[secondMark]==0){
                tempScore=columnData[numberOfMark]+columnData[3];
            }
            else if(columnData[3]==1&&columnData[secondMark]>0&&columnData[numberOfMark]==0){
                tempScore=columnData[secondMark]+columnData[3];
            }else {
                tempScore=columnData[3];
            }
            if(tempScore>score) {
                score = tempScore;
                for (int j = 0; j < table.length; j++) {
                    if(table[j][j]=='_') {
                        columnData[1] = j;
                        columnData[2] = j;
                        theBestScore[0] = columnData[1];
                        theBestScore[1] = columnData[2];
                        theBestScore[2] = score;
                        break;
                    }
                }

            }
    }
    private void findTheBestMoveByForwardSlash(Character[][] table){
        Integer tempScore = 0;
        Integer[] columnData = {0,0,0,0,0,0};
        for (int j = 0; j < table.length; j++) {
            if(table[table.length-j-1][j]=='_') columnData[3]++;
            if(table[table.length-j-1][j]=='X') columnData[4]++;
            if(table[table.length-j-1][j]=='O') columnData[5]++;
        }
        //Integer tempScore = (columnData[3])+(columnData[numberOfMark])-(columnData[secondMark]);
        if(columnData[3]>0&&columnData[numberOfMark]>=0&&columnData[secondMark]==0){
            tempScore=columnData[numberOfMark]+columnData[3];
        }
        else if(columnData[3]==1&&columnData[secondMark]>0&&columnData[numberOfMark]==0){
            tempScore=columnData[secondMark]+columnData[3];
        }else {
            tempScore=columnData[3];
        }
        if(tempScore>score) {
            score = tempScore;
            for (int j = 0; j < table.length; j++) {
                if(table[j][j]=='_') {
                    columnData[1] = j;
                    columnData[2] = j;
                    theBestScore[0] = columnData[1];
                    theBestScore[1] = columnData[2];
                    theBestScore[2] = score;
                    break;
                }
            }

        }
    }
}
