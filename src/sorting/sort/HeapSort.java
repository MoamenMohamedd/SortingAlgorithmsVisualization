package sorting.sort;

import javafx.scene.paint.Color;
import sorting.sortingApp.stepUtils.Select;
import sorting.sortingApp.stepUtils.Step;
import sorting.sortingApp.stepUtils.Swap;

import java.util.ArrayList;

public class HeapSort implements Sort {
    private int[] data;
    private ArrayList<Step> steps;


    //complete tree: it is a binary tree with all its levels complete
    //except may be the last level and it is filled from left to right
    //index of children : 2i+1 , 2i+2
    //index of parent : ceil(i/2) -1

    //heap: is a complete tree with extra condition that every parent
    //is less than it's 2 children (minimum heap)
    //or greater than it's 2 children (maximum heap)


    //heap sort
    //time complexity O(nlog(n))
    //in place
    public HeapSort() {
        steps = new ArrayList<>();
    }


    private void buildMaxHeap(int[] array) {
        int lastParent = (array.length / 2) - 1;
        for (int i = lastParent; i >= 0; i--) {
            maxHeapify(array, array.length, i);
        }


        //remove element by element
        for (int i = array.length - 1; i >= 0; i--) {
            int temp = data[0];
            data[0] = data[i];
            data[i] = temp;
            steps.add(new Swap(0, i, Color.RED, Color.WHITE));
            //notice w heapify the array - one element
            maxHeapify(array, i, 0);
        }
    }

    private void maxHeapify(int[] array, int size, int parentIndex) {
        int leftI = 2 * parentIndex + 1;
        int rightI = 2 * parentIndex + 2;
        int largestI = parentIndex;


        if (leftI < size && array[leftI] > array[largestI]) {
            largestI = leftI;
        }
        if (rightI < size && array[rightI] > array[largestI]) {
            largestI = rightI;
        }
        if (largestI != parentIndex) {
            int temp = data[largestI];
            data[largestI] = data[parentIndex];
            data[parentIndex] = temp;

            steps.add(new Swap(largestI, parentIndex, Color.RED, Color.WHITE));

            maxHeapify(array, size, largestI);
        }


    }

    @Override
    public void setData(int[] data) {
        this.data = data;
    }


    @Override
    public void execute() {
        buildMaxHeap(data);
    }

    @Override
    public String getName() {
        return "Heap Sort";
    }

    @Override
    public ArrayList<Step> getSteps() {
        return steps;
    }

}
