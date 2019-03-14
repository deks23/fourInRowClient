package pl.damiankotynia.fourinrow.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;
import pl.damiankotynia.fourinrow.client.MainApp;
import pl.damiankotynia.fourinrow.model.MessageRequest;
import pl.damiankotynia.fourinrow.model.RequestType;

public class ChatController {

    private StringBuilder messageViewBuilder;
    @FXML
    private TextArea chatOutput;

    @FXML
    private TextField chatInput;

    @FXML
    private Button send;


    private MainApp mainApp;

    public ChatController(){
        messageViewBuilder = new StringBuilder();

    }

    @FXML
    private void initialize(){
        chatInput.setText(messageViewBuilder.toString());
    }

    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }


    @FXML
    public void sendMessage(){
        String message = chatInput.getCharacters().toString();
        if(!message.isEmpty()){
            chatInput.clear();
            messageViewBuilder.append(message);
            messageViewBuilder.append("\n");
            chatOutput.setText(messageViewBuilder.toString());
            MessageRequest messageRequest = new MessageRequest(mainApp.getPlayer());
            messageRequest.setRequestType(RequestType.MESSAGE);
            messageRequest.setMessage(message);

            mainApp.getClient().getOutboundConnection().writeObject(messageRequest);
        }
    }

    public void updateMessages(String message){
        messageViewBuilder.append(message);
        messageViewBuilder.append("\n");
        chatOutput.setText(messageViewBuilder.toString());
    }

    public void disableSendMessageButton(){
        send.setDisable(true);
    }

    public void enableSendMessageButton(){
        send.setDisable(false);
    }

    public void cleanChatWindow(){
        messageViewBuilder = new StringBuilder();
        chatOutput.clear();
    }


}
