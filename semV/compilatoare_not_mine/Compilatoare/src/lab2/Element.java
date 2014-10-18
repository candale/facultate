package lab2;


public class Element {
    private String from;
    private String to;
    private  String cost;

    public Element(String from, String to, String cost) {
        this.from = from;
        this.to = to;
        this.cost = cost;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "Element{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", cost='" + cost + '\'' +
                '}';
    }
}
