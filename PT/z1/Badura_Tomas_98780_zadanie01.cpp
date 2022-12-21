/*
Meno a priezvisko:

POKYNY:
(1)  Subor premenujte na Priezvisko_Meno_ID_zadanie01.cpp (pouzite vase udaje bez diakritiky).
(2)  Implementujte funkcie tak, aby splnovali popis pri ich deklaraciach.
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
#include <cmath>
#include <cstring>
#include <cctype>

//#include <iomanip>

using namespace std;

//-------------------------------------------------------------------------------------------------
// DATOVE TYPY
//-------------------------------------------------------------------------------------------------

// Bod v rovine
struct Point {
    int x; // x-ova suradnica
    int y; // y-ova suradnica
};

// Usecka v rovine
struct Line {
    Point start; // zaciatocny bod
    Point end;   // koncovy bod
};

// Student
struct Student {
    char* name; // meno studenta
    int year;   // rocnik studia
};

// Uspesnost vykonania funkcie
enum class Result {
    SUCCESS, // funkcia vykonana uspesne
    FAILURE  // chyba pri vykonavani funkcie
};

// Matematicka funkcia
enum class Operation {
    PLUS,
    MINUS,
    MULTIPLY,
    DIVIDE
};

// Vstup pre matematicky vypocet funkcie s dvoma parametrami
struct CalculationInput {
    int operand1; // prvy parameter
    int operand2; // druhy parameter
    Operation operation; // funkcia
};

//-------------------------------------------------------------------------------------------------
// 1. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Vytlaci obsah pola na standardny vystup.
    V implementacii musi byt pouzity 'std::cout'.

    Format tlace:
    - prvky pola (cisla) su oddelene jednou medzerou
    - pred prvym prvkom je gulata otvaracia zatvorka (medzi zatvorkou a cislom nesmie byt medzera)
    - za poslednym prvkom je gulata zatvaracia zatvorka (medzi cislom a zatvorkou nesmie byt medzera)

    PARAMETRE:
        [in] data   - pole cisiel
        [in] length - dlzka pola 'data'

    VSTUPNE PODMIENKY:
        'length' moze mat lubovolnu hodnotu
        'data' ukazuje na platne pole

    PRIKLADY:
        prazdne pole: ()
        jednoprvkove pole: (10)
        2 prvkove pole:    (10 20)
        10 prvkove pole:   (8 -5 100000 2 1 2 4 5 -20 345)
*/
void printArray(const int* data, size_t length) {
    
    std::cout << "(";
    for (size_t i = 0; i < length;i++) {
        std::cout << data[i];
        if (i != length - 1)
        {
            std::cout << " ";
        }
    }
    std::cout << ")";
}

//-------------------------------------------------------------------------------------------------
// 2. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Zo standardneho vstupu nacita dve cele cisla (typu 'int') a vrati ich sucet.
    V implementacii musi byt pouzity 'std::cin'.

    NAVRATOVA HODNOTA:
        sucet cisiel nacitanych zo standardneho vstupu

    VSTUPNE PODMIENKY:
        Textovy vstup zadany na standardny vstup programu obsahuje dve cele cisla, ktore mozno reprezetovat typom 'int'.
        Nemusite osetrovat moznost chybnych udajov zadanych na standardny vstup.

    PRIKLADY:
        Ak je na standardny vstup zadany text "10 20\n" (bez uvodzoviek), tak funkcia vrati 30.
        Ak je na standardny vstup zadany text " 10 \n 20 \n" (bez uvodzoviek), tak funkcia vrati 30.
*/
int sumOfTwoNumbersFromConsole() {
    int y, z;
    std::cin >> y >> z;
    int sum = y + z;
    //std::cout << sum;
    return sum; // tento riadok zmente podla zadania, je tu len kvoli kompilacii
}

//-------------------------------------------------------------------------------------------------
// 3. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Vrati 'true' ak 'text' obsahuje velke pismeno, inak vrati 'false'.

    PARAMETER:
        [in] text - textovy retazec ukonceny '\0'

    NAVRATOVA HODNOTA:
        'true'  - ak 'text' obsahuje aspon jedno velke pismeno
        'false' - ak 'text' neobsahuje ani jedno velke pismeno (plati aj pre prazdny retazec)

    POZNAMKA:
        Pri implementacii pouzite funkciu 'std::isupper'.
*/
bool containsUpperCase(const char* text) {
    int i = 0;
    char c;
    while (text[i])
    {
        c = text[i];
        if (isupper(c))
        {
            return true;
        }
        i++;
    }
    return false; // tento riadok zmente podla zadania, je tu len kvoli kompilacii
}

