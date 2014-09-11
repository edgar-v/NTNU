n = int(input("Enter n: "))

total = 0
n_element = 0

while n > total:
    total += pow(n_element + 1, 2)
    n_element += 1
total -= pow(n_element, 2)
n_element -= 1

print(total, n_element)
