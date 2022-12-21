/*
Meno a priezvisko: Tomas Badura

POKYNY:
(1)  Subor premenujte na Priezvisko_Meno_ID_zadanie10.cpp (pouzite vase udaje bez diakritiky).
(2)  Implementujte funkcie tak, aby splnali popis pri ich deklaraciach.
(3)  Cela implementacia musi byt v tomto jednom subore.
(4)  Odovzdajte len tento (spravne premenovany) zdrojovy subor.
(5)  Program musi byt kompilovatelny.
(6)  Globalne a staticke premenne su zakazane.
(7)  V ziadnom pripade nemente deklaracie funkcii, ktore mate za ulohu naprogramovat
     (nemente nazvy, navratove hodnoty ani typ a pocet parametrov v zadanych funkciach).
     Nemente implementacie zadanych datovych typov, ani implementacie hotovych pomocnych funkcii
     (ak nie je v zadani ulohy uvedene inak).
(8)  V pripade potreby mozete kod doplnit o dalsie pomocne funkcie alebo struktury.
(9)  Vase riesenie otestujte (vo funkcii 'main' a pomocou doplnenych pomocnych funkcii alebo struktur).
     Testovaci kod ale nebude hodnoteny.
(10) Funkcia 'main' musi byt v zdrojovom kode posledna.
*/

#include <iostream>
#include <iomanip>
#include <cassert>
#include <list>
#include <map>
#include <queue>
#include <stack>
#include <set>
#include <vector>
#include <deque>
#include <algorithm>
#include <limits>
#include <sstream>

using namespace std;

//-------------------------------------------------------------------------------------------------
// 1. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Upravte definiciu metody 'T::met()' tak, aby informovala, ze metoda nemeni stav objektu.

    Pre kontrolu odkomentujte riadok v testovacej funkcii. Odkomentovany riadok musi byt kompilovatelny.
*/

class T {
public:
    void met() const { cout << "----- 1. uloha (metoda nemeni stav objektu) ------------------------------------" << endl; }
};

void testUloha1() {
    cout << "----- 1. uloha (metoda nemeni stav objektu) ------------------------------------" << endl;
    const T o;
    o.met();   
}

//-------------------------------------------------------------------------------------------------
// 2. ULOHA (0.4 bodu)  
//-------------------------------------------------------------------------------------------------
/*
    Upravte definiciiu konstruktora tak, aby bol konverznym konstruktorom.

    Zakomentovane riadky v testovacom kode musia byt kompilovatelne.
*/

class Number {
private:
    int number;
public:
    Number(int value) : number(value){
    }
    int getValue() const {
        return number;
    }
};

void fun(Number n) {
    cout << n.getValue() << endl;
}

void testUloha2() {
    cout << "----- 2. uloha (konverzny konstruktor) -----------------------------------------" << endl;
    Number a = 10; // ak existuje zodpovedajuci konverzny konstruktor, tak je tento riadok kompilovatelny
    cout << a.getValue() << endl;

    fun(20); // ak existuje zodpovedajuci konverzny konstruktor, tak je tento riadok kompilovatelny
}

//-------------------------------------------------------------------------------------------------
// 3. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Vytvorte triedu 'B', ktora je podtriedou triedy 'A' .
    Upravte definiciu metody 'A::met()' a definujte metodu 'B::met()' tak,
    aby bol testovaci kod po odkomentovani uspesne vykonany (aby bola vzdy volana spravna metoda).
*/

class A {
public:
    virtual char met() const {
        return 'a';
    }
};

class B : public A {
public:
    char met() const {
        return 'b';
    }
};

void testUloha3() {
    cout << "----- 3. uloha (volanie spravnej metody) ---------------------------------------" << endl;

    A a;
    B b;

    A *pa  = &a;
    A *pab = &b;
    B *pb  = &b;

    assert(pa->met()  == 'a'); // volanie A::met()
    assert(pab->met() == 'b'); // volanie B::met()
    assert(pb->met()  == 'b'); // volanie B::met()
}

//-------------------------------------------------------------------------------------------------
// 4. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Upravte definicie destruktorov tried 'C' a 'D' tak, aby sa v testovacej funkcii zavolal spravny destruktor.
    Testovacia funkcia po uprave namiesto:
        C::~C()
    musi vypisat:
        D::~D()
        C::~C()
*/

class C {
public:
    virtual ~C() {
        cout << "C::~C()" << endl;
    }
};

