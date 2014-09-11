import math


############ A #############
def lesInput():
    a, b, c = map(float, input("Enter 3 componets: ").split())
    return a, b, c

############ B #############
def printVector(a):
    print("x:", a[0])
    print("y:", a[1])
    print("z:", a[2])

vec1 = lesInput()
printVector(vec1)

############ C ##############
def skalarmult(vektor, skalar):
    a = [float(vektor[0] * skalar), float(vektor[1] * skalar), float(vektor[2] * skalar)]
    return a

############ D ##############
def length(vektor):
    lengde = math.sqrt(pow(vektor[0], 2) + pow(vektor[1], 2) + pow(vektor[2], 2))
    return lengde

############ E ##############
def indreprodukt(vec1, vec2):
    return vec1[0] * vec2[0] + vec1[1] * vec2[1] + vec1[2] + vec2[2]

print("Length before scalar multiplication:", length(vec1))
skalar = float(input("Skalar: ").replace(',', '.'))
vec2 = vec1
vec1 = skalarmult(vec1, skalar)
printVector(vec1)
print("Length after scalar multiplication:", length(vec1))
print("Relationship between the two lengths:", length(vec1) / length(vec2))

vec2 = lesInput()
print("Indreprodukt av vec1 og vec2:", indreprodukt(vec1, vec2))

