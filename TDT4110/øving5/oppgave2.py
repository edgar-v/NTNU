antall_kvinner = 0
antall_menn = 0
antall_sosmedier = 0
antall_facebook = 0
antall_timer_sosmedier = 0
while True:
    kjonn = input("Er du mann('m') eller kvinne('k'): ")
    if kjonn.lower() == "m":
        antall_menn = antall_menn + 1
    elif kjonn.lower() == "k":
        antall_kvinner = antall_kvinner + 1
    elif kjonn == "hade":
        break
    
    alder = input("Hvor gammel er du? ")
    if alder == "hade":
        break
    else:
        alder = int(alder)
    if alder < 16 or alder > 25:
        print("Du er for gammel til å ta denne testen")
        continue

    aktiv_sosmedier = input("Benytter du deg av ett eller flere sosiale medier? 'ja / nei':")
    if aktiv_sosmedier == "ja":
        antall_sosmedier += 1
        medlem_facebook = ""
        if kjonn == "k":
            medlem_facebook = input("Mellom 55-60% av Facebook sine brukere er kvinner. Er du en av disse?")
        else:
            medlem_facebook = input("Mellom 40-45% av Facebook sine brukere er menn. Er du en av disse?")
        
        if medlem_facebook == "ja":
            antall_facebook += 1
        if medlem_facebook == "hade":
            break
        timer_sosmedier = input("Hvor mange timer bruker du i snitt daglig på sosiale medier?")
        if timer_sosmedier == "hade":
            break
        else:
            timer_sosmedier = int(timer_sosmedier)
        antall_timer_sosmedier += timer_sosmedier
    elif aktiv_sosmedier == "hade":
        break
print("Antall kvinner:", antall_kvinner)
print("Antall Menn:", antall_menn)
print("Antall Sosiale Medier:", antall_sosmedier)
print("Antall Facebook-medlemmer:", antall_facebook)
print("Antal timer brukt på sosiale medier:", antall_timer_sosmedier)
    
