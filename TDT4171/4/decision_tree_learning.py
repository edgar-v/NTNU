#!/usr/bin/env python3

import random
import math
import copy

class Node():
    def __init__(self, attribute):
        self.attribute = attribute # The attribute this node splits on
        self.children = []
        self.classification = None # What choice lead us here

    def add_branch(self, tree, value):
        self.children.append(tree)
        tree.classification = value

    # Recursively print this node and it's children
    def print_tree(self, indent=0):
        if indent is 0:
            attribute = self.attribute
            print(f"A:{attribute}")
            for child in self.children:
                child.print_tree(1)
            return

        spacing = '\t'*indent
        classification = self.classification

        if len(self.children) > 0:
            attribute = self.attribute
            print(f"{spacing}C:{classification} A:{attribute}")

            for child in self.children:
                child.print_tree(indent+1)
        else:
            leaf = self.attribute
            print(f"{spacing}C:{classification} L:{leaf}")


# Finds the most common output from the examples
def plurality_value(examples):
    sum_1 = sum(1 for i in examples if i[-1] == '1')
    sum_2 = sum(1 for i in examples if i[-1] == '2')
    return Node(1) if sum_1 > sum_2 else Node(2)

# The decision-tree-learning algorithm from the book
def decision_tree_learning(examples, attributes, parent_examples, importance):
    if not examples:
        return plurality_value(parent_examples)
    elif all(examples[0][-1] == example[-1] for example in examples):
        return Node(int(examples[0][-1]))
    elif len(attributes) is 0:
        return plurality_value(examples)
    else:
        A = importance(attributes, examples)
        attributes.pop(A)

        tree = Node(A)

        for value in ['1', '2']:
            exs = [e for e in examples if e[A] == value]
            subtree = decision_tree_learning(exs, copy.deepcopy(attributes), examples, importance)
            tree.add_branch(subtree, value)

        return tree

# Remainder function from the book
def remainder(A, examples):
    remainder = 0.0
    for i in ['1', '2']:
        p_k = sum(1 for x in examples if x[-1] == i and x[A] == '1')
        n_k = sum(1 for x in examples if x[-1] == i and x[A] == '2')
        a = (p_k + n_k) / len(examples)
        b = B(p_k / (p_k + n_k))
        remainder += a * b

    return remainder

# «B» Equation from the book
def B(q):
    if q <= 1:
        return 0
    return -(q * math.log(q, 2) + (1 - q) * math.log(1 - q, 2))

# Finds the best attribute to split on
def expected_information_gain(attribute, examples):
    best_gain = 0
    best_attribute = 0
    for i in attribute.keys():
        p = sum(1 for x in examples if x[i] == '1')
        gain = 1 - remainder(i, examples)
        if gain > best_gain:
            best_gain = gain
            best_attribute = i
    return best_attribute

# Get a random attribute
def random_importance(attribute, examples):
    return random.choice(list(attribute.keys()))

# Run every test and report the results
def test_tree(root):
    tests = [line.split() for line in open("test.txt")]
    total_tests = len(tests)
    correct = 0

    for test in tests:
        tmp_root = root
        while len(tmp_root.children) > 0:
            for child in tmp_root.children:
                if child.classification == test[tmp_root.attribute]:
                    tmp_root = child
                    break
        if tmp_root.attribute == int(test[-1]):
            correct += 1
    percentage = int(correct / total_tests * 100)
    print(f"Result: {percentage}%")
            

def main():
    examples = [line.split() for line in open("training.txt", "r")]
    attributes = {x: [] for x in range(7)}

    for example in examples:
        for j in range(0, 7):
            if example[j] == "1":
                attributes[j].append(example[-1])


    print("legend:")
    print("A: Attribute, which attribute to split on")
    print("C: Classification, what the actual value was")
    print("L: Leaf node")
    print()

    print("Random:")
    root = decision_tree_learning(examples, copy.deepcopy(attributes), None, random_importance)
    root.print_tree()
    test_tree(root)

    print()
    print("Information gain:")
    root = decision_tree_learning(examples, copy.deepcopy(attributes), None, expected_information_gain)
    root.print_tree()
    test_tree(root)

if __name__ == '__main__':
    main()
