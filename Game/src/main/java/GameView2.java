import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import pl.lukaszparadylo.board.Board;
import pl.lukaszparadylo.gamers.Gamer;
import pl.lukaszparadylo.gamers.Robot2;
import pl.lukaszparadylo.judge.Judge2;

import java.util.*;
import java.util.stream.Collectors;


public class GameView2 {

    @FXML Button start, nextMove, restart, newGame;
    @FXML ChoiceBox choiceBoxGamer1, choiceBoxGamer2, boardSizeChoiceBox;
    @FXML TextField textFieldGamer1,textFieldGamer2;
    @FXML AnchorPane anchorPane;
    @FXML CheckBox xShape1, oShape1,xShape2, oShape2, levelEasy1, levelMedium1, levelHard1,
            levelEasy2, levelMedium2, levelHard2;
    @FXML Label infoLabel, scoreGamer1, scoreGamer2, roundNumber, moveNumber, winnerLabel;

    public static final String board3x3 = "3x3";
    public static final String board5x5 = "5x5";
    public static final String board4x4 = "4x4";
    public static final String GAMER = "Gamer";
    public static final String ROBOT = "Robot";
    public Integer boardSize;
    private Integer roundNumberInt = 1;

    private Judge2 judge2;
    private Board board;
    private Gamer gamer1;
    private Gamer gamer2;
    private Robot2 robot1;
    private Robot2 robot2;
    private Integer scoreGamer1int=0, scoreGamer2int=0;

    List<Robot2> robotsList;
    List<Gamer> gamersList = new ArrayList<>();

