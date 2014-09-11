videoer = [
"http :// www . youtube . com / watch ? v = oKI - tD0L18A" ,
"http :// www . youtube . com / watch ? v = oHg5SJYRHA0" ,
"http :// www . youtube . com / watch ? v = 82LCKBdjywQ" ,
"http :// www . youtube . com / watch ? v = GpNSip5gyKo" ,
"http :// www . youtube . com / watch ? v = rHG - JO8gIGk" ,
"http :// www . youtube . com / watch ? v = OZBWfyYtYQY"
]

def shorten():
    short = []
    for i in range(0, len(videoer)):
        short.append("youtu.be/" + videoer[i][43:])
    return short
print(shorten())
