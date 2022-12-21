/*
Meno a priezvisko: Tomas Badura

POKYNY:
(1)  Subor premenujte na Priezvisko_Meno_ID_zadanie04.cpp (pouzite vase udaje bez diakritiky).
(2)  Implementujte funkcie tak, aby splnali popis pri ich deklaraciach.
(3)  Cela implementacia musi byt v tomto jednom subore.
(4)  Odovzdajte len tento (spravne premenovany) zdrojovy subor.
(5)  Program musi byt kompilovatelny.
(6)  Globalne a staticke premenne su zakazane.
(7)  V ziadnom pripade nemente deklaracie funkcii, ktore mate za ulohu naprogramovat
     (nemente nazvy, navratove hodnoty ani typ a pocet parametrov v zadanych funkciach).
     Nemente implementacie hotovych pomocnych funkcii, ani implementacie zadanych datovych typov.
(8)  V pripade potreby mozete kod doplnit o dalsie pomocne funkcie alebo struktury.
(9)  Vase riesenie otestujte (vo funkcii 'main' a pomocou doplnenych pomocnych funkcii alebo struktur).
     Testovaci kod ale nebude hodnoteny.
(10) Funkcia 'main' musi byt v zdrojovom kode posledna.
*/
#define _CRT_SECURE_NO_WARNINGS
#include <iostream>

using namespace std;

//-------------------------------------------------------------------------------------------------
// DATOVE TYPY
//-------------------------------------------------------------------------------------------------

// Hmotnost produktu a obalu. Hmotnost zabaleneho produktu je suctom obidvoch poloziek
struct Weight {
    int product; // hmotnost produktu
    int packing; // hmotnost balenia
};

struct ElementInfo {
    int value;
    size_t index;
};
//-------------------------------------------------------------------------------------------------

void PivotSort(ElementInfo* data, const size_t length) {
    ElementInfo tmp;
    for (size_t i = 0; i < length - 1; i++) {
        for (size_t j = 0; j < length - i - 1; j++) {
            if (data[j + 1].value > data[j].value) {
                tmp = data[j + 1];
                data[j + 1] = data[j];
                data[j] = tmp;
            }
        }
    }
}

//-------------------------------------------------------------------------------------------------
// 1. ULOHA (0.8 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Funkcia usporiada pole 'data'.
    Pouzije algoritmus bubble sort.
    Poradie usporiadania je od najvacsieho prvku po najmensi.

    PARAMETRE:
        [in, out] data - pole, ktore funkcia usporiada
        [in] length    - pocet prvkov pola

    VSTUPNA PODMIENKA:
        ak 'length' > 0, tak 'data' ukazuje na platne pole

    PRIKLADY:
        {1,3,2} -> {3, 2, 1}
        {} -> {}
*/
void bubbleSort(int *data, const size_t length) {
    if (length < 1)
    {
        return;
    }
    int tmp;
    for (size_t i = 0; i < length - 1; i++) {
        for (size_t j = 0; j < length - i - 1; j++) {
            if (data[j + 1] > data[j]) {
                tmp = data[j + 1];
                data[j + 1] = data[j];
                data[j] = tmp;
            }
        }
    }
}

//-------------------------------------------------------------------------------------------------
// 2. ULOHA (0.8 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Funkcia usporiada pole 'data' podla celkovej hmotnosti zabaleneho tovaru, t.j. podla suctu poloziek ('product' a 'packing').
    Pouzije algoritmus bubble sort.
    Poradie usporiadania je od najvacsieho prvku po najmensi (od najtazsieho zabaleneho tovaru po najlahsi zabaleny tovar).

    Podmienka porovnania struktur:
    Pri porovnavani prvkov funkcia scita hodnoty 'product' a 'packing' oboch porovnavanych struktur.
    Struktury s vacsim suctom poloziek budu po usporiadani pred strukturami s mensim suctom poloziek.

    Vzajomne usporiadanie struktur s rovnakym suctom poloziek:
    Pri bodovom hodnoteni nezalezi na vzajomnom usporiadani struktur s rovnakym suctom poloziek (aj ked hodnoty poloziek mozu byt rozne).
    Lepsie je vsak implementovat stabilne triedenie (struktury s rovnakym suctom poloziek nemenia vzajomne usporiadanie).

    PARAMETRE:
        [in, out] data - pole, ktore funkcia usporiada
        [in] length    - pocet prvkov pola

    VSTUPNA PODMIENKA:
        ak 'length' > 0, tak 'data' ukazuje na platne pole

    PRIKLADY:
        {{10, 1}, {20, 2}, {5,2}} -> {{20, 2}, {10, 1},{5, 2}} pretoze 20+2=22, 10+1=11, 5+2=7 a 22 > 11 > 7
        {} -> {}

    POZNAMKA:
        Priklady jednoducheho vytvorenia pola v testovacom kode:
        Weight baliky[] = {{10, 1}, {20, 2}, {5, 2}};
        Weight baliky[] = {{.product = 10, .packing = 1}, {.product = 20, .packing = 2}, {.product = 5, .packing = 2}};
