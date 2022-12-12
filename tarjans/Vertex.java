import java.util.ArrayList;

public class Vertex {
    public ArrayList<String> edges;
    public int index;
    public int lowlink;
    public boolean onStack;
    
    public Vertex(){
        this.edges = new ArrayList<String>();
        this.index = -1;
        this.lowlink = -1;
        this.onStack = false;
    }
}
