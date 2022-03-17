# Directed Weighted Graph- Python version
This project aims to construct a graph, perform certain alogirthms on it, and project it via matplotlib. <br />
Our method of representing a graph with a group of edges and vertices, G (V,E) is with a dictionary of nodes. <br />
Each node entry contains a unique integer id, tuple of (x,y,z), and a dictionary for the edges going out of him. <br />

## How it looks
![DEMO](https://github.com/Tomi-1997/CS-2ndYear/blob/main/GraphExamples.png)

## Algorithm implementations
| Algorithm | Description |
| --- | --- |
| Shortest Path | Returns the lowest cost of travel between two nodes, and a list with the path between them. |
| TSP | Given a list of nodes, returns an approximation of the shortest path that passes through them. |
| Center | Returns the node that for each other node, has the minimal path to all. |

## Time analysis
_Initialization:_  <br />
| Elements | Time in seconds |
| --- | --- |
| 1,000 | 0.01 |
| 10,000 | 0.02 |
| 100,000 | 0.2 |
| 1,000,000 | 2 |

_Shortest distance between two vertices:_ <br />
| Elements | Time in seconds |
| --- | --- |
| 1,000 | 0.0673 |
| 10,000 | 9.4420 |
| 100,000 | 2792.4380 (46 minutes) |
| 1,000,000 | x |

_TSP:_  <br />
| Elements | Time in seconds |
| --- | --- |
| 100 | 0.0096 |
| 1,000 | 0.6250 |
| 10,000 | 85.7481 |
| 100,000 | x |

_Center:_  <br />
| Elements | Time in seconds |
| --- | --- |
| 10 | 0.0023 |
| 50 | 0.7673 |
| 100 | 10.2599 |
| 500 | x |

# Comparing results to the previous java project
As seen below, we can see major differences. <br /> 
We believe it is entirely because in this build we've implemented Dijkstra with a minimum heap.
![CEN](https://github.com/Tomi-1997/CS-2ndYear/blob/main/CENTER_P_VS_J.png)
![TSP](https://github.com/Tomi-1997/CS-2ndYear/blob/main/TSP_P_VS_J.png)
![SHORTEST](https://github.com/Tomi-1997/CS-2ndYear/blob/main/SHORTEST_P_VS_J.png)


