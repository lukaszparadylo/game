package pl.lukaszparadylo.exceptions;

public class IllegalBoardSize extends Exception{
        public IllegalBoardSize(){
            super("Wrong type or size of table");
        }
}
