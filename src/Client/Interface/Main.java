package Client.Interface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("vueClient.fxml"));
        Controller controller=new Controller();
        primaryStage.setTitle("Client POP3");
        primaryStage.setScene(new Scene(root, 560, 390));
        //controller.getList().setDisable(true);
        //controller.list.setDisable(true);
        controller.retr.setDisable(true);
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
