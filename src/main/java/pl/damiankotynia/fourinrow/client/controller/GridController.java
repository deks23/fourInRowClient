package pl.damiankotynia.fourinrow.client.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import pl.damiankotynia.fourinrow.client.MainApp;
import pl.damiankotynia.fourinrow.client.exceptions.FullColumnException;
import pl.damiankotynia.fourinrow.client.service.MoveService;
import pl.damiankotynia.fourinrow.model.GameField;
import pl.damiankotynia.fourinrow.model.MoveRequest;
import pl.damiankotynia.fourinrow.model.RequestType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GridController {

    private List<Button> buttons;
    private MainApp mainApp;
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Button button4;
    @FXML
    private Button button5;
    @FXML
    private Button button6;
    @FXML
    private Button button7;

    @FXML
    private GridPane gridPane;

    @FXML
    private void initialize(){

    }

    public GridController() {

    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        updateGrid(new GameField());
    }

    private void performMove(int column){
        MoveService moveService =  mainApp.getMoveService();
        try {
            int index = moveService.getFirstAvalibleSlot(column);
            GameField gameBoard = moveService.performMove(index, column);
            MoveRequest moveRequest = new MoveRequest(mainApp.getPlayer());
            moveRequest.setGameField(gameBoard);
            moveRequest.setRequestType(RequestType.MOVE);
            mainApp.getClient().getOutboundConnection().writeObject(moveRequest);
            disableButtons();
            updateGrid(gameBoard);
        } catch (FullColumnException e) {
            e.printStackTrace();
        }

        System.out.println("move" + column);
    }

    public void initButtons(){
        buttons = new ArrayList<>();
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        buttons.add(button5);
        buttons.add(button6);
        buttons.add(button7);
        updateGrid(new GameField());
        buttons.stream().forEach(button -> {
            button.setOnAction(event -> {
                Button clickedButton = (Button)event.getSource();
                performMove(Integer.valueOf(clickedButton.getText())-1);
            });
        });

    }
    public void disableButtons(){
        for(Button button : buttons){
            button.setDisable(true);

        }
    }

    public void enableButtons(){
        for(Button button : buttons){
            button.setDisable(false);
        }
    }

    public void updateGrid(GameField gameField){
        int playerSign;
        playerSign = mainApp.getPlayer().getPlayerSign();

        int[][] gameBoard = gameField.getGameBoard();

        for(int i = 0; i < gameField.getHeight();i++){
            for(int j = 0; j < gameField.getWidth(); j++){

                Canvas canvas = new Canvas(gridPane.getWidth()/7, gridPane.getHeight()/7);
                GraphicsContext gc = canvas.getGraphicsContext2D();
                if(gameBoard[i][j]==0){
                    gc.setFill(Color.WHITE);
                } else if(gameBoard[i][j] == playerSign){
                    gc.setFill(Color.GREEN);
                }else{
                    gc.setFill(Color.RED);
                }
                gc.fillRect(0,0, gridPane.getWidth()/7, gridPane.getHeight()/7);
                gridPane.add(canvas,j,i);
            }
        }



    }
}
