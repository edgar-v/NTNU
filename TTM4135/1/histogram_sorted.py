#!/usr/bin/env python

import matplotlib.pyplot as plt
import sys
import collections
import numpy as np

def main(argv):
    f = open(argv[1], "r").read().lower()
    allow = "abcdefghijklmnopqrstuvwxyz"
    freq = dict()
    for k in allow:
        freq[k] = 0

    for l in f:
        if l.lower() in freq:
            freq[l.lower()] += 1
    
    freq = sorted([(key,  freq[key] / (len(f)) * 100) for key in freq], key=lambda x: x[1], reverse=True)

    r = np.arange(len(freq)) * 2
    freq = list(zip(*freq))
    plt.bar(r, freq[1])
    plt.xticks(r + 0.5, freq[0])
    plt.xlabel("character")
    plt.xlim(xmin=-1)
    plt.ylabel("%")
    plt.savefig("histogram_sorted_" + argv[1][0] + ".png")

if __name__ == '__main__':
    main(sys.argv)
