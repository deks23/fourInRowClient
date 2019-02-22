package pl.damiankotynia.fourinrow.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    private AnchorPane gridView;
    private AnchorPane chatView;

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
            rootLayout.setLeft(chatView);
            rootLayout.setRight(gridView);
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

         /*   userListController = loader2.getController();
            userListController.setMainApp(this);
            userListController.setTableData(getUserList());*/

            FXMLLoader loader2 = new FXMLLoader();
            loader2.setLocation(MainApp.class.getResource("/Chat.fxml"));
            chatView = (AnchorPane) loader2.load();
            chatView = loader2.getController();

           /* userListController = loader2.getController();
            userListController.setMainApp(this);
            userListController.setTableData(getUserList());*/


        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public Stage getPrimaryStage(){
        return primaryStage;
    }
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Four in row ");
        loadFXML();
        initRootLayout();
    }
}
