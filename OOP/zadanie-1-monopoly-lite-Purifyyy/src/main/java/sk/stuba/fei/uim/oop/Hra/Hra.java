package sk.stuba.fei.uim.oop.Hra;

import sk.stuba.fei.uim.oop.Hra.Policka.Nehnutelnost;
import sk.stuba.fei.uim.oop.Hra.Policka.Policko;
import sk.stuba.fei.uim.oop.Hra.Policka.Rohove.PlatbaDane;
import sk.stuba.fei.uim.oop.Hra.Policka.Rohove.Policia;
import sk.stuba.fei.uim.oop.Hra.Policka.Rohove.Start;
import sk.stuba.fei.uim.oop.Hra.Policka.Rohove.Vazenie;
import sk.stuba.fei.uim.oop.Hra.Policka.Sanca;
import sk.stuba.fei.uim.oop.Hra.Sanca.*;
import sk.stuba.fei.uim.oop.KeyboardInput;

import java.util.ArrayList;
import java.util.Scanner;

public class Hra {

    private ArrayList<Policko> policka = new ArrayList<>(24);
    private ArrayList<Hrac> hraci = new ArrayList<>();
    //private Hrac naRade;
    private int pocetHracov;
    private BalikKarietSance kartySance = new BalikKarietSance();

    public Hra() {
        zostavHraciuPlochu();
    }

    public void zostavHraciuPlochu() {
        policka.add(0,new Start(0,"Štart"));
        policka.add(1,new Nehnutelnost(1, "Zochova ulica",80, 55));
        policka.add(2,new Nehnutelnost(2, "Dunajská ulica",100, 90));
        policka.add(3,new Sanca(3,"Šanca"));
        policka.add(4,new Nehnutelnost(4, "Karadžičova ulica",100, 90));
        policka.add(5,new Nehnutelnost(5, "Špitálska ulica",140, 125));
        policka.add(6,new Vazenie(6,"Väzenie"));
        policka.add(7,new Nehnutelnost(7, "Dostojevského rad",180, 165));
        policka.add(8,new Sanca(8,"Šanca"));
        policka.add(9,new Nehnutelnost(9, "Štúrova ulica",200, 180));
        policka.add(10,new Nehnutelnost(10, "Vajnorská ulica",220, 195));
        policka.add(11,new Nehnutelnost(11, "Palisády",240, 215));
        policka.add(12,new PlatbaDane(12,"Platba daňe",200));
        policka.add(13,new Sanca(13,"Šanca"));
        policka.add(14,new Nehnutelnost(14, "Račianska ulica",260, 220));
        policka.add(15,new Nehnutelnost(15, "Košická ulica",265, 230));
        policka.add(16,new Nehnutelnost(16, "Šancová ulica",220, 195));
        policka.add(17,new Nehnutelnost(17, "Americké námestie",300, 260));
        policka.add(18,new Policia(18,"Polícia"));
        policka.add(19,new Nehnutelnost(19, "Mlynské Nivy",310, 275));
        policka.add(20,new Nehnutelnost(20, "Einsteinova ulica",320, 280));
        policka.add(21,new Sanca(21,"Šanca"));
        policka.add(22,new Nehnutelnost(22, "Obchodná ulica",350, 295));
        policka.add(23,new Nehnutelnost(23, "Hlavné námestie",400, 340));

        kartySance.pridajKartu(new KartaUpravPeniaze("Gratulujeme, vyhral si v lotérií 250.",false,250));
        kartySance.pridajKartu(new KartaUpravPeniaze("Dostávaš pokutu za vysokú rýchlosť vo výške 120.",false,-120));
        kartySance.pridajKartu(new KartaUtekZVazenia("Získal si kartu Útek z väzenia, pri najbližšom uväznení políciou, budeš ignorovať trest stánia 3 kôl.",false));
        kartySance.pridajKartu(new KartaChodDoVazania("Choď do väzenia, neprechádzaj štartom, a nevyberaj si bonus za prejdenie štartom.",false));
        kartySance.pridajKartu(new KartaPosunHraca("Presuň sa o 3 políčka vpred, ak zastavíš na Nehnuteľnosti nemôžeš ju zakúpiť, ale nemusíš ani platiť stojné.",false,3));
    }

    public void inicializaciaHracov() {
        boolean uspech = false;
        String vstup;
        while(!uspech){
            System.out.print("Zadaj počet hráčov (2-4): ");
            vstup = KeyboardInput.readString();
            try {
                pocetHracov = Integer.parseInt(vstup);
                if(pocetHracov < 2 || pocetHracov > 4)
                {
                    System.out.println("Zadaný vstup nieje v rozsahu 2-4, skús to znova.");
                }
                else
                {
                    uspech = true;
                }
            }
            catch (NumberFormatException e)
            {
                System.out.println("Zadaný vstup nieje číslo.");
            }
        }
        for(int i=0;i<pocetHracov;i++)
        {
            System.out.format("Zadaj meno pre hráča č.%d: ",i+1);
            vstup = KeyboardInput.readString();
            hraci.add(new Hrac(vstup,0,1000, false, 0, false));
        }
    }

    public void stlacEnterPrePokracovanie()
    {
        System.out.println("Stlač ENTER pre hod kockou...");
        Scanner s = new Scanner(System.in);
        s.nextLine();
    }

    public int hodKockou() {
        return (int) (Math.random() * 6) + 1;
    }

    public void uvolniNehnutelnostiHraca(ArrayList<Nehnutelnost> majetok) {
        for (Nehnutelnost i : majetok)
        {
            i.setMajitel(null);
            i.setZakupena(false);
        }
    }

