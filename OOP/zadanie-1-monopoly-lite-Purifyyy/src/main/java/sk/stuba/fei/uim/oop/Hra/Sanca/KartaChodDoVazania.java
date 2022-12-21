package sk.stuba.fei.uim.oop.Hra.Sanca;

import sk.stuba.fei.uim.oop.Hra.Hrac;


public class KartaChodDoVazania extends KartaSance{

    public KartaChodDoVazania(String popis, boolean pouzita) {
        super(popis, pouzita);
    }

    @Override
    public void ukon(Hrac naRade) {
        naRade.chodDoVazby();
    }
}
