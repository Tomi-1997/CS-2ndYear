import random
from unittest import TestCase
from src.DiGraph import DiGraph
from src.GraphAlgo import GraphAlgo
import time
from src.Factory import Factory

debug = False


class Test(TestCase):

    def test2(self):
        g = DiGraph()
        for i in range(7):
            g.add_node(i)

        g.add_edge(0, 1, 1)
        g.add_edge(0, 4, 3)
        g.add_edge(1, 2, 1)
        g.add_edge(2, 3, 1)
        g.add_edge(4, 3, 3)
        g.add_edge(3, 5, 2)
        g.add_edge(5, 6, 2)
        g.add_edge(6, 0, 2)

        ga = GraphAlgo(g)
        ga.shortest_path(4, 0)
        for i in range(g.v_size()):
            for j in range(g.v_size()):
                print(f' i = {i}, j = {j}, {ga.shortest_path(i, j)}')

    # TSP On kinda big numbers
    def test3(self):

        # Tested originally on 10,000 nodes
        V = 5000
        g = Factory.get_graph(V)
        ga = GraphAlgo(g)
        dt = 0
        avg_dt = 0

        exp_num = 10
        for i in range(1,exp_num+1):
            ga.g = Factory.get_graph(V)
            start = time.time()

            # SHORTEST PATH
            a = random.randint(0,int(V/2))
            b = random.randint(int(V/2), V)
            ga.shortest_path(a,b)

            #TSP
            # rnd_list = [random.randint(0,V) for i in range(10)]
            # ga.TSP(rnd_list)

            end = time.time()
            avg_dt += (end-start)/exp_num
            dt += (end-start)
            print(f'{i} loop: {end-start}')

        ###### TESTING CENTER FROM JSON INPUT
        # for i in range(6):
        #     myString = "C:\\Users\\tomto\\PycharmProjects\\Ex3\data\\A"
        #     myString = myString + str(i) + ".json"
        #
        #     ga.load_from_json(myString)
        #     start = time.time()
        #     print(ga.centerPoint())
        #
        #     end = time.time()
        #     avg_dt += (end-start)/5
        #     dt += (end-start)

        print(f'Overall Runtime: {dt}')
        print(f'Average Runtime: {avg_dt}\n')

    def test_graph_algo(self):

        ######################################
        # Shortest path, tsp, center testing #
        ######################################

        start = time.time()
        g = DiGraph()
        g.add_node(0, (0, 3, 0))
        g.add_node(1, (3, 3, 0))
        g.add_node(2, (1.5, 1.5, 0))
        g.add_node(3, (3, 0, 0))
        g.add_node(4, (0, 0, 0))
        if debug is True: print(g.v_size())

        g.add_edge(0, 1, 1)
        g.add_edge(0, 2, 4)
        g.add_edge(0, 4, 5)
        g.add_edge(1, 2, 1)
        g.add_edge(2, 3, 1)
        g.add_edge(3, 4, 1)
        g.add_edge(4, 2, 5)
        if debug is True: print(g.e_size())

        ga = GraphAlgo(g)

        # for i in range(g.v_size()):
        #     for j in range(g.v_size()):
        #         print(f'i={i},j={j},\nShortest path= {ga.shortest_path(i,j)}')

        if debug is True: ga.plot_graph()

        ######################################
        # Load and save testing
        ######################################

        for i in range(5):
            myString = "C:\\Users\\tomto\\PycharmProjects\\Ex3\data\\A"
            myString = myString + str(i) + ".json"

            ga.load_from_json(myString)
            # ga.plot_graph()
            if debug is True:
                print("Loaded file:" + myString)
                ga.plot_graph()

        ga.load_from_json("C:\\Users\\tomto\\PycharmProjects\\Ex3\data\\T0.json")
        if debug is True: ga.plot_graph()

        end = time.time()
        self.assertTrue(end - start < 1)
        print(f'Seconds between start test and end: {end - start}')

    def test_load(self):

        # This will test loading graph from json, showing it,
        # saving it and then loading it again.

        ga = GraphAlgo
        # ga.load_from_json(self=ga, file_name="C:\\Users\\tomto\\PycharmProjects\\Ex3\\src\\Tests\\GraphTest.json")
        # ga.plot_graph(ga)
        # ga.save_to_json(ga,"deleteMe")
        # ga.load_from_json(ga,"deleteMe")
        # ga.plot_graph(ga)
        # if debug is True: print(ga.get_graph(ga))

    def test4(self):
        V = 100
        g = Factory.get_graph(V)
        ga = GraphAlgo(g)
        dt = 0
        avg_dt = 0

        exp_num = 10
        for i in range(1,exp_num+1):
            ga.g = Factory.get_graph(V)
            start = time.time()

            ga.centerPoint()

            end = time.time()
            avg_dt += (end-start)/exp_num
            dt += (end-start)
            print(f'{i} loop: {end-start}')