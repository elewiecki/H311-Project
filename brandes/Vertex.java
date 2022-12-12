import java.util.ArrayList;

public class Vertex {
    public ArrayList<String> edges;
    public int dist;
    public ArrayList<String> pred;
    int sigma;
    double delta;
    double CB;
    
    public Vertex(){
        this.edges = new ArrayList<String>();
        this.dist = Integer.MAX_VALUE;
        this.pred = new ArrayList<>();
        this.sigma = 0;
        this.delta = 0;
        this.CB = 0;
    }
}