    public int vstupPriKupe(){
        boolean uspech = false;
        String vstup;
        int vystup = 0;
        while(!uspech){
            vstup = KeyboardInput.readString();
            try {
                vystup = Integer.parseInt(vstup);
                if(vystup != 0 && vystup != 1)
                {
                    System.out.println("Zadaný vstup nieje jedna z možností, skús to ešte raz. (1 - áno / 0 - nie)");
                }
                else
                {
                    uspech = true;
                }
            }
            catch (NumberFormatException e)
            {
                System.out.println("Zadaný vstup nieje číslo.");
            }
        }
        return vystup;
    }

    public boolean odohrajKolo(Hrac naRade) {
        boolean dohralHracKolo = true;
        int vstupHraca;
        String vypisStavu;
        System.out.format("-----------------------------------------------------%n%n");
        System.out.println("Na rade je " + naRade.getMeno() + ", má " + naRade.getPeniaze() + " peňazí, a stojí na políčku " + policka.get(naRade.getPozicia()).getMeno() + ".");
        if(!naRade.jeVoVazbe())
        {
            stlacEnterPrePokracovanie();
            vypisStavu = naRade.posunHraca(hodKockou());
            var polickoKdeStojiHrac = policka.get(naRade.getPozicia());
            System.out.println(vypisStavu + polickoKdeStojiHrac.getMeno() + ".");

            if(polickoKdeStojiHrac instanceof Nehnutelnost)
            {
                if(((Nehnutelnost) polickoKdeStojiHrac).isZakupena())
                {
                    if(((Nehnutelnost) polickoKdeStojiHrac).getMajitel() != naRade){
                        System.out.println(polickoKdeStojiHrac.getMeno() + " je zakúpená hráčom " + ((Nehnutelnost) polickoKdeStojiHrac).getMajitel().getMeno() + ", ktorému platíš stojné "
                                + ((Nehnutelnost) polickoKdeStojiHrac).getStojne() + ".");
                        if(!naRade.zaplatHracovi(((Nehnutelnost) polickoKdeStojiHrac).getStojne(),((Nehnutelnost) polickoKdeStojiHrac).getMajitel())) {
                            dohralHracKolo = false;
                        }
                    }
                    else { System.out.println("Nehnuteľnosť " + polickoKdeStojiHrac.getMeno() + " patrí tebe, stojné teda neplatíš.");}
                }
                else
                {
                    System.out.println("Máš záujem o kúpu nehnuteľnosti " + polickoKdeStojiHrac.getMeno() + ", cena je " + ((Nehnutelnost) polickoKdeStojiHrac).getCena() + "." +" (1 - áno / 0 - nie)?");
                    vstupHraca = vstupPriKupe();
                    //vstupHraca = readInt("Máš záujem o kúpu nehnuteľnosti " + polickoKdeStojiHrac.getMeno() + " (1 - áno / 0 - nie)?");
                    if(vstupHraca == 1)
                    {
                        if(naRade.kupNehnutelnost((Nehnutelnost) polickoKdeStojiHrac,((Nehnutelnost) polickoKdeStojiHrac).getCena())) {
                            System.out.println("Úspešne si zakúpil nehnuteľnosť " + polickoKdeStojiHrac.getMeno() + ".");
                        }
                        else { System.out.println("Nemáš dosť penazí na zakúpenie tejto nehnuteľnosti!"); }
                    }
                }
            }
            else if (polickoKdeStojiHrac instanceof Sanca)
            {
                var k = kartySance.potiahniKartu();
                System.out.println(k.getPopis());
                k.ukon(naRade);
                if(k instanceof KartaChodDoVazania)
                {
                    naRade.presunHraca(policka.get(6));
                }
                else if(k instanceof KartaUpravPeniaze)
                {
                    if(naRade.getPeniaze() < 0)
                    {
                        dohralHracKolo = false;
                    }
                }
            }
            else if (polickoKdeStojiHrac instanceof Vazenie)
            {
                System.out.println(naRade.getMeno() + " je vo väzení len na návšteve.");
            }
            else if (polickoKdeStojiHrac instanceof Policia)
            {
                System.out.println(((Policia) polickoKdeStojiHrac).uvazniHraca(naRade,policka.get(6)));
            }
            else if (polickoKdeStojiHrac instanceof PlatbaDane)
            {
                System.out.println(naRade.getMeno() + " platí daň vo výške " + ((PlatbaDane) polickoKdeStojiHrac).getPoplatok() + ".");
                if(!((PlatbaDane) polickoKdeStojiHrac).zaplatDan(naRade))
                {
                    dohralHracKolo = false;
                }
            }
        }
        else { System.out.println("Bohužiaľ " + naRade.getMeno() + " je vo väzení, takže sa jeho kolo preskakuje.");}
        if(!dohralHracKolo)
        {
            bankrotHraca(naRade);
        }
        return dohralHracKolo;
    }

    public void bankrotHraca(Hrac naRade) {
        System.out.println(naRade.getMeno() + " zbankrotova-l/-la, týmto pre ňu/preňho hra končí.");
        uvolniNehnutelnostiHraca(naRade.getMajetok());
        hraci.remove(naRade);
        pocetHracov--;
    }

    public void hraj() {
        Hrac naRade;
        inicializaciaHracov();
        System.out.format("%n %n");
        int indexHracaNaRade = 0;
        while(hraci.size() > 1)
        {
            naRade = hraci.get(indexHracaNaRade);
            if(!odohrajKolo(naRade))
            {
                indexHracaNaRade--;
            }
            indexHracaNaRade++;
            if(indexHracaNaRade==pocetHracov)
            {
                indexHracaNaRade=0;
            }
            System.out.println();
        }
        System.out.format("-----------------------------------------------------%n%n");
        System.out.format("Koniec hry, vyhráva " + hraci.get(0).getMeno() + " !!!%n%n");
        System.out.format("-----------------------------------------------------");
    }
}
