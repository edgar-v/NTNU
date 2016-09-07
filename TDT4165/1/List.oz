fun {Length L}
    if L.2 == nil then
        1
    else
        1 + {Length L.2}
    end
end

fun {Take L C}
    if C == 0 then
        nil
    else
        L.1 | {Take L.2 C-1}
    end
end

fun {Drop L C}
    if C > 0 then
        {Drop L.2 C-1}
    else
        L
    end
end

fun {Append L1 L2}
    if L1.2 == nil then
        L1.1 | L2
    else
        L1.1 | {Append L1.2 L2}
    end
end

fun {Member L E}
    if L.1 == E then
        true
    elseif L.2 == nil then
        false
    else
        {Member L.2 E}
    end
end

fun {Position L E}
    if L.1 == E then
        1
    else
        1 + {Position L.2 E}
    end
end