    public void startGame(){
        roundNumber.setText(roundNumberInt.toString());
        if(boardSizeChoiceBox.getValue()==null) {
            infoLabel.setText("Set board size !");
        }else if(choiceBoxGamer1.getValue()==null||choiceBoxGamer2.getValue()==null){
            infoLabel.setText("Select type of gamer !");
        }else if((xShape1.isSelected()||!oShape1.isSelected())&& (xShape2.isSelected()||!oShape2.isSelected())){
            infoLabel.setText("Choose mark for gamer !");
        }else if(!(levelEasy1.isSelected()||levelMedium1.isSelected()||levelHard1.isSelected())
                &&choiceBoxGamer1.getValue()==ROBOT){
            infoLabel.setText("Choose robot level !");
        }else if(!(levelEasy2.isSelected()||levelMedium2.isSelected()||levelHard2.isSelected())&&
            choiceBoxGamer2.getValue()==ROBOT){
            infoLabel.setText("Choose robot level !");
        }
        else {
            Character gamer1Mark;
            Character gamer2Mark;
            Integer robot1Level = 1;
            Integer robot2Level = 1;
            enableButtons();
            if(xShape1.isSelected()) gamer1Mark='X';
            else gamer1Mark='O';
            if (xShape2.isSelected()) gamer2Mark='X';
            else gamer2Mark='O';

            if(choiceBoxGamer1.getValue()==ROBOT&&levelHard1.isSelected()) robot1Level=1;
            if(choiceBoxGamer1.getValue()==ROBOT&&levelMedium1.isSelected()) robot1Level=2;
            if(choiceBoxGamer1.getValue()==ROBOT&&levelEasy1.isSelected()) robot1Level=3;
            if(choiceBoxGamer2.getValue()==ROBOT&&levelHard2.isSelected()) robot1Level=1;
            if(choiceBoxGamer2.getValue()==ROBOT&&levelMedium2.isSelected()) robot1Level=2;
            if(choiceBoxGamer2.getValue()==ROBOT&&levelEasy2.isSelected()) robot1Level=3;

            boardSize = Integer.parseInt(boardSizeChoiceBox.getValue().toString().substring(0,1));
            board = new Board(boardSize);
            judge2 = new Judge2(board);
            if(choiceBoxGamer1.getValue()==ROBOT) {
                robot1 = new Robot2(gamer1Mark,robot1Level);
                robot1.setNextMove(true);
            }
            if (choiceBoxGamer1.getValue()==GAMER) {
                gamer1 = new Gamer(textFieldGamer1.getText(),gamer1Mark);
                gamer1.setNextMove(true);
            }
            if(choiceBoxGamer2.getValue()==ROBOT) {
                robot2 = new Robot2(gamer2Mark,robot2Level);
            }
            if (choiceBoxGamer2.getValue()==GAMER) {
                gamer2 = new Gamer(textFieldGamer2.getText(),gamer2Mark);
            }
            robotsList = new ArrayList<>();
            robotsList.add(robot1);
            robotsList.add(robot2);
            gamersList = new ArrayList<>();
            gamersList.add(gamer1);
            gamersList.add(gamer2);
            infoLabel.setText(boardSize.toString());
            setVisibleButtons();
            setNewBoard();
            nextMoveFinishGame();
            disableStartControls();
            start.setDisable(true);
            //nextMove.setDisable(false);
        }
    }
    public void robotVsRobot(){

    }
    public  void robotVsHuman(){

    }
    public void humanVsHuman(){

    }
    public void nextMoveFinishGame(){
        nextMove.setDisable(false);
        for (Robot2 robot : robotsList) {
            if(robot!=null&&robot.getNextMove()){
                robot.makeMove(board);
                moveToggle();
                disableButtons();
                nextMove.setDisable(false);
                if(judge2.checkWhoWon().equals(robot.getChosenMark().toString())){
                    theWinner();
                }
                else if(board.getMoveNumber()>=boardSize*boardSize){
                    draw();
                }
                break;
            }
        }
        if((robot1!=null&&gamer1!=null)||(robot1!=null&&gamer2!=null)||
                (robot2!=null&&gamer1!=null)||(robot2!=null&&gamer2!=null)){
            enableButtons();
            nextMove.setDisable(true);
        }
        if(gamer1!=null&&gamer2!=null) {
            nextMove.setDisable(true);
        }
        board.incrementMoveNumber();
        moveNumber.setText(board.getMoveNumber().toString());
        insertDataFromBoardToScreen();
        if(!judge2.checkWhoWon().equals("draw")){
            theWinner();
        }
        else if(board.getMoveNumber()>=boardSize*boardSize){
            draw();
        }

    }
    public void setNewGame(){
        roundNumberInt++;
        roundNumber.setText(roundNumberInt.toString());
        uncheckControls();
        enableStartControls();
        hideButtons();
        start.setDisable(false);
        winnerLabel.setText("");
        enableButtons();

    }
    public void resetButton(){
        roundNumberInt++;
        roundNumber.setText(roundNumberInt.toString());
        hideButtons();
        start.setDisable(false);
        winnerLabel.setText("");
        enableButtons();
        startGame();
        insertDataFromBoardToScreen();
        if(choiceBoxGamer1.getValue().equals(ROBOT)&&choiceBoxGamer2.getValue().equals(ROBOT)){
            disableButtons();
        }
        else if(choiceBoxGamer1.getValue().equals(GAMER)&&choiceBoxGamer2.getValue().equals(GAMER)){
            enableButtons();
        }
        else {
            enableButtons();
        }
        start.setDisable(true);
        //nextMove.setDisable(false);
        /*setVisibleButtons();
        insertDataFromBoardToScreen();
        moveNumber.setText(board.getMoveNumber().toString());
        winnerLabel.setText("");
        board.showBoard();*/
    }
    private void draw(){
        hideButtons();
        winnerLabel.setText("DRAW");
        nextMove.setDisable(true);
    }
    private void theWinner(){
        String winner="";
        if(board.getMoveNumber()%2==0){
            winner=textFieldGamer2.getText();
            scoreGamer2int++;
            scoreGamer2.setText(scoreGamer2int.toString());
        }
        else {
            winner=textFieldGamer1.getText();
            scoreGamer1int++;
            scoreGamer1.setText(scoreGamer1int.toString());
        }
        hideButtons();
        winnerLabel.setText("The winner is "+winner);
        nextMove.setDisable(true);
    }
    public void insertDataToBoard(ActionEvent event){
        Integer s = Integer.parseInt(event.getSource().toString().substring(11,13));
        for (Gamer gamer : gamersList) {
            if(gamer!=null&&gamer.getNextMove()){
                board.setFields(s,gamer.getChosenMark());
                enableButtons();
                if(judge2.checkWhoWon().equals(gamer.getChosenMark().toString())){
                    theWinner();
                }
                else if(board.getMoveNumber()>=boardSize*boardSize){
                    draw();
                }
            }
        }
        board.incrementMoveNumber();
        moveNumber.setText(board.getMoveNumber().toString());
        moveToggle();
        if((robot1!=null&&gamer1!=null)||(robot1!=null&&gamer2!=null)||
                (robot2!=null&&gamer1!=null)||(robot2!=null||gamer2!=null)){
            disableButtons();
            nextMove.setDisable(false);
        }
        if(gamer1!=null&&gamer2!=null){
            enableButtons();
            nextMove.setDisable(true);
        }
        insertDataFromBoardToScreen();
    }

