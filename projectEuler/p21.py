if __name__ == '__main__':
    end = 10000
    l = [[] for i in range(end)]

    for i in range(1, end):
        for k in range(i+i, end, i):
            l[k].append(i)

    l_sum = [sum(divs) for divs in l]
    l_sum[1] = 1

    # for i, val in enumerate(l_sum):
    #     print(f'num: {i}, sum of divisors: {val}.')

    ans = [x for i,x in enumerate(l_sum) if x < end and x != i and l_sum[x] == i]

    # print(ans)
    print(sum(ans))