package sk.stuba.fei.uim.oop.Hra;

import sk.stuba.fei.uim.oop.Hra.Policka.Nehnutelnost;
import sk.stuba.fei.uim.oop.Hra.Policka.Policko;

import java.util.ArrayList;

public class Hrac {

    static int BONUS_ZA_PREJDENIE_STARTOM = 200;
    static int POCET_POLICOK = 23;

    private final String meno;
    private int peniaze;
    private ArrayList<Nehnutelnost> majetok = new ArrayList<>();
    private int pozicia;
    private int kolVazenia;
    private boolean jeUvazneny;
    private boolean maKartuUtekZVazenia;

    public Hrac(String meno, int pozicia, int peniaze, boolean jeUvazneny, int kolVazenia, boolean maKartuUtekZVazenia) {
        this.meno = meno;
        this.pozicia = pozicia;
        this.peniaze = peniaze;
        this.kolVazenia = kolVazenia;
        this.jeUvazneny = jeUvazneny;
        this.maKartuUtekZVazenia = maKartuUtekZVazenia;
    }

    public String getMeno() {
        return meno;
    }

    public int getPeniaze() {
        return peniaze;
    }

    public int getPozicia() {
        return pozicia;
    }

    public boolean kupNehnutelnost(Nehnutelnost n, int cena) {
        if (cena > peniaze)
        {
            return false;
        }
        else
        {
            peniaze-=cena;
            n.setMajitel(this);
            n.setZakupena(true);
            return majetok.add(n);
        }
    }

    public ArrayList<Nehnutelnost> getMajetok(){
        return majetok;
    }

    public String posunHraca(int pocet){
        pozicia += pocet;

        if (pozicia > POCET_POLICOK)
        {
            pozicia = pozicia - POCET_POLICOK - 1;
            peniaze += BONUS_ZA_PREJDENIE_STARTOM;
            System.out.println("Hráč " + meno + " prešiel štartom, a získal " + BONUS_ZA_PREJDENIE_STARTOM + ".");
        }
        return meno + " sa posunul o " + pocet +" políč-ko/-ka/-ok, a zastavil na políčku ";
    }

    public void presunHraca(Policko miesto){
        pozicia = miesto.getPozicia();
    }

    public boolean upravPeniaze(int ciastka){
        peniaze += ciastka;
        return peniaze >= 0;
    }

    public boolean zaplatHracovi(int ciastka, Hrac prijemnca){

        prijemnca.upravPeniaze(ciastka);
        peniaze -= ciastka;

        return peniaze >= 0;
    }

    public boolean jeVoVazbe(){
        kolVazenia--;
        if (kolVazenia < 0)
        {
            jeUvazneny = false;
        }
        else if (maKartuUtekZVazenia)
        {
            jeUvazneny = false;
            maKartuUtekZVazenia = false;
        }
        return jeUvazneny;
    }

    public String chodDoVazby(){
        jeUvazneny = true;
        kolVazenia = 3;
        return meno + " bol uväznený na 3 kolá!";
    }

    public void setMaKartuUtekZVazenia(boolean maKartuUtekZVazenia) {
        this.maKartuUtekZVazenia = maKartuUtekZVazenia;
    }
}
