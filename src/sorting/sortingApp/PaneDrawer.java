package sorting.sortingApp;

import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import sun.font.FontFamily;

public class PaneDrawer {
    private Pane treePane;
    private GridPane barsPane;
    private int[] simulationData = {40, 70, 25, 15, 10, 63, 30, 54, 89, 18};

    public PaneDrawer() {

    }

    public GridPane getBarsPane(StackPane sp) {
        if (barsPane != null)
            return barsPane;
        barsPane = new GridPane();

//        TextField textField = new TextField();
//        sp.getChildren().add(textField);
//        StackPane.setAlignment(textField, Pos.TOP_CENTER);
//        barsPane.setOnMouseMoved(event -> {
//            textField.setText(event.getX()+","+event.getY());
//        });

        barsPane.setHgap(10);
        barsPane.hgapProperty().bind(sp.widthProperty().divide(1.6).add(10).divide(10).subtract(40));
        for (int i = 0; i < simulationData.length; i++) {
            int height = 200 * simulationData[i] / 100;
            int width = 40;

            Rectangle rectangle = new Rectangle(width, height);
            rectangle.setFill(Color.WHITE);
            rectangle.setId(i + "");//makano el asly fel array
            Label num = new Label(simulationData[i]+"",rectangle);
            num.setContentDisplay(ContentDisplay.TOP);
            GridPane.setValignment(num, VPos.BOTTOM);
            barsPane.add(num, i, 0);
        }
        return barsPane;
    }

    public Pane getTreePane(StackPane sp) {
        Pane pane = new Pane();

        int treeHeight = (int) Math.ceil(Math.log(simulationData.length) / Math.log(2));
        int y = 1;
        int numberOfNodes = 0;
        for (int i = 0 ;i<treeHeight ; i++){
            int nodesInLevel = (int)Math.pow(2,i);
            int x =1;
            for (int j = 0 ; j < nodesInLevel ; j++) {
                if (numberOfNodes == simulationData.length)
                    break;
                Circle circle = new Circle(20 , Color.LAVENDER);
                circle.setStrokeWidth(5);
                circle.setStroke(Color.WHITE);
                Text text = new Text(simulationData[numberOfNodes]+"");
                text.xProperty().bind(circle.translateXProperty().add(circle.centerXProperty()).subtract(5));
                text.yProperty().bind(circle.translateYProperty().add(circle.centerYProperty()).add(5));
                circle.centerXProperty().bind(sp.widthProperty().divide(2*nodesInLevel).multiply(x));
                circle.centerYProperty().bind(sp.heightProperty().divide(treeHeight*1.4).multiply(y));
                pane.getChildren().add(numberOfNodes,circle);
                pane.getChildren().add(text);
                numberOfNodes++;
                x = x +2;
            }
            y++;
        }


        Text text = new Text();
        text.setFill(Color.WHITE);
        text.setFont(Font.font("Basic" , FontWeight.BOLD , FontPosture.REGULAR,17));

        text.xProperty().bind(pane.widthProperty().subtract(200));
        text.setY(100);
        pane.getChildren().add(text);


        return pane;
    }

}
