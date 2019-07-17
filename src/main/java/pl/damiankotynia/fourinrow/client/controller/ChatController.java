package pl.damiankotynia.fourinrow.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import pl.damiankotynia.fourinrow.client.MainApp;
import pl.damiankotynia.fourinrow.client.service.Utils;
import pl.damiankotynia.fourinrow.model.MessageRequest;
import pl.damiankotynia.fourinrow.model.Request;
import pl.damiankotynia.fourinrow.model.RequestType;

public class ChatController {

    private StringBuilder messageViewBuilder;
    @FXML
    private TextArea chatOutput;

    @FXML
    private TextField chatInput;

    @FXML
    private Button send;

    @FXML
    private Button findGame;

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

    @FXML
    public void findGame() {
        disableFindGameButton();
        Request request = new Request(mainApp.getPlayer());
        request.setRequestType(RequestType.FIND_GAME);
        mainApp.getClient().getOutboundConnection().writeObject(request);
        disableFindGameButton();
        updateMessages(Utils.WAITING_FOR_OPPONENT);
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


    public void disableFindGameButton(){
        findGame.setDisable(true);
    }

    public void enableFindGameButton(){
        findGame.setDisable(false);
    }

    public void cleanChatWindow(){
        messageViewBuilder = new StringBuilder();
        chatOutput.clear();
    }

}
