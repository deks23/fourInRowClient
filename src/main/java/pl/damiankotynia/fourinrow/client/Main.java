package pl.damiankotynia.fourinrow.client;

public class Main {
    public static void main(String []args){
        Client client = new Client();
        new Thread(client).start();
    }
}
