import json
from typing import List
import matplotlib.pyplot as plt
from src.GraphAlgoInterface import GraphAlgoInterface
from src.GraphInterface import GraphInterface
from src.DiGraph import DiGraph, Node
import heapq

DEBUG = False


class GraphAlgo(GraphAlgoInterface):

    def __init__(self, g: GraphInterface):
        self.g = g

    def get_graph(self) -> GraphInterface:
        return self.g

    def load_from_json(self, file_name: str) -> bool:
        my_dict = {}
        g = DiGraph()

        with open(file_name, "r") as f:
            my_dict = json.load(f)

        # Load all node info from list to graph,
        for node in my_dict["Nodes"]:
            # Null exception if node has no keyword 'pos', solution: generate random pos in Node class.
            try:
                x, y, z = str(node["pos"]).split(",")
                g.add_node(node["id"], (float(x), float(y), float(z)))
            except:
                g.add_node(node["id"])

        for edge in my_dict["Edges"]:
            g.add_edge(edge["src"], edge["dest"], edge["w"])

        self.g = g
        return True

    def save_to_json(self, file_name: str) -> bool:

        jsonGraph = {}
        nodes = []
        edge = []

        # Convert to a list of nodes
        for n in self.g.nodes.values():
            jsonNode = {}
            if n.pos is None:
                jsonNode["id"] = n["id"]
            else:
                pos_str = str(n.pos).replace("(", "").replace(")", "")
                jsonNode["pos"] = pos_str
                jsonNode["id"] = n.id
            nodes.append(jsonNode)
        jsonGraph["Nodes"] = nodes

        for n in self.g.nodes.keys():
            for e in self.g.all_out_edges_of_node(n).items():
                jsonEdge = {"src": n, "w": e[1], "dest": e[0]}
                edge.append(jsonEdge)
        jsonGraph["Edges"] = edge

        with open(file_name, "w") as f:
            json.dump(jsonGraph, fp=f, indent=2)
            # json.dump(self, fp=f, indent=4, default=lambda obj: obj.__dict__)

    def shortest_path(self, id1: int, id2: int) -> (float, list):

        # Invalid input
        if id1 not in self.g.nodes or id2 not in self.g.nodes:
            return float('inf'), []
        # Same node
        if id1 == id2:
            return 0, []
        # Valid input
        # We will use a Min-Heap to store the cost of traveling to certain node.

        if DEBUG: print(f'Source = {id1}, Destination = {id2}')
        # Make dict with size of vertices, initialize with cost "infinity"
        heap = dict({(i, float('inf')) for i in range(self.g.v_size())})
        cost_info = [float('inf') for i in range(self.g.v_size())]
        # Make source with cost 0
        heap[id1] = 0
        cost_info[id1] = 0
        if DEBUG: print(f'Heap at start:{heap}')

        # The element at a[k] is the parent of a[k]
        # -1 if no parent.
        parents = {i: -1 for i in range(self.g.v_size())}
        parents[id1] = 0
        if DEBUG: print(f'Parent list at start:{parents}\n')

        current = self.g.nodes[id1].id
        # Start dijkstra
        run = True
        while run:
            if DEBUG: print(f'Current node:{current}')
            # Get all edges coming from current node
            for edge in self.g.all_out_edges_of_node(current).items():
                if DEBUG: print(f'Edges coming out of {current}: {edge}')
                # New vertex cost is min of previous or current path
                weight = edge[1]
                dst = edge[0]

                if cost_info[current] + weight < cost_info[dst]:
                    cost_info[dst] = cost_info[current] + weight
                    parents[dst] = current

                    if dst in heap:
                        heap[dst] = cost_info[dst]

            heap.pop(current)
            temp = list(heap.items())
            temp.sort(key=lambda x: x[1])
            heap = dict(temp)

            if DEBUG:
                print(f'Heap now:{heap}')
                print(f'Cost info now:{cost_info}')
                print(f'Parent list now:{parents}\n')

            if len(heap) == 0:
                break

            current = min(heap, key=heap.get)
            if heap[current] == float('inf'):
                break

        answer = []
        current = id2
        run = True

        # Make list out of parent database
        while run:
            if parents[current] == -1:
                return float('inf'), []
            if DEBUG:
                print(f'Node = {current}, his parent = {parents[current]}')
            answer.append(current)
            current = parents[current]
            run = current != id1

        answer.append(id1)
        answer.reverse()
        return cost_info[id2], answer

    def TSP(self, node_lst: List[int]) -> (List[int], float):
        tsp = []
        cost = 0
        length = len(node_lst)

        # For node list (v1, v2, v3, ..., vn)
        # get shortest path between v1->v2, v2->v3, ... up to vn and join them.
        # Remove last element because it will be added at the start at the next iteration.
        for n in range(length - 1):
            current = self.shortest_path(n, n + 1)
            cost += current[0]
            tsp.extend(current[1])

            if n != length - 1:
                tsp.remove(tsp[-1])

        return tsp, cost

    def centerPoint(self) -> (int, float):
        max_distance = []
        max = -1

        for n1 in self.g.get_all_v():
            for n2 in self.g.get_all_v():
                if n1 != n2:
                    dt = self.shortest_path(n1, n2)[0]
                    if dt != float('inf') and dt > max:
                        max = dt
            max_distance.append(max)
            max = -1

        min_of_max = float('inf')
        index = 0
        for i in range(len(max_distance)):
            if max_distance[i] == -1:
                continue
            if max_distance[i] < min_of_max:
                min_of_max = max_distance[i]
                index = i

        return index, min_of_max

    def plot_graph(self) -> None:
        ball_size = "."
        for node in self.g.get_all_v().values():

            # First draw each node
            x, y, z = node.pos
            plt.plot(x, y, markersize=10, marker=ball_size, color="cyan")

            # Then draw each edge going out of current node
            for e in self.g.all_out_edges_of_node(node.id).items():
                dx, dy, dz = self.g.nodes[e[0]].pos
                plt.annotate("", xy=(x, y), xytext=(dx, dy), arrowprops=dict(arrowstyle="<-"))

            # Make node id appear above all
            plt.text(x, y, f"v{node.id}", color="red", fontsize=13)

        plt.show()
