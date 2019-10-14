package com.jelle;

import java.awt.image.BufferedImage;

public class Decoder {
    private BufferedImage img;

    public Decoder(BufferedImage img) {
        this.img = img;
    }

    //Rood, groen of blauw is oneven. dit is de kleur die is aangepast.
    //De aanpassing is maximaal 10. De volgende in de rij geeft de wijziging aan. (dus bij oneven R geeft G de afwijking aan).
    //De afwijking tussen de laatste digit is de afwijking in totaal

    //30 tekens betekent 0-9 bij R, 10-19, bij G, 20-19 bij B.
    //Het oneven getal geeft aan dat het teken in de reks R, G of B gezocht moet worden
    //Bij oneven R zit de afwijking tussen 0-9
    //Bij oneven G zit de afwijking tussen 10-19
    //Bij oneven B zit de afwijking tussen 20-29

    //Wanneer de afwijking ook oneven is, is het oneven getal voor de even kleur het Stamgetal.
    //Bijvoorbeeld (R:135, G:142, B:213)
    //Het teken is dan 'b'
    //R en B zijn oneven. Maar het controle getal is altijd even.
    //R zit voor B, dus zit het getal in de reeks 0-9.
    //het verschil in het laatste cijfer van G en B is 1.
    //1 is 'b' in de keymap.

    /* TODO:
    - Een cypher afspreken. bijvoorbeeld de rgb componenten van de eerste 4 pixels van de bovenste rij bepalen de lengte van het bericht
        binair geschreven waarbij oneven staat voor 1 en even staat voor 0.
    - De volgende 2 pixels bepalen de afmeting van ene region, binair.
    - De volgende pixel bapaald de methode van sorteren, volgens een bepaalde afspraak, binair. 1 voor ascendig offset, 2 voor descending offset.
    - Meer sorteermanieren bedenken.
    - Decoden alleen van pixels met RGB componenten tussen 10 en 246.

     */

}
