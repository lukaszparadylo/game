import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import pl.lukaszparadylo.board.Board;
import pl.lukaszparadylo.data.InputData;
import pl.lukaszparadylo.gamers.Gamer;
import pl.lukaszparadylo.gamers.Robot2;
import pl.lukaszparadylo.judge.Judge2;
import pl.lukaszparadylo.levels.Level;
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
    private Judge2 judge2;
    private Board board;
    private Gamer gamer1, gamer2;
    private Robot2 robot1, robot2;
    private Integer scoreGamer1int=0, scoreGamer2int=0, roundNumberInt = 1, boardSize;
    private List<Robot2> robotsList;
    private List<Gamer> gamersList = new ArrayList<>();
    public void startGame(){
        roundNumber.setText(roundNumberInt.toString());
        if(boardSizeChoiceBox.getValue()==null)
            infoLabel.setText(InputData.SET_BOARD_SIZE);
        else if(choiceBoxGamer1.getValue()==null||choiceBoxGamer2.getValue()==null)
            infoLabel.setText(InputData.SELECT_GAMER_TYPE);
        else if((xShape1.isSelected()||!oShape1.isSelected())&& (xShape2.isSelected()||!oShape2.isSelected()))
            infoLabel.setText(InputData.CHOOSE_MARK_FOR_GAMER);
        else if(!(levelEasy1.isSelected()||levelMedium1.isSelected()||levelHard1.isSelected())
                &&choiceBoxGamer1.getValue()==InputData.ROBOT) infoLabel.setText(InputData.CHOOSE_ROBOT_LEVEL);
        else if(!(levelEasy2.isSelected()||levelMedium2.isSelected()||levelHard2.isSelected())&&
            choiceBoxGamer2.getValue()==InputData.ROBOT) infoLabel.setText(InputData.CHOOSE_ROBOT_LEVEL);
        else {
            Character gamer1Mark;
            Character gamer2Mark;
            Integer robot1Level = Level.LOW;
            Integer robot2Level = Level.LOW;
            boardSize = Integer.parseInt(boardSizeChoiceBox.getValue().toString().substring(0,1));
            board = new Board(boardSize);
            judge2 = new Judge2(board);
            enableButtons();
            if(xShape1.isSelected()) gamer1Mark= InputData.MARK_X;
            else gamer1Mark=InputData.MARK_O;
            if (xShape2.isSelected()) gamer2Mark=InputData.MARK_X;
            else gamer2Mark=InputData.MARK_O;
            if(choiceBoxGamer1.getValue()==InputData.ROBOT&&levelHard1.isSelected()) robot1Level=Level.LOW;
            if(choiceBoxGamer1.getValue()==InputData.ROBOT&&levelMedium1.isSelected()) robot1Level=Level.MEDIUM;
            if(choiceBoxGamer1.getValue()==InputData.ROBOT&&levelEasy1.isSelected()) robot1Level=Level.HIGH;
            if(choiceBoxGamer2.getValue()==InputData.ROBOT&&levelHard2.isSelected()) robot1Level=Level.LOW;
            if(choiceBoxGamer2.getValue()==InputData.ROBOT&&levelMedium2.isSelected()) robot1Level=Level.MEDIUM;
            if(choiceBoxGamer2.getValue()==InputData.ROBOT&&levelEasy2.isSelected()) robot1Level=Level.HIGH;
            if(choiceBoxGamer1.getValue()==InputData.ROBOT) {
                robot1 = new Robot2(gamer1Mark,robot1Level);
                robot1.setNextMove(true);
            }
            if (choiceBoxGamer1.getValue()==InputData.GAMER) {
                gamer1 = new Gamer(textFieldGamer1.getText(),gamer1Mark);
                gamer1.setNextMove(true);
            }
            if(choiceBoxGamer2.getValue()==InputData.ROBOT) {
                robot2 = new Robot2(gamer2Mark,robot2Level);
            }
            if (choiceBoxGamer2.getValue()==InputData.GAMER) {
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
            gameSequenceDependsOfPlayersCombination();
            disableStartControls();
            start.setDisable(true);
        }
    }
    private void gameSequenceDependsOfPlayersCombination(){
        if(gamer1!=null&&gamer2!=null){
            nextMove.setDisable(true);
            enableButtons();
        }
        if(gamer1!=null&&robot2!=null){
            nextMove.setDisable(true);
            enableButtons();
        }
        if(gamer2!=null&&robot1!=null){
            nextMoveFinishGame();
        }
        if(robot1!=null&&robot2!=null){
            disableButtons();
            nextMoveFinishGame();
        }
    }
    public void insertDataToBoard(ActionEvent event){
        Integer s = Integer.parseInt(event.getSource().toString().substring(11,13));
        for (Gamer gamer : gamersList) {
            if(gamer!=null&&gamer.getNextMove()){
                board.setFields(s,gamer.getChosenMark());
                enableButtons();
                if(judge2.checkWhoWon().equals(gamer.getChosenMark().toString()))
                    theWinner(judge2.checkWhoWon());
                if(board.getMoveNumber()>=boardSize*boardSize) draw();
            }
        }
        if(gamer1!=null^gamer2!=null){
            nextMove.setDisable(false);
            disableButtons();
        }
        nexMoveFinishGameInsertDataToBoardSequence();
    }
    public void nextMoveFinishGame(){
        nextMove.setDisable(false);
        for (Robot2 robot : robotsList) {
            if(robot!=null&&robot.getNextMove()){
                robot.makeMove(board);
                disableButtons();
                nextMove.setDisable(false);
                if(judge2.checkWhoWon().equals(robot.getChosenMark().toString()))
                    theWinner(judge2.checkWhoWon());
                if(board.getMoveNumber()>=boardSize*boardSize)
                    draw();
                break;
            }
        }
        if(gamer1!=null^gamer2!=null){
            nextMove.setDisable(true);
            enableButtons();
        }
        nexMoveFinishGameInsertDataToBoardSequence();
    }
    private void nexMoveFinishGameInsertDataToBoardSequence(){
        moveToggle();
        board.incrementMoveNumber();
        actualPointScoreRoundNumber();
        insertDataFromBoardToScreen();
        if(!judge2.checkWhoWon().equals(InputData.DRAW))
            theWinner(judge2.checkWhoWon());
        if(board.getMoveNumber()>=boardSize*boardSize)
            draw();
    }
    public void setNewGame(){
        gamer1=null;
        gamer2=null;
        robot1=null;
        robot2=null;
        roundNumberInt++;
        uncheckControls();
        enableStartControls();
        hideButtons();
        start.setDisable(false);
        winnerLabel.setText("");
        board.resetMoveNumber();
        scoreGamer1int=0;
        scoreGamer2int=0;
        roundNumberInt=1;
        actualPointScoreRoundNumber();
    }
    public void resetButton(){
        roundNumberInt++;
        actualPointScoreRoundNumber();
        hideButtons();
        start.setDisable(false);
        winnerLabel.setText("");
        enableButtons();
        start.setDisable(true);
        board.resetMoveNumber();
        moveNumber.setText(board.getMoveNumber().toString());
        startGame();
    }
    private void draw(){
        hideButtons();
        winnerLabel.setText(InputData.DRAW.toUpperCase());
        nextMove.setDisable(true);
        board.resetMoveNumber();
    }
    private void theWinner(String winnChar){
        String winner="";
        if((oShape1.isSelected()&&winnChar.equals(InputData.MARK_O.toString()))||
                (xShape1.isSelected()&&winnChar.equals(InputData.MARK_X.toString()))){
            scoreGamer1int++;
            winner=textFieldGamer1.getText();
        }
        if((oShape2.isSelected()&&winnChar.equals(InputData.MARK_O.toString()))||
                (xShape2.isSelected()&&winnChar.equals(InputData.MARK_X.toString()))){
            scoreGamer2int++;
            winner=textFieldGamer2.getText();
        }
        actualPointScoreRoundNumber();
        hideButtons();
        winnerLabel.setText("The winner is "+winner);
        nextMove.setDisable(true);
        board.resetMoveNumber();
    }
    private void actualPointScoreRoundNumber(){
        scoreGamer1.setText(scoreGamer1int.toString());
        scoreGamer2.setText(scoreGamer2int.toString());
        roundNumber.setText(roundNumberInt.toString());
        moveNumber.setText(board.getMoveNumber().toString());
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
            if(buttonsList.get(i).getText().equals(InputData.MARK_X.toString())||buttonsList.get(i).getText().equals(InputData.MARK_O.toString())){
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
        list.add(InputData.ROBOT);
        list.add(InputData.GAMER);
        choiceBoxGamer1.setItems(FXCollections.observableList(list));
        updateRobotLevelFields();
    }
    public void setGamer2Type(){
        List<String> list = new ArrayList<>();
        list.add(InputData.ROBOT);
        list.add(InputData.GAMER);
        choiceBoxGamer2.setItems(FXCollections.observableList(list));
        updateRobotLevelFields();
    }
    public void setBoardSize(){
        List<String> list = new ArrayList<>();
        list.add(InputData.BOARD3x3);
        list.add(InputData.BOARD4x4);
        list.add(InputData.BOARD5x5);
        boardSizeChoiceBox.setItems(FXCollections.observableList(list));
        updateRobotLevelFields();
    }
    private void updateRobotLevelFields(){
        if(choiceBoxGamer1.getValue()==InputData.ROBOT) enableRobotLevel1();
        else disableRobotLevel1();
        if(choiceBoxGamer2.getValue()==InputData.ROBOT) enableRobotLevel2();
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
        textFieldGamer1.setText(InputData.GAMER+" 1");
        textFieldGamer2.setText(InputData.GAMER+" 2");
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
