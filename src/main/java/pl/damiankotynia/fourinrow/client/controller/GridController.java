package pl.damiankotynia.fourinrow.client.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import pl.damiankotynia.fourinrow.client.MainApp;

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


    }

    private void performMove(int row){
        //TODO wykonanie ruchu
        System.out.println("move" + row);
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

        buttons.stream().forEach(button -> {
            button.setOnAction(event -> {
                Button clickedButton = (Button)event.getSource();
                performMove(Integer.valueOf(clickedButton.getText()));
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


}
