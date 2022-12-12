from collections import defaultdict, namedtuple
import gzip
import sys
sys.setrecursionlimit(10 ** 6)

class Vertex:
    def __init__(self):
        self.edges = []
        self.index = -1
        self.lowlink = -1
        self.on_stack = False 

class Graph:
    def __init__(self):
        self.graph_dict = defaultdict(Vertex)
        self.index = 0
        self.S = []
        self.SCCs = []
    
    def add_edge(self, u, v):
        self.graph_dict[u].edges.append(v)
        self.graph_dict[v]
        
    def tarjans(self):
        print("here")
        for v in self.graph_dict:
            if self.graph_dict[v].index == -1:
                self.SCC(v)
    
    def SCC(self, vertex):
        v = self.graph_dict[vertex]
        v.lowlink = self.index
        v.index = self.index
        self.index += 1
        v.on_stack = True
        self.S.append(vertex)
        for neighbor in v.edges:
            w = self.graph_dict[neighbor]
            if w.index == -1:
                self.SCC(neighbor)
                v.lowlink = min(v.lowlink, w.lowlink)
            elif w.on_stack:
                v.lowlink = min(v.lowlink, w.index)
        
        if v.lowlink == v.index:
            scc = []
            w = None
            while w != vertex:
                w = self.S.pop()
                scc.append(w)
                self.graph_dict[w].on_stack = False
            self.SCCs.append(scc)
            # if len(scc) > 3:
            #     print(scc)
    
def main():
    G = Graph()
    with gzip.open("links.srt.gz", mode="rb") as infile:
        for line in infile:
            # print(line)
            line = line.split()
            source = line[0]
            dest = line[1]
            G.add_edge(source, dest)
    G.tarjans()
    
            
    
if __name__ == "__main__":
    main()