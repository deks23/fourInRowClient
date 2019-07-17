package pl.damiankotynia.fourinrow.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.damiankotynia.fourinrow.client.controller.ChatController;
import pl.damiankotynia.fourinrow.client.controller.GridController;
import pl.damiankotynia.fourinrow.client.service.MoveService;
import pl.damiankotynia.fourinrow.model.*;

import java.io.IOException;

public class MainApp extends Application {

    private Player player;
    private Stage primaryStage;
    private BorderPane rootLayout;

    private AnchorPane gridView;
    private AnchorPane chatView;

    public  ChatController chatController;
    public GridController gridController;
    private Client client;
    private MoveService moveService;

    public static void main(String[] args) {
        launch(args);
    }

    public void initRootLayout (){
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setMaxHeight(550);
            primaryStage.setMinHeight(550);
            primaryStage.setMaxWidth(900);
            primaryStage.setMinWidth(900);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   public void loadFXML (){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/Grid.fxml"));
            gridView = (AnchorPane) loader.load();

            gridController = loader.getController();
            gridController.setMainApp(this);

            FXMLLoader loader2 = new FXMLLoader();
            loader2.setLocation(MainApp.class.getResource("/Chat.fxml"));
            chatView = (AnchorPane) loader2.load();
            chatController = loader2.getController();
            chatController.setMainApp(this);
            chatController.disableSendMessageButton();
            chatController.disableSendMessageButton();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showChat(){
        rootLayout.setCenter(chatView);
    }


    public void showGame() {
        rootLayout.setLeft(gridView);
        gridController.initButtons();
    }
        public Stage getPrimaryStage(){
        return primaryStage;
    }

    public Player getPlayer() {
        return player;
    }

    public void setChatController(ChatController chatController) {
        this.chatController = chatController;
    }

    public ChatController getChatController() {
        return chatController;
    }

    public Client getClient() {
        return client;
    }

    public MoveService getMoveService() {
        return moveService;
    }

    public void setMoveService(MoveService moveService) {
        this.moveService = moveService;
    }

    @Override
    public void start(Stage primaryStage) {
        player = new Player();
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Four in row ");
        new Thread(client).start();

        initRootLayout();
        loadFXML();
        showChat();
        showGame();
        gridController.disableButtons();


        client = new Client();
        client.getOutboundConnection().getResponseListener().setMainApp(this);
        primaryStage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });
    }
}
