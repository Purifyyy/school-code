package sk.stuba.fei.uim.oop.Hra.Policka;

public abstract class Policko {

    private final int pozicia;
    private final String meno;

    public Policko(int pozicia, String meno) {
        this.pozicia = pozicia;
        this.meno = meno;
    }

    public int getPozicia() {
        return pozicia;
    }

    public String getMeno() {
        return meno;
    }
}
