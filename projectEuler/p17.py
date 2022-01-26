if __name__ == '__main__':
    # list of numbers taken from https://math.tools/table/counting/1-1000,
    # copied into txt file and then removed anything that isn't a letter from the abc
    with open("temp.txt", "r") as f:
        info = f.read()

    less_info = ""
    for c in info:
        if c.islower():
            less_info += c

    print(len(less_info) + len("and") * 99 * 9)
