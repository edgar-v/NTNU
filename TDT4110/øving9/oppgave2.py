import re

cheeses = {
  'cheddar':
      ('A235-4', 'A236-1', 'A236-2', 'A236-3', 'A236-5', 'C31-1', 'C31-2'),
  'mozarella':
      ('Q456-9', 'Q456-8', 'A234-5', 'Q457-1', 'Q457-2'),
  'brie':
      ('ZLAFS55-4', 'ZLAFS55-9', 'GOMBOS-7', 'A236-4'),
  'geitost':
      ('SPAZ-1', 'SPAZ-3', 'EMACS45-0'),
  'port salut':
      ('B15-1', 'B15-2', 'B15-3', 'B15-4', 'B16-1', 'B16-2', 'B16-4'),
  'camembert':
      ('A243-1', 'A234-2', 'A234-3', 'A234-4', 'A235-1', 'A235-2', 'A235-3'),
  'ridder':
      ('GOMBOS-4', 'B16-3'),
}

## A ##
print("## A ##")
print(cheeses['port salut'])

## B ##
print("## B ##")
infectedCheese = {}
for item in cheeses:
    a = cheeses[item]
    for i in range(0, len(a)):
        if a[i].find("A234") == 0 or a[i].find("A235") == 0 or a[i].find("B13") == 0 or a[i].find("B14") == 0 or a[i].find("B15") == 0:
            infectedCheese.setdefault(item, i)


print(infectedCheese.keys())

## C ##
print("## C ##")
a = []
b = list(cheeses.keys())
for i in cheeses.values():
    string = string = str(i)
    string = string.replace('(', ' ')
    string = string.replace(')', ' ')
    string = string.replace(',', ' ')
    string = string.replace('\'', ' ')
    a.append(string.split())
for i in range(0, len(a)):
    for j in range(0, len(a[i])):
        if a[i][j].find("A234") != 0 and a[i][j].find("A235") != 0 and a[i][j].find("B13") != 0 and a[i][j].find("B14") != 0 and a[i][j].find("B15") != 0:
            print(a[i][j], b[i]) 
