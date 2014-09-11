def is_leap_year(year):
    if year % 400 == 0:
        return True
    elif year % 100 == 0:
        return False
    elif year % 4 == 0:
        return True

    return False

def weekday_newyear(year):
    day = 0
    current_year = 1900
    while year - current_year > 0:
        if is_leap_year(current_year):
            day += 2
        else:
            day += 1
        current_year += 1
    return day % 7



def is_workday(weekday):
    if weekday == 0 or weekday == 1 or weekday == 2 or weekday == 3 or weekday == 4:
        return True
    else:
        return False

def workdays_in_year(year):
    workdays = 0
    current_day = weekday_newyear(year)
   
    if is_leap_year(year):
        for i in range(0, 366):
            if is_workday(current_day):
                workdays += 1
            current_day = (current_day + 1) % 7
    else:
         for i in range(0, 365):
            if is_workday(current_day):
                workdays += 1
            current_day = (current_day + 1) % 7
    return workdays

for i in range(0, 20):
    weekday = weekday_newyear(1900 + i)
    workdays = workdays_in_year(1900 + i)
    if weekday == 0:
        print(1900 + i,"man" )
    if weekday == 1:
        print(1900 + i, "tir")
    if weekday == 2:
        print(1900 + i, "ons")
    if weekday == 3:
        print(1900 + i, "tor")
    if weekday == 4:
        print(1900 + i, "fre")
    if weekday == 5:
        print(1900 + i, "lor")
    if weekday == 6:
        print(1900 + i, "son")
    print("Workdays :", workdays)
