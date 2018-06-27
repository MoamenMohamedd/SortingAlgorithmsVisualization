package sorting.sortingApp.stepUtils;

import java.util.ArrayList;

public class Arrange implements Step {
    private ArrayList<GoTo> goTos;

    public Arrange(ArrayList<GoTo> goTos){this.goTos = goTos;}

    public ArrayList<GoTo> getGoTos() {
        return goTos;
    }
}
