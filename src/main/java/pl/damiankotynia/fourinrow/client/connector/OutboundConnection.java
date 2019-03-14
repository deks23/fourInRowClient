package pl.damiankotynia.fourinrow.client.connector;

import pl.damiankotynia.fourinrow.model.Request;

import java.io.*;
import java.net.Socket;


        import java.io.*;
        import java.net.Socket;

        import static pl.damiankotynia.fourinrow.client.service.Utils.OUTBOUND_CONNECTION_LOGGER;

public class OutboundConnection {

    private Socket socket;
    private int port;
    private String serverAddress;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private boolean isRunning;
    private ResponseListener responseListener;

    /**
     * Constructor for OutboundConnection class
     * @param port output connection port
     * @param serverAddress server adress
     * @throws IOException
     */
    public OutboundConnection(int port, String serverAddress) throws IOException {
        socket = new Socket(serverAddress, port);
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
        this.isRunning = true;
    }

    public void run() {
        responseListener = new ResponseListener(inputStream);
        new Thread(responseListener).start();
    }

    /**
     * Write object on output stream
     * @param object object to write
     * @return  return true if object has been written properly
     */

    public synchronized boolean writeObject(Object object){
        try {
            outputStream.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(OUTBOUND_CONNECTION_LOGGER + " sending " + ((Request)object).getRequestType());
        try {
            outputStream.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ResponseListener getResponseListener() {
        return responseListener;
    }
}

