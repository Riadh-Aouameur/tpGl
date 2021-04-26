package app;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import model.Pert;

import java.io.IOException;

public  class MyListCell_2 extends ListCell<ThreeString> {
    @Override
    protected void updateItem(ThreeString item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty)

            setGraphic(null);
        else {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("list_2.fxml"));

            loader.setControllerFactory(c -> {
                return new listCtrl_2(item);
            });
            VBox Box =new VBox();
            Button btn1 = new Button();
            Button btn2 = new Button();
   //         btn.getStylesheets().add("Css/button1.css");




            try {
                setGraphic(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        setText("");
    }



}