class D : public C {
public:
    ~D() {
        cout << "D::~D()" << endl;
    }
};

void testUloha4() {
    cout << "----- 4. uloha (volanie spravneho destruktora) ---------------------------------" << endl;
    C *obj = new D;
    delete obj; // aby sa zavolal spravny detruktor
}

//-------------------------------------------------------------------------------------------------
// 5. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Vytvorte operator +=, ktory bude sluzit pre pripocitanie druheho komplexneho cisla ku prvemu.
    Operator definujte ako clena triedy.

    Pre kontrolu odkomentujte riadky v testovacom kode, kde je priklad pouzitia operatora.
*/

class Complex {
private:
    int real;
    int imaginary;
public:
    Complex(int realPart, int imaginaryPart)
            : real(realPart)
            , imaginary(imaginaryPart) {
    }
    int getRealPart() const {
        return real;
    }
    int getImaginaryPart() const {
        return imaginary;
    }
    Complex& operator+=(const Complex& pravy){
        this->real = this->real + pravy.real;
        this->imaginary = this->imaginary + pravy.imaginary;
        return *this;
    }
};

void testUloha5() {
    cout << "----- 5. uloha (operator +=) ---------------------------------------------------" << endl;

    Complex a(1,2);
    Complex b(10, 20);
    Complex c(100, 200);

    c += b += a;

    assert(a.getRealPart() == 1);
    assert(a.getImaginaryPart() == 2);
    assert(b.getRealPart() == 11);
    assert(b.getImaginaryPart() == 22);
    assert(c.getRealPart() == 111);
    assert(c.getImaginaryPart() == 222);
}

//-------------------------------------------------------------------------------------------------
// 6. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Vytvorte operator <<, ktory zapise hodnotu objektu triedy 'Complex' (z predchadzajuceho prikladu)
    do textoveho prudu dat.
    Poznamka: Operator musi byt definovany ako globalny.

    Pre kontrolu odkomentujte riadky v testovacom kode.
*/

ostream& operator<<( ostream& output, const Complex& x){
    if (x.getImaginaryPart() > -1) output << x.getRealPart() << "+" << x.getImaginaryPart() << "i";
    else output << x.getRealPart() << x.getImaginaryPart() << "i";
    return output;
}

void testUloha6() {
    cout << "----- 6. uloha (operator << ) --------------------------------------------------" << endl;

    Complex a( 1, 2);
    Complex b(-3,-4);
    cout << "a = " << a << ", b = " << b << endl;

    ostringstream stream;
    stream << a << " " << b;
    assert(stream.str() == "1+2i -3-4i");
}

//=================================================================================================
// STROM - definicia pre dalsie ulohy
//=================================================================================================

// Uzol stromu
struct Node {
    char value; // hodnota uzla
    list<Node*> children; // zoznam nasledovnikov

    explicit Node(char value) : value(value) {}
};

// Strom
struct Tree {
    Node * root; // koren stromu

    explicit Tree(Node *root = nullptr) : root(root) {}
};

//-------------------------------------------------------------------------------------------------
// 7. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Funkcia vrati zoznam vsetkych hodnot uzlov v strome 'tree', ktorych hodnotou je velke pismeno.
    Pouzite algoritmus hladania DO HLBKY (styl pre-order).
    Pouzite iterativnu implementaciu.
    Doporucenie: pre identifikaciu velkych pismen pouzite funkciu 'std::isupper'

    VSTUPNA HODNOTA:
        [in] tree - prehladavany strom

    NAVRATOVA HODNOTA:
        zoznam velkych pismen, ktore su hodnotami uzlov v strome 'tree'

    VYSTUPNA PODMIENKA:
        Poradie vo vystupnom zozname musi zodpovedat postupnosti prehladavania algoritmom DO HLBKY, styl PRE-ORDER.

    PRIKLAD:
        obrazok na webe
*/

void preOrder(const Node* node, list<char> *vystup) 
{
    if (node) {
        if (isupper(node->value))vystup->push_back(node->value);
        for (auto it = node->children.rbegin(); it != node->children.rend(); it++)
        {
            preOrder(*it, vystup);
        }
    }
}

list<char> depthFirstSearchUpperCases(const Tree *tree) {
    list<char> vystup;
    preOrder(tree->root, &vystup);
    return vystup;
}

