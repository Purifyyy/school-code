/*
Meno a priezvisko: Tomas Badura

POKYNY:
(1)  Subor premenujte na Priezvisko_Meno_ID_zadanie09.cpp (pouzite vase udaje bez diakritiky).
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
     V prvych osmich prikladoch mozete pouzit iterativnu alebo rekurzivnu implementaciu. <- SPECIALNE INSTRUKCIE PRE TOTO ZADANIE
     V pripade implementacie rekurziou, moze byt hlavna cast implemetovana v pridanej rekurzivnej funkcii. <- SPECIALNE INSTRUKCIE PRE TOTO ZADANIE
(9)  Vase riesenie otestujte (vo funkcii 'main' a pomocou doplnenych pomocnych funkcii alebo struktur).
     Testovaci kod ale nebude hodnoteny.
(10) Funkcia 'main' musi byt v zdrojovom kode posledna.
*/

#include <iostream>
#include <list>
#include <vector>
#include <map>
#include <exception>
#include <set>
#include <initializer_list>
#include <queue>

using namespace std;

//-------------------------------------------------------------------------------------------------
// DATOVE TYPY
//-------------------------------------------------------------------------------------------------

// Uzol binarneho vyhladavacieho stromu
struct Node {
    int value;     // hodnota uzla
    Node *smaller; // uzol 'smaller' a jeho nasledovnici maju hodnotu mensiu ako 'value'
    Node *greater; // uzol 'greater' a jeho nasledovnici maju hodnotu vacsiu ako 'value'

    explicit Node(int value = 0, Node *smaller = nullptr, Node *greater = nullptr)
            : value(value)
            , smaller(smaller)
            , greater(greater)
    {
    }
};

// Binarny vyhladavaci strom
struct BinarySearchTree {
    Node *root; // koren stromu

    explicit BinarySearchTree(Node *root = nullptr)
            : root(root)
    {
    }
};

// Vynimka oznacujuca, ze uzol so zadanou hodnotou v strome neexistuje
class ValueNotExistsException : public std::exception {
};

//-------------------------------------------------------------------------------------------------
// 1. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Funkcia vrati najmensiu hodnotu v strome.
    V pripade neexistencie tejto hodnoty vyhodi vynimku.

    VSTUPNY PARAMETER:
        [in] tree - strom, v ktorom funkcia hlada minimum

    NAVRATOVA HODNOTA:
        najmensia hodnota v strome

    VYNIMKA:
        ValueNotExistsException - ak je strom prazdny
*/

int min(const BinarySearchTree *tree) {
    Node* uzol = tree->root;
    int min = 0;
    if (uzol == nullptr)
    {
        throw ValueNotExistsException();
    }
    while (uzol)
    {
        min = uzol->value;
        uzol = uzol->smaller;
    }
    return min;
}

//-------------------------------------------------------------------------------------------------
// 2. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Funkcia vrati hlbku uzla s hodnotou 'value' v strome 'tree'.
    Hlbka korena je 0.
    Ak hladany uzol v strome nie je, tak funkcia vyhodi vynimku.

    VSTUPNE PARAMETRE:
        [in] tree - strom, v ktorom funkcia hlada uzol
        [in] value - hodnota hladaneho uzla

    NAVRATOVA HODNOTA:
        Hlbka uzla s hodnotou 'value'.

    VYNIMKA:
        ValueNotExistsException - ak sa uzol s hodnotou 'value' v strome nenachdza
*/

unsigned zistiHlbku(struct Node* uzol, int hodnota, int hlbka)
{
    if (uzol == nullptr)
        return 0;

    if (uzol->value == hodnota)
        return hlbka;

    int poschodie = zistiHlbku(uzol->smaller, hodnota, hlbka + 1);
    if (poschodie != 0)
        return poschodie;

    poschodie = zistiHlbku(uzol->greater, hodnota, hlbka + 1);
    return poschodie;
}

unsigned depth(const BinarySearchTree *tree, int value) {
    Node* uzol = tree->root;
    if (!uzol)
    {
        throw ValueNotExistsException();
    }
    if (value == uzol->value)
    {
        return 0;
    }
    unsigned level = zistiHlbku(uzol, value, 0);
    if (level) return level;
    else throw ValueNotExistsException();
}

