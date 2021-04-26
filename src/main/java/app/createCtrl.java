package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Pert;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class createCtrl implements Initializable {
    public TextField nameFil;
    public TextField dateFil;
    public TextField ta;
    public TextField du;
    public TextField an;
    public ListView <ThreeString>lv;
    ObservableList<ThreeString> observableList = FXCollections.observableArrayList();
    public Button btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lv.setItems(observableList);
        lv.setCellFactory(param -> new MyListCell_2());
        dateFil.setText(LocalDate.now().toString());

        btn.setOnAction(new EventHandler() {

            @Override
            public void handle(Event event) {

                if(!(ta.getText().isEmpty()||du.getText().isEmpty()||an.getText().isEmpty())){
                    ThreeString s= new ThreeString(ta.getText(),du.getText(),an.getText()) ;
                    observableList.add(s);

                }

            }


        });







    }

    public void onSave(ActionEvent actionEvent) {
        if(!(nameFil.getText().isEmpty()||lv.getItems().isEmpty())){
            String tasks ="";
            for (ThreeString threeString :observableList){
                tasks+=threeString.getT1()+"|"+threeString.getT2()+"|"+threeString.getT3()+"\n";

            }

            System.out.println(tasks);


            Pert pert=new Pert(nameFil.getText(),tasks);
            Db db = new Db();
            db.InsertPert(pert);


        }

    }
}
