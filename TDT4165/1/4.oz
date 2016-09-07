functor
import
    System
define
    fun {Min X Y}
        if X < Y then
            X
        else
            Y
        end
    end

    fun {Max X Y}
        if X > Y then
            X
        else
            Y
        end
    end

    proc {PrintGreater X Y}
        {System.showInfo {Max X Y}}
    end

    {System.showInfo {Min 6 7}}
    {System.showInfo {Max 6 7}}
    {PrintGreater 8 1}
end