//-------------------------------------------------------------------------------------------------
// 4. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Vrati 'true' ak 'data' obsahuje stupajucu postupnost, inak vrati 'false'.

    PARAMETRE:
        [in] data   - pole hodnot
        [in] length - pocet prvkov pola 'data'

    NAVRATOVA HODNOTA:
        'true' - ak 'data' obsahuje stupajucu postupnost hodnot, alebo pocet prvkov pola je mensi ako 2
        'false' - v opacnom pripade

    PRIKLADY:
        data = {10, 11, 15, 20} => vysledok je 'true'
        data = {10, 15, 15, 20} => vysledok je 'false'
        data = {10, 11, 15, 12} => vysledok je 'false'
        data = {10}             => vysledok je 'true'
        data = {}               => vysledok je 'true'
*/
bool isAscending(const int* data, size_t length) {
    int x, y;
    if (length < 2)
    {
        return true;
    }
    x = data[0];
    for (size_t i = 1; i < length; i++)
    {
        y = data[i];
        if (x >= y)
        {
            return false;
        }
        x = y;
    }
    return true; // tento riadok zmente podla zadania, je tu len kvoli kompilacii
}

//-------------------------------------------------------------------------------------------------
// 5. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Posunie hodnoty pola 'data' o 1 miesto vyssie. Hodnotu posledneho prvku presunie na zaciatok.

    PARAMETRE:
        [in,out] data - pole hodnot, ktore bude preusporiadane
        [in] length   - pocet prvkov pola 'data'

    PRIKLADY:
        {10, 20, 30, 40, 50} => {50, 10, 20, 30, 40}
        {10} => {10}
        {} => {}
*/
void shiftUp(int* data, size_t length) {
    int x, y;
    if (length > 1)
    {
        x = data[0];
        for (size_t i = 1; i < length; i++)
        {
            y = data[i];
            data[i] = x;
            x = y;
        }
        data[0] = x;
    }
}

//-------------------------------------------------------------------------------------------------
// 6. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Preusporiada pole 'data' tak, ze jeho prvky budu v opacnom poradi.

    PARAMETRE:
        [in, out] data - pole hodnot, ktore bude preusporiadane
        [in] lenght    - pocet prvkov pola 'data'

    PRIKLADY:
        {10, 20, 30, 40, 50} => {50, 40, 30, 20, 10}
        {10, 20, 30, 40} => {40, 30, 20, 10}
        {10} => {10}
        {} => {}
*/
void reverse(int* data, size_t length) {
    if (length > 1)
    {
        int x, p = length-1;
        for (size_t i = 0; i < length / 2; i++)
        {
            x = data[i];
            data[i] = data[p];
            data[p] = x;
            p--;
        }
    }
}

//-------------------------------------------------------------------------------------------------
// 7. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Vrati dlzku usecky 'line'

    PARAMETER:
        [in] line - usecka

    NAVRATOVA HODNOTA:
        dlzka usecky 'line'

    PRIKLAD:
        line:
            start:
                x = 10
                y = 20
            end:
                x = 100
                y = 50
        return 94.868329805
*/
double length(const Line* line) {
    double sum1 = pow((line->end.x - line->start.x),2);
    double sum2 = pow((line->end.y - line->start.y), 2);
    double result = sqrt(sum1 + sum2);
    /*std::cout << line->start.x << endl;
    std::cout << line->start.y << endl;
    std::cout << line->end.x << endl;
    std::cout << line->end.y << endl;*/
    //cout << std::setprecision(20) << result;;
    return result; // tento riadok zmente podla zadania, je tu len kvoli kompilacii
}

//-------------------------------------------------------------------------------------------------
// 8. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Vrati dlzku usecky 'line'

    PARAMETER:
        [in] line - usecka

    NAVRATOVA HODNOTA:
        dlzka usecky 'line'

    PRIKLAD:
        line:
            start:
                x = 10
                y = 20
            end:
                x = 100
                y = 50
        return 94.868329805
*/
double length(const Line& line) {
    double sum1 = pow((line.end.x - line.start.x), 2);
    double sum2 = pow((line.end.y - line.start.y), 2);
    double result = sqrt(sum1 + sum2);
    return result; // tento riadok zmente podla zadania, je tu len kvoli kompilacii
}

//-------------------------------------------------------------------------------------------------
// 9. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Vytvori noveho studenta s menom 'name' a rocnikom studia 'year'.
    Vrati smernik na vytvoreneho studenta.

    PARAMETRE:
        [in] name - meno studenta (textovy retazec ukonceny '\0')
        [in] year - rocnik v ktorom student studuje

    NAVRATOVA HODNOTA:
        vytvoreny student
