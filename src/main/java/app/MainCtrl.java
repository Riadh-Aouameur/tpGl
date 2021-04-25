package app;

import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

public class MainCtrl implements Initializable {
    public ListView <Pert>listView;
    @FXML
    private Button openBtn;

    @FXML
    private LineChart<Float, String> lineChart;

    @FXML
    private Button pertBtn;

    @FXML
    private Button ganttBtn;

    @FXML
    private AnchorPane ganttPane;

    @FXML
    private Button durationBtn;

    @FXML
    private ScrollPane scrollPane;

    private AnchorPane pane;

    private Stage stage;
    private Graphe graphe;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


//
//        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
//        transition.setRate(-1);
//        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
//            transition.setRate(transition.getRate() * -1);
//            transition.play();
//
//            if (drawer.isOpened()) {
//                drawer.close();
//            } else {
//                drawer.open();
//            }
//        });


        pertBtn.setOnAction(e -> {
//            ganttBtn.getStyleClass().remove("workBtn");
//            ganttBtn.getStyleClass().add("menuBtn");
//
//            pertBtn.getStyleClass().remove("menuBtn");
//            pertBtn.getStyleClass().add("workBtn");

            scrollPane.setVisible(true);
            ganttPane.setVisible(false);
        });

        ganttBtn.setOnAction(e -> {
//            pertBtn.getStyleClass().remove("workBtn");
//            pertBtn.getStyleClass().add("menuBtn");
//
//            ganttBtn.getStyleClass().remove("menuBtn");
//            ganttBtn.getStyleClass().add("workBtn");

            scrollPane.setVisible(false);
            ganttPane.setVisible(true);

        });

