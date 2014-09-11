from sys import stdin, stderr

def antallIsolerteStier(kapasiteter, startrom, utganger):
    flow = []
    count = 0
    nodes_used = [0 for n in kapasiteter]
    for n in kapasiteter:
        row = [0 for n in kapasiteter]
        flow.append(row)
    for i in range(len(startrom)):
        for j in range(len(utganger)):
            road = finnFlytsti(startrom[i], utganger[j], flow, kapasiteter, nodes_used)
            if road == None:
                continue
            count += 1
            for k in range(len(road) - 1):
                a = road[k]
                b = road[k+1]
                nodes_used[a] = 1
                nodes_used[b] = 1
                flow[a][b] = 1
                flow[b][a] = 1
    return count

def finnFlytsti(kilde, sluk, F, C, used):
    n = len(F)
    oppdaget = [False] * n
    forelder = [None] * len(F)
    koe = [kilde]
    while koe:
        node = koe.pop(0)
        if node == sluk:
            # Har funnet sluken, lager en array med passerte noder
            sti = []
            i = node
            while True:
                sti.append(i)
                if used[i] == 1:
                    return None
                if i == kilde:
                    break
                i = forelder[i]
            sti.reverse()
            return sti;
        for nabo in range(n):
            if not oppdaget[nabo] and F[node][nabo] < C[node][nabo]:
                koe.append(nabo);
                oppdaget[nabo] = True;
                forelder[nabo] = node;
                
    return None

noder, _, _ = [int(x) for x in stdin.readline().split()]
startrom = [int(x) for x in stdin.readline().split()]
utganger = [int(x) for x in stdin.readline().split()]
nabomatrise = []
for linje in stdin:
    naborad = [int(nabo) for nabo in linje.split()]
    nabomatrise.append(naborad)
print (antallIsolerteStier(nabomatrise, startrom, utganger))
