package sorting.sort;

import javafx.scene.paint.Color;
import sorting.sortingApp.stepUtils.Arrange;
import sorting.sortingApp.stepUtils.GoTo;
import sorting.sortingApp.stepUtils.Select;
import sorting.sortingApp.stepUtils.Step;

import java.util.ArrayList;

public class MergeSort implements Sort {


    private int[] data;
    private ArrayList<Step> steps;

    public MergeSort(){
        steps = new ArrayList<>();
    }



//leeh 3eeb by5aly el space complexity O(nlog(n))

//	private void mergeSort(int[] array) {
//		int n = array.length;
//		int mid = n/2;
//		
//		if (n<2) 
//			return;
//		
//		int nLeft = mid;
//		int nRight = n-mid;
//		
//		int[] left = new int[nLeft];
//		int[] right = new int[nRight];
//		
//		for (int i = 0; i < mid; i++) {
//			left[i] = array[i];
//		}
//		for (int i = mid; i < n; i++) {
//			right[i-mid] = array[i];
//		}
//		mergeSort(left);
//		mergeSort(right);
//		merge(left , right , array);
//		
//		
//	}
//	
//	private void merge(int[] left , int[] right , int[] array) {
//		
//		int i = 0;
//		int j = 0;
//		int k = 0;//di mohema
//		
//		while (i< left.length && j <right.length) {
//			if(left[i] <= right[j]) {
//				array[k] = left[i];
//				i++;
//			}else if(left[i] > right[j]) {
//				array[k] = right[j];
//				j++;
//			}
//			k++;
//			
//		}
//		
//		
//			while(i!= left.length) {
//				array[k++] = left[i++];
//			}
//		
//		
//			while(j!= right.length) {
//				array[k++] = right[j++];
//			}
//		
//	}

    //stable
    //not in place
    //time complexity O(nlog(n))
    //space complexity O(n)
    private void mergeSort(int[] array, int start, int end) {

        int mid = (start + end) / 2;

        if (start >= end)
           return;


        mergeSort(array, start, mid);
        mergeSort(array, mid + 1, end);
        merge(array, start, mid, end);
    }

    private void merge(int[] array, int start, int mid, int end) {
        int nLeft = mid - start + 1;
        int nRight = end - mid;

        int[] left = new int[nLeft];
        int[] right = new int[nRight];

        for (int i = 0; i < nLeft; i++) {
            left[i] = array[i + start];
        }
        for (int i = 0; i < nRight; i++) {
            right[i] = array[i + mid + 1];
        }

        steps.add(new Select(start, mid, Color.rgb(255, 255, 204 ),true));
        steps.add(new Select(mid+1, end, Color.rgb(255, 153, 255),true));
        ArrayList<GoTo> goTos = new ArrayList<>();



        int i = 0;
        int j = 0;
        int k = start;
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                array[k] = left[i];
                goTos.add(new GoTo(i+start , k));
                i++;
            } else if (left[i] > right[j]) {
                array[k] = right[j];
                goTos.add(new GoTo(j+mid+1 , k));
                j++;
            }
            k++;

        }


        while (i != left.length) {
            goTos.add(new GoTo(i+start , k));
            array[k++] = left[i++];
        }


        while (j != right.length) {
            goTos.add(new GoTo(j+mid+1 , k));
            array[k++] = right[j++];
        }

        steps.add(new Arrange(goTos));
        steps.add(new Select(start, end, Color.WHITE,true));


    }

    @Override
    public void setData(int[] data) {
        this.data = data;
    }

    @Override
    public void execute() {
        mergeSort(data, 0, data.length - 1);
    }

    @Override
    public String getName() {
        return "Merge Sort";
    }

    @Override
    public ArrayList<Step> getSteps() {
        return steps;
    }


}
