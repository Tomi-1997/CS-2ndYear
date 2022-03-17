from unittest import TestCase
from src.Factory import Factory
from src.GraphAlgo import GraphAlgo


class Test(TestCase):
    def test_get_graph(self):
        v = 4
        g = Factory.get_graph(v, "complete")
        # self.assertEqual(g.v_size(), v)
        # self.assertEqual(g.e_size(), v * (v-1))
        # ga = GraphAlgo(g)
        # ga.plot_graph()
