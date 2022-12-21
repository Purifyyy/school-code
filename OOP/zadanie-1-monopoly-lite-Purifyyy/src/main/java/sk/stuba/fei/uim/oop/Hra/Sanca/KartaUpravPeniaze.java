package sk.stuba.fei.uim.oop.Hra.Sanca;

import sk.stuba.fei.uim.oop.Hra.Hrac;

public class KartaUpravPeniaze extends KartaSance{

    private final int ciastka;

    public KartaUpravPeniaze(String popis, boolean pouzita, int ciastka) {
        super(popis, pouzita);
        this.ciastka = ciastka;
    }

    @Override
    public void ukon(Hrac naRade) {
        naRade.upravPeniaze(ciastka);
    }
}
