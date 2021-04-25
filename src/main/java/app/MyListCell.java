package app;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import model.Pert;

import java.io.IOException;

public  class MyListCell extends ListCell<Pert> {
    @Override
    protected void updateItem(Pert item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty)

            setGraphic(null);
        else {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("list.fxml"));

            loader.setControllerFactory(c -> {
                return new listCtrl(item);
            });
            VBox Box =new VBox();
            Button btn1 = new Button();
            Button btn2 = new Button();
   //         btn.getStylesheets().add("Css/button1.css");


            btn1.setOnAction(e->{
                getListView().getItems().remove(item);
            });


            btn2.setOnAction(e->{
                getListView().getItems().remove(item);
            });


            try {
                setGraphic(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        setText("");
    }



}
