import random
from src.GraphInterface import GraphInterface


class Node():
    """
    Builds a new node with given id and 3 co-ordinate position.
    if not position is given, gives it a defined (x,y,z)
    x : random integer from 0 to 100
    y : random integer from 0 to 100
    z ; 0 (always)
    """
    def __init__(self, id: int, pos: tuple = None):
        self.id = id
        if pos is None:
            x = random.randint(0, 100)
            y = random.randint(0, 100)
            pos = (x, y, 0)
        self.pos = pos

    def __repr__(self):
        return f'id={self.id},pos={self.pos}'


class DiGraph(GraphInterface):

    """
    Each graph has a dictionary of nodes.
    Each node has a dictionary of edges going out of him.
    """
    def __init__(self):
        self.nodes = {}
        self.edges = {}
        self.mc = 0

    def __str__(self):
        return f'Nodes: {self.nodes} \nEdges: {self.edges}'

    """
    Returns the number of vertices
    """
    def v_size(self) -> int:
        c = 0
        for node in self.nodes:
            c += 1
        return c

    """
    Returns the number of edges
    """
    def e_size(self) -> int:
        answer = 0
        for n in self.nodes:
            for e in self.edges[n]:
                answer += 1
        return answer

    """
    Returns a dictionary of the graph's nodes.
    """
    def get_all_v(self) -> dict:
        return self.nodes

    """
    Returns a dictionary of the graph's edges with given destination.
    """
    def all_in_edges_of_node(self, id1: int) -> dict:
        d = {}
        for n in self.nodes:
            e = self.edges[n].get(id1)
            if e is not None:
                d[n] = e
        return d


    """
    Returns a dictionary of the graph's edges with the given source.
    """
    def all_out_edges_of_node(self, id1: int) -> dict:
        return self.edges[id1]
    """
    Returns how many changes was made to the graph since it was created.
    """
    def get_mc(self) -> int:
        return self.mc

    """
    Adds an edge to the graph and returns true
    if any change was made
    """
    def add_edge(self, id1: int, id2: int, weight: float) -> bool:
        # Check for valid insertion, two real nodes and non-negative weight
        if id1 != id2 and id1 in self.nodes and id2 in self.nodes and weight >= 0:
            self.edges[id1][id2] = weight
            self.mc += 1
            return True
        return False

    """
    Add a node to the graph and returns true
    if any change was made
    """
    def add_node(self, node_id: int, pos: tuple = None) -> bool:
        # If node already exists, do nothing
        if node_id in self.nodes.keys():
            return False

        # valid input, create node and dict of every edge going out of it
        self.nodes[node_id] = Node(node_id, pos)
        self.edges[node_id] = {}
        self.mc += 1
        return True

    """
    Remove a node and all of the edges connected to it.
    """
    def remove_node(self, node_id: int) -> bool:
        if node_id in self.nodes:
            # Remove every edge that is coming into given node
            for n in self.nodes:
                self.remove_edge(n, node_id)

            # Remove node and with it the dict that holds each edge that is coming out of given node
            self.nodes.pop(node_id)
            self.mc += 1
            return True
        return False

    """
    Removes a single edge.
    """
    def remove_edge(self, node_id1: int, node_id2: int) -> bool:
        # Check if edge does exist
        if node_id2 in self.edges[node_id1]:
            self.edges[node_id1].pop(node_id2)
            self.mc += 1
            return True
        return False
