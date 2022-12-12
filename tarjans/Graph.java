import java.io.*;
import java.util.*;
 
class Graph {

    private int index;
    private Map<String, Vertex> graph;
    private Stack<String> stack;
    private ArrayList<ArrayList<String>> sccs;

    public Graph(){
        this.index = 0;
        this.graph = new HashMap<String, Vertex>();
        this.stack = new Stack<String>();
        this.sccs = new ArrayList<ArrayList<String>>();
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

    void SCC(String v){
        Vertex vertex = graph.get(v);
        vertex.index = index;
        vertex.lowlink = index;
        vertex.onStack = true;
        index++;
        stack.push(v);
        for (String neighbor : vertex.edges){
            Vertex w = graph.get(neighbor);
            if(w.index == -1){
                SCC(neighbor);
                vertex.lowlink = Math.min(vertex.lowlink, w.lowlink);
            }
            else if(w.onStack){
                vertex.lowlink = Math.min(vertex.lowlink, w.index);
            }
        }
        if(vertex.lowlink == vertex.index){
            ArrayList<String> scc = new ArrayList<String>();
            String w = null;
            while (w == null || !w.equals(v)){
                w = stack.pop();
                scc.add(w);
                graph.get(w).onStack = false;
            }
            sccs.add(scc);
        }
    }

    void tarjans(){
        for (Map.Entry<String, Vertex> v : graph.entrySet()){
            if (v.getValue().index == -1){
                SCC(v.getKey());
            }
        }
    }

    public static void main(String args[]){
        Graph g = new Graph();

        String filename = "../links.srt";
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

        g.tarjans();
        Map<String, Integer> sizes = new HashMap<>();
        for(ArrayList<String> scc: g.sccs){
            for (String s: scc){
                if(s.equals("Breadth-first_search")){
                    System.out.println(scc);
                }
                sizes.put(s, scc.size());
            }
        }
        try{
            FileWriter fout = new FileWriter("../scc-ireland.txt");
            BufferedWriter writer = new BufferedWriter(fout);
            for(Map.Entry<String, Integer> entry: sizes.entrySet()){
                writer.write(entry.getKey() + "\t" + entry.getValue() + "\n");
            }
            writer.close();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        
    }
    


}