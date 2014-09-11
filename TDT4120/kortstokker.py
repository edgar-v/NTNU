from sys import stdin
from itertools import repeat

def merge(decks):
    output = ''
    sortedlist = []
    sortedlist.append(decks[0][0])
    for keys in decks:
        for entries in keys:
            i = 0
            while entries[0] > sortedlist[i][0]:
                i += 1
                if i == len(sortedlist):
                    break
            sortedlist.insert(i, entries)

    for i in sortedlist:
        output += i[1]
    if len(output) == 1:
        return output
    else:
        return output[1:]

decks = []
for line in stdin:
    (index, list) = line.split(':')
    deck = zip(map(int, list.split(',')), repeat(index))
    decks.append(deck)
print merge(decks)
