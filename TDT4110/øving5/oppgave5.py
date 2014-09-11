def fibonacci(n):
    fib = [0, 1]
    for i in range(2, n + 1):
        fib.append(fib[i - 1] + fib[i - 2])

    return fib[n]

def rekfib(n):
    if n == 1:
        return 1
    elif n == 0:
        return 0
    else:
        return rekfib(n-1) + rekfib(n-2
        )
mode = input("rekursivt('r') eller iterativt('i'): ")
if mode == 'r':
    print(rekfib(int(input("fibonacci tall n: "))))
else:
    print(fibonacci(int(input("fibonacci tall n: "))))
    

