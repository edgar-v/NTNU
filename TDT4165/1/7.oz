functor
import
    System
define
    \insert List.oz

    L = [1 4 6 9]
    J = [3 8 7 2]
    {System.show {Length L}}
    {System.show {Take L 3}}
    {System.show {Drop L 2}}
    {System.show {Append L J}}
    {System.show {Member L 2}}
    {System.show {Member J 2}}
    {System.show {Position J 2}}
end