//-------------------------------------------------------------------------------------------------
// 8. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Funkcia vrati zoznam vsetkych hodnot uzlov v strome 'tree', ktorych hodnotou je velke pismeno.
    Pouzite algoritmus hladania DO SIRKY.
    Pouzite iterativnu implementaciu.
    Doporucenie: pre identifikaciu velkych pismen pouzite funkciu 'std::isupper'

    VSTUPNA HODNOTA:
        [in] tree - prehladavany strom

    NAVRATOVA HODNOTA:
        zoznam velkych pismen, ktore su hodnotami uzlov v strome 'tree'

    VYSTUPNA PODMIENKA:
        Poradie vo vystupnom zozname musi zodpovedat postupnosti prehladavania algoritmom DO SIRKY.

    PRIKLAD:
        obrazok na webe
*/

list<char> breadthFirstSearchUpperCases(const Tree *tree) {
    list<char> vystup;
    if (!tree->root)
    {
        return vystup;
    }
    queue<Node*> rada;
    Node* uzol;
    rada.push(tree->root);
    while (!rada.empty())
    {
        uzol = rada.front();
        for (auto it = uzol->children.begin(); it != uzol->children.end(); it++)
        {
            rada.push(*it);
        }
        if (isupper(rada.front()->value)) vystup.push_back(rada.front()->value);
        rada.pop();
    }
    return vystup;
}

//=================================================================================================
// GRAF - definicia pre dalsie ulohy
//=================================================================================================
// Graf reprezentuje cestnu siet medzi obcami na planete.
// Graf je orientovany -> Hrana grafu reprezentuje moznost jazdy len jednosmernym smerom.
//  Preto je moznost "priamej" jazdy medzi obcami oboma smermy reprezentovana dvoma hranami grafu.
// Graf je ohodnoteny -> Mapa cestnej siete eviduje dlzky "priamych" cestnych spojeni medzi obcami.
// Obce nemusia byt prepojene cestnou sietou, napriklad ak sa nachadzaju na roznych pevninach.

// Forward deklaracie su potrebne, pretoze:
//  - definicie 'City' a 'RoadTo' su cyklicky zavisle:
//      - v definicii 'City' je pouzite 'RoadTo'
//      - a v definicii 'RoadTo' je pouzite 'City'
//  - definicie 'City' a 'SearchData' su cyklicky zavisle:
//      - v definicii 'City' je pouzite 'SearchData'
//      - a v definicii 'SearchData' je pouzite 'City'
struct RoadTo;
struct City;

// Udaje pouzite v algoritmoch hladania
struct SearchData {
    // Mozete si doplnit dalsie atributy a metody, ktore pouzijete v algoritmoch hladania
    bool discovered;
    unsigned distance;
    const City *previous;
    bool foundShortestPath;

    void clear() { // nastavi na implicitne hodnoty (tuto metodu mozete upravit (ale nie jej deklaracnu cast - nedoplnajte parametre))
        discovered = false;
        distance = numeric_limits<unsigned>::max();
        previous = nullptr;
        foundShortestPath = false;
    }
};

// Obec (nie len velke mesto) (uzol grafu)
struct City {
    string name; // nazov obce
    list<RoadTo> roads; // zoznam "priamych" jednosmernych cestnych spojeni do dalsich obci (zoznam vystupnych hran z tohto uzla grafu)

    SearchData searchData; // udaje pouzite v algoritmoch hladania

    // Mozete doplnit dalsi konstruktor alebo metody, ale tento konstruktor nemente
    explicit City(string name) : name(move(name)) {
        searchData.clear();
    }
};

// Jednosmerne "priame" cestne spojenie do dalsej obce (orientovana, ohodnotena hrana grafu)
struct RoadTo {
    City *city; // obec, do ktorej je toto "priame" cestne spojenie
    unsigned length; // dlzka tohto "priameho" spojenia

    // Mozete doplnit dalsi konstruktor alebo metody, ale tento konstruktor nemente
    RoadTo(City *city, unsigned length)
            : city(city)
            , length(length) {
    }
};

// Cestna mapa planety (orientovany, ohodnoteny graf)
struct Planet {
    list<City> cities; // zoznam obci na planete (zoznam vrcholov grafu)

    void clearSearchData() { // inicializuje atributy pouzite v algoritme hladania
        for(City &c : cities) {
            c.searchData.clear();
        }
    }
};

