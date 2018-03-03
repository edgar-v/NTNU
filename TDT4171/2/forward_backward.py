#!/usr/bin/env python3

import numpy as np

#  Observational array
O = np.array([[0.9, 0.0],
              [0.0, 0.2]])

# Transition array
T = np.array([[0.7, 0.3],
              [0.3, 0.7]])

def forward(fw, evidence):

    if evidence is False:
        local_O = np.identity(len(O)) - O
    else:
        local_O = O

    # Eq. 15.12
    result = np.dot(local_O, np.transpose(T))
    result = np.dot(result, fw)

    # Normalize
    result = result / result.sum()
    
    return result

def backward(bw, evidence):
    if evidence is False:
        local_O = np.identity(len(O)) - O
    else:
        local_O = O

    # Eq. 15.13
    result = np.dot(T, local_O)
    result = np.dot(result, bw)

    return result

# Implement the Forward-Backward algorithm from Fig. 15.4
def main():

    ev = [True, True, False, True, True] # Evidence values
    prior = np.array([0.5, 0.5]) # The prior distribution on the initial state
    t = len(ev) + 1# Number of steps

    fv = np.array([None] * t) # vector of forward messages
    fv[0] = prior

    print("Forward: ")
    for i in range(1, t):
        fv[i] = forward(fv[i-1], ev[i-1])
        print(i, fv[i])

    sv = np.array([None] * t) # vector of smoothed estimates
    sv[0] = prior

    b = np.array([1, 1]) # representation of the backward message

    print("Backward: ")
    for i in range(t-1, -1, -1):
        sv[i] = fv[i] * b
        sv[i] = sv[i] / sv[i].sum()
        print(i, b)
        b = backward(b, ev[i-1])

if __name__ == '__main__':
    main()