*/

void bubbleSort(Weight *data, const size_t length) {
    if (length < 1)
    {
        return;
    }
    Weight tmp;
    for (size_t i = 0; i < length - 1; i++) {
        for (size_t j = 0; j < length - i - 1; j++) {
            if ((data[j + 1].packing + data[j + 1].product) > (data[j].packing + data[j].product)) {
                tmp = data[j + 1];
                data[j + 1] = data[j];
                data[j] = tmp;
            }
        }
    }
}

//-------------------------------------------------------------------------------------------------
// 3. ULOHA (0.8 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Funkcia vyberie pivota a vrati jeho index.
    Pivota vyberie ako median prvkov:
      - data[low]
      - data[(high+low)/2]
      - data[high-1]

    PARAMETRE:
        [in] data - pole, v ktoreho casti s indexami 'low' ... 'high'-1, funkcia vybera pivot
        [in] low  - index prveho prvku casti pola, v ktorej funkcia hlada pivot
        [in] high - index za poslednym prvkom casti pola, v ktorej funkcia hlada pivot

    NAVRATOVA HODNOTA:
        index pivota

    VSTUPNE PODMIENKY:
        'data' ukazuje na platne pole
        'low' < 'high'

    PRIKLADY:
        data: {10, 20, 2000, 30, 1000, 40, 5000, 50, 60, 70}, low: 2, high: 7 -> return 2
        data: {10, 20, 1000, 30, 2000, 40, 5000, 50, 60, 70}, low: 2, high: 7 -> return 4
        data: {10, 20, 5000, 30, 1000, 40, 2000, 50, 60, 70}, low: 2, high: 7 -> return 6

        data: {10, 20, 1000, 30, 40, 2000, 50, 5000, 60, 70}, low: 2, high: 8 -> return 5

        data: {10, 20, 1000, 2000, 30, 40, 50},               low: 2, high: 4 -> return 3
        data: {10, 20, 2000, 1000, 30, 40, 50},               low: 2, high: 4 -> return 3

        data: {10, 20, 1000, 30, 40},                         low: 2, high: 3 -> return 2
*/
size_t getPivotIndex(const int *data, const size_t low, const size_t high) {
    int counter = 0;
    ElementInfo candidates[3];
    for (size_t i = 0; i < high; i++)
    {
        if (i == low)
        {
            candidates[counter].value = data[i];
            candidates[counter].index = i;
            counter++;
        }
        if (i == (high + low) / 2)
        {
            candidates[counter].value = data[i];
            candidates[counter].index = i;
            counter++;
        }
        if (i == high - 1)
        {
            candidates[counter].value = data[i];
            candidates[counter].index = i;
            counter++;
        }
    }
    PivotSort(candidates, 3);
    return candidates[1].index;
}

//-------------------------------------------------------------------------------------------------
// 4. ULOHA (0.8 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Funkcia vykona partition (cast algoritmu quick sort) a vrati novy index pivota.
    Pouzije styl algoritmu Lomuto.
    Poradie usporiadania:
        Najprv (vlavo) budu prvky vacsie alebo rovne ako pivot,
        potom pivot,
        potom (vpravo) prvky mensie ako pivot.

    PARAMETRE:
        [in, out] data - pole, v ktoreho casti 'low' ... 'high'-1 bude vykonane partition
        [in] pivot     - index pivota (pred partition)
        [in] low       - index prveho prvku casti pola, v ktorej bude vykonany partition
        [in] high      - index za poslednym prvkom casti pola, v ktorej bude vykonany partition

    NAVRATOVA HODNOTA:
        Index pivota po vykonani partition.

    VSTUPNE PODMIENKY:
        'low' <= 'pivot' < 'high'
        (index pivota moze byt lubobolny v rozsahu 'low'...'high'-1, napriklad v pripade nahodneho vyberu pivota)
        'data' ukazuje na platne pole

    PRIKLADY:
        1. priklad:                 .           .   .    
            vstup:  data: {10, 20, 30, 40, 50, 60, 70, 80, 90}, pivot: 5, low: 2, high: 7
            vystup: data: {10, 20, 70, 60, 50, 30, 40, 80, 90}, return 3

        2. priklad:
            vstup:  data: {10, 20, 30, 40, 50, 60, 70, 50, 80, 90}, pivot: 4, low: 2, high: 8
            vystup: data: {10, 20, 50, 60, 70, 50, 30, 40, 80, 90}, return 5
*/

void swap(int* a, int* b)
{
    int tmp = *a;
    *a = *b;
    *b = tmp;
}

