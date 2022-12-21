package sk.stuba.fei.uim.oop.Hra.Sanca;

import sk.stuba.fei.uim.oop.Hra.Hrac;

public class KartaUtekZVazenia extends KartaSance{

    public KartaUtekZVazenia(String popis, boolean pouzita) {
        super(popis, pouzita);
    }

    @Override
    public void ukon(Hrac naRade) {
        naRade.setMaKartuUtekZVazenia(true);
    }
}
