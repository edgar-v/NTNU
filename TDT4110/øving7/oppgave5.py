## A ##
def match(a, b):
    for i in range(0, len(a)):
        if a[i] != b[i]:
            return False
    return True

print(match("asdfghjkl", "asdfghjkl"))
print(match("luliuhhkj", "hsjkadhk"))

## B ##
def reverse(a):
    b = ""
    for i in range(1, len(a) + 1):
        b += a[-i]
    return b

print(reverse("asdfghjkl"))

## C ##
def palindrome(a):
    n = int(len(a) / 2)
    if len(a) % 2 == 1:
        n = int(len(a) / 2) + 1

    for i in range(0, n + 1):
        if a[i] != a[-i - 1]:
            return False
    return True

print(palindrome("absba"))

## D ##

def substr(a, b):
    for i in range(0, len(a)):
        found = True
        for j in range(0, len(b)):
            if a[i + j] != b[j]:
                found = False
                break
        if found == True:
            return i
    return False

print(substr("asdfghjkl", "ghj"))
