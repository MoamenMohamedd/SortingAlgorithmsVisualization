package sorting.sort;

import javafx.scene.paint.Color;
import sorting.sortingApp.stepUtils.Select;
import sorting.sortingApp.stepUtils.Step;
import sorting.sortingApp.stepUtils.Swap;

import java.util.ArrayList;

public class BubbleSort implements Sort {

    private int[] data;
    private ArrayList<Step> steps;

    public BubbleSort(){
        steps = new ArrayList<>();
    }


    //worst case = average case = O(n^2)
    //best case = O(n)
    //stable
    //in place
    @Override
    public void execute() {
        int n = data.length;

        //repeat the pass (n-1) times as after each pass an element is sorted
        //and last pass is not needed
        for (int i = 1; i <= n - 1; i++) {

            //flag to see if no swaps occurred then the array is sorted
            boolean flag = false;
            //one pass
            //loop to n - 2 to avoid index out of bound till last sorted element n-i-1
            for (int j = 0; j <= n - i - 1; j++) {
                steps.add(new Select(j , j , Color.BLUE , false));
                if (data[j] > data[j + 1]) {
                    //swap
                    int temp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = temp;
                    steps.add(new Swap(j, j + 1,Color.RED,Color.WHITE));

                    flag = true;
                }
            }

            if (!flag) {
                break;
            }

        }

    }

    @Override
    public void setData(int[] data) {
        this.data = data;
    }

    @Override
    public String getName() {
        return "Bubble Sort";
    }

    @Override
    public ArrayList<Step> getSteps() {
        return steps;
    }

}
