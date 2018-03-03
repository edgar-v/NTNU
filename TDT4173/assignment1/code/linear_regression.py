import numpy as np
import matplotlib.pyplot as plt

# The ordinary least squares equation from equation 9
ordinary_least_squares = lambda x, y: np.dot(np.dot(np.linalg.inv(np.dot(x.transpose(),x)), x.transpose()), y)

# the mean squared error equation from equation 3
mean_squared_error = lambda w, x, y: sum(np.power(np.dot(w.transpose(), x[i]) - y[i], 2) for i in range(len(y))) / len(y)

def regression_2d():
    # Load training data, and separate the input(x) from the ouput(y)
    training_data = np.genfromtxt("datasets/regression/train_2d_reg_data.csv", delimiter=',')
    training_x = np.c_[np.ones(len(training_data)), training_data[:,:-1]] # Add column of ones to X
    training_y = training_data[:,-1]

    # Calculate the weights and the mean squared error of these weight on the training data
    weights = ordinary_least_squares(training_x, training_y)
    print("Weights:", weights.transpose())
    training_error = mean_squared_error(weights, training_x, training_y)
    print("Training data error:", training_error)

    # Calculate the mean squared error for the test data
    test_data = np.genfromtxt("datasets/regression/test_2d_reg_data.csv", delimiter=',')
    test_x = np.c_[np.ones(len(test_data)), test_data[:,:-1]]
    test_y = test_data[:,-1]
    test_error = mean_squared_error(weights, test_x, test_y)
    print("Test data error:", test_error)


def regression_1d():
    # Load training data, and separate the input(x) from the ouput(y)
    training_data = np.genfromtxt("datasets/regression/train_1d_reg_data.csv", delimiter=',')
    training_x = np.c_[np.ones(len(training_data)), training_data[:,:-1]] # Add column of ones to X
    training_y = training_data[:,-1]

    # Calculate the weights and the mean squared error of these weight on the training data
    weights = ordinary_least_squares(training_x, training_y)
    print("Weights:", weights.transpose())
    training_error = mean_squared_error(weights, training_x, training_y)
    print("Training data error:", training_error)

    # Calculate the mean squared error for the test data
    test_data = np.genfromtxt("datasets/regression/test_1d_reg_data.csv", delimiter=',')
    test_x = np.c_[np.ones(len(test_data)), test_data[:,:-1]]
    test_y = test_data[:,-1]
    test_error = mean_squared_error(weights, test_x, test_y)
    print("Test data error:", test_error)

    # Plot the data
    plt.scatter(test_x[:,[1]], test_y, label='Data')
    plt.plot(test_x[:,[1]], test_x[:,[0]] * weights[0] + test_x[:,[1]] * weights[1], color="red")
    plt.show()

print("2d_reg:")
regression_2d()
print("\n1d_reg:")
regression_1d()
