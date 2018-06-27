package sorting.sortingApp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import sorting.sort.*;

import java.util.Arrays;
import java.util.Scanner;

public class SortApp extends Application implements Graph.ReturnHome {

    private Sort sort;
    private Graph graphPlot;
    private Simulator simulator;
    private PaneDrawer paneDrawer;

    private Button playPause;
    private ImageView play;
    private ImageView pause;

    private BorderPane bp;
    private StackPane sp;
    private Stage stage;
    private Scene mainScene, graphScene;

    public static void main(String[] args) {

//App for simulation and graph
        Application.launch();

//normal test
//		Scanner scanner = new Scanner(System.in);
//		Sort sort = new QuickSort();
//		int n = scanner.nextInt();
//		int[] data = Graph.getDataSet(n,1000);
//		sort.setData(data);
//		sort.execute();
//		System.out.println(Arrays.toString(data));
//		System.exit(0);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sorting Visualization");
        stage = primaryStage;
        simulator = new Simulator();
        paneDrawer = new PaneDrawer();
        graphPlot = new Graph(this);
        playPause = new Button();
        play = new ImageView(new Image("res/play.png"));
        pause = new ImageView(new Image("res/pause.png"));

        bp = new BorderPane();


        setUpSortButtons();

        sp = new StackPane();
        sp.setStyle("-fx-background-color:grey;");


        playPause = new Button();
        playPause.setStyle("-fx-background-color:transparent;-fx-border-color:white;");
        playPause.setGraphic(play);
        playPause.setOnAction(e -> {
            ImageView image = (ImageView) playPause.getGraphic();
            if (image.equals(play)) {
                simulator.run();
                playPause.setGraphic(pause);
            } else {
                simulator.pause();
                playPause.setGraphic(play);
            }
        });

        sp.getChildren().add(playPause);
        StackPane.setAlignment(playPause, Pos.BOTTOM_CENTER);



        GridPane barsPane = paneDrawer.getBarsPane(sp);
        simulator.setPane(barsPane);
        sp.getChildren().add(0, barsPane);
        barsPane.setAlignment(Pos.CENTER);

        Button graphB = new Button("Graph");
        graphB.getStyleClass().add("graph");
        StackPane.setMargin(graphB, new Insets(20));
        graphB.setOnAction(e -> {
            simulator.pause();
            playPause.setGraphic(play);
            if (graphScene == null) {
                graphScene = graphPlot.getGraphScene();
                primaryStage.setScene(graphScene);
                graphPlot.plot();
            } else {
                primaryStage.setScene(graphScene);
            }
        });
        graphB.setPrefSize(100, 100);
        graphB.setId("graph");
        graphB.setMaxSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        sp.getChildren().add(graphB);
        StackPane.setAlignment(graphB, Pos.TOP_LEFT);

        bp.setCenter(sp);
        mainScene = new Scene(bp, 800, 700);
        mainScene.getStylesheets().add("res/sceneStyle.css");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    @Override
    public void returnHome() {
        stage.setScene(mainScene);
    }

    private void setUpSortButtons() {
        ToggleGroup tGroup = new ToggleGroup();

        ToggleButton mergeB = new ToggleButton("Merge");
        mergeB.setSelected(true);
        mergeB.setToggleGroup(tGroup);
        mergeB.getStyleClass().add("merge");

        ToggleButton quickB = new ToggleButton("Quick");
        quickB.setToggleGroup(tGroup);
        quickB.getStyleClass().add("quick");

        ToggleButton heapB = new ToggleButton("Heap");
        heapB.setToggleGroup(tGroup);
        heapB.getStyleClass().add("heap");

        ToggleButton insertionB = new ToggleButton("Insertion");
        insertionB.setToggleGroup(tGroup);
        insertionB.getStyleClass().add("insertion");

        ToggleButton selectionB = new ToggleButton("Selection");
        selectionB.setToggleGroup(tGroup);
        selectionB.getStyleClass().add("selection");

        ToggleButton bubbleB = new ToggleButton("Bubble");
        bubbleB.setToggleGroup(tGroup);
        bubbleB.getStyleClass().add("bubble");
        tGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != oldValue) {
                if (tGroup.getSelectedToggle() != null) {
                    ToggleButton t = (ToggleButton) tGroup.getSelectedToggle();
                    switch (t.getText()) {
                        case "Merge":
                            sort = new MergeSort();
                            break;
                        case "Quick":
                            sort = new QuickSort();
                            break;
                        case "Bubble":
                            sort = new BubbleSort();
                            break;
                        case "Insertion":
                            sort = new InsertionSort();
                            break;
                        case "Selection":
                            sort = new SelectionSort();
                            break;
                        case "Heap":
                            sort = new HeapSort();
                            break;

                    }
                    playPause.setGraphic(play);
                    setUpPaneView();
                    simulator.setSort(sort);
                }
            }
        });



        HBox hbox = new HBox(bubbleB, selectionB, insertionB, mergeB, quickB, heapB);
        hbox.setSpacing(5);
        hbox.setAlignment(Pos.CENTER);
        bp.setBottom(hbox);
        BorderPane.setMargin(hbox, new Insets(50, 0, 50, 0));
    }


    private void setUpPaneView() {
        if (sort instanceof HeapSort) {

            Pane treeView = paneDrawer.getTreePane(sp);
            simulator.setPane(treeView);
            sp.getChildren().remove(0);
            sp.getChildren().add(0, treeView);
            StackPane.setAlignment(treeView, Pos.CENTER);
        } else {

            GridPane barsPane = paneDrawer.getBarsPane(sp);
            simulator.setPane(barsPane);
            sp.getChildren().remove(0);
            sp.getChildren().add(0, barsPane);
            barsPane.setAlignment(Pos.CENTER);
        }


    }


}
