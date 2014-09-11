li = [1, 2, 3, 4, 5, 6]
for i in range(0, len(li) ):
    if li[i] % 2 == 0:
        li[i] *= -1
li = sorted(li, reverse=True)


print(li)
