3a.
select Tittel
from Bok;

3b.
select *
from Forfatter
where Nasjonalitet = 'Norsk';

3c.
select Forlagnavn, Telefon
from Forlag
where Adresse = 'Oslo' order by Forlagnavn asc;

3d.
select Bok.Tittel, Forlag.Forlagnavn
from Bok inner
join Forlag on Bok.Forlagid = Forlag.Forlagid;

3e.
select Bok.Tittel, Bok.Utgittår
from Bokforfatter
join Bok on (Bok.Bokid = Bokforfatter.Bokid)
join Forfatter on (Forfatter.Forfatterid = Bokforfatter.Forfatterid)
where Forfatter.Fornavn = 'Knut' and Forfatter.Etternavn = 'Hamsun';

3f.
select Fornavn, Etternavn, Fødeår
from Forfatter
where Etternavn like 'H%';

3g.
select count(Forlagid)
from Forlag;

3h.
select Bok.Tittel, Forfatter.Fornavn, Forfatter.etternavn, Forlag.Forlagnavn
from Bokforfatter
join Bok on (Bok.Bokid = Bokforfatter.Bokid)
join Forfatter on (Forfatter.Forfatterid = Bokforfatter.Forfatterid)
join Forlag on (Bok.Forlagid = Forlag.Forlagid)
where Forfatter.Nasjonalitet = 'Britisk'; 

3i.
select Forfatter.Fornavn, Forfatter.Etternavn, count(*) as antall
from  Forfatter left
join Bokforfatter on (Forfatter.Forfatterid = Bokforfatter.Forfatterid) left
join Bok on (Bok.Bokid = Bokforfatter.Bokid)
group by Forfatter.Fornavn, Forfatter.Etternavn order by antall desc;

3j.
select Tittel, Utgittår
from Bok
where Utgittår = (select min(utgittår) from bok);

3k.
select Forlag.Forlagnavn, count(*) as antall
from Bok inner
join Forlag on Bok.Forlagid = Forlag.Forlagid
group by Forlag.Forlagnavn having count(*) > 2;

3l.
select Forlag.Forlagnavn
from Forlag
where Forlag.Forlagid not in (select Bok.Forlagid from Bok);
