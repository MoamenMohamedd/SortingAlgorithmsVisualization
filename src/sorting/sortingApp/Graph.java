package sorting.sortingApp;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import sorting.sort.Sort;
import sorting.sort.*;

import java.util.ArrayList;
import java.util.Random;

public class Graph {
    private ArrayList<Sort> sortAlgo;
    private ArrayList<XYChart.Series<Number, Number>> series;
    private ReturnHome returnHome;
    private LineChart<Number, Number> lineChart;

    public Graph(ReturnHome returnHome) {
        this.returnHome = returnHome;
        series = new ArrayList();
        sortAlgo = new ArrayList();
        sortAlgo.add(new MergeSort());
        sortAlgo.add(new HeapSort());
        sortAlgo.add(new SelectionSort());
        sortAlgo.add(new InsertionSort());
        sortAlgo.add(new BubbleSort());
        sortAlgo.add(new QuickSort());

        for (int i = 0; i < 6; i++)
            series.add(new XYChart.Series<Number, Number>());


    }

    public static int[] getDataSet(int size, int range) {
        Random random = new Random();
        int max = range;
        int min = -range;
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(max - min + 1) + min;

        }
        return arr;
    }

    public Scene getGraphScene() {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        yAxis.setAnimated(true);
        xAxis.setAnimated(true);
        yAxis.setAutoRanging(false);
        xAxis.setLabel("Array Size");
        yAxis.setLabel("Time(ms)");

        lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        lineChart.setTitle("Sort Algorithms Comparison");
        lineChart.setAnimated(true);
        lineChart.getData().addAll(series);
        lineChart.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent ev) {
                double zoomFactor = 1.05;
                double deltaY = ev.getDeltaY();

                if (deltaY < 0) {
                    zoomFactor = 2 - zoomFactor;
                }
                yAxis.setUpperBound(yAxis.getUpperBound() * zoomFactor);
                yAxis.setLowerBound(yAxis.getLowerBound() * zoomFactor);
                yAxis.setTickUnit(yAxis.getTickUnit() * zoomFactor);

                ev.consume();
            }
        });

        lineChart.setOnKeyPressed(ev -> {
            double zoomFactor = 1.05;

            if (ev.getCode().equals(KeyCode.UP)) {
                yAxis.setUpperBound(yAxis.getUpperBound() * zoomFactor);
                yAxis.setLowerBound(yAxis.getLowerBound() * zoomFactor);
                yAxis.setTickUnit(yAxis.getTickUnit() * zoomFactor);

            } else if (ev.getCode().equals(KeyCode.DOWN)) {
                zoomFactor = 2 - zoomFactor;
                yAxis.setUpperBound(yAxis.getUpperBound() * zoomFactor);
                yAxis.setLowerBound(yAxis.getLowerBound() * zoomFactor);
                yAxis.setTickUnit(yAxis.getTickUnit() * zoomFactor);
            }
        });

        lineChart.setFocusTraversable(true);

        StackPane sp = new StackPane();
        Button goToHome = new Button();
        goToHome.setOnAction(e -> {
            returnHome.returnHome();
        });
        goToHome.setStyle("-fx-background-color:transparent;");
        ImageView iView = new ImageView(new Image("res/back.png"));
        iView.setFitWidth(25);
        iView.setFitHeight(25);
        goToHome.setGraphic(iView);

        sp.getChildren().addAll(lineChart, goToHome);
        StackPane.setAlignment(goToHome, Pos.TOP_LEFT);


        Scene scene = new Scene(sp, 800, 700);

        lineChart.requestFocus();
        return scene;

    }

    public void plot() {

        clearResults();

        long startTime, endTime, totalTime;
        int[] dataSetsSizes = {1000, 2000, 3000, 4000, 5000};
        for (int i = 0; i < dataSetsSizes.length; i++) {
            int[] data = getDataSet(dataSetsSizes[i], 1000000);
            for (int j = 0; j < sortAlgo.size(); j++) {
                Sort sort = sortAlgo.get(j);
                if (sort instanceof BubbleSort || sort instanceof SelectionSort || sort instanceof QuickSort)
                    continue;
                sort.setData(data.clone());
                startTime = System.currentTimeMillis();
                sort.execute();
                endTime = System.currentTimeMillis();
                totalTime = endTime - startTime;
                series.get(j).getData().add(new XYChart.Data(data.length, totalTime));
                if (i == 0)
                    series.get(j).setName(sort.getName());

            }
        }


    }


    private void clearResults() {
        for (int i = 0; i < lineChart.getData().size(); i++) {
            lineChart.getData().get(i).getData().clear();
        }

    }

    public interface ReturnHome {
        void returnHome();
    }


}
