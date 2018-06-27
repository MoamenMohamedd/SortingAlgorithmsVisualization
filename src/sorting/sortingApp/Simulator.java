package sorting.sortingApp;

import javafx.animation.*;
import javafx.animation.Animation.Status;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import sorting.sort.HeapSort;
import sorting.sort.MergeSort;
import sorting.sort.Sort;
import sorting.sortingApp.stepUtils.*;

import java.util.ArrayList;

//Timeline timeline = new Timeline();
// KeyValue kv1 = new KeyValue(((Rectangle)
// elements.getChildren().get(8)).xProperty(),
// ((Rectangle) elements.getChildren().get(1)).getX());
// KeyValue kv2 = new KeyValue(((Rectangle)
// elements.getChildren().get(1)).xProperty(),
// ((Rectangle) elements.getChildren().get(8)).getX());
// KeyFrame kf = new KeyFrame(Duration.millis(800), kv1, kv2);
// timeline.getKeyFrames().add(kf);
// timeline.play();

public class Simulator {

    private int[] simulationData = {40, 70, 25, 15, 10, 63, 30, 54, 89, 18};
    private Pane pane;
    private int[] trackIndexes;
    private Sort sort;
    private SequentialTransition sequence;


    public Simulator() {
        sort = new MergeSort();
        sequence = new SequentialTransition();
        trackIndexes = new int[simulationData.length];
    }


    private void prepareAnimation() {

        sequence.getChildren().clear();
        for (int i = 0; i < trackIndexes.length; i++) {
            trackIndexes[i] = i;
        }

        sort.setData(simulationData.clone());
        sort.execute();
        ArrayList<Step> steps = sort.getSteps();

        if (sort instanceof HeapSort) {
            ObservableList<Node> children = pane.getChildren();
            for (Step step : steps) {
                if (step instanceof Swap) {
                    Swap swap = (Swap) step;
                    Circle circle1 = (Circle) children.get(trackIndexes[swap.getI1()]);
                    Circle circle2 = (Circle) children.get(trackIndexes[swap.getI2()]);
                    sequence.getChildren().addAll(getNodesSwapTransition(circle1, circle2, swap),
                            new PauseTransition(Duration.millis(500)));
                }
//                } else if (step instanceof Select) {
//                    Select select = (Select) step;
//                    if (select.getIndex() >= trackIndexes.length)
//                        continue;
//                    Circle circle = (Circle) children.get(trackIndexes[select.getIndex()]);
//                    FillTransition fillTransition = new FillTransition(Duration.millis(300),
//                            circle, (Color) circle.getFill(), select.getSelectColor());
//                    if (select.isHolded()){
//                        fillTransition.setCycleCount(1);
//                    }else {
//                        fillTransition.setCycleCount(2);
//                        fillTransition.setAutoReverse(true);
//                    }
//
//                    circle.setStroke(select.getStrokeColor());
//
//                    sequence.getChildren().addAll(fillTransition);
//
//                }
            }

            sequence.getChildren().add(getTreeResetTransition());
            sequence.setCycleCount(Animation.INDEFINITE);
            sequence.setRate(0.5);

        } else {
            GridPane gp = (GridPane) pane;
            ObservableList<Node> children = gp.getChildren();

            for (Step step : steps) {
                if (step instanceof Swap) {
                    Swap swap = (Swap) step;
                    Label bar1 = (Label) children.get(trackIndexes[swap.getI1()]);
                    Label bar2 = (Label) children.get(trackIndexes[swap.getI2()]);

                    sequence.getChildren().addAll(getBarsSwapTransition(bar1, bar2, swap),
                            new PauseTransition(Duration.millis(500)));


                } else if (step instanceof Select) {
                    Select select = (Select) step;
                    ParallelTransition selectGroup = new ParallelTransition();
                    if (select.isHolded()) {
                        for (int i = select.getStart(); i <= select.getEnd(); i++) {
                            Label bar = (Label) children.get(trackIndexes[i]);
                            FillTransition fillTransition = new FillTransition(Duration.millis(300),
                                    (Rectangle) bar.getGraphic(), (Color) ((Rectangle) bar.getGraphic()).getFill(), select.getSelectColor());
                            fillTransition.setCycleCount(1);
                            selectGroup.getChildren().addAll(fillTransition);
                        }
                    } else {
                        for (int i = select.getStart(); i <= select.getEnd(); i++) {
                            Label bar = (Label) children.get(trackIndexes[i]);
                            FillTransition fillTransition = new FillTransition(Duration.millis(300),
                                    (Rectangle) bar.getGraphic(), (Color) ((Rectangle) bar.getGraphic()).getFill(), select.getSelectColor());
                            fillTransition.setCycleCount(2);
                            fillTransition.setAutoReverse(true);
                            selectGroup.getChildren().addAll(fillTransition);
                        }
                    }

                    sequence.getChildren().addAll(selectGroup, new PauseTransition(Duration.millis(500)));


                } else if (step instanceof Arrange) {
                    Arrange arrange = (Arrange) step;
                    int[] indexes = trackIndexes.clone();
                    for (GoTo goTo : arrange.getGoTos()) {
                        int rectPos = getIndexOf(goTo.getFrom());
                        Label bar = (Label) children.get(rectPos);
                        animateRectangleTo(bar, (Label) children.get(goTo.getTo()));
                        indexes[rectPos] = goTo.getTo();
                    }
                    trackIndexes = indexes;

                }

            }

            SequentialTransition finished = getFinishTransition();
            sequence.getChildren().addAll(finished, getBarsResetTransition());
            sequence.setCycleCount(Animation.INDEFINITE);
            sequence.setRate(2);

        }
    }


