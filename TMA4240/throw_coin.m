function [KandM,S_sim] = throw_coin(throws, display, P_K)

% A function for throwing two norwegian coins (Krone=K, Mynt=M) which returns 
% the frequency of Ks and Ms, and the frequency of the possible combinations 
% in S={KK,KM,MK,MM} 
%
% INPUT PARAMETERS
%   throws: # of times we throw two coins
%   display: 0 or 1, whether the function should plot histogram (1) or not (0)
% OUTPUT PARAMETERS
%   KandM: how many Ks and Ms that have been tossed
%   S_sim: how many times each combination in S has occured


KandM = zeros(1,2);     % Stores how many Ks and Ms that have been thrown
S_sim = zeros(1,4);     % Stores the frequency of possible combinations, sample space S={KK,KM,MK,MM}  
          % Probability of throwing K (Krone) 

for i=1:throws          % We throw the coins 'throws' times, for each throw we do:
    
   u = rand(1,2);       % Draw two random numbers, u(1) and u(2), between 0 and 1 to simulate coin toss, 
                        % u<P_K gives K and u>=P_K gives M
   
   if(u(1)<P_K && u(2)<P_K)         % KK: both tosses, u(1) and u(2), result in K
       KandM = KandM + [2,0];
       S_sim(1) = S_sim(1)+1;
   elseif(u(1)<P_K && u(2)>=P_K)    % KM: tosses result in K for u(1) and then M for u(2)
       KandM = KandM + [1,1];
       S_sim(2) = S_sim(2)+1;
   elseif(u(1)>=P_K && u(2)<P_K)    % MK: tosses result in M for u(1) and then K for u(2)
       KandM = KandM + [1,1];
       S_sim(3) = S_sim(3)+1; 
   elseif(u(1)>=P_K && u(2)>=P_K)   % MM: both tosses, u(1) and u(2), result in M
       KandM = KandM + [0,2];
       S_sim(4) = S_sim(4)+1; 
   end

end


% The rest of the code is only for plotting, i.e. when 'display'=1
if(display==1)
    if(max(KandM)<1000)
        disp('     K    M')
        disp(KandM)
    else
        disp('          K           M')
        disp(KandM)
    end
    if(max(S_sim)<1000)
        disp('    KK    KM    MK    MM')
        disp(S_sim)
    else
        disp('         KK          KM          MK          MM')
        disp(S_sim)
    end
    
    subplot(1,2,1)
        bar(KandM);
        set(gca,'XTick',1:1:2)
        set(gca,'XTickLabel',{'K','M'})
    subplot(1,2,2)
        bar(S_sim);
        set(gca,'XTick',1:1:4)
        set(gca,'XTickLabel',{'KK','KM','MK','MM'})
    set(gcf,'Color',[1,1,1])    
end