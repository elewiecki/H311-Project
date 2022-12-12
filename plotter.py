import matplotlib.pyplot as plt
import numpy as np
from collections import Counter

SCC_DATA_PATH = "scc-ireland.txt"
PAGERANK_DATA_PATH  = "pagerank.txt"
BETWEENNESS_DATA_PATH= "betweenness.txt"

def plot_data(lst1, name1, lst2, name2):
    a, b = np.polyfit(lst1, lst2, 1)
    plt.scatter(lst1, lst2)
    plt.plot(lst1, a*np.array(lst1)+b)
    plt.xlabel(name1)
    plt.ylabel(name2)
    plt.title(name1 + " VS. " + name2)
    plt.savefig(name1 + " VS. " + name2 + ".png")


def main():
    sccs = {}
    with open(SCC_DATA_PATH) as file:
        for line in file:
            line = line.split()
            sccs[line[0]] = int(line[1])
    lst_sccs = []
    for i in sorted(sccs):
        lst_sccs.append(sccs[i])
    # sccs = OrderedDict(sorted(sccs.items()))
    
    betweenness = {}
    with open(BETWEENNESS_DATA_PATH) as file:
        for line in file:
            line = line.split()
            betweenness[line[0]] = float(line[1])
            
    lst_betweenness = []
    for i in sorted(betweenness):
        lst_betweenness.append(betweenness[i])
        
    # with open("betweenness-sorted.txt", "w") as out:
    #     counted = Counter(betweenness)
    #     for key, value in counted.most_common(100):
    #         out.write(str(key) + "\t" + str(value) + "\n" )
        
    lst_pagerank = []
    with open(PAGERANK_DATA_PATH) as file:
        for line in file:
            lst_pagerank.append(float(line))

    # i = 0
    # for e in range(len(lst_pagerank)):
    #     if i >= len(lst_pagerank):
    #         break
    #     if lst_pagerank[i] > 0.00008:
    #         lst_pagerank.pop(i)
    #         lst_betweenness.pop(i)
    #     else:
    #         i += 1

    plot_data(lst_sccs, "SCC Size", lst_pagerank, "PageRank Value")

if __name__ == "__main__":
    main()