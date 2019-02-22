package pl.damiankotynia.fourinrow.client;

import pl.damiankotynia.fourinrow.client.connector.OutboundConnection;
import pl.damiankotynia.fourinrow.client.connector.ResponseListener;
import pl.damiankotynia.fourinrow.model.MessageRequest;
import pl.damiankotynia.fourinrow.model.RequestType;

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
        MessageRequest qwe = new MessageRequest();
        qwe.setMessage("qwe");
        qwe.setRequestType(RequestType.MESSAGE);
        outboundConnection.writeObject(qwe);
        while (isRunning) {


        }
    }
    public boolean isRunning() {
        return isRunning;
    }
}
