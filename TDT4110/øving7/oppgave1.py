import math

def polynom(x):
    return (x ** 5) - 4 * (x ** 3) + 10 * (x ** 2) - 10
    #return ( 1 / 4 ) * (x ** 2) + math.cos(2 * x)
    #return math.copysign(1, x) * math.sqrt(abs(x))

def polynom_derivative(x):
    return 5 * (x ** 4) - 12 * (x ** 2) + 20 * x
    #return (1 / 2) * x - 2 * math.sin(2 * x)
    #return (1 / 2) * ( 1 / math.sqrt(abs(x)))


def newton(func, deriv, start, treshold, max_iterations):
    prev = start
    neste = 0
    for i in range(0, max_iterations + 1):
        neste = prev - (func(prev) / deriv(prev))
        if (abs(neste - prev) <= treshold):
            return neste;
        else:
            prev = neste
    return False

print(newton(polynom, polynom_derivative, 1, 0.001, 20))
