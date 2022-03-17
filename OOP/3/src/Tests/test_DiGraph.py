from unittest import TestCase
from src.DiGraph import DiGraph


class TestDiGraph(TestCase):

    """
    In this test class we are testing these functions:
    v_size , e_size , get all v, get all edge from and to v from V
    get mode counter , add edge, remove edge, add node and remove node.
    """
    global g
    g = DiGraph()

    def test1(self):
        # Build graph with ten nodes
        for node in range(10):
            g.add_node(node)
        # Connect one node to all other nodes
        # 0 -- > 1 , 0 -- > 2 and so on
        for edge in range(1, 10):
            g.add_edge(0, edge , 1)

        self.assertEqual(g.v_size(), 10)
        self.assertEqual(g.e_size(), 9)

        # get all V test
        all_v = g.get_all_v()
        for node in all_v:
            self.assertIn(node , g.nodes)

        # get all edges that go into 0. this should be empty
        all_v = g.all_in_edges_of_node(0)
        self.assertTrue(len(all_v) == 0)

        # get all edges that go out of 0, this should be all of them
        all_v = g.all_out_edges_of_node(0)
        self.assertTrue(len(all_v) == 9)

        # remove node that isn't 0, now edge size should be 8.
        g.remove_node(1)
        self.assertEqual(g.e_size(), 8)

        # remove node 0 that all edges go through him, now edge size should be 0
        g.remove_node(0)
        self.assertEqual(g.e_size(), 0)

    def test2(self):
        g = DiGraph()
        for i in range(100):
            g.add_node(i)

        self.assertEqual(g.mc, 100)
        g.add_edge(0, 1, 1)
        self.assertEqual(g.mc, 101)

        # Make sure that node 0 has an entry for edge with dest 1
        self.assertTrue( 1 in g.edges[0])

        # After removing node 1, make sure node 0 has no entry for that edge anymore
        g.remove_node(1)
        self.assertFalse( 1 in g.edges[0])

        self.assertEqual(g.mc, 103)