size_t partition(int* data, const size_t pivot, const size_t low, const size_t high)
{
    swap(&data[pivot], &data[high - 1]);
    size_t insert_index = low;
    size_t compare_index = low;
    while (compare_index < high) {
        if (data[compare_index] >= data[high - 1]) {
            swap(&data[insert_index], &data[compare_index]);
            insert_index++;
        }
        compare_index++;
    }
    return insert_index - 1;
}

//-------------------------------------------------------------------------------------------------
// 5. ULOHA (0.8 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Funkcia usporiada cast pola 'data' (s indexami 'low' ... 'high'-1).
    Pouzije algoritmus quick sort, styl Lomuto.
    Poradie usporiadania je od najvacsieho prvku po najmensi.

    PARAMETRE:
        [in, out] data - pole, ktoreho cast funkcia usporiada
        [in] low       - index prveho prvkou casti pola, ktoru funkcia usporiada
        [in] high      - index za posledny prvok casti pola, ktoru funkcia usporiada

    VSTUPNA PODMIENKA:
        ak 'low' < 'high', tak 'data' ukazuje na platne pole

    PRIKLAD:
        data: {1, 2, 3, 4, 5, 6, 7, 8, 9}, low: 2, high: 7 -> data: {1, 2, 7, 6, 5, 4, 3, 8, 9}
*/

void quickSort(int *data, const size_t low, const size_t high)
{
    if (low < high)
    {
        size_t pivot = partition(data, high-1, low, high);

        quickSort(data, low, pivot);
        quickSort(data, pivot + 1, high);
    }
}

//-------------------------------------------------------------------------------------------------
// TESTOVANIE
//-------------------------------------------------------------------------------------------------

// tu mozete doplnit pomocne funkcie a struktury

    /*void parita(int cislo)
    {
        if (cislo % 2 == 0)
            goto parne;
        else
            goto neparne;

    parne:
        printf("Cislo %d je parne", cislo);
        return;
    neparne:
        printf("Cislo %d je neparne", cislo);
    }

    int vypocet(int a)
    {
        int sum;
        for (int i = 0; i < a; i++)
        {
            sum += i;
        }
        return sum;
    }*/

int main() {



    
    //parita(8);
    
            /*int i = 1;

            while (i <= 5)
            {
                printf("%i\n", i);
                i++;
            }


            //int i = 1;

            do
            {
                printf("%i\n", i);
                i++;
            } while (i <= 5);
    
    
            
    
    


            for (int i = 1; i < 11; ++i)
            {
                printf("%i ", i);
            }
    
    
            
            for (;;) {
                //telo for cyklu
            }*/
    
            /*int i, sum = 0;
            while (scanf("%d", &i))
            {
                if (i < 0)
                {
                    break;
                }
                sum += i;
            }
            printf("%d",sum);*/
    
            
            /*for (int j = 0; j <= 10; j++)
            {
                if (j % 2 == 0) //ak je cislo parne
                {
                    continue;
                }
                printf("%d ", j);
            }*/
            
    
    
            /*int znamka = 3;

            switch (znamka) {
            case 1:
                printf("Výborný\n");
                break;
            case 2:
                printf("Chválitebný\n");
                break;
            case 3:
                printf("Dobrý\n");
                //break;
            case 4:
                printf("Dostatoèný\n");
                break;
            case 5:
                printf("Nedostatoèný\n");
                break;
            default:
                printf("Neplatná známka\n");
            }*/

    
    
    
    
    
    
    
    
    
    
    /*int data[] = { 1 };
    size_t lenght = sizeof(data) / sizeof(data[0]);
    bubbleSort(data, 0);
    for (size_t i = 0; i < lenght; i++)                     //uloha 1
    {
        cout << data[i] << " ";
    }*/

    /*Weight baliky[] = { {10, 1}, {20, 2}, {60,2}, {5,2}, {15,22}, {5,12}, {5,2} };
    size_t lenght = sizeof(baliky) / sizeof(baliky[0]);
    bubbleSort(baliky, lenght);
    cout << "{ ";
    for (size_t i = 0; i < lenght; i++)                                             //uloha 2
    {
        cout << "{" <<baliky[i].product << ", " << baliky[i].packing << "} ";
    }
    cout << "}" << endl;*/

    /*int data[] = { 10, 20, 1000, 30, 40 };                                    //uloha 3
    cout << getPivotIndex(data, 2, 3);*/

    /*int data[] = { 10, 20, 30, 40, 50, 60, 70, 50, 80, 90 };  
    cout << partition(data, 4, 2, 8) << endl;                       //uloha 4
    for (int i = 0; i < 10; i++)
    {
        cout << data[i] << " ";
    }*/

    /*int data[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    quickSort(data, 2, 7);
    for (int i = 0; i < 9; i++)                             //uloha5
    {
        cout << data[i] << " ";
    }*/


    //cout << endl;
    return 0;
    }




