package pl.damiankotynia.fourinrow.client.connector;

import javafx.application.Platform;
import pl.damiankotynia.fourinrow.client.MainApp;
import pl.damiankotynia.fourinrow.client.service.MoveService;
import pl.damiankotynia.fourinrow.client.service.Utils;
import pl.damiankotynia.fourinrow.model.*;

import java.io.IOException;
import java.io.ObjectInputStream;

import static pl.damiankotynia.fourinrow.client.service.Utils.INBOUND_CONNECTION_LOGGER;

public class ResponseListener implements Runnable {
    private ObjectInputStream inputStream;
    private boolean isRunning;
    private MainApp mainApp;

    public ResponseListener(ObjectInputStream inputStream) {
        this.inputStream = inputStream;
        this.isRunning = true;
    }

    @Override
    public void run() {
        Platform.runLater(()->{
            mainApp.gridController.updateGrid(new GameField());
        });
        while (isRunning) {
            try {

                Object  objectResponse = inputStream.readObject();
                Response response = (Response) objectResponse;
                System.out.println(INBOUND_CONNECTION_LOGGER + " recieved object " + response.getResponseStatus());
                switch (response.getResponseStatus()){
                    case MESSAGE:
                        mainApp.getChatController().updateMessages(((MessageResponse)response).getMessage());
                        break;
                    case MOVE:
                        MoveResponse moveResponse = (MoveResponse)response;
                        mainApp.getMoveService().setGameField(moveResponse.getGameField());
                        Platform.runLater(() -> {
                            mainApp.gridController.updateGrid(moveResponse.getGameField());
                            mainApp.gridController.enableButtons();
                        });
                    break;

                    case OPONENT_DISCONECTED:
                        Platform.runLater(()->{
                            mainApp.chatController.enableFindGameButton();
                            mainApp.chatController.disableSendMessageButton();
                            mainApp.gridController.disableButtons();
                            mainApp.chatController.updateMessages(Utils.OPPONENT_DISCONENCTED);
                            mainApp.gridController.updateGrid(new GameField());
                        });
                        break;


                    case WON:
                        Platform.runLater(()->{
                            mainApp.chatController.enableFindGameButton();
                            mainApp.chatController.disableSendMessageButton();
                            mainApp.gridController.disableButtons();
                            mainApp.chatController.updateMessages(Utils.YOU_WON);
                            mainApp.gridController.updateGrid(new GameField());
                        });
                        break;

                    case LOST:
                        Platform.runLater(()->{
                            mainApp.chatController.enableFindGameButton();
                            mainApp.chatController.disableSendMessageButton();
                            mainApp.gridController.disableButtons();
                            mainApp.chatController.updateMessages(Utils.YOU_LOSE);
                            mainApp.gridController.updateGrid(new GameField());
                        });
                        break;

                    case START:
                        Platform.runLater(()->{
                            mainApp.chatController.cleanChatWindow();
                            mainApp.chatController.enableSendMessageButton();
                            mainApp.gridController.enableButtons();
                            mainApp.chatController.updateMessages(">>>>You are player:" + String.valueOf(((StartGameResponse)response).getPlayerNumber()) + "<<<<");
                            mainApp.setMoveService(new MoveService(((StartGameResponse) response).getPlayerNumber()));
                            mainApp.getPlayer().setPlayerSign(((StartGameResponse) response).getPlayerNumber());
                            if(mainApp.getPlayer().getPlayerSign()==2){
                                mainApp.gridController.disableButtons();
                            }
                        });
                        break;

                }


            } catch (IOException e) {
                System.out.println("\n Błąd serwera. Kończę działanie aplikacji \n");
                isRunning ^= true;
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.out.println("\n Niepoprawna odpowiedź serwera. Kończę działanie aplikacji \n");
                isRunning ^= true;
            }
        }
    }

    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }


}