//-------------------------------------------------------------------------------------------------
// 3. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Funkcia vrati zoznam hodnot uzlov, ktore musi prejst pri hladani uzla s hodnotou 'value'.
    Prva hodnota vystupneho zoznamu je hodnota korena, druha hodnota zoznamu je lavy alebo pravy nasledovnik korena, atd.
    Poslednou hodnotou vo vystupnom zozname je hodnota uzla, na ktorom sa hladanie zastavilo.
    Ak 'tree' obsahuje uzol s hodnotou 'value', tak poslednou hodnotou vystupeho zoznamu je 'value'.

    VSTUPNE PARAMETRE:
        [in] tree - strom, v ktorom funkcia hlada uzol s hodnotou 'value'
        [in] value - hodnota hladaneho uzla

    NAVRATOVA HODNOTA:
        Zoznam hodnot uzlov, ktorych hodnoty musela funkcia pri hladani porovnavat.
        Poradie hodnot vo vystupnom zozname musi zodpovedat poradiu prechadzania uzlov stromu.

    PRIKLADY:
        V prikladoch predpokladajme strom:
                  40
                 /  \
               20    50
              /  \    \
            10   30    60

        1. priklad
            value: 30 -> vystup: (40, 20, 30)
        2. priklad
            value: 45
            vystup: (40, 50)
*/

list<int> path(const BinarySearchTree *tree, int value) noexcept {
    list<int> postupnost;
    Node* uzol = tree->root;
    while (uzol)
    {
        if (uzol->value == value)
        {
            postupnost.push_back(uzol->value);
            return postupnost;
        }
        if (value > uzol->value)
        {
            postupnost.push_back(uzol->value);
            uzol = uzol->greater;
        }
        else
        {
            postupnost.push_back(uzol->value);
            uzol = uzol->smaller;
        }
    }
    return postupnost;
}

//-------------------------------------------------------------------------------------------------
// 4. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Funkcia vrati pocet uzlov stromu.

    VSTUPNY PARAMETER:
        [in] tree - strom, ktoreho pocet uzlov funkcia zistuje

    NAVRATOVA HODNOTA:
        pocet uzlov stromu
*/

size_t count(const BinarySearchTree *tree) noexcept {
    if (!tree->root)
        return 0;
    queue<Node*> rad;
    int pocet = 1;
    rad.push(tree->root);
    while (!rad.empty())
    {
        Node* temp = rad.front();
        rad.pop();

        if (temp->smaller)
            pocet++;
        
        if (temp->greater)
            pocet++;

        if (temp->smaller != nullptr)
            rad.push(temp->smaller);
        if (temp->greater != nullptr)
            rad.push(temp->greater);
    }
    return pocet;
}

//-------------------------------------------------------------------------------------------------
// 5. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Funkcia vrati zoznam hodnot vsetkych uzlov v strome 'tree'.
    Zoznam musi byt usporiadany od najmensieho prvku po najvacsi.

    Usporiadanie dosiahnite vhodnou postupnostou prechadzania uzlov stromu!

    VSTUPNY PARAMETER:
        [in] tree - strom, ktoreho hodnoty funkcia vrati

    NAVRATOVA HODNOTA:
        hodnoty uzlov, v poradi od najmensej po najvacsiu
*/

void inOrder(const Node* node, list<int> *hodnoty) {
    if (node) {
        inOrder(node->smaller, hodnoty);
        hodnoty->push_back(node->value);
        inOrder(node->greater, hodnoty);
    }
}

list<int> all(const BinarySearchTree *tree) noexcept {
    list<int> hodnoty;
    inOrder(tree->root, &hodnoty);
    return hodnoty;
}

//-------------------------------------------------------------------------------------------------
// 6. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Funkcia vrati pocet uzlov stromu 'tree', ktorych hodnota je vacsie ako 'value'

    VSTUPNE PARAMETRE:
        [in] tree - strom, v ktorom funkcia pocita pocet uzlov splnajich podmienku
        [in] value - hodnota, podla ktorej sa vyberaju uzly

    NAVRATOVA HODNOTA:
        pocet uzlov s hodnotou vacsou ako 'value'
*/

size_t countGreater(const BinarySearchTree* tree, int value) noexcept {
    list<int> hodnoty;
    size_t pocet = 0;
    inOrder(tree->root, &hodnoty);
    for (auto i = hodnoty.rbegin(); i != hodnoty.rend(); i++)
    {
        if (*i <= value)
        {
            break;
        }
        pocet++;
    }
    return pocet;
}

//-------------------------------------------------------------------------------------------------
// 7. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Funkcia odstrani vsetky uzly stromu a dealokuje ich.

    Na dealokaciu pouzite operator 'delete'!

    VSTUPNY PARAMETER:
        [in] tree - strom, ktoreho uzly funkcia odstrani

    VYSTUPNE PODMIENKY:
        vsetky uzly su dealokovane
        'tree->root' je nulovy smernik
*/

