package pl.damiankotynia.fourinrow.client.connector;



import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import static pl.damiankotynia.fourinrow.client.service.Utils.INBOUND_CONNECTION_LOGGER;

public class ResponseListener implements Runnable {
    private ObjectInputStream inputStream;
    private boolean isRunning;

    public ResponseListener(ObjectInputStream inputStream) {
        this.inputStream = inputStream;
        this.isRunning = true;
    }

    @Override
    public void run() {
        while (isRunning) {



            try {

                inputStream.readObject();
                System.out.println(INBOUND_CONNECTION_LOGGER + " recieved object ");

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



}
