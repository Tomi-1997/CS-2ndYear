import random

from src.DiGraph import DiGraph

"""
Factory class intended to build a graph with various options.
First parameter is the number of vertices(int).
Second parameter is an attribute of the graph(String), such as: complete or connected.
"""


class Factory:

    @staticmethod
    def get_graph(vertices: int, attribute: str = None):
        g = DiGraph()

        for v in range(vertices):
            g.add_node(v)

        # For smaller graphs add V / 5 edges, for bigger add around twenty for each vertex.
        edge_num = min(20, int(vertices/5))
        for v in range(vertices):
            for i in range(edge_num):

                rand1 = random.randint(0,v)
                rand2 = random.randint(v,vertices-1)

                if random.randint(0,1) == 1:
                    rand1 = rand2

                g.add_edge(v, rand1, random.random())

        if attribute is not None:
            if attribute.lower().__contains__("complete"):
                for v1 in range(vertices):
                    for v2 in range(vertices):
                        if v1 != v2:
                            g.add_edge(v1, v2, random.randint(1,3))
            elif attribute.lower().__contains__("connected"):
                for v1 in range(vertices-1):
                    g.add_edge(v1, v1+1, random.randint(1,3))
                g.add_edge(vertices-1, 0, random.randint(1,3))
                pass
        else:
            pass

        return g
