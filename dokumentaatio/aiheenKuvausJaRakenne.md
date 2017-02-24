**Aihe:** Laivanupotuspeli. Toteutetaan peli, jossa yritetään upottaa vastustajan laivat. Peliä voi pelata tietokonetta vastaan, jolloin pelaaja ja tietokone aluksi asettavat laivansa ja sen jälkeen vuorotellen yrittävät upottaa vastustajan laivat. Peliä voi pelata myös toista pelaajaa vastaan, jolloin erona on se, että molemmat pelaajat asettavat laivansa peräjälkeen.

**Käyttäjät:** Pelaaja

**Pelaajan toiminnot:**

  * laivan asetus
    * onnistuu jos ruudussa ei ole jo laivaa, toinen laiva ei ole liian lähellä ja laiva on pelialueen sisäpuolella kokonaisuudessaan.
  * ampuminen
    * onnistuu jos koordinaatit ovat alueen sisäpuolella

**Rakenne**
Pelin logiikka rakentuu seitsemästä luokasta ja käyttöliittymä kahdeksasta. 
* Battleships luokka pyörittää pelin toiminnallisuutta ja käyttöliittymä hakeekin tarvitsemansa tiedot siltä. 
* Player-luokka on abstrakti ja sisältää kaikki toiminnallisuudet, jotka ovat yhteisiä molemmille pelaajatyypeille (tekoäly ja ihmispelaaja). Se pitää kirjaa pelaajan asettamista laivoista ja niiden sijainneista, sekä pelaajan ammuskelun tuloksista. 
* ShipValidator-luokka sisältää metodit sen varmistamiseen, että Player-luokan vastaanottamat laivat ovat sääntöjen mukaisia ja sääntöjen mukaisesti sijoittumassa pelaajan pelialueelle. 
* Person-luokka perii Player-luokan ja toteuttaa ainoastaan Player luokan abstraktin palaute-metodin, joka eroaa hieman tekoälyn vastaavasta metodista.
* Ai perii myös Player luokan, mutta palaute-metodin lisäksi luokka hakee järkevät ampumisen kohteet ja arpoo niistä jonkun ammuttavaksi (eli palautettavaksi Battleships luokalle). 
* Ship-luokka pitää sisällään yksittäisen laivan koordinaatit, koon, sekä osumat. 
* ShipBuilder-luokan tehtävä on luoda Ship-olioita parametrien mukaan, eli yhden koordinaatin, koon ja orientaation perusteella. Luokka siis tuottaa laivan loput koordinaatit siten, että laiva on eheä kokonaisuus. Luokka luo myös kokonaisia satunnaisesti sijoiteltuja laivastoja tuottamalla tietyn määrän satunnaisia aloituskoordinaatteja, joista rakennetaan kokonaisia laivoja. Tämä metodi saa parametrinä pelaajan, jolle laivat sijoitetaan suoraan. Battleships-luokka käyttää ShipBuilder-luokan ilmentymää luodakseen yksittäisiä laivoja käyttöliittymän lähettämien koordinaattien perusteella. Se luo myös aina pelin alussa laivaston tekoälylle ja ihmispelaajalle silloin kun on valittu laivojen satunnainen sijoittelu.
* Käyttöliittymä koostuu pääkäyttöliittymäluokasta, jossa pelin ilmentymää säilytetään. Sillä on myös metodi käyttöliittymän ikkunan näkymän muuttamiseksi. Jokaista näkymää kohti on oma luokkansa ja lisäksi kuuntelija kyseisen näkymän painikkeiden kuuntelemiseen. Jokainen näkymän luova luokka myös perii abstraktin luokan, joka sallii pääkäyttöliittymäluokan käsitellä niitä samanlaisina olioina. 

![luokkakaavio](http://yuml.me/09ad3fb5)

![sekvenssikaavio - laivan asettaminen](https://github.com/Sadelise/Battleships/blob/master/dokumentaatio/playerPlacingShip.png)
![sekvenssikaavio - Pelaaja 1:sen vuoro, jota seuraa automaattinen tekoälyn vuoro](https://github.com/Sadelise/Battleships/blob/master/dokumentaatio/turnPlayed.png)
