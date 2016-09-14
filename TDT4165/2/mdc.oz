functor
import
    System
define
    \insert List.oz

    fun {Lex Input}
        {String.tokens Input & }
    end

    fun {Tokenize Lexemes}
        {Map Lexemes
        fun {$ Lexeme}
            if Lexeme == "+" then
                operator(type:plus)
            elseif Lexeme == "-" then
                operator(type:minus)
            elseif Lexeme == "*" then
                operator(type:multiply)
            elseif Lexeme == "/" then
                operator(type:divide)
            elseif Lexeme == "p" then
                command(print)
            elseif Lexeme == "d" then
                command(duplicate)
            elseif Lexeme == "i" then
                command(sign)
            elseif Lexeme == "^" then
                command(invert)
            else 
                try number({String.toFloat Lexeme})
                catch _ then raise "Invalid lexeme: "#Lexeme#"" end
                end
            end
        end}
    end

    fun {Interpret Tokens}
        fun {Iterate Stack Tokens}
            case Tokens of nil then
                Stack
            [] number(Float)|Tokens then
                {Iterate {Append Stack number(Float)} Tokens}
            [] operator(type:Operator)|Tokens then
                number(H)|number(T)|_ = {Drop Stack {Length Stack}-2} in
                    if Operator == plus then
                        {Iterate {Append {Take Stack {Length Stack}-2} number(H+T)} Tokens}
                    elseif Operator == minus then
                        {Iterate {Append {Take Stack {Length Stack}-2} number(H-T)} Tokens}
                    elseif Operator == multiply then
                        {Iterate {Append {Take Stack {Length Stack}-2} number(H*T)} Tokens}
                    elseif Operator == divide then
                        {Iterate {Append {Take Stack {Length Stack}-2} number(H div T)} Tokens}
                    end
            [] command(Command)|Tokens then
                if Command == print then
                    {System.show Stack} 
                    {Iterate Stack Tokens}
                elseif Command == duplicate then
                    {Iterate {Append Stack {Last Stack}} Tokens}
                elseif Command == sign then
                    number(H)|_ = {Drop Stack {Length Stack}-1} in
                        {Iterate {Append {Take Stack {Length Stack}-1} number(~H)} Tokens}
                elseif Command == invert then
                    number(H)|_ = {Drop Stack {Length Stack}-1} in
                        {Iterate {Append {Take Stack {Length Stack}-1} number(1.0 / H)} Tokens}
                end
            end
        end
    in
        {Iterate nil Tokens}
    end

    fun {ShuntInternal Tokens OperatorStack OutputStack}
        local Top PrevTop OpStack OutStack in 
            if {Length OperatorStack}>1 then
                if {OpLeq {Drop OperatorStack {Length OperatorStack}-1}.1 {Take {Drop OperatorStack {Length OperatorStack}-2} 1}.1} then
                   Top = {Drop OperatorStack {Length OperatorStack}-1}.1
                   PrevTop = {Take {Drop OperatorStack {Length OperatorStack}-2} 1}.1
                    OpStack = {Append {Take OperatorStack {Length OperatorStack}-2} Top} 
                    OutStack = {Append OutputStack PrevTop}
                    {ShuntInternal Tokens OpStack OutStack}
                else
                    case Tokens of nil then
                        {Append OutputStack OperatorStack}
                    [] operator(type:Operator)|Tokens then
                        {ShuntInternal Tokens {Append OperatorStack operator(type:Operator)} OutputStack}
                    [] number(Float)|Tokens then
                        {ShuntInternal Tokens OperatorStack {Append OutputStack number(Float)}}
                    end 
                end
            else
                case Tokens of nil then
                    {Append OutputStack OperatorStack}
                [] operator(type:Operator)|Tokens then
                    {ShuntInternal Tokens {Append OperatorStack operator(type:Operator)} OutputStack}
                [] number(Float)|Tokens then
                    {ShuntInternal Tokens OperatorStack {Append OutputStack number(Float)}}
                end
            end
        end
    end

    fun {OpLeq Pushing Top}
        if {Or Pushing.type==multiply Pushing.type==divide} then
            if Top.type == plus then
                false
            elseif Top.type == minus then
                false
            else
                true
            end
        elseif {Or Top.type==multiply Top.type==divide} then
            if {Or Pushing.type == multiply Pushing.type==divide} then false
            else true
            end
        else true
        end
    end

    fun {Shunt Tokens}
        {ShuntInternal Tokens nil nil}
    end

    %{System.show {Interpret {Tokenize {Lex "3.0 10.0 9.0 * - 0.3 +"}}}}
    %{System.show {Shunt {Tokenize {Lex "3.0 - 10.0 * 9.0 + 0.3"}}}}
    {System.show {Interpret {Shunt {Tokenize {Lex "3.0 - 10.0 * 9.0 + 0.3"}}}}}


end

% 1: V = {S,A,N,O,C}
%    S = {+,-,/,*,p,i,d,^,[0-9],.}
%    R = {S -> N} 
%        {N -> A}
%        {   | [0-9]N}
%        {   | .N}
%        {   | ε}
%        {A -> AA}
%        {   | N}
%        {   | O}
%        {   | C}
%        {O -> +}
%        {   | -}
%        {   | *}
%        {   | /}
%        {   | ε}
%        {C -> p}
%        {   | d}
%        {   | i}
%        {   | ^}
%        {   | ε}
%    Vs = S

% 2: V = {Expression, Number, Operator Int}
%    S = {+,-,*,/,[0-9],.}
%    R = {S ::= Expression
%        Expression ::= Number Operator Number
%                     | Expression Operator Expression
%                     | Number
%        Number ::= Int . Int
%        Int ::= [0-9] Int
%              | ε
%        Operator ::= +
%                   | -
%                   | *
%                   | /
%    Vs = Expression
% 
% Ambigious
% 
% 3: in a context-free grammar, all rules are of the form:
% v ::= γ
% where v is a variable in V and γ is any sequence of variables and symbols
% from V ∪ S.
% In a context-sensitive grammar, all rules are of the form:
% αvβ ::= αγβ
% where v is any variable from V, and α, β and γ are any sequences of
% variables and symbols from V ∪ S.
%  
% 4: To make the programmer aware that he is doing floating point arithmetic.
