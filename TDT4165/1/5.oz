functor
import
    System
define
    local
        PI A D C
    in
        proc {Circle R}
            PI = 355.0 / 113.0
            A = PI * {Int.toFloat R} * {Int.toFloat R}
            D = 2 * R 
            C = PI * {Int.toFloat D}
            {System.printInfo "A: "}
            {System.showInfo A}
            {System.printInfo "D: "}
            {System.showInfo D}
            {System.printInfo "C: "}
            {System.showInfo C}
        end
    end
    {Circle 10}
end
