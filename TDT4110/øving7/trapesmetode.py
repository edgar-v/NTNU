import math
def trapezoidal_rule(f, a, b, n):
    h = (b-a) / n
    s = f(a) + f(b)
    for i in range(1, n):
        s += 2 * f(a + i * h)
    return s * h / 2

def func(x):
    return math.exp(-x**2)

print(trapezoidal_rule(func, 0.0, 10.0, 4))