    private void moveToggle(){
        if(gamer1!=null){
            if(gamer1.getNextMove())gamer1.setNextMove(false);
            else gamer1.setNextMove(true);
        }
        if(gamer2!=null){
            if(gamer2.getNextMove())gamer2.setNextMove(false);
            else gamer2.setNextMove(true);
        }
        if(robot1!=null){
            if(robot1.getNextMove())robot1.setNextMove(false);
            else robot1.setNextMove(true);
        }
        if(robot2!=null){
            if(robot2.getNextMove())robot2.setNextMove(false);
            else robot2.setNextMove(true);
        }
    }

    public void enableButtons(){
        anchorPane.getChildren().stream()
                .filter(f->f instanceof Button)
                .filter((p->p.getId().endsWith("_5")||p.getId().endsWith("_4")||p.getId().endsWith("_3")))
                .forEach(j->j.setDisable(false));
    }
    public void setVisibleButtons(){
        anchorPane.getChildren().stream()
                .filter(f->f instanceof Button)
                .filter((p->p.getId().endsWith("_"+boardSize)))
                .forEach(j->j.setVisible(true));
    }
    public void hideButtons(){
        anchorPane.getChildren().stream()
                .filter(f->f instanceof Button)
                .filter((p->p.getId().endsWith("_5")||p.getId().endsWith("_4")||p.getId().endsWith("_3")))
                .forEach(j->j.setVisible(false));
    }
    public void setNewBoard(){
        anchorPane.getChildren().stream()
                .filter(f->f instanceof Button)
                .filter((p->p.getId().endsWith("_5")||p.getId().endsWith("_4")||p.getId().endsWith("_3")))
                .forEach(j->((Button) j).setText(" "));
    }
    public void disableButtons(){
        anchorPane.getChildren().stream()
                .filter(f->f instanceof Button)
                .filter((p->p.getId().endsWith("_5")||p.getId().endsWith("_4")||p.getId().endsWith("_3")))
                .forEach(j->j.setDisable(true));
    }
    public void insertDataFromBoardToScreen(){
        List<Button> buttonsList  = anchorPane.getChildren().stream()
                .filter(i -> i instanceof Button)
                .filter(j -> j.getId().endsWith("_" + boardSize))
                .map(k->(Button)k)
                .collect(Collectors.toList());
        for (int i = 0; i < boardSize*boardSize; i++) {
            Integer buttonId = Integer.parseInt(buttonsList.get(i).getId().substring(1,3));
            buttonsList.get(i).setText(board.getFieldByPosition(buttonId).toString());
            if(buttonsList.get(i).getText().equals("X")||buttonsList.get(i).getText().equals("O")){
                System.out.println("insertDataFromBoardToScreen "+i);
                buttonsList.get(i).setDisable(true);
            }
        }
    }

