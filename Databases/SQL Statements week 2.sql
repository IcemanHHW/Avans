SELECT stad FROM bestemmingen;

SELECT stad, land FROM bestemmingen WHERE upper(land) = 'FRANKRIJK';

SELECT stad FROM bestemmingen WHERE stad LIKE 'M%';

SELECT voornaam, achternaam, geboortedatum FROM reizigers;

SELECT vertrekdatum, terugdatum FROM reizen;

SELECT vertrekdatum, terugdatum FROM reizen WHERE vertrekdatum > '2018-01-01';

SELECT id, voornaam, stad FROM contactpersonen WHERE stad LIKE 'H%' AND voornaam LIKE 'V%';

SELECT datediff(terugdatum, vertrekdatum) AS "duur van de reis" FROM reizen WHERE vertrekdatum = '2018-02-23';

SELECT id, vertrekdatum, terugdatum, datediff(terugdatum, vertrekdatum) AS "duur van de reis" FROM reizen WHERE vertrekdatum = '2017-01-29';

SELECt id, vertrekdatum, terugdatum, datediff(terugdatum, vertrekdatum) AS duur FROM reizen WHERE vertrekdatum = '2017-01-29' AND datediff(terugdatum, vertrekdatum) > 15;

SELECT voornaam, achternaam, floor(datediff(now(),geboortedatum)/365) AS leeftijd FROM reizigers;

SELECT voornaam, achternaam, floor(datediff(now(),geboortedatum)/365) AS leeftijd FROM reizigers WHERE floor(datediff(now(),geboortedatum)/365) < 18;
