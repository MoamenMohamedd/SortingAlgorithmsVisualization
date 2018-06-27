package sorting.sortingApp.stepUtils;


public class GoTo implements Step {
    private int from, to;

    public GoTo(int from, int to) {
        this.from = from;
        this.to = to;
    }


    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

}
