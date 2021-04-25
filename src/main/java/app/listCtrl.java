package app;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.Pert;


import java.net.URL;
import java.util.ResourceBundle;

public class listCtrl implements Initializable {

    public Label l1;
    public Label l2;
    public Label l3;
Pert pert ;
    public listCtrl(Pert pert) {
this.pert = pert;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        l1.setText("ID : "+pert.getId());
        l2.setText("Name : "+pert.getName());


    }


}
