package sorting.sort;

import javafx.scene.paint.Color;
import sorting.sortingApp.stepUtils.Select;
import sorting.sortingApp.stepUtils.Step;
import sorting.sortingApp.stepUtils.Swap;

import java.util.ArrayList;

public class InsertionSort implements Sort {

    private int[] data;
    private ArrayList<Step> steps;

    public InsertionSort(){
        steps = new ArrayList<>();
    }


    //worst case reversed sorted = O(n^2)
    //average case = O(n^2)
    //best case sorted array O(n)

    @Override
    public void execute() {
        int n = data.length;

        //loop start from second element assuming 1 element array is already sorted before insertion
        for (int i = 1; i < n; i++) {
            steps.add(new Select(i , i , Color.BLUE , false));
            int value = data[i];
            //insert into sorted part of array
            int hole = i;
            //hole > 0 to avoid index out of bound
            while (hole > 0 && data[hole - 1] > value) {
                //swap (replace)
                data[hole] = data[hole - 1];
                steps.add(new Swap(hole, hole - 1,Color.RED,Color.WHITE));

                hole--;
            }

            data[hole] = value;

        }


    }

    @Override
    public void setData(int[] data) {
        this.data = data;
    }

    @Override
    public String getName() {
        return "Insertion Sort";
    }

    @Override
    public ArrayList<Step> getSteps() {
        return steps;
    }

}