*/
Student* createStudent(const char* name, int year) {
    Student* entry = new Student();
    entry->name = new char[strlen(name) + 1];
    entry->year = year;
    strcpy(entry->name, name);
    return entry; // tento riadok zmente podla zadania, je tu len kvoli kompilacii
}

//-------------------------------------------------------------------------------------------------
// 10. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Vypocita celociselny matematicky vypocet zadany parametrom 'input'.
    Vysledok ulozi do 'output'.

    PARAMETRE:
        [in] input   - vstupne udaje pre matematicky vypocet
        [out] output - vystupna hodnota vypoctu (v pripade moznosti vypoctu)

    NAVRATOVA HODNOTA:
        Result::SUCCESS - ak je mozne vypocitat vyslednu hodnotu
        Result::FAILURE - ak nie je mozne vypocitat vyslednu hodnotu (v pripade delenia nulou)

    VYSTUPNE PODMIENKY:
        - Vysledok vypoctu je celociselny.
        - Ak je mozne vykonat vypocet, tak sa vysledok ulozi do 'output' a funkcia vrati 'Result::SUCCESS',
          inak (v pripade delenia nulou) sa hodnota 'output' nemeni a funkcia vrati 'Result::FAILURE'.
*/
Result calculate(int* output, const CalculationInput* input) {
    switch (input->operation)
    {
    case Operation::PLUS:
        *output = input->operand1 + input->operand2;
        return Result::SUCCESS;
    case Operation::MINUS:
        *output = input->operand1 - input->operand2;
        return Result::SUCCESS;
    case Operation::MULTIPLY:
        *output = input->operand1 * input->operand2;
        return Result::SUCCESS;
    case Operation::DIVIDE:
        if (input->operand2 == 0)
        {
            return Result::FAILURE;
        }
        *output = input->operand1 / input->operand2;
        return Result::SUCCESS;
    }
    return Result::FAILURE; // tento riadok zmente podla zadania, je tu len kvoli kompilacii
}

//-------------------------------------------------------------------------------------------------
// TESTOVANIE
//-------------------------------------------------------------------------------------------------

// tu mozete doplnit pomocne funkcie a struktury
const char* toString(Result vysledok)
{
    switch (vysledok)
    {
    case Result::SUCCESS:
        return "SUCCESS";
    case Result::FAILURE:
        return "FAILURE";
    }
    return "ERROR";
}

int main(int argc, char** argv) {

    /*int pole_cisel[] = {1,66,59,78,2,6,8};
    size_t dlzka = sizeof(pole_cisel) / sizeof(pole_cisel[0]);                                              //uloha 1
    printArray(pole_cisel, dlzka);*/
    
    //std::cout << containsUpperCase("anabolic");
    
    //std::cout << isAscending(data,0);
    
    /*int data[] = { 10,20 };
    for (int i = 0; i < 2; i++)
    {
        std::cout << data[i] << " ";
    }
    std::cout << "\n";                                  //uloha 5
    shiftUp(data,2);
    for (int i = 0; i < 2; i++)
    {
        std::cout << data[i] << " ";
    }*/
    
    
    /*int opacne[] = { 10};
    for (int i = 0; i < 1; i++)
    {
        std::cout << opacne[i] << " ";
    }
    std::cout << "\n";                                  //uloha 6
    reverse(opacne, 1);
    for (int i = 0; i < 1; i++)
    {
        std::cout << opacne[i] << " ";
    }*/

   /* Line usecka;
    usecka.start.x = 10;
    usecka.start.y = 20;                        //uloha 7
    usecka.end.x = 100;
    usecka.end.y = 50;
    cout << std::setprecision(11) << length(&usecka);
    length(&usecka);*/
    
   /* Line usecka;
    usecka.start.x = 10;
    usecka.start.y = 20;                        //uloha 8
    usecka.end.x = 100;
    usecka.end.y = 50;    
    cout << std::setprecision(11) << length(usecka);*/
    
    /*Student* pointer = createStudent("Alexander Hlavaty", 2);
    cout << pointer->name << endl;                                      //uloha 9   
    cout << pointer->year << endl;
    delete pointer;*/
    
    
    /*int out = 0;
    CalculationInput priklad;
    priklad.operand1 = 0;
    priklad.operand2 = 5;
    Operation op = Operation::DIVIDE;
    priklad.operation = op;
    cout << "OUT pred vypoctom " << out << endl;            //uloha 10
    cout << toString(calculate(&out, &priklad));
    cout << endl;
    cout << "OUT po vypocte " << out << endl;*/


    return 0;
}
