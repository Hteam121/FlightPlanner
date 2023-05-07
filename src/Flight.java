/** This class takes in respective values and creates a Flight Object **/

public class Flight {
    private String start;
    private String end;
    private double cost;
    private int time;

    //Constructor
    public Flight(String start, String end, double cost, int time) {
        this.start = start;
        this.end = end;
        this.cost = cost;
        this.time = time;
    }

    //Getter methods for testing
    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public double getCost() {
        return cost;
    }

    public int getTime() {
        return time;
    }
}