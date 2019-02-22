package pl.damiankotynia.fourinrow.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;
import pl.damiankotynia.fourinrow.client.MainApp;
import pl.damiankotynia.fourinrow.model.MessageRequest;
import pl.damiankotynia.fourinrow.model.RequestType;

public class ChatController {

    @FXML
    private TextFlow chatOutput;

    @FXML
    private TextField chatInput;

    @FXML
    private Button send;


    private MainApp mainApp;

    public ChatController(){
    }

    @FXML
    private void initialize(){

    }

    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }


    @FXML
    public void sendMessage(){
        String message = chatInput.getCharacters().toString();
        if(!message.isEmpty()){
            chatInput.clear();
            MessageRequest messageRequest = new MessageRequest(mainApp.getPlayer());
            messageRequest.setRequestType(RequestType.MESSAGE);
            messageRequest.setMessage(message);
            mainApp.getClient().getOutboundConnection().writeObject(messageRequest);
        }
    }


}
