# Independent-Storylines
This repo contains the code for finding Independent Storylines in a given story. The story is formalised as a undirected simple graph where, characters are the vertices of the graph and the edge represents the number of co-occurances between the characters( 0 if no co-occurance). The algorithm is implemented using DFS on undirected graphs.

There are two additional functions available in the code:
1. Average:- print the average number of characters each character is associated with, as a float upto two decimal places.
2. Rank:- print a sorted list of all characters, with comma as delimiter (only comma, as delimiter and no space).

To call average function use, ```java A4_2019CS10349 nodes.csv edges.csv average```

To call rank function use, ```java A4_2019CS10349 nodes.csv edges.csv rank```

To call independent storylines function use, ```java A4_2019CS10349 nodes.csv edges.csv independent_storylines_dfs```




---
