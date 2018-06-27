package sorting.sort;

import javafx.scene.paint.Color;
import sorting.sortingApp.stepUtils.Select;
import sorting.sortingApp.stepUtils.Step;
import sorting.sortingApp.stepUtils.Swap;

import java.util.ArrayList;

public class SelectionSort implements Sort {

    private int[] data;
    private ArrayList<Step> steps;

    public SelectionSort(){
        steps = new ArrayList<>();
    }




    //Time complexity = O(n^2)
    //in place

    @Override
    public void execute() {
        int n = data.length;
        //loop to n-1 as last element
        for (int i = 0; i < n - 1; i++) {
            //find iMin
            int iMin = i;
            //start from next element
            for (int j = i + 1; j < n; j++) {
                steps.add(new Select(j , j , Color.BLUE , false));
                if (data[iMin] > data[j]) {
                    iMin = j;
                }
            }

            //swap
            int temp = data[i];
            data[i] = data[iMin];
            data[iMin] = temp;
            steps.add(new Swap(i, iMin,Color.RED,Color.WHITE));


        }

    }

    @Override
    public void setData(int[] data) {
        this.data = data;
    }

    @Override
    public String getName() {
        return "Selection Sort";
    }

    @Override
    public ArrayList<Step> getSteps() {
        return steps;
    }

}

