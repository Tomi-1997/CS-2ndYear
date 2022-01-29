import networkx
import networkx as nx
if __name__ == '__main__':

    # read info from file into and dump into a graph
    with open("p67.txt", "r") as f:
        info = f.read().replace('\n',' ').split(' ')

    info_len = len(info)
    info = [int(info[i]) for i in range(info_len)] # from string to int

    # init graph
    gr = networkx.DiGraph()
    for i in range(info_len):
        gr.add_node(i)

    curr_line_num = 1
    last_line_elems = 100 # 15 for p18
    tri = 0
    j = 0

    # Connect edges in graph
    while j < last_line_elems - 1:

        for i in range(tri, tri + curr_line_num):
            left_son = i + curr_line_num
            right_son = i + curr_line_num + 1
            gr.add_weighted_edges_from([(i, left_son, info[left_son]), (i, right_son, info[right_son])])

        tri = tri + curr_line_num
        j += 1
        curr_line_num += 1


    # Calculate longest path from start to fin
    ans =networkx.algorithms.dag_longest_path(G= gr)
    print(f'path is: {ans}')
    sum = 0
    for i in ans:
        sum += info[i]
    print(f'path length is: {sum}')
