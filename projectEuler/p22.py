if __name__ == '__main__':
    with open("p22.txt", "r") as f:
        names = f.read().replace("\"", "").split(',')

    names.sort()
    names_val = []

    for i, word in enumerate(names):
        val = 0
        for letter in word:
            val += ord(letter) - ord('A') + 1
        names_val.append(val * (i+1))

    ans = sum(names_val)