void deleteTree(Node* node)
{
    if (node == nullptr) return;

    deleteTree(node->smaller);
    deleteTree(node->greater);

    delete node;
}

void clear(BinarySearchTree *tree) noexcept {
    deleteTree(tree->root);
    tree->root = nullptr;
}

//-------------------------------------------------------------------------------------------------
// 8. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Funkcia hlada hodnotu 'value' vo vektore 'data'.
    Vrati pocet prvkov v 'data', ktore pri hladni porovnala s 'value'.
    Vektor 'data' obsahuje usporiadane hodnoty. 
    Casova narocnost algoritmu je O(n log(n)).

    PARAMETRE:
        'data' - hodnoty usporiadane od najmensieho prvku po najvacsi
        'value' - hodnota hladana v 'data'

    NAVRATOVA HODNOTA:
        Pocet prvkov 'data', ktore funkcia pri hladani porovnala s 'value'.
        (bez ohladu na to, ci 'data' obsahuje hodnotu 'value').

    VSTUPNA PODMIENKA:
        Hodnoty v 'data' su usporiadane od najmensieho prvku po najvacsi.

    PRIKLADY:
        data = (100, 102, 104, 106, 108, 110, 112), value = 106 -> vystup: 1
        data = (100, 102, 104, 106, 108, 110, 112), value = 110 -> vystup: 2
        data = (100, 102, 104, 106, 108, 110, 112), value = 108 -> vystup: 3
        data = (100, 102, 104, 106, 108, 110), value = 104 -> vystup: 1 alebo 3
        data = (100, 102, 104, 106, 108, 110), value = 107 -> vystup: 3
        data = (100), value = 100 -> vystup: 1
        data = (200), value = 200 -> vystup: 1
        data = (), value = 100 -> vystup: 0
*/

unsigned contains(const vector<int> & data, int value) noexcept {
    int low = 0, high = data.size()-1, middle = 0;
    unsigned pocet = 0;
    while (low <= high)
    {
        middle = low + (high - low) / 2;
        if (value == data[middle])
        {
            pocet++;
            break;
        }
        else if (value < data[middle])
        {
            high = middle - 1;
        }
        else
        {
            low = middle + 1;
        }
        pocet++;
    }
    return pocet;
}

