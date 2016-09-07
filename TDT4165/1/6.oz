functor
import
    System
define
    fun {Factorial N} A in
        if N > 1 then
            A = {Factorial N-1} * N
        else
            A = 1
        end
        A
    end
    {System.showInfo {Factorial 10}}
end
