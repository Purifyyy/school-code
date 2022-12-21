package sk.stuba.fei.uim.oop.Hra.Sanca;

import sk.stuba.fei.uim.oop.Hra.Hrac;

public abstract class KartaSance {

    private final String popis;
    private boolean pouzita;

    public KartaSance(String popis, boolean pouzita) {
        this.popis = popis;
        this.pouzita = pouzita;
    }

    public boolean isPouzita() {
        return pouzita;
    }

    public void setPouzita(boolean pouzita) {
        this.pouzita = pouzita;
    }

    public abstract void ukon(Hrac naRade);

    public String getPopis() {
        return popis;
    }
}