    public void chooseMark1X(){
        xShape2.setSelected(false);
        oShape1.setSelected(false);
        oShape2.setSelected(true);
        updateRobotLevelFields();
    }
    public void chooseMark1O(){
        oShape2.setSelected(false);
        xShape1.setSelected(false);
        xShape2.setSelected(true);
        updateRobotLevelFields();
    }
    public void chooseMark2X(){
        xShape1.setSelected(false);
        oShape1.setSelected(true);
        oShape2.setSelected(false);
        updateRobotLevelFields();
    }
    public void chooseMark2O(){
        oShape1.setSelected(false);
        xShape1.setSelected(true);
        xShape2.setSelected(false);
        updateRobotLevelFields();
    }
    public void setGamer1Type(){
        List<String> list = new ArrayList<>();
        list.add(ROBOT);
        list.add(GAMER);
        choiceBoxGamer1.setItems(FXCollections.observableList(list));
        updateRobotLevelFields();
    }
    public void setGamer2Type(){
        List<String> list = new ArrayList<>();
        list.add(ROBOT);
        list.add(GAMER);
        choiceBoxGamer2.setItems(FXCollections.observableList(list));
        updateRobotLevelFields();
    }
    public void setBoardSize(){
        List<String> list = new ArrayList<>();
        list.add(board3x3);
        list.add(board4x4);
        list.add(board5x5);
        boardSizeChoiceBox.setItems(FXCollections.observableList(list));
        updateRobotLevelFields();
    }
    private void updateRobotLevelFields(){
        if(choiceBoxGamer1.getValue()==ROBOT) enableRobotLevel1();
        else disableRobotLevel1();
        if(choiceBoxGamer2.getValue()==ROBOT) enableRobotLevel2();
        else disableRobotLevel2();
    }
    public void setRobot1Level(){
        if(levelHard1.isSelected()){
            levelMedium1.setDisable(true);
            levelEasy1.setDisable(true);
        } else if(levelMedium1.isSelected()){
            levelEasy1.setDisable(true);
            levelHard1.setDisable(true);
        } else if(levelEasy1.isSelected()){
            levelMedium1.setDisable(true);
            levelHard1.setDisable(true);
        } else enableRobotLevel1();
    }
    public void setRobot2Level(){
        if(levelHard2.isSelected()){
            levelMedium2.setDisable(true);
            levelEasy2.setDisable(true);
        } else if(levelMedium2.isSelected()){
            levelEasy2.setDisable(true);
            levelHard2.setDisable(true);
        } else if(levelEasy2.isSelected()){
            levelMedium2.setDisable(true);
            levelHard2.setDisable(true);
        } else enableRobotLevel2();
    }
    private void disableRobotLevel1(){
        levelEasy1.setDisable(true);
        levelMedium1.setDisable(true);
        levelHard1.setDisable(true);
    }
    private void disableRobotLevel2(){
        levelEasy2.setDisable(true);
        levelMedium2.setDisable(true);
        levelHard2.setDisable(true);
    }
    private void enableRobotLevel1(){
        levelEasy1.setDisable(false);
        levelMedium1.setDisable(false);
        levelHard1.setDisable(false);
    }
    private void enableRobotLevel2(){
        levelEasy2.setDisable(false);
        levelMedium2.setDisable(false);
        levelHard2.setDisable(false);
    }
    private void disableStartControls(){
        xShape1.setDisable(true);
        xShape2.setDisable(true);
        oShape1.setDisable(true);
        oShape2.setDisable(true);
        textFieldGamer1.setDisable(true);
        textFieldGamer2.setDisable(true);
        choiceBoxGamer1.setDisable(true);
        choiceBoxGamer2.setDisable(true);
        boardSizeChoiceBox.setDisable(true);
        levelEasy1.setDisable(true);
        levelMedium1.setDisable(true);
        levelHard1.setDisable(true);
        levelEasy2.setDisable(true);
        levelMedium2.setDisable(true);
        levelHard2.setDisable(true);
    }
    private void enableStartControls(){
        xShape1.setDisable(false);
        xShape2.setDisable(false);
        oShape1.setDisable(false);
        oShape2.setDisable(false);
        textFieldGamer1.setDisable(false);
        textFieldGamer2.setDisable(false);
        choiceBoxGamer1.setDisable(false);
        choiceBoxGamer2.setDisable(false);
        boardSizeChoiceBox.setDisable(false);
        levelEasy1.setDisable(false);
        levelMedium1.setDisable(false);
        levelHard1.setDisable(false);
        levelEasy2.setDisable(false);
        levelMedium2.setDisable(false);
        levelHard2.setDisable(false);
    }
    private void uncheckControls(){
        xShape1.setSelected(false);
        xShape2.setSelected(false);
        oShape1.setSelected(false);
        oShape2.setSelected(false);
        textFieldGamer1.setText("Gamer 1");
        textFieldGamer2.setText("Gamer 2");
        choiceBoxGamer1.setValue(null);
        choiceBoxGamer2.setValue(null);
        boardSizeChoiceBox.setValue(null);
        levelEasy1.setSelected(false);
        levelMedium1.setSelected(false);
        levelHard1.setSelected(false);
        levelEasy2.setSelected(false);
        levelMedium2.setSelected(false);
        levelHard2.setSelected(false);
    }
}
