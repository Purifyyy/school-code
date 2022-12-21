package sk.stuba.fei.uim.oop.Hra.Policka.Rohove;

import sk.stuba.fei.uim.oop.Hra.Hrac;
import sk.stuba.fei.uim.oop.Hra.Policka.Policko;

public class PlatbaDane extends Policko {

    private final int poplatok;

    public PlatbaDane(int pozicia, String meno, int poplatok) {
        super(pozicia, meno);
        this.poplatok = poplatok;
    }

    public boolean zaplatDan(Hrac naRade){
        return naRade.upravPeniaze(-(poplatok));
    }

    public int getPoplatok() {
        return poplatok;
    }
}
