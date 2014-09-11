import random

def pick_sentence(sentences):
    return sentences[random.randint(0, len(sentences) - 1)]

def intro_text():
    print("Velkommen til snakkeroboten")

def print_sentence(sentence, extra_info, tegn):
     print("{} {}{}".format(sentence, extra_info, tegn))

def main():

    question = ['Hva gjør du', 'Hvordan går det', 'Hvorfor heter du', 'Liker du å hete', 'Føler du deg bra', 'Hva har du gjort i dag', 'Hva tenker du om framtida', 'Hva gjør deg glad', 'Hva gjør deg trist']

    follow_ups = ['Hvorfor sier du', 'Hva mener du med', 'Hvor lenge har du sagt', 'Hvilke tanker har du om', 'Kan du si meg litt mer om', 'Når tenkte du første gang på']

    responses = ['Fint du sier det', 'Det skjønner jeg godt', 'Så dumt da', 'Føler meg sånn', 'Blir trist av det du sier', 'Så bra', 'Du er jammen frekk']
    
    intro_text()
    answer = input("Hva er navnet ditt? ")
    navn = answer
    while answer != "hade":

        next_sentence = pick_sentence(question)
        print_sentence(next_sentence, navn, "?")

        answer = input("Svar: ")

        next_sentence = pick_sentence(follow_ups)
        print_sentence(next_sentence, answer, "?")

        input("Svar: ")

        next_sentence = pick_sentence(responses)
        print_sentence(next_sentence, navn, ".")

main()

