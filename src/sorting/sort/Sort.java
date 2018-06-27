package sorting.sort;


import sorting.sortingApp.stepUtils.Step;

import java.util.ArrayList;

public interface Sort {

    void execute();
    String getName();
    ArrayList<Step> getSteps();
    void setData(int[] data);
}
