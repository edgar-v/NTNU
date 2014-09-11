teeth = [95, 103, 71, 99, 114, 64, 95, 53, 97, 114, 109, 11, 2, 21,
        45, 2, 26, 81, 54, 14, 118, 108, 117, 27, 115, 43, 70, 58, 107]

coins = [20, 10, 5, 1, 0.5]

def find_fewest_coins(amount):
    current_amount = 0
    used_coins = [0]
    for i in range(0, len(coins) - 1):
        used_coins.append(0)
    while current_amount < amount:
        for i in range(0, len(coins)):
            if current_amount + coins[i] <= amount:
                current_amount += coins[i]
                used_coins[i] += 1
                break
    return used_coins

def printmynter(mynter, total):
    print("for {}kr har følgende mynter blitt brukt:".format(total))
    ut = ""
    for i in range(0, len(coins)):
        ut = ut + str(coins[i]) + "kr: " + str(mynter[i]) + "stk"
        if i != len(coins) - 1:
            ut += " - "
    print(ut, "\n")

for i in range(0, len(teeth)):
    mynter = find_fewest_coins(teeth[i])
    printmynter(mynter, teeth[i])
