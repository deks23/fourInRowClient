package pl.damiankotynia.fourinrow.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import pl.damiankotynia.fourinrow.client.MainApp;

import java.util.ArrayList;
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
        buttons = new ArrayList<>();
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        buttons.add(button5);
        buttons.add(button6);
        buttons.add(button7);
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;


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

    @FXML
    public void button1Click(){
        System.out.println(button1.getText());
    }

    @FXML
    public void button2Click(){

    }

    @FXML
    public void button3Click(){

    }

    @FXML
    public void button4Click(){

    }

    @FXML
    public void button5Click(){

    }

    @FXML
    public void button6Click(){

    }

    @FXML
    public void button7Click(){

    }
}
