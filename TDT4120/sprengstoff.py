from sys import stdin
val = 0
for line in stdin:
    val = max(int(line), val)
print(val)
