package sk.stuba.fei.uim.oop.Hra.Sanca;

import java.util.ArrayList;
import java.util.Collections;

public class BalikKarietSance {

    ArrayList<KartaSance> karty = new ArrayList<>();

    public void pridajKartu (KartaSance k) {
        karty.add(k);
    }

    public void zamiesajBalik () {
        Collections.shuffle(karty);
    }

    public KartaSance resetujBalik() {
        for (KartaSance k : karty) {
            k.setPouzita(false);
        }
        zamiesajBalik();
        karty.get(0).setPouzita(true);
        return karty.get(0);
    }

    public KartaSance potiahniKartu () {
        int i = 0;
        while(i < karty.size())
        {
            if(!(karty.get(i).isPouzita()))
            {
                karty.get(i).setPouzita(true);
                return karty.get(i);
            }
            i++;
        }
        return resetujBalik();
    }
}