// Vyminka v pripade neexistencie obce so zadanym nazvom
class CityNotExistsException : exception {
    string cityName; // nazov obce
public:
    explicit CityNotExistsException(string name)
            : cityName(move(name)) {
    }
    const char * what() const noexcept override { // vrati nazov neexistujucej obce
        return cityName.c_str();
    }
};

City* getCity(Planet* g, const string& name) {
    list<City>::iterator it = find_if(g->cities.begin(), g->cities.end(), [&name](const City& v) {
        return v.name == name;
    });
    if (it != g->cities.end()) {
        return &(*it);
    }
    return nullptr;
}

//-------------------------------------------------------------------------------------------------
// 9. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Funkcia vrati zoznam nazvov vsetkych obci na planete 'planet',
    ktore su z obce s nazvom 'startCity' dosiahnutelne pomocou cestnej siete.
    Pouzite algoritmus hladania DO SIRKY.

    VSTUPNA HODNOTA:
        [in] planet - cestna siet na planete
        [in] startCity - nazov obce

    NAVRATOVA HODNOTA:
        zoznam nazvov vsetkych obci, dosiahnutelnych z obce 'startCity'

    VYNIMKA:
        CityNotExistsException - ak na planete 'planet' neexistuje obec s nazvom 'startCity'.
        Metoda 'CityNotExistsException::what()' vrati nazov neexistujucej obce (hodnota 'startCity').

    VYSTUPNE PODMIENKY:
        Vystupny zoznam obsahuje aj 'startCity'.
        Poradie vo vystupnom zozname musi zodpovedat postupnosti prehladavania algoritmom DO SIRKY.

    PRIKLAD:
        obrazok na webe
*/

list<string> breadthFirstSearchReachable(Planet * planet, const string & startCity) {
    City* c = getCity(planet, startCity);
    if (c == nullptr)
    {
        throw CityNotExistsException(startCity);
    }
    list<string> l;
    queue<City*> q;
    map<City*, bool> visited;
    for_each(planet->cities.begin(), planet->cities.end(), [&visited](City& p) {
        visited[&p] = false;
    });
    visited[c] = true;
    l.push_back(c->name);
    q.push(c);  
    while (!q.empty())
    {
        c = q.front();
        q.pop();
        for (const RoadTo& e : c->roads)
        {
             if (!visited[e.city]) 
             {
                    visited[e.city] = true;
                    l.push_back(e.city->name);
                    q.push(e.city);
             }
        }
    }
    return l;
}

//-------------------------------------------------------------------------------------------------
// 10. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Funkcia najte dlzky najkratsich ciest z obce 'startCity' do vsetkych dosiahnutelnych obci.
    Pouzite Dijkstrov algoritmus.

    VSTUPNE HODNOTY:
        [in] planet - cestna siet na planete
        [in] startCity - nazov obce

    NAVRATOVA HODNOTA:
        Pre kazdu obec dosiahnutelnu zo 'startCity' (na planete 'planet') obsahuje dlzku najkratsej cesty zo 'startCity'.

    VYNIMKA:
        CityNotExistsException - ak na planete 'planet' neexistuje obec s nazvom 'startCity'.
        Metoda 'CityNotExistsException::what()' vrati nazov neexistujucej obce (hodnota 'startCity').

    VYSTUPNA PODMIENKA:
        Navratova hodnota obsahuje nazvy len tych miest, ktore su dosiahnutelne zo 'startCity'.
        Navratova hodnota obsahuje aj vzdialenost do 'startCity' (nula).

    PRIKLAD:
        obrazok na webe
*/

map<string, unsigned> dijkstra(Planet * planet, const string & startCity) {
    City* c = getCity(planet, startCity);
    if (c == nullptr)
    {
        throw CityNotExistsException(startCity);
    }
    map<string, unsigned> cesty;
    queue<City*> q;
    c->searchData.distance = 0;
    c->searchData.foundShortestPath = 1;
    c->searchData.discovered = 1;
    q.push(c);
    while (!q.empty())
    {
        c = q.front();
        q.pop();
        for (auto e = c->roads.begin();e!=c->roads.end();e++)
        {
            if (!(*e).city->searchData.discovered)
            {
                (*e).city->searchData.discovered = 1;
                (*e).city->searchData.distance = (*e).length + c->searchData.distance;
                (*e).city->searchData.previous = c;
                q.push((*e).city);
            }
        }
    }
    for (auto it = planet->cities.begin(); it != planet->cities.end(); it++)
    {
        cout << (*it).name << ' ' << (*it).searchData.distance << endl;
    }
    return cesty;
}

