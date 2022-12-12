import math
import sys
from collections import Counter
import gzip

def write_dict_ordered(dict: dict, outfile):
    with open(outfile, "w") as file:
        for key in sorted(dict):
            file.write(str(dict[key]) + "\n" )

def has_converged(old_pr, new_pr, t):
    distance = 0
    for p in old_pr:
        distance += (old_pr[p] - new_pr[p]) ** 2
    distance = math.sqrt(distance)  
    return (distance < t)

def pagerank(dataset, l, t):
    old_pr = {}
    new_pr = {} 
    for p in dataset.keys():
        old_pr[p] = 1 / len(dataset)
        new_pr[p] = 10 #large so doesn't converge on first iteration
        
    i = 0
    while not has_converged(old_pr, new_pr, t):
        #Init old_pr
        old_pr = new_pr.copy() if i != 0 else old_pr
        for p in dataset.keys():
            new_pr[p] = l/len(dataset)
        #loop through dataset
        amt = 0
        for p in dataset.keys():
            p_outlinks = dataset[p]
            #distribute to outlinks
            if len(p_outlinks) > 0:
                for outlink in p_outlinks:
                    new_pr[outlink] += (1-l)*(old_pr[p] / len(p_outlinks))
            else:
                amt += (1-l)*(old_pr[p] / len(dataset))     
        #distibute to all
        for p in dataset.keys():
            new_pr[p] += amt 
        i += 1
    
    total = 0
    for pr in new_pr:
        total += new_pr[pr]
        
        
    print(total)
    return new_pr

if __name__ == '__main__':
    
    inputFile =  "links-ireland.srt.gz"
    lambda_val =  0.2
    tau =  0.005
    pagerankFile =  "pagerank.txt"
    # k =  100
    
    dataset = {}
    # inlink_counts = {}
    
    with gzip.open(inputFile, encoding="utf-8", mode="rt") as infile:
        #build dataset
        for line in infile:
            link = line.split()
            
            inlink = link[1]
            outlink = link[0]
            
            # if inlink not in inlink_counts:
            #     inlink_counts[inlink] = 1
            # else:
            #     inlink_counts[inlink] += 1
                
            if inlink not in dataset:
                dataset[inlink] = []
                
            if outlink not in dataset:
                dataset[outlink] = [inlink]
            else:
                dataset[outlink].append(inlink)
            
    # write_dict_ordered(inlink_counts, inLinksFile, k)
    
    pr = pagerank(dataset, lambda_val, tau)
    
    write_dict_ordered(pr, pagerankFile)