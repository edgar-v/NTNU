def viterbi(obs, states, start_p, trans_p, emit_p):
     V = [{}]
     for st in states:
         V[0][st] = {"prob": start_p[st] * emit_p[st][obs[0]], "prev": None}
   # Run Viterbi when t > 0
     for t in range(1, len(obs)):
         V.append({})
         for st in states:
             max_tr_prob = max(V[t-1][prev_st]["prob"]*trans_p[prev_st][st] for prev_st in states)
             for prev_st in states:
                 if V[t-1][prev_st]["prob"] * trans_p[prev_st][st] == max_tr_prob:
                     max_prob = max_tr_prob * emit_p[st][obs[t]]
                     V[t][st] = {"prob": max_prob, "prev": prev_st}
                     break
     for line in dptable(V):
         print line
     opt = []
     # The highest probability
     max_prob = max(value["prob"] for value in V[-1].values())
     previous = None
     # Get most probable state and its backtrack
     for st, data in V[-1].items():
         if data["prob"] == max_prob:
             opt.append(st)
             previous = st
             break
     # Follow the backtrack till the first observation
     for t in range(len(V) - 2, -1, -1):
         opt.insert(0, V[t + 1][previous]["prev"])
         previous = V[t + 1][previous]["prev"]
 
     print 'The steps of states are ' + ' '.join(opt) + ' with highest probability of %s' % max_prob
 
def dptable(V):
     # Print a table of steps from dictionary
     yield " ".join(("%12d" % i) for i in range(len(V)))
     for state in V[0]:
         yield "%.7s: " % state + " ".join("%.7s" % ("%f" % v[state]["prob"]) for v in V)

obs = ('Umbrella', 'Umbrella', 'NotUmbrella', 'Umbrella', 'Umbrella')
states = ('Raining', 'NotRaining')
start_p = {'Raining': 0.5, 'NotRaining': 0.5}
trans_p = {
   'Raining' : {'Raining': 0.9, 'NotRaining': 0.0},
   'NotRaining' : {'Raining': 0.0, 'NotRaining': 0.2}
   }
emit_p = {
   'Raining' : {'Umbrella': 0.7, 'NotUmbrella': 0.3},
   'NotRaining' : {'Umbrella': 0.3, 'NotUmbrella': 0.7}
   }

viterbi(obs, states, start_p, trans_p, emit_p)
