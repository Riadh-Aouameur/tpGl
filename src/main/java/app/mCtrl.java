package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class mCtrl implements Initializable {

    public void onCreate(ActionEvent actionEvent) {


    }

    public void onLoad(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("list.fxml"));
        AnchorPane root = fxmlLoader.load();
        // ((MainCtrl) fxmlLoader.getController()).setStage(primaryStage);
         stage.setScene(new Scene(root));
        stage.setTitle("Planification");
        stage.show();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {




    }
}