    private void animateRectangleTo(Label bar, Label toBar) {
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(bar);
        transition.setToX(toBar.getBoundsInParent().getMinX() - bar.getBoundsInParent().getMinX());
        sequence.getChildren().add(transition);
    }

    private int getIndexOf(int x) {
        for (int i = 0; i < trackIndexes.length; i++) {
            if (trackIndexes[i] == x)
                return i;
        }
        return 0;
    }

    private ParallelTransition getBarsSwapTransition(Label bar1, Label bar2, Swap swap) {
        ParallelTransition swapAnimation = new ParallelTransition();
        swapAnimation.statusProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Status.RUNNING) {
                ((Rectangle) bar1.getGraphic()).setFill(swap.getSwapOnPaint());
                ((Rectangle) bar2.getGraphic()).setFill(swap.getSwapOnPaint());
            } else if (newValue == Status.STOPPED) {
                ((Rectangle) bar1.getGraphic()).setFill(swap.getSwapOffPaint());
                ((Rectangle) bar2.getGraphic()).setFill(swap.getSwapOffPaint());
            }

        });


        TranslateTransition moveRect1ToRect2 = new TranslateTransition();
        moveRect1ToRect2.setNode(bar1);
        moveRect1ToRect2.setDuration(Duration.millis(500));
        moveRect1ToRect2.setByX(bar2.getBoundsInParent().getMinX() - bar1.getBoundsInParent().getMinX());


        TranslateTransition moveRect2ToRect1 = new TranslateTransition();
        moveRect2ToRect1.setNode(bar2);
        moveRect2ToRect1.setDuration(Duration.millis(500));
        moveRect2ToRect1.setByX(bar1.getBoundsInParent().getMinX() - bar2.getBoundsInParent().getMinX());

        ChangeListener<Bounds> listener = (observable, oldValue, newValue) -> {
            if (newValue.getMinX() != oldValue.getMinX()) {
                moveRect1ToRect2.setByX(bar2.getBoundsInParent().getMinX() - bar1.getBoundsInParent().getMinX());
                moveRect2ToRect1.setByX(bar1.getBoundsInParent().getMinX() - bar2.getBoundsInParent().getMinX());
            }
        };
        bar1.boundsInParentProperty().addListener(listener);
        bar2.boundsInParentProperty().addListener(listener);


        swapAnimation.getChildren().addAll(moveRect1ToRect2, moveRect2ToRect1);

        swapAnimation.setOnFinished(event -> {
            bar1.boundsInParentProperty().removeListener(listener);
            bar2.boundsInParentProperty().removeListener(listener);
        });


        int temp = trackIndexes[swap.getI1()];
        trackIndexes[swap.getI1()] = trackIndexes[swap.getI2()];
        trackIndexes[swap.getI2()] = temp;

        return swapAnimation;

    }

    private ParallelTransition getNodesSwapTransition(Circle circle1, Circle circle2, Swap swap) {
        ParallelTransition swapAnimation = new ParallelTransition();
        swapAnimation.statusProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Status.RUNNING) {
                circle1.setFill(swap.getSwapOnPaint());
                circle2.setFill(swap.getSwapOnPaint());
            } else if (newValue == Status.STOPPED) {
                circle1.setFill(swap.getSwapOffPaint());
                circle2.setFill(swap.getSwapOffPaint());
            }

        });


        TranslateTransition moveNode1ToNode2 = new TranslateTransition();
        moveNode1ToNode2.setNode(circle1);
        moveNode1ToNode2.setDuration(Duration.millis(500));
        moveNode1ToNode2.setByX(circle2.getBoundsInParent().getMinX() - circle1.getBoundsInParent().getMinX());
        moveNode1ToNode2.setByY(circle2.getBoundsInParent().getMinY() - circle1.getBoundsInParent().getMinY());


        TranslateTransition moveNode2ToNode1 = new TranslateTransition();
        moveNode2ToNode1.setNode(circle2);
        moveNode2ToNode1.setDuration(Duration.millis(500));
        moveNode2ToNode1.setByX(circle1.getBoundsInParent().getMinX() - circle2.getBoundsInParent().getMinX());
        moveNode2ToNode1.setByY(circle1.getBoundsInParent().getMinY() - circle2.getBoundsInParent().getMinY());

        ChangeListener<Bounds> listener = (observable, oldValue, newValue) -> {
            if (newValue.getMinX() != oldValue.getMinX() || newValue.getMinY() != oldValue.getMinY()) {
                moveNode1ToNode2.setByX(circle2.getBoundsInParent().getMinX() - circle1.getBoundsInParent().getMinX());
                moveNode1ToNode2.setByY(circle2.getBoundsInParent().getMinY() - circle1.getBoundsInParent().getMinY());
                moveNode2ToNode1.setByX(circle1.getBoundsInParent().getMinX() - circle2.getBoundsInParent().getMinX());
                moveNode2ToNode1.setByY(circle1.getBoundsInParent().getMinY() - circle2.getBoundsInParent().getMinY());
            }
        };
        circle1.boundsInParentProperty().addListener(listener);
        circle2.boundsInParentProperty().addListener(listener);


        swapAnimation.getChildren().addAll(moveNode1ToNode2, moveNode2ToNode1);

        swapAnimation.setOnFinished(event -> {
            circle1.boundsInParentProperty().removeListener(listener);
            circle2.boundsInParentProperty().removeListener(listener);
        });


        int temp = trackIndexes[swap.getI1()];
        trackIndexes[swap.getI1()] = trackIndexes[swap.getI2()];
        trackIndexes[swap.getI2()] = temp;

        return swapAnimation;

    }


    private SequentialTransition getFinishTransition() {
        SequentialTransition finished = new SequentialTransition();
        if (sort instanceof HeapSort) {

        } else {
            GridPane gp = (GridPane) pane;
            ObservableList<Node> children = gp.getChildren();
            if (sort instanceof MergeSort) {
                for (int i = 0; i < trackIndexes.length; i++) {
                    Label bar = (Label) children.get(getIndexOf(i));
                    FillTransition fillTransition = new FillTransition(Duration.millis(300),
                            (Rectangle) bar.getGraphic(), Color.WHITE, Color.GREEN);
                    finished.getChildren().add(fillTransition);
                }
            } else {
                for (int i = 0; i < trackIndexes.length; i++) {
                    Label bar = (Label) children.get(trackIndexes[i]);
                    FillTransition fillTransition = new FillTransition(Duration.millis(300),
                            (Rectangle) bar.getGraphic(), Color.WHITE, Color.GREEN);
                    finished.getChildren().add(fillTransition);
                }
            }
        }

        finished.setCycleCount(2);
        finished.setAutoReverse(true);
        return finished;
    }

    public void pause() {
        if (!sequence.getStatus().equals(Animation.Status.PAUSED))
            sequence.pause();
    }

    public void run() {
        if (sequence.getCurrentTime().equals(Duration.ZERO)) {
            prepareAnimation();
        }
        if (!sequence.getStatus().equals(Animation.Status.RUNNING))
            sequence.play();
    }

    public void setSort(Sort sort) {
        this.sort = sort;
        if (!sequence.getStatus().equals(Animation.Status.STOPPED)) {
            sequence.stop();
        }

        ParallelTransition reset = null;
        if (this.sort instanceof HeapSort) {
            reset = getTreeResetTransition();
        } else {
            reset = getBarsResetTransition();
        }
        reset.play();
    }

    private ParallelTransition getBarsResetTransition() {
        ParallelTransition reset = new ParallelTransition();
        GridPane gp = (GridPane) pane;
        for (Node node : gp.getChildren()) {
            Label bar = (Label) node;
            ((Rectangle) bar.getGraphic()).setFill(Color.WHITE);
            TranslateTransition returnToOriginalPlace = new TranslateTransition();
            returnToOriginalPlace.setNode(bar);
//            returnToOriginalPlace.setByX(-rectangle.getTranslateX());
            returnToOriginalPlace.byXProperty().bind(bar.translateXProperty().multiply(-1));
            reset.getChildren().add(returnToOriginalPlace);

        }

        return reset;
    }

    private ParallelTransition getTreeResetTransition() {
        ParallelTransition reset = new ParallelTransition();
        int n = simulationData.length;
        ObservableList<Node> children = pane.getChildren();
        for (int i = 0 ; i < n ; i++) {
            Circle circle = (Circle) children.get(i);
            circle.setFill(Color.WHITE);
            circle.setStroke(Color.WHITE);
            TranslateTransition returnToOriginalPlace = new TranslateTransition();
            returnToOriginalPlace.setNode(circle);
//            returnToOriginalPlace.setByX(-rectangle.getTranslateX());
            returnToOriginalPlace.byXProperty().bind(circle.translateXProperty().multiply(-1));
            reset.getChildren().add(returnToOriginalPlace);

        }
        return reset;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

}