//-------------------------------------------------------------------------------------------------
// 9. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Funkcia vytvori a vrati histogram slov v 'data' (zisti, kolko krat sa ktore slovo nachadza v 'data')

    PARAMETER:
        'data' - vektor slov

    NAVRATOVA HODNOTA:
        histogram slov (pre kazde slovo z 'data' obsahuje pocet jeho vyskytov)

    VSTUPNE PODMIENKY:
        'data' moze obsahovat lubobolny pocet slov
        'data' moze obsahovat opakujuce sa slova
        slova mozu byt prazdne retazce

    VYSTUPNE PODMIENKY:
        Histogram obsahuje iba slova, ktore sa v 'data' nachadzaju

    PRIKLADY:
        vstup: data = ("pocitac", "lietadlo", luk", "pocitac", "pocitac", "okno", "luk")
        vystup: {"pocitac" -> 3, "lietadlo" -> 1, "luk" -> 2, "okno" -> 1}

        vstup: data = ("pocitac", "")
        vystup: {"pocitac" -> 1, "" -> 1}

        vstup: data = ()
        vystup: {}
*/

map<string, size_t> histogram(const vector<string> & data) noexcept {
    map<string, size_t> histogram;
    pair<map<string, size_t>::iterator, bool> navrat;
    for (auto i = data.begin(); i != data.end(); i++)
    {
        navrat = histogram.insert(pair<string,size_t>{ *i,1 });
        if (navrat.second == false)
        {
            histogram[*i]++;    
        }
    }
    return histogram;
}

//-------------------------------------------------------------------------------------------------
// 10. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Funkcia vytvori a vrati index slov v 'data' (pre kazde slovo mnozinu indexov jeho vyskytu).

    PARAMETER:
        data - vektor slov

    NAVRATOVA HODNOTA:
        index slov (pre kazde slovo z 'data' obsahuje indexy, na ktorych sa slovo v 'data' nachadza)

    VSTUPNE PODMIENKY:
        'data' moze obsahovat lubobolny pocet slov
        'data' moze obsahovat opakujuce sa slova
        slova mozu byt prazdne retazce

    VYSTUPNE PODMIENKY:
        Index obsahuje zoznamy vyskytov len pre slova, ktore sa nachadzaju v 'data'

    PRIKLADY:
        vstup: data = ("pocitac", "lietadlo", luk", "pocitac", "pocitac", "okno", "luk")
        vystup: {"pocitac" -> {0, 3, 4}, "lietadlo" -> {1}, "luk" -> {2, 6}, "okno" -> {5}}

        vstup: data = ("pocitac", "")
        vystup: {"pocitac" -> {0}, "" -> {1}}

        vstup: data = ()
        vystup: {}
*/

map<string, set<size_t>> index(const vector<string>& data) noexcept {
    map<string, set<size_t>> indexArr;
    set<size_t> myset;
    pair<map<string, set<size_t>>::iterator, bool> navrat;
    size_t miesto = 0;
    for (auto it = data.begin(); it != data.end(); it++)
    {
        navrat = indexArr.insert(pair<string, set<size_t>>{ *it, {} });
        if (navrat.second != false)
        {
            for (auto it2 = data.begin(); it2 != data.end(); it2++)
            {
                if (*it2 == *it)
                {
                    myset.insert(miesto);
                }
                miesto++;
            }
            miesto = 0;
            indexArr[*it] = myset;
            myset.clear();
        }
    }
    return indexArr;
}
//-------------------------------------------------------------------------------------------------
// TESTOVANIE
//-------------------------------------------------------------------------------------------------

Node* addNode(Node* curr_node, const int val) {
    if (curr_node) {
        if (val < curr_node->value) {
            curr_node->smaller = addNode(curr_node->smaller, val);
        }
        else if (val > curr_node->value) {
            curr_node->greater = addNode(curr_node->greater, val);
        }
        return curr_node;
    }
    else {
        return new Node{ val,nullptr,nullptr };
    }
}

void addNode(BinarySearchTree* bst, const int val) {
    bst->root = addNode(bst->root, val);
}

BinarySearchTree* createBST(const initializer_list<int>& list){
    BinarySearchTree* bst{new BinarySearchTree{}};
    for (int i : list) {
        addNode(bst, i);
    }
    return bst; 
}

int main() {

    /*struct Node* koren = new Node(5);
    koren->smaller = new Node(3);
    koren->smaller->smaller = new Node(2);                                  //uloha 1
    BinarySearchTree strom;
    strom.root = koren;
    cout<< endl << min(&strom);*/                   
    
    //BinarySearchTree* bst{ createBST({10}) };                             //uloha 2
    //cout << depth(bst, 10);

    //BinarySearchTree* bst{ createBST({20,10,70,0,15,50,80,18,55}) };
    //auto list = path(bst, 65);
    //for (auto i = list.begin(); i != list.end(); i++)                       //uloha 3
    //{
    //    cout << *i << ' ';
    //}

    /*BinarySearchTree* bst{ createBST({20,10,70,0,15,50,80,18,55}) };      //uloha 4
    cout << count(bst);*/

    //BinarySearchTree* bst{ createBST({20,10,70,0,15,50,80,18,55}) };
    //auto hodnoty = all(bst);
    //for (auto i = hodnoty.begin(); i != hodnoty.end(); i++)                 //uloha 5
    //{
    //    cout << *i << ' ';
    //}

    /*BinarySearchTree* bst{ createBST({20,10,70,0,15,50,80,18,55}) };      //uloha 6
    cout << countGreater(bst, 79);*/

    //BinarySearchTree* bst{ createBST({20,10,70,0,15,50,80,18,55}) };      //uloha 7
    //clear(bst);
        
    /*vector<int> data = {  };
    data = { 100, 102, 104, 106, 108, 110, 112 };
    cout << contains(data, 106) << endl;
    cout << contains(data, 110) << endl;
    cout << contains(data, 108) << endl;
    data = { 100, 102, 104, 106, 108, 110 };
    cout << contains(data, 104) << endl;
    cout << contains(data, 107) << endl;
    data = { 100 };
    cout << contains(data, 100) << endl;
    data = { 200 };
    cout << contains(data, 200) << endl;
    data = {};
    cout << contains(data, 100) << endl;*/
    
    /*vector<string> data = { "pocitac", "" };
    auto output = histogram(data);
    for (auto it = output.begin(); it != output.end(); it++)            //uloha 9
    {
        cout << it->first << " => " << it->second << '\n';
    }*/
    
    /*vector<string> data = { "pocitac", "lietadlo", "luk", "pocitac", "pocitac", "okno", "luk" };
    map<string, set<size_t> >::iterator m;
    auto vystup = index(data);
    for (auto it = vystup.begin(); it != vystup.end(); it++)                                                //uloha 10
    {
        cout << it->first << " => ";
        for (auto m = it->second.begin(); m != it->second.end(); m++)
        {
            cout << *m << ' ';
        }
        cout << endl;
    }*/

    return 0;
}
