package pl.lukaszparadylo.board;

import lombok.Getter;

@Getter
public class Board {
    public static final Integer EMPTY = 0;
    public static final Integer CROSS = 1;
    public static final Integer CIRCLE = 2;
    private Character[][] fields;
    private Integer moveNumber;

    public Board(Integer size) {
        if(size<=0) size = 1;
        this.fields = new Character[size][size];
        for (int c = 0; c<size; c++){
            for (int s = 0; s<size; s++){
                this.fields[c][s]='_';
            }
        }
        moveNumber = 0;
    }
    public Character getFieldByPosition(Integer position){
        Integer counter=1;
        for (int i = 0; i<fields.length; i++){
            for(int j = 0; j<fields.length; j++){
                if(position==counter) return fields[i][j];
                counter++;
            }
        }
        return '#';
    }
    public void incrementMoveNumber(){
        moveNumber++;
    }
    public Character[][] setFields(Integer position, Character mark) {
        Integer count = 0;
        if(position==0) position = 1;
        if(position>=getBoardSize()*getBoardSize())position=getBoardSize()*getBoardSize();
        for (int i = 0; i<fields.length; i++){
            for(int j = 0; j<fields.length; j++){
                count++;
                if(count==position) this.fields[i][j]=mark;
            }
        }
        return this.getFields();
    }
    public void showBoard(){
        for(Character[] s : this.fields){
            System.out.print("|");
            for (Character c : s){
                System.out.print(c+"|");
            }
            System.out.print("\n");
        }
    }
    public Integer getBoardSize(){
        return fields.length;
    }

}
