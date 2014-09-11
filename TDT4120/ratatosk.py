from sys import stdin

def main():
    level = 0
    stdin.readline()
    stdin.readline()
    root = stdin.readline().strip()
    ratatosk = stdin.readline().strip()
    
    foreldre = {}

    for line in stdin:
        barn_liste = line.split()
        far = barn_liste.pop(0)
        for barn in barn_liste:
            foreldre[barn] = far
        while ratatosk in foreldre:
            ratatosk = foreldre[ratatosk]
            level += 1
        if ratatosk == root:
            print(level)
            return

main()
