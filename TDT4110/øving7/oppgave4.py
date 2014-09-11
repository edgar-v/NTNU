## A ##
def is_prime(x):
    for i in range(2, x):
        if x % i == 0:
            return False
    return True

## B ##
def separate(numbers, treshold):
    a = []
    b = []
    for i in range(0, len(numbers)):
        if numbers[i] < treshold:
            a.append(numbers[i])
        else:
            b.append(numbers[i])
    return a, b

## C ##
def multiplication_table(n):
    tabell = []
    for i in range(1, n + 1):
        ny_rad = []
        for j in range(1, n + 1):
            ny_rad.append(i * j)
        tabell.append(ny_rad)
    return tabell

# testkode
a = multiplication_table(10)
for i in range(0, len(a)):
    b = ""
    for j in range(0, len(a)):
        b += str(a[i][j]) + " "
        if len(str(a[i][j])) == 1:
            b += " "
    print(b)
