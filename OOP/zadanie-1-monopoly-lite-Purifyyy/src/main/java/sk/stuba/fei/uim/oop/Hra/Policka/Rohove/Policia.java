package sk.stuba.fei.uim.oop.Hra.Policka.Rohove;

import sk.stuba.fei.uim.oop.Hra.Hrac;
import sk.stuba.fei.uim.oop.Hra.Policka.Policko;

public class Policia extends Policko {

    public Policia(int pozicia, String meno) {
        super(pozicia, meno);
    }

    public String uvazniHraca(Hrac naRade, Policko vazenie){
        naRade.presunHraca(vazenie);
        return naRade.chodDoVazby();
    }
}
