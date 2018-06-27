package sorting.sortingApp.stepUtils;

import javafx.scene.paint.Paint;

public class Swap implements Step {
    private int i1, i2;
    private Paint swapOnPaint , swapOffPaint;

    public Swap(int i1, int i2, Paint swapOnPaint , Paint swapOffPaint) {
        this.i1 = i1;
        this.i2 = i2;
        this.swapOffPaint = swapOffPaint;
        this.swapOnPaint = swapOnPaint;
    }

    public Paint getSwapOnPaint() {
        return swapOnPaint;
    }

    public Paint getSwapOffPaint() {
        return swapOffPaint;
    }

    public int getI1() {
        return i1;
    }

    public int getI2() {
        return i2;
    }

}