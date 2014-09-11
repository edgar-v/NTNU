def gcd(a, b):
   while b != 0:
       gammel_b = b
       b = a % b
       a = gammel_b
   return a

def reduce_fraction(a, b):
    d = gcd(a, b)
    a /= d
    b /= d
    return int(a), int(b)

a = int(input("teller: "))
b = int(input("nevner: "))
a, b = reduce_fraction(a, b)
print(a, "/", b)
