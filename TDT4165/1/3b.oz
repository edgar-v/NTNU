functor
import
    System
define
    X = "This is a string"
    thread {System.showInfo Y} end
    Y = X
end

% showInfo can not print Y before it is assigned.
% It waits for Y=X to execute first.