        Db db = new Db();
        listView.setItems(db.getPert());


    }

    @FXML
    private void openClicked(ActionEvent event) throws IOException {
        Pert pert = listView.getSelectionModel().getSelectedItem();



        if (pert.getTasks() != null) {
            ReadFromDataBase readFromDataBase = new ReadFromDataBase(pert.getTasks());

            //TODO
            graphe = readFromDataBase.getGraphe();


            LinkedList<Integer> topSort = graphe.topologicalSort();
            graphe.forwardpass(topSort);
            graphe.backwardPass(topSort);
            Stack<Tache> cPath = graphe.criticalPath(topSort);

            Collections.reverse(topSort);

            Tache keys[] = graphe.getKeys();

            lineChart.getData().clear();

            for (Tache tache : keys) {

                XYChart.Series<Float, String> set = new XYChart.Series<>();
                set.getData().add(new XYChart.Data<>(tache.getDto(), tache.getId()));
                set.getData().add(new XYChart.Data<>(tache.getFto(), tache.getId()));

                lineChart.getData().add(set);
            }

            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);
            hBox.setStyle("-fx-background-color: transparent");
            hBox.setFillHeight(true);
            hBox.setSpacing(40);

            pane = new AnchorPane();
            AnchorPane.setBottomAnchor(hBox, 10.0);
            AnchorPane.setLeftAnchor(hBox, 10.0);
            AnchorPane.setRightAnchor(hBox, 10.0);
            AnchorPane.setTopAnchor(hBox, 10.0);

            LinkedList<Tache>[] niveaux = graphe.getN();

            HashMap<Tache, AnchorPane> cards = new HashMap<>();
            HashMap<Integer, ObjectBinding<Bounds>> niveauPos = new HashMap<>();

            int currentNiv = 0;
            int count = 0;
            VBox vBox = new VBox();
            HBox.setHgrow(vBox, Priority.ALWAYS);
            vBox.setFillWidth(true);

            for (int tache : topSort) {
                vBox.setAlignment(Pos.CENTER);
                vBox.setSpacing(15);


                AnchorPane card;
                FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("card.fxml"));
                card = fxmlLoader2.load();
                ((CardCtrl) fxmlLoader2.getController()).setTache(keys[tache]);
                vBox.getChildren().add(card);

                cards.put(keys[tache], card);
                count++;

                if (count == niveaux[currentNiv].size()) {
                    niveauPos.put(currentNiv, bounds(card));
                    count = 0;
                    currentNiv++;
                    hBox.getChildren().add(vBox);
                    vBox = new VBox();
                    vBox.setFillWidth(true);
                    HBox.setHgrow(vBox, Priority.ALWAYS);
                }
            }
            Color colors[] = {Color.web("#ec622f"), Color.web("#f7a621"), Color.web("#58b358"), Color.web("#34a9c9"),
                    Color.web("#4d61aa"), Color.web("#794a98"), Color.web("#868888")};
            int c = 0;
            for (LinkedList<Tache> niv : niveaux) {
                for (Tache t : niv) {
                    ObjectBinding<Bounds> tBounds = bounds(cards.get(t));
                    for (Tache pred : t.getPreds()) {
                        ObjectBinding<Bounds> predBounds = bounds(cards.get(pred));
                        Line l = new Line();

                        bindStartX(l, niveauPos.get(graphe.getNiveau(t) - 1));
                        bindEndX(l, tBounds);
                        bindStartY(l, predBounds);
                        bindEndY(l, tBounds);
                        pane.getChildren().add(l);

                        Line l2 = new Line();
                        if (graphe.getNiveau(t) - 1 != graphe.getNiveau(pred)) {
                            bindStartX(l2, predBounds);
                            bindEndX2(l2, niveauPos.get(graphe.getNiveau(t) - 1));
                            bindStartY(l2, predBounds);
                            bindEndY(l2, predBounds);

                            pane.getChildren().add(l2);
                        }


                        l.setStroke(colors[c]);
                        l.setStyle("-fx-stroke-line-cap: round; -fx-stroke-line-join: round;-fx-stroke-width: 1.5");
                        l2.setStroke(colors[c]);
                        l2.setStyle("-fx-stroke-line-cap: round; -fx-stroke-line-join: round;-fx-stroke-width: 1.5");
                        c = (c + 1) % 7;
                        if (cPath.contains(pred) && cPath.contains(t)) {


                            l.toFront();
                            l.setStyle("-fx-stroke: #e53935; -fx-stroke-width: 6; -fx-effect: dropshadow(GAUSSIAN, #c6c5c5, 10, 0, 0, 0)");
                            l2.toFront();
                            l2.setStyle("-fx-stroke: #e53935; -fx-stroke-width: 6");
                        } else {

                            l.toBack();
                            l2.toBack();
                        }

                    }

                }
            }
            pane.getChildren().add(hBox);

            scrollPane.setContent(pane);
            pertBtn.setDisable(false);
            ganttBtn.setDisable(false);
            pertBtn.fire();
            durationBtn.setText("Total Duration: " + Math.round(graphe.getProjet().getDureeProjet()) + " days");
            durationBtn.setVisible(true);
        }
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private ObjectBinding<Bounds> bounds(Node n1) {

        return Bindings.createObjectBinding(() -> {
                    Bounds nodeLocal = n1.getBoundsInLocal();
                    Bounds nodeScene = n1.getLocalToSceneTransform().transform(nodeLocal);
                    return pane.sceneToLocal(nodeScene);
                }, n1.boundsInLocalProperty(), n1.localToSceneTransformProperty(),
                pane.localToSceneTransformProperty());
    }

    private void bindStartX(Line line, ObjectBinding<Bounds> nivBounds) {

        line.startXProperty().bind(Bindings.createDoubleBinding(
                () -> nivBounds.get().getMinX() + nivBounds.get().getWidth() - 30,
                nivBounds));
    }

    private void bindEndX(Line line, ObjectBinding<Bounds> nivBounds) {

        line.endXProperty().bind(Bindings.createDoubleBinding(
                () -> nivBounds.get().getMinX() + 30,
                nivBounds));
    }

    private void bindEndX2(Line line, ObjectBinding<Bounds> nivBounds) {

        line.endXProperty().bind(Bindings.createDoubleBinding(
                () -> nivBounds.get().getMinX() + nivBounds.get().getWidth() - 30,
                nivBounds));
    }

    private void bindStartY(Line line, ObjectBinding<Bounds> startBounds) {

        line.startYProperty().bind(Bindings.createDoubleBinding(
                () -> startBounds.get().getMinY() + startBounds.get().getHeight() / 2,
                startBounds));
    }

    private void bindEndY(Line line, ObjectBinding<Bounds> endBounds) {

        line.endYProperty().bind(Bindings.createDoubleBinding(
                () -> endBounds.get().getMinY() + endBounds.get().getHeight() / 2,
                endBounds));
    }

        }