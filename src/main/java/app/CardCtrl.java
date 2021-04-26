package app;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Tache;

public class CardCtrl {
    @FXML
    private Label idL;

    @FXML
    private Label dureeL;

    @FXML
    private Label mlL;

    @FXML
    private Label ftoL;

    @FXML
    private Label dtaL;

    @FXML
    private Label ftaL;

    @FXML
    private Label dtoL;

    @FXML
    private Label mtL;


    public void setTache(Tache t) {
        idL.setText(t.getId());
        dureeL.setText(String.valueOf(Math.round(t.getDuree())) + " jours");
//        dtoL.setText(String.valueOf(Math.round(t.getDto())));
//        dtaL.setText(String.valueOf(Math.round(t.getDta())));
//        ftaL.setText(String.valueOf(Math.round(t.getFta())));
//        ftoL.setText(String.valueOf(Math.round(t.getFto())));
//        mlL.setText(String.valueOf(Math.round(t.getMl())) + " jours");
//        mtL.setText(String.valueOf(Math.round(t.getMt())) + " jours");

        if (t.getMt() == 0) {
            idL.setTextFill(Color.web("#e53935"));
        }

    }

    public void onClick(MouseEvent mouseEvent) {
        //TODO
        Stage stage = new Stage();
        stage.show();

    }
}
