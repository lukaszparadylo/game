package pl.lukaszparadylo.trash;

import com.sun.javafx.event.EventHandlerManager;
import com.sun.javafx.event.EventUtil;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import pl.lukaszparadylo.board.Board;
import pl.lukaszparadylo.gamers.Gamer;
import pl.lukaszparadylo.gamers.Robot2;
import pl.lukaszparadylo.judge.Judge2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class GameView {
    @FXML Button _1_5,_2_5,_3_5,_4_5,_5_5,_6_5,_7_5,_8_5,_9_5,_10_5,_11_5,_12_5,_13_5,_14_5,
            _15_5,_16_5,_17_5,_18_5,_19_5,_20_5,_21_5,_22_5,_23_5,_24_5,_25_5;
    @FXML Button _1_4,_2_4,_3_4,_4_4,_5_4,_6_4,_7_4,_8_4,_9_4,_10_4,_11_4,_12_4,_13_4,_14_4,
            _15_4,_16_4;
    @FXML Button _1_3,_2_3,_3_3,_4_3,_5_3,_6_3,_7_3,_8_3,_9_3;
    @FXML Button start, nextMove, restart, newGame;
    @FXML ChoiceBox choiceBoxGamer1, choiceBoxGamer2, boardSizeChoiceBox;
    @FXML TextField textFieldGamer1,textFieldGamer2;
    @FXML AnchorPane anchorPane;
    @FXML CheckBox xShape, oShape, levelEasy, levelMedium, levelHard, levelEasy2, levelMedium2, levelHard2;
    @FXML Label infoLabel;

    public static final String board3x3 = "3x3";
    public static final String board5x5 = "5x5";
    public static final String board4x4 = "4x4";
    public static final String GAMER = "Gamer";
    public static final String ROBOT = "Robot";
    private List<Integer> tabAndPosition = new ArrayList<>();
    private Judge2 judge2;
    private Board board;
    private Gamer gamer1;
    private Gamer gamer2;
    private Robot2 robot;
    private Robot2 robot2;

    public void setNextMove(){
        nextMove.setDisable(true);
        if(board.getMoveNumber()%2==0&&gamer1!=null&&!tabAndPosition.isEmpty()) {
            infoLabel.setText(textFieldGamer1.getText()+" move");
            gamer1.makeMove(board,tabAndPosition.get(0));
            board.incrementMoveNumber();
        }
        else if(gamer2!=null&&!tabAndPosition.isEmpty()){
            infoLabel.setText(textFieldGamer2.getText()+" move");
            gamer2.makeMove(board,tabAndPosition.get(0));
            board.incrementMoveNumber();
        }

        String winner = judge2.checkWhoWon();
        if(gamer1!=null && winner.equals(gamer1.getChosenMark().toString())){
            afterWonSequence(gamer1.getName());
        }
        if(gamer2!=null && winner.equals(gamer2.getChosenMark().toString())){
            afterWonSequence(gamer2.getName());
        }
        if(robot!=null && winner.equals(robot.getChosenMark().toString())){
            afterWonSequence(robot.getName());
        }
        if(robot2!=null && winner.equals(robot2.getChosenMark().toString())){
            afterWonSequence(robot2.getName());
        }
        System.out.println(judge2.checkWhoWon());
        board.showBoard();
        System.out.println(board.getMoveNumber());

    }
    public void afterWonSequence(String gamerName){
        infoLabel.setText("The winner is "+gamerName);
        disableButtons();
        nextMove.setDisable(true);
    }
    public void setStartButton(){
        System.out.println(choiceBoxGamer1.getValue());
        if(choiceBoxGamer1.getValue()!=null && choiceBoxGamer2.getValue()!=null&&boardSizeChoiceBox.getValue()!=null){
            hideFieldsGamer1();
            hideFieldsGamer2();
            hideShapeFields();
            hideBoardSize();
            hideRobotLevel();
            showMainButtons();
            hideButtons();
            disableLevelButtonsRobot2();
            if(boardSizeChoiceBox.getValue()==board3x3){
                board = new Board(3);
                showBoard(3);
            }
            if(boardSizeChoiceBox.getValue()==board4x4){
                board = new Board(4);
                showBoard(4);
            }
            if(boardSizeChoiceBox.getValue()==board5x5){
                board= new Board(5);
                showBoard(5);
            }
            judge2 = new Judge2(board);
            if(choiceBoxGamer1.getValue()==ROBOT) robot = new Robot2(getMark(),getRobotLevel());
            if(choiceBoxGamer2.getValue()==ROBOT) robot2 = new Robot2(getMark(),getRobotLevel());
            if(choiceBoxGamer1.getValue()==GAMER) gamer1 = new Gamer(textFieldGamer1.getText(),getMark());
            if(choiceBoxGamer2.getValue()==GAMER) gamer2 = new Gamer(textFieldGamer2.getText(),getSecondMark());
            setNextMove();
            infoLabel.setText("");
            start.setDisable(true);
        }else {
            infoLabel.setText("Insert gamers type or board size");
            infoLabel.setTextFill(Color.RED);
        }

    }
    public Character getMark(){
        if(xShape.isSelected())return 'X';
        if(xShape.isSelected())return 'O';
        else return '_';
    }
    public Character getSecondMark(){
        if(getMark()=='X')return 'O';
        else return 'X';
    }
    public Integer getRobotLevel(){
        if(levelEasy.isSelected())return 1;
        if(levelMedium.isSelected())return 2;
        if(levelHard.isSelected())return 3;
        else return 0;
    }
    public void resetFieldsValues(){
        choiceBoxGamer1.setValue(null);
        choiceBoxGamer2.setValue(null);
        textFieldGamer1.setText("Gamer 1");
        textFieldGamer2.setText("Gamer 2");
        xShape.setSelected(false);
        oShape.setSelected(false);
        levelEasy.setSelected(false);
        levelMedium.setSelected(false);
        levelHard.setSelected(false);
        boardSizeChoiceBox.setValue(null);
    }
    public void calculate(ActionEvent event){
        if(nextMove.isDisable()) {
            String s = event.getSource().toString();
            tabAndPosition = Stream.of(s.split("(_)|(,)"))
                    .filter(n -> !n.startsWith("Button"))
                    .filter(m -> !m.endsWith("'"))
                    .map(h -> Integer.parseInt(h))
                    .collect(Collectors.toList());
            anchorPane.getChildren().stream()
                    .filter(f -> f instanceof Button)
                    .filter(g -> g.getId().contains("_" + tabAndPosition.get(0) + "_" + tabAndPosition.get(1)))
                    .forEach(t -> {
                        if (gamer1 != null && board.getMoveNumber() % 2 == 0) {
                            ((Button) t).setText(gamer1.getChosenMark().toString());
                            ((Button) t).setDisable(true);
                        }
                        if (gamer2 != null && board.getMoveNumber() % 2 != 0) {
                            ((Button) t).setText(gamer2.getChosenMark().toString());
                            ((Button) t).setDisable(true);
                        }
                        if (robot != null && board.getMoveNumber() % 2 == 0) {
                            ((Button) t).setText(robot.getChosenMark().toString());
                            ((Button) t).setDisable(true);
                        }
                        if (robot2 != null && board.getMoveNumber() % 2 != 0) {
                            ((Button) t).setText(robot2.getChosenMark().toString());
                            ((Button) t).setDisable(true);
                        }
                    });
            nextMove.setDisable(false);
        }else{
            infoLabel.setText("First click: Next button");
        }
    }
    public void setNewGameButton(){
        hideMainButtons();
        showFieldsGamer1();
        showShapeFields();
        hideRobotLevel();
        hideBoardSize();
        hideFieldsGamer2();
        resetFieldsValues();
        hideButtons();
    }
    public void showShapeFields(){
        oShape.setDisable(false);
        xShape.setDisable(false);
    }
    public void hideShapeFields(){
        oShape.setDisable(true);
        xShape.setDisable(true);
    }
    public void setLevelEasy(){
        levelMedium.setDisable(true);
        levelHard.setDisable(true);
        if(choiceBoxGamer1.getValue()==ROBOT&&choiceBoxGamer2.getValue()==ROBOT) enableLevelButtonsRobot2();
    }
    public void setLevelMedium(){
        levelEasy.setDisable(true);
        levelHard.setDisable(true);
        if(choiceBoxGamer1.getValue()==ROBOT&&choiceBoxGamer2.getValue()==ROBOT) enableLevelButtonsRobot2();
    }
    public void setLevelHard(){
        levelMedium.setDisable(true);
        levelEasy.setDisable(true);
        if(choiceBoxGamer1.getValue()==ROBOT&&choiceBoxGamer2.getValue()==ROBOT) enableLevelButtonsRobot2();
    }
    public void enableLevelButtonsRobot2(){
        levelEasy2.setDisable(false);
        levelMedium2.setDisable(false);
        levelHard2.setDisable(false);
    }
    public void disableLevelButtonsRobot2(){
        levelEasy2.setDisable(true);
        levelMedium2.setDisable(true);
        levelHard2.setDisable(true);
    }
    public void showMainButtons(){
        start.setDisable(false);
        restart.setDisable(false);
        nextMove.setDisable(false);
    }
    public void hideMainButtons(){
        start.setDisable(true);
        restart.setDisable(true);
        nextMove.setDisable(true);
    }
    public void showBoardSize(){
        boardSizeChoiceBox.setDisable(false);
    }
    public void hideBoardSize(){
        boardSizeChoiceBox.setDisable(true);
    }
    public void showRobotLevel(){
        levelEasy.setDisable(false);
        levelMedium.setDisable(false);
        levelHard.setDisable(false);
    }
    public void hideRobotLevel(){
        levelEasy.setDisable(true);
        levelMedium.setDisable(true);
        levelHard.setDisable(true);
    }
    public void showFieldsGamer2(){
        choiceBoxGamer2.setDisable(false);
        textFieldGamer2.setDisable(false);
    }
    public void hideFieldsGamer2(){
        choiceBoxGamer2.setDisable(true);
        textFieldGamer2.setDisable(true);
    }
    public void showFieldsGamer1(){
        choiceBoxGamer1.setDisable(false);
        textFieldGamer1.setDisable(false);
    }
    public void hideFieldsGamer1(){
        choiceBoxGamer1.setDisable(true);
        textFieldGamer1.setDisable(true);
    }
    public void setChoiceBoxGamer1(){
        List<String> list = new ArrayList<>();
        list.add(ROBOT);
        list.add(GAMER);
        choiceBoxGamer1.setItems(FXCollections.observableList(list));
        if(choiceBoxGamer1.getValue()==ROBOT)showRobotLevel();
    }
    public void setChoiceBoxGamer2(){
        List<String> list = new ArrayList<>();
        list.add(ROBOT);
        list.add(GAMER);
        choiceBoxGamer2.setItems(FXCollections.observableList(list));
        if(choiceBoxGamer2.getValue()==ROBOT)showRobotLevel();
    }
    public void setBoardSizeChoiceBox(){
        List<String> list = new ArrayList<>();
        list.add(board3x3);
        list.add(board4x4);
        list.add(board5x5);
        boardSizeChoiceBox.setItems(FXCollections.observableList(list));
    }
    public void chooseMarkX(){
        if(oShape.isDisable()) {
            oShape.setDisable(false);
            hideFieldsGamer2();
            hideBoardSize();
            hideRobotLevel();
        }
        else {
            oShape.setDisable(true);
            showFieldsGamer2();
            showBoardSize();
            showRobotLevel();
        }
    }
    public void chooseMarkO(){
        if(xShape.isDisable()){
            xShape.setDisable(false);
            hideFieldsGamer2();
            hideBoardSize();
            hideRobotLevel();
        }
        else {
            xShape.setDisable(true);
            showFieldsGamer2();
            showBoardSize();
            showRobotLevel();
        }
    }
    public void field1(ActionEvent event){

    }
    public List<Button> createListOfButton(String suffix){
        List<Button> buttonsList = new ArrayList<>();
        anchorPane.getChildren().stream()
                .filter(f->f instanceof Button)
                .filter((p->p.getId().endsWith(suffix)))
                .forEach(j-> buttonsList.add((Button) j));
        return buttonsList;
    }
    public void showBoard(Integer boardSize){
        if(boardSize<=5&&boardSize>2){
            createListOfButton("_"+boardSize)
                    .stream().forEach(n->n.setVisible(true));
        }
    }
    public void hideButtons(){
        anchorPane.getChildren().stream()
                .filter(f->f instanceof Button)
                .filter((p->p.getId().endsWith("_5")||p.getId().endsWith("_4")||p.getId().endsWith("_3")))
                .forEach(j->j.setVisible(false));
    }
    public void enableButtons(){
        anchorPane.getChildren().stream()
                .filter(f->f instanceof Button)
                .filter((p->p.getId().endsWith("_5")||p.getId().endsWith("_4")||p.getId().endsWith("_3")))
                .forEach(j->j.setDisable(false));
    }
    public void disableButtons(){
        anchorPane.getChildren().stream()
                .filter(f->f instanceof Button)
                .filter((p->p.getId().endsWith("_5")||p.getId().endsWith("_4")||p.getId().endsWith("_3")))
                .forEach(j->j.setDisable(true));
    }

}
