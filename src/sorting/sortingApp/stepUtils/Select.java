package sorting.sortingApp.stepUtils;

import javafx.scene.paint.Color;



public class Select implements Step {
    private int start, end , index;
    private Color selectColor , strokeColor;
    private boolean holded;

    public Select(int start, int end, Color selectColor, boolean holded) {
        this.start = start;
        this.end = end;
        this.selectColor = selectColor;
        this.holded = holded;
    }

    public Select(int index , Color selectColor , Color strokeColor , boolean holded){
        this.index = index;
        this.strokeColor =strokeColor;
        this.selectColor = selectColor;
        this.holded = holded;
    }


    public boolean isHolded() {
        return holded;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getIndex() {
        return index;
    }

    public Color getSelectColor() {
        return selectColor;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }
}


