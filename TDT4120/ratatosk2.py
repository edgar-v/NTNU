from sys import stdin

funksjon = stdin.readline().strip()
antall_noder = int(stdin.readline())

nodes = [-1]*antall_noder
start = int(stdin.readline())
start = 0
ratatosk = int(stdin.readline())
for linje in stdin:
    tall = linje.split()
    rot = int(tall.pop(0))
    for barn in tall:
        nodes[int(barn)] = rot
def rec(node):
    if node == -1:
        return 0
    else:
        return rec(nodes[node]) + 1
    
print rec(ratatosk) - 1
