import math
def simpson(func, a, b, h):
    n = (b - a) / h
    s = func(a)

    for i in range(1, h, 2):
        s += 4 * func((a + n) * i)
    for i in range(2, h - 1, 2):
        s += 2 * func((a + n) * i)

    s += func(b)
    f = (n / 3) * s
    return f

def funksjon(x):
    return math.exp(-x ** 2)

def simpson_error(func, a, b, error):
    i = 1
    while abs(simpson(func, a, b, 2 ** i) - simpson(func, a, b, 2 ** (i + 1))) > error:
        i += 1
    return simpson(func, a, b, 2 ** i)

#print(simpson(funksjon, 0, 1, 1000))
print(simpson_error(funksjon, 0, 1, 10 ** -8))
