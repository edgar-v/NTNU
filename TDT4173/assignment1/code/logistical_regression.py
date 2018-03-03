import numpy as np
import matplotlib.pyplot as plt

# Sigmoid function
sigmoid = lambda z: 1 / (1 + np.exp(-z))

# Cross entropy function from equation 14.
cross_entropy_error = lambda w, x, y: -(1 / len(y)) * sum(y[i] * np.log(sigmoid(np.dot(w.transpose(), x[i]))) + (1 - y[i]) * np.log(1- sigmoid(np.dot(w.transpose(), x[i]))) for i in range(len(y)))

# Run regression for task 1
def regression_1():
    learning_rate = 0.1
    weights = np.array([0, 0, 0])
    num_iterations = 1000

    # Load training data, and separate the input(x) from the ouput(y)
    training_data = np.genfromtxt("datasets/classification/cl_train_1.csv", delimiter=',')
    # Add a column of ones(1) as the bias to the x
    training_x = np.c_[np.ones(len(training_data)), training_data[:,:-1]]
    training_y = training_data[:,-1]

    for iteration in range(num_iterations):
        # Calculate the weight for next iteration as in equation 20
        weights = weights - learning_rate * sum((sigmoid(np.dot(weights.transpose(), training_x[i])) - training_y[i]) * training_x[i] for i in range(len(training_data)))

    print("\tWeights:", weights)

    # Load training data, and separate the input(x) from the ouput(y)
    test_data = np.genfromtxt("datasets/classification/cl_test_1.csv", delimiter=',')
    # Add a column of ones(1) as the bias to the x
    test_x = np.c_[np.ones(len(test_data)), test_data[:,:-1]]
    test_y = test_data[:,-1]


    # Draw the blue and red dots
    true_values = np.array([test_x[i] for i in range(len(test_data)) if test_y[i]])
    false_values = np.array([test_x[i] for i in range(len(test_data)) if not test_y[i]])
    plt.scatter(true_values[:,[1]], true_values[:,[2]], color="blue", label="1")
    plt.scatter(false_values[:,[1]], false_values[:,[2]], color="red", label="0")

    # Decision line
    xs = np.linspace(0.0, 1.0, 1000)
    ys = [(-weights[1]*x - weights[0]) / weights[2] for x in xs]
    plt.plot(xs, ys, label="Decision boundary")

    plt.legend()
    plt.show()

# Run regression for task 2
def regression_2():
    learning_rate = 0.5
    weights = np.array([0, 0, 0, 0, 0])
    num_iterations = 1000

    # Load training data, and separate the input(x) from the ouput(y)
    training_data = np.genfromtxt("datasets/classification/cl_train_2.csv", delimiter=',')
    # Add a column of ones(1) as the bias to the x
    training_x = np.c_[np.ones(len(training_data)), training_data[:,:-1]]
    training_x = np.c_[training_x, np.power(training_x[:,1],2), np.power(training_x[:,2], 2)]
    training_y = training_data[:,-1]

    for iteration in range(num_iterations):
        # Calculate the weight for next iteration as in equation 20
        weights = weights - learning_rate * sum((sigmoid(np.dot(weights.transpose(), training_x[i])) - training_y[i]) * training_x[i] for i in range(len(training_data)))

    print("\tWeights:", weights)

    # Load training data, and separate the input(x) from the ouput(y)
    test_data = np.genfromtxt("datasets/classification/cl_test_2.csv", delimiter=',')
    # Add a column of ones(1) as the bias to the x
    test_x = np.c_[np.ones(len(test_data)), test_data[:,:-1]]
    test_y = test_data[:,-1]


    # Draw the blue and red dots for training set
    training_true_values = np.array([training_x[i] for i in range(len(training_data)) if training_y[i]])
    training_false_values = np.array([training_x[i] for i in range(len(training_data)) if not training_y[i]])
    plt.scatter(training_true_values[:,[1]], training_true_values[:,[2]], color="blue", label="training data 1")
    plt.scatter(training_false_values[:,[1]], training_false_values[:,[2]], color="red", label="training data 0")

    # Draw the blue and red dots for test set
    test_true_values = np.array([test_x[i] for i in range(len(test_data)) if test_y[i]])
    test_false_values = np.array([test_x[i] for i in range(len(test_data)) if not test_y[i]])
    plt.scatter(test_true_values[:,[1]], test_true_values[:,[2]], color="#00008B", label="test data 1")
    plt.scatter(test_false_values[:,[1]], test_false_values[:,[2]], color="#8B0000", label="test data 0")

    # Decision line
    xs = np.linspace(0.0, 1.0, 1000)
    y1 = [-1/(2*weights[4]) * (weights[2] + np.sqrt(weights[2]**2 -4 * weights[4] * (weights[1]*x + weights[3]*x**2 + weights[0]))) for x in xs]
    y2 = [-1/(2*weights[4]) * (weights[2] - np.sqrt(weights[2]**2 -4 * weights[4] * (weights[1]*x + weights[3]*x**2 + weights[0]))) for x in xs]
    plt.plot(xs, y1, color="blue", label="Decision boundary")
    plt.plot(xs, y2, color="blue")

    plt.legend()
    plt.show()

# Calucaltes the cross entropy error after every iteration and returns them as an array
def cross_entropy_calc(data_file, learning_rate, weights, num_iterations):

    # Load  data, and separate the input(x) from the ouput(y)
    data = np.genfromtxt(data_file, delimiter=',')
    # All columns but the last is x and we add a column of ones at the beginning
    x = np.c_[np.ones(len(data)), data[:,:-1]]
    # Get the last column as y
    y = data[:,-1]

    cross_errors = np.array([]).reshape(0,1)

    for iteration in range(num_iterations):
        # Calculate the weight for next iteration as in equation 20
        weights = weights - learning_rate * sum((sigmoid(np.dot(weights.transpose(), x[i])) - y[i]) * x[i] for i in range(len(data)))
        c = cross_entropy_error(weights, x, y)
        cross_errors = np.vstack([cross_errors, c])
    
    print("\tWeights:", weights)
    return cross_errors


def plot_cross_entropy():
    learning_rate = 0.01
    initial_weights = np.array([0, 0, 0])
    num_iterations = 1000
    training_cross_entropy = cross_entropy_calc("datasets/classification/cl_train_1.csv",
                                                learning_rate,
                                                initial_weights,
                                                num_iterations) 
    test_cross_entropy = cross_entropy_calc("datasets/classification/cl_test_1.csv",
                                            learning_rate,
                                            initial_weights,
                                            num_iterations) 
    xs = np.arange(0, num_iterations, 1)
    plt.plot(xs, training_cross_entropy, color="blue", label="Training set")
    plt.plot(xs, test_cross_entropy, color="red", label="Test set")

    plt.xlabel("Iterations")
    plt.ylabel("Cross entropy")
    plt.legend()
    plt.show()

print("First data set:")
regression_1()
print("Cross entropy of first data set:")
plot_cross_entropy()
print("Second data set corrected:")
regression_2()
