def number_of_lines(filename):
    fil = open(filename, "r")
    linjer = 0
    while fil.readline() != '':
        linjer += 1
    return linjer
#print(number_of_lines("nummer.txt"))

def number_frequency(filename):
    fil = open(filename, "r")
    frequency = {}
    line = fil.readline()
    while line != '':
        line = line.split()
        line = int(line[0])
        frequency.setdefault(line, 0)
        frequency[line] = frequency[line] + 1
        line = fil.readline()
    for i in frequency.items():
        string = str(i)
        string = string.replace("("," ")
        string = string.replace(")"," ")
        string = string.replace(","," ")
        string = string.split()
        print(string[0] + ":", string[1])
number_frequency("nummer.txt")
