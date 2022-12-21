package sk.stuba.fei.uim.oop.Hra.Sanca;

import sk.stuba.fei.uim.oop.Hra.Hrac;

public class KartaPosunHraca extends KartaSance{

    private final int pocetPolicok;

    public KartaPosunHraca(String popis, boolean pouzita, int pocetPolicok) {
        super(popis, pouzita);
        this.pocetPolicok = pocetPolicok;
    }

    @Override
    public void ukon(Hrac naRade) {
        naRade.posunHraca(pocetPolicok);
    }
}
