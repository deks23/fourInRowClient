package pl.damiankotynia.fourinrow.client.connector;

import pl.damiankotynia.fourinrow.client.MainApp;
import pl.damiankotynia.fourinrow.model.MessageResponse;
import pl.damiankotynia.fourinrow.model.Response;

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

                        break;

                    case OPONENT_DISCONECTED:
                        mainApp.chatController.disableSendMessageButton();
                        mainApp.gridController.disableButtons();
                        mainApp.chatController.updateMessages("Oponent disconnected");
                        //TODO aktywacja przycisku szukaj przeciwnika
                        break;

                    case START:
                        mainApp.chatController.cleanChatWindow();
                        mainApp.chatController.enableSendMessageButton();
                        mainApp.gridController.enableButtons();
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
