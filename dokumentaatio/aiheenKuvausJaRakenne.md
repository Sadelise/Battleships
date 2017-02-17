**Aihe:** Laivanupotuspeli. Toteutetaan peli, jossa yritetään upottaa vastustajan laivat. Peliä voi pelata tietokonetta vastaan, jolloin pelaaja ja tietokone aluksi asettavat laivansa ja sen jälkeen vuorotellen yrittävät upottaa vastustajan laivat.

**Käyttäjät:** Pelaaja

**Pelaajan toiminnot:**

  * laivan asetus
    * onnistuu jos ruudussa ei ole jo laivaa tai toinen laiva ei ole liian lähellä
  * ampuminen
    * onnistuu jos koordinaatit ovat alueen sisäpuolella

![luokkakaavio](http://yuml.me/48ffd75a)

![sekvenssikaavio - laivan asettaminen](https://github.com/Sadelise/Battleships/blob/master/dokumentaatio/playerPlacingShip.png)
![sekvenssikaavio - Pelaaja 1:sen vuoro, jota seuraa automaattinen tekoälyn vuoro](https://github.com/Sadelise/Battleships/blob/master/dokumentaatio/turnPlayed.png)
