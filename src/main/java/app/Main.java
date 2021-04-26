package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("m.fxml"));
        Parent root = fxmlLoader.load();
        //((MainCtrl) fxmlLoader.getController()).setStage(primaryStage);
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Planning");
        primaryStage.getIcons().add(new Image("app/steps.png"));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
