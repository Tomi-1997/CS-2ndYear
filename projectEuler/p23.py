if __name__ == '__main__':
    upper_lim = 28123

    l = [[] for i in range(upper_lim)] # assign each element a list of divisors
    for i in range(1, upper_lim):
        for k in range(i+i, upper_lim, i):
            l[k].append(i)

    l_sum = [sum(divs) for divs in l] # for each element, sum divisors
    abn_nums = [i for i,x in enumerate(l_sum) if x > i] # if sum is bigger than the num, add to list of abundant numbers

    written_as_abn = [i for i in range(upper_lim)]
    for i in abn_nums:
        for j in abn_nums:
            if i + j < upper_lim:
                written_as_abn[i + j] = 0

    ans = sum(written_as_abn)