//-------------------------------------------------------------------------------------------------
// TESTOVANIE
//-------------------------------------------------------------------------------------------------

// tu mozete doplnit pomocne funkcie a struktury

int main() {
    //testUloha1();
    //testUloha2();
    //testUloha3();
    //testUloha4();
    //testUloha5();
    //testUloha6();

    /*Tree strom;
    list<Node*> detiEmpty = {};

    Node D('D');
    D.children = detiEmpty;
    Node E('E');
    E.children = detiEmpty;
    Node L('L');
    L.children = detiEmpty;
    Node C('C');
    C.children = { &D,&E };
    Node F('F');
    F.children = detiEmpty;
    Node H('H');
    H.children = detiEmpty;
    Node j('j');
    j.children = detiEmpty;
    Node K('K');
    K.children = { &L };
    Node M('M');
    M.children = detiEmpty;
    Node N('N');
    N.children = detiEmpty;
    Node B('B');
    B.children = { &C,&F };
    Node g('g');
    g.children = { &H };
    Node I('I');
    I.children = { &j,&K,&M,&N };
    Node A('A');
    A.children = { &B,&g,&I };

    strom.root = &A;
    
    Tree stromcek;
    stromcek.root = nullptr;*/

    /*auto output = depthFirstSearchUpperCases(&strom);
    for (auto it = output.begin(); it != output.end(); ++it)        //uloha 7
    {
        cout << *it << ' ';
    }*/

    /*auto output = breadthFirstSearchUpperCases(&stromcek);
    for (auto it = output.begin(); it != output.end(); ++it)        //uloha 8
    {
        cout << *it << ' ';
    }*/

    /*City Nylon("Nylon");
    City Gulidon("Gulidon");
    City Etalon("Etalon");
    City Lion("Lion");
    City Gaucon("Gaucon");
    City Tigron("Tigron");
    City Wisdon("Wisdon");
    City London("London");
    City Melon("Melon");
    City Billon("Billon");
    City Tilon("Tilon");
    City Turbilon("Turbilon");

    Nylon.roads.push_back({ &Wisdon, 1 });

    Gulidon.roads.push_back({ &Nylon,1 });
    Gulidon.roads.push_back({ &Gaucon,1 });

    Etalon.roads.push_back({ &Tigron,1 });

    Lion.roads.push_back({ &Nylon,1 });
    
    Wisdon.roads.push_back({ &Tilon,1 });
    Wisdon.roads.push_back({ &Lion,1 });

    London.roads.push_back({ &Wisdon,1 });
    London.roads.push_back({ &Melon,1 });                       //uloha 9

    Melon.roads.push_back({ &Lion,1 });
    Melon.roads.push_back({ &Billon,1 });

    Tilon.roads.push_back({ &Turbilon,1 });

    Turbilon.roads.push_back({ &Melon,1 });

    Planet Melmac;
    Melmac.cities = { Nylon,Gulidon,Etalon,Lion,Gaucon,Tigron,Wisdon,London,Melon,Billon,Tilon,Turbilon };

    try
    {
        auto list = breadthFirstSearchReachable(&Melmac, "London");
        for (auto it = list.begin(); it != list.end(); it++)
        {
            cout << *it << ' ';
        }
    }
    catch (CityNotExistsException& c)
    {
        cout << c.what();
    }*/

    /*City London("London");
    City Haron("Haron");
    City Pecelon("Pecelon");
    City Ballon("Ballon");
    City Nicudon("Nicudon");
    City Etalon("Etalon");
    City Tigron("Tigron");

    London.roads.push_back({ &Haron,10 });
    London.roads.push_back({ &Pecelon,40 });
    London.roads.push_back({ &Nicudon,100 });

    Haron.roads.push_back({ &Pecelon,20 });

    Pecelon.roads.push_back({ &Ballon,10 });
    Pecelon.roads.push_back({ &Nicudon,50 });

    Ballon.roads.push_back({ &Haron,15 });
    Ballon.roads.push_back({ &Nicudon,50 });

    Etalon.roads.push_back({ &Tigron,10 });

    Planet Melmac2;
    Melmac2.cities = { London,Haron,Pecelon,Ballon,Nicudon,Etalon,Tigron };
    dijkstra(&Melmac2,"London");*/

    return 0;
}
