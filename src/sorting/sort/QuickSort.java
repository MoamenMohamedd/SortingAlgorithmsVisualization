package sorting.sort;

import javafx.scene.paint.Color;
import sorting.sortingApp.stepUtils.Select;
import sorting.sortingApp.stepUtils.Step;
import sorting.sortingApp.stepUtils.Swap;


import java.util.ArrayList;

public class QuickSort implements Sort {

    private int[] data;
    private ArrayList<Step> steps;

    public QuickSort(){
        steps = new ArrayList<>();
    }




    // not stable
    // in place
    // best & average cases time complexity O(nlog(n))
    // worst case O(n^2) but can be avoided rarely occurs
    // space complexity average case is O(log(n))
    // space complexity worst case is O(n)
    private void quickSort(int[] array, int start, int end) {
        if (start < end) {
            int pIndex = partition(array, start, end);
            quickSort(array, start, pIndex - 1);
            quickSort(array, pIndex + 1, end);
        }
    }

    // momken btaree2t el 2 pointers start w end w swap law fi makaneen 3'alat
    private int partition(int[] array, int start, int end) {
        // int randI = (int)(Math.random()*(end+1));
        // swap(array , randI ,end);

        int pivot = array[end];


        steps.add(new Select(start, end, Color.LIGHTCORAL , true));
        steps.add(new Select(end, end, Color.VIOLET , true));


        int pIndex = start;
        for (int i = start; i < end; i++) {
            if (array[i] <= pivot) {
                int temp = data[pIndex];
                data[pIndex] = data[i];
                data[i] = temp;
                steps.add(new Swap(pIndex, i,Color.RED,Color.LIGHTCORAL));
                pIndex++;
            }
        }
        // put pivot in place
        int temp = data[pIndex];
        data[pIndex] = data[end];
        data[end] = temp;
        steps.add(new Swap(pIndex, end,Color.RED,Color.LIGHTCORAL));

        steps.add(new Select(start, end, Color.WHITE , true));

        return pIndex;

    }

    @Override
    public void setData(int[] data) {
        this.data = data;
    }

    @Override
    public void execute() {
        quickSort(data, 0, data.length - 1);
    }

    @Override
    public String getName() {
        return "Quick Sort";
    }

    @Override
    public ArrayList<Step> getSteps() {
        return steps;
    }

}
