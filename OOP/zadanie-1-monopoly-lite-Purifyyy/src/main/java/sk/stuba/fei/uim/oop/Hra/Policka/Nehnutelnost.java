package sk.stuba.fei.uim.oop.Hra.Policka;

import sk.stuba.fei.uim.oop.Hra.Hrac;

public class Nehnutelnost extends Policko {

    private boolean zakupena = false;
    private Hrac majitel;
    private final int cena;
    private final int stojne;

    public Nehnutelnost(int pozicia, String meno, int cena, int stojne) {
        super(pozicia,meno);
        this.cena = cena;
        this.stojne = stojne;
    }

    public Hrac getMajitel() {
        return majitel;
    }

    public void setMajitel(Hrac naRade){
        majitel = naRade;
    }

    public boolean isZakupena() {
        return zakupena;
    }

    public void setZakupena(boolean zakupena) {
        this.zakupena = zakupena;
    }

    public int getCena() {
        return cena;
    }

    public int getStojne() {
        return stojne;
    }
}
