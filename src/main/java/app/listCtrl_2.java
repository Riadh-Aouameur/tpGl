package app;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.Pert;

import java.net.URL;
import java.util.ResourceBundle;

public class listCtrl_2 implements Initializable {

    public Label l1;
    public Label l2;
    public Label l3;
ThreeString threeString;

    public listCtrl_2(ThreeString threeString) {
        this.threeString = threeString;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        l1.setText(threeString.getT1());
        l2.setText(threeString.getT2());
        l3.setText(threeString.getT3());


    }


}
