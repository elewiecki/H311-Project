import java.io.*;
import java.util.*;
 
class Graph {

    private Map<String, Vertex> graph;
    private Queue<String> Q;
    private Stack<String> S;

    public Graph(){
        this.graph = new HashMap<String, Vertex>();
        this.Q = new LinkedList<String>();
        S = new Stack<String>();
    }

    void addEdge(String source, String dest){
        if(graph.get(source) == null){
            graph.put(source, new Vertex());
        }
        graph.get(source).edges.add(dest);

        if(graph.get(dest) == null){
            graph.put(dest, new Vertex());
        }
    }

    void brandes(){
        
        for(Map.Entry<String, Vertex> s: graph.entrySet()){
            //Single Source Shortest Paths
            //Initialization
            for (Vertex w: graph.values()){
                w.pred = new ArrayList<>();
                w.dist = Integer.MAX_VALUE;
                w.sigma = 0;
            }
            s.getValue().dist = 0;
            s.getValue().sigma = 1;
            Q.add(s.getKey());

            while(!Q.isEmpty()){
                String v = Q.remove();
                S.push(v);
                for(String w: graph.get(v).edges){
                    if(graph.get(w).dist == Integer.MAX_VALUE){
                        graph.get(w).dist = graph.get(v).dist + 1;
                        Q.add(w);
                    }
                    if(graph.get(w).dist == graph.get(v).dist + 1){
                        graph.get(w).sigma += graph.get(v).sigma;
                        graph.get(w).pred.add(v);
                    }
                }
            }

            //Accumulation
            for(Map.Entry<String, Vertex> v: graph.entrySet()){
                v.getValue().delta = 0;
            }
            while(!S.isEmpty()){
                String ws = S.pop();
                Vertex w = graph.get(ws);
                for(String t: w.pred){
                    Vertex v = graph.get(t);
                    v.delta = v.delta + ((double)v.sigma / w.sigma) * (1 + w.delta);
                }
                if(!ws.equals(s.getKey())){
                    w.CB += w.delta;
                }
            }
        }
    }

    public static void main(String args[]){
        Graph g = new Graph();

        String filename = "links-ireland.srt";
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null)
            {
                String[] parts = line.split("\\s+");
                String source = parts[0];
                String dest = parts[1];
                g.addEdge(source, dest);
            }
        }
        catch(Exception e){
            System.out.print(e.getMessage());
        }
        // System.out.println(g.graph.size());
        g.brandes();
        Map<String, Double> CBs = new HashMap<>();
        for(String s: g.graph.keySet()){
            CBs.put(s, g.graph.get(s).CB);
        }
        try{
            FileWriter fout = new FileWriter("../betweenness.txt");
            BufferedWriter writer = new BufferedWriter(fout);
            for(Map.Entry<String, Double> entry: CBs.entrySet()){
                writer.write(entry.getKey() + "\t" + entry.getValue() + "\n");
            }
            writer.close();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    


}