package pl.damiankotynia.fourinrow.client;

import pl.damiankotynia.fourinrow.client.connector.OutboundConnection;
import pl.damiankotynia.fourinrow.client.connector.ResponseListener;

import java.io.IOException;

public class Client implements Runnable {
    private boolean isRunning;
    private OutboundConnection outboundConnection;
    private ResponseListener responseListener;
    private int port = 4444;
    private String serverAddress = "localhost";

    public Client(){
        this.isRunning = true;
        try {
            outboundConnection = new OutboundConnection(port, serverAddress);
            outboundConnection.run();
            responseListener = outboundConnection.getResponseListener();
        } catch (IOException e) {
            this.isRunning = false;
            System.out.println("Nie można połączyć z serwerem. Spróbuj później.");
        }
    }

    @Override
    public void run() {
        while (isRunning) {


        }
    }
    public boolean isRunning() {
        return isRunning;
    }
}
