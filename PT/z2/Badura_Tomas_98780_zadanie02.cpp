/*
Meno a priezvisko: Tomas Badura

POKYNY:
(1)  Subor premenujte na Priezvisko_Meno_ID_zadanie02.cpp (pouzite vase udaje bez diakritiky).
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

#include <iostream>

using namespace std;

//-------------------------------------------------------------------------------------------------
// DATOVE TYPY
//-------------------------------------------------------------------------------------------------

// Reprezentacia uzla zretazeneho zoznamu
struct Node {
    int data; // hodnota uzla
    Node* next; // adresa nasledujuceho uzla zoznamu
};

// Reprezentacia zretazeneho zoznamu
struct List {
    Node* first; // adresa prveho uzla zoznamu
};

// Uspesnost vykonania funkcie
enum class Result {
    SUCCESS, // funkcia vykonana uspesne
    FAILURE  // chyba pri vykonavani funkcie
};

Node* createNode(const int value) {
    Node* tmp = new Node;               // pomocna funkcia vytvori a vrati uzol
    tmp->data = value;
    tmp->next = nullptr;
    return tmp;
}
//-------------------------------------------------------------------------------------------------
// 1. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Funkcia prida novy uzol s hodnotou 'val' na zaciatok zoznamu 'list'.

    PARAMETRE:
        [in] list - vstupny zretazeny zoznam
        [in] val - hodnota uzla pridaneho na zaciatok zoznamu

    PRIKLADY:
        list={} a val=0 ... zoznam po vykonani funkcie ... {0}
        list={-3} a val=1 ... zoznam po vykonani funkcie ... {1,-3}
        list={6,6,6,8} a val=10 ... zoznam po vykonani funkcie ... {10,6,6,6,8}
*/


void prependNode(List* list, const int val) {
    Node* newNode = createNode(val);
    newNode->next = list->first;
    list->first = newNode;
}

//-------------------------------------------------------------------------------------------------
// 2. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Funkcia prida novy uzol s hodnotou 'val' na koniec zoznamu 'list'.

    PARAMETRE:
        [in] list - vstupny zretazeny zoznam
        [in] val - hodnota uzla pridaneho na koniec zoznamu

    PRIKLADY:
        list={} a val=7 ... zoznam po vykonani funkcie ... {7}
        list={0} a val=1 ... zoznam po vykonani funkcie ... {0,1}
        list={1,2,3,4,5} a val=6 ... zoznam po vykonani funkcie ... {1,2,3,4,5,6}
*/

void appendNode(List* list, const int val) {
    Node* newNode = createNode(val);
    if (!list->first) {
        list->first = newNode;
    }
    else {
        Node* tmp = list->first;
        while (tmp->next) {
            tmp = tmp->next;
        }
        tmp->next = newNode;
    }
}

//-------------------------------------------------------------------------------------------------
// 3. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Funkcia vytvori novy zretazeny zoznam a vyplni ho vsetkymi cislami (v zachovanom poradi),
    ktore sa nachadzaju vo vstupnom poli 'data', ktore ma dlzku 'n'.

    PARAMETRE:
        [in] data - vstupne pole cisiel
        [in] n - dlzka vstupneho pola

    RETURN:
        Vytvoreny zretazeny zoznam obsahujuci vsetky hodnoty zo vstupneho pola 'data' (v zachovanom poradi).
        V pripade prazdneho pola (dlzka 0), funkcia vrati prazdny zoznam (prazdny zoznam je taky, kde smernik 'first'
        ukazuje na 'nullptr'). V pripade neplatnej dlzky (ak je parameter 'n' zaporny), funkcia vrati 'nullptr'.

    PRIKLADY:
        data={1} a n=1 ... vrati zoznam ... {1}
        data={7,6,41,2} a n=4 ... vrati zoznam ... {7,6,41,2}
        data={3,2,1} a n=0 ... vrati zoznam ... {}
        data={6,3,9,10} a n=-7 ... vrati ... 'nullptr'
*/

List* createListFromArray(const int* data, const int n) {
    if (n < 0)
    {
        return nullptr;
    }
    List* list = new List;
    list->first = nullptr;
    if (n == 0)
    {
        return list;
    }
    else 
    {
        for (int i = 0; i < n; i++)
        {
            appendNode(list, data[i]);
        }
    }
	return list; 
}

//-------------------------------------------------------------------------------------------------
// 4. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Funkcia vytvori novy symetricky zretazeny zoznam (vid priklady) podla parametra 'val'. Symetricky zoznam ma
    tvar 0,1,2,...val...2,1,0.

    PARAMETRE:
        [in] val - hodnota uzla podla, ktoreho sa vytvori novy symetricky zretazeny zoznam (uzol v strede zoznamu).

    RETURN:
        Vytvoreny zretazeny zoznam obsahujuci prvky v takom poradi, aby bol symetricky. V pripade 'val'<0, funkcia vrati
        'nullptr'.

    PRIKLADY:
        val=-31 ... vrati ... nullptr
        val=0 ... vrati zoznam ... {0}
        val=1 ... vrati zoznam ... {0,1,0}
        val=2 ... vrati zoznam ... {0,1,2,1,0}
        val=3 ... vrati zoznam ... {0,1,2,3,2,1,0}
        val=4 ... vrati zoznam ... {0,1,2,3,4,3,2,1,0}
        val=6 ... vrati zoznam ... {0,1,2,3,4,5,6,5,4,3,2,1,0}
*/

List* createSymmetricList(const int val) {
    if (val < 0)
    {
        return nullptr;
    }
    List* list = new List;
    list->first = nullptr;
    int secondHalf = val - 1;
    for (int i = 0; i < (2 * val + 1); i++) 
    {
        if (i <= val)
        {
            appendNode(list, i);
        }
        else
        {
            appendNode(list, secondHalf);
            secondHalf--;
        }
    }
    return list;
}

//-------------------------------------------------------------------------------------------------
// 5. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Funkcia odstrani (aj spravne uvolni pamat) zo vstupneho zoznamu 'list' prvy uzol.

    PARAMETRE:
        [in,out] list - vstupny zretazeny zoznam

    RETURN:
        Result::SUCCESS, ak bol prvy uzol vymazany
        Result::FAILURE, ak nebol prvy uzol vymazany (vstupny zoznam bol prazdny)

    PRIKLADY:
        list={} ... funkcia vrati Result::FAILURE
        list={5} ... zoznam po vykonani funkcie ... {} a vrati Result::SUCCESS
        list={10,10,10} ... zoznam po vykonani funkcie ... {10,10} a vrati Result::SUCCESS
        list={3,2,1} ... zoznam po vykonani funkcie ... {2,1} a vrati Result::SUCCESS
*/

Result removeFirstNode(List* list) {
    if (list->first) {
        Node* tmp = list->first->next;
        delete list->first;
        list->first = tmp;
        return Result::SUCCESS;
    }
	return Result::FAILURE; 
}

//-------------------------------------------------------------------------------------------------
// 6. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Funkcia najde prvy uzol zretazeneho zoznamu 'list', ktory obsahuje hodnotu 'val'.

    PARAMETRE:
        [in] list - vstupny zretazeny zoznam
        [in] val - hodnota uzla, ktory sa hlada

    RETURN:
        Funkcia vrati prvy uzol, ktory obsahuje hodnotu 'val'. Ak sa taky uzol v zozname nenachadza alebo je vstupny
        zoznam prazdny, vtedy funkcia vrati 'nullptr'.

    PRIKLADY:
        list={}, val=10 ... funkcia vrati 'nullptr'
        list={2}, val=3 ... funkcia vrati 'nullptr'
        list={1,2,3,4,5,6}, val=-1 ... funkcia vrati 'nullptr'
        list={1}, val=1 ... funkcia vrati uzol s hodnotou 1
        list={5,9,18}, val=9 ... funkcia vrati uzol s hodnotou 9
*/

Node* findNodeInList(List* list, const int val) {
    Node* tmp = list->first;
    while (tmp)
    {
        if (tmp->data == val)
        {
            return tmp;
        }
        tmp = tmp->next;
    }
	return nullptr; 
}

//-------------------------------------------------------------------------------------------------
// 7. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Funkcia zisti, ci su dva vstupne zoznamy 'list1' a 'list2' rovnake (t.j. rovnako dlhe a obsahujuce
    rovnake hodnoty uzlov v rovnakom poradi).

    PARAMETRE:
        [in] list1 - prvy zretazeny zoznam
        [in] list2 - druhy zretazeny zoznam

    RETURN:
        'true' - ak su vstupne zoznamy rovnake
        'false' - ak vstupne zoznamy nie su rovnake

    PRIKLADY:
        list1={1}
        list2={}
        Funkcia vrati 'false'.

        list1={}
        list2={5,3}
        Funkcia vrati 'false'.

        list1={}
        list2={}
        Funkcia vrati 'true'.

        list1={1}
        list2={1}
        Funkcia vrati 'true'.

        list1={4,-9,2}
        list2={4,-9,2}
        Funkcia vrati 'true'.

        list1={3,2,1}
        list2={1,2,3}
        Funkcia vrati 'false'.

        list1={2}
        list2={7,4,5}
        Funkcia vrati 'false'.
*/

bool areListsEqual(List* list1, List* list2) {
    Node *tmp1 = list1->first, *tmp2 = list2->first;
    while (tmp1 && tmp2)
    {
        if (tmp1->data != tmp2->data)
        {
            return false;
        }
        tmp1 = tmp1->next;
        tmp2 = tmp2->next;
    }
    if (tmp1 == nullptr && tmp2 == nullptr)
    {
        return true;
    }
    return false;
}


//-------------------------------------------------------------------------------------------------
// 8. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Funkcia vytvori novy zretazeny zoznam prekopirovanim vsetkych hodnot uzlov vstupneho zoznamu 'list' v obratenom
    poradi.

    PARAMETRE:
        [in] list - vstupny zretazeny zoznam

    RETURN:
        Zretazeny oznam, ktory vznikne prekopirovanim hodnot uzlov vstupneho zoznamu 'list' v obratenom poradi.

    PRIKLADY:
        list={} ... funkcia vrati ... {} t.j. prazdny zoznam
        list={1} ... funkcia vrati ... {1}
        list={5,6} ... funkcia vrati ... {6,5}
        list={8,14,2,3} ... funkcia vrati ... {3,2,14,8}
*/

List* copyListReverse(List* list) {
    Node* tmp = list->first;
    List* listReverse = new List;
    listReverse->first = nullptr;
    while (tmp)
    {
        prependNode(listReverse, tmp->data);
        tmp = tmp->next;
    }
	return listReverse; 
}

//-------------------------------------------------------------------------------------------------
// 9. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Funkcia vyhlada predchodcu zadaneho uzla 'node' vo vstupnom zretazenom zozname 'list'. Uzol 'node' je vzdy
    existujucim uzlom vstupneho zoznamu 'list'.

    PARAMETRE:
        [in] list - vstupny zretazeny zoznam
        [in] node - uzol zretazeneho zoznamu 'list', ktoreho predchodcu hladame

    RETURN:
        Uzol, ktory je predchodcom uzla 'node' v zozname 'list'. V pripade hladania predchodcu prveho uzla zoznamu,
        funkcia vrati 'nullptr'.

    PRIKLADY:
        list={4}
        node=4
        Funkcia vrati 'nullptr'.

        list={1,2,3,4,5}
        node=1
        Funkcia vrati 'nullptr'.

        list={-2,-3,56,4,41}
        node=-3
        Funkcia vrati uzol s hodnotou -2.

        list={10,54,69,82,6}
        node=6
        Funkcia vrati uzol s hodnotou 82.
*/

Node* findPreviousNode(List* list, Node* node) {
    Node* tmp = list->first, * NodeToReturn;
    if (tmp == node)
    {
        return nullptr;
    }
    NodeToReturn = tmp;
    tmp = tmp->next;
    while (tmp)
    {
        if (tmp == node)
        {
            return NodeToReturn;
        }
        NodeToReturn = tmp;
        tmp = tmp->next;
    }
    return nullptr; // questionable return
}  

//-------------------------------------------------------------------------------------------------
// 10. ULOHA (0.4 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Funkcia zduplikuje vsetky uzly vstupneho zoznamu 'list', ktore obsahuju kladnu hodnotu.

    PARAMETRE:
        [in,out] list - vstupny zretazeny zoznam

    PRIKLADY:
        list={} ... po vykonani funkcie ... {}
        list={3} ... po vykonani funkcie ... {3,3}
        list={-1,1} ... po vykonani funkcie ... {-1,1,1}
        list={-8,-9,-13} ... po vykonani funkcie ... {-8,-9,-13}
        list={1,0,-2,3,-4} ... po vykonani funkcie ... {1,1,0,-2,3,3,-4}
*/

void duplicatePositiveNodes(List* list) {
    Node* tmp = list->first, * newNode, * nextNode = nullptr;
    while (tmp)
    {
        if (tmp->data > 0)
        {
            newNode = createNode(tmp->data);
            if (tmp->next != nullptr)
            {
                nextNode = tmp->next;
            }
            tmp->next = newNode;
            newNode->next = nextNode;
            tmp = tmp->next;
        }
        nextNode = nullptr;
        tmp = tmp->next;
    }
}

//-------------------------------------------------------------------------------------------------
// TESTOVANIE
//-------------------------------------------------------------------------------------------------

// tu mozete doplnit pomocne funkcie a struktury}

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

    /*List* list = new List;
    list->first = nullptr;
    prependNode(list, 0);
    prependNode(list, 46);
    prependNode(list, 10);
    prependNode(list, 666);          //uloha 1
    Node* p = list->first;
    while (p)
    {
        cout << p->data << endl;
        p = p->next;
    }*/


    /*int data[] = { 1,5,9,588,456,13 };
    List* smernik = createListFromArray(data, 6);
    Node* p = smernik->first;                           //uloha 3
    while (p)
    {
        cout << p->data << " ";
        p = p->next;
    }*/
    

    /*List* smernik = createSymmetricList(6);
    Node* p = smernik->first;                           //uloha 4
    while (p)
    {
        cout << p->data << " ";
        p = p->next;
    }*/


    /*List* list = new List;
    list->first = nullptr;                          
    for (int i = 0; i < 10; i++)
    {
        appendNode(list, i+10);                             
    }
    Node* p = list->first;
    while (p)
    {
        cout << p->data << " ";
        p = p->next;
    }
    cout << endl;
    //appendNode(list, 10);                             //uloha 5
    cout << toString(removeFirstNode(list));
    cout << endl;
    p = list->first;
    while (p)
    {
        cout << p->data << " ";
        p = p->next;
    }*/


    /*List* list = new List;
    list->first = nullptr;
    for (int i = 0; i < 4; i++)
    {
        appendNode(list, i + 1);                             //uloha 6
    }
    appendNode(list, 4);
    appendNode(list, 3);
    appendNode(list, 3);
    Node* p;
    if ((p = findNodeInList(list, 3)) == nullptr)
        cout << "nullptr bby" << endl;
    else
        cout << p << endl << p->data << endl;
    delete p;*/


    /*List* list1 = new List;
    List* list2 = new List;
    list1->first = nullptr;
    list2->first = nullptr;
    int top = 3;
    for (int i = 0; i < top; i++)
    {
        appendNode(list1, 3);
        appendNode(list1, 2);                  //uloha 7
        appendNode(list1, 1);
    }
    for (int i = 0; i < top; i++)
    {
        appendNode(list2, 3);
        appendNode(list2, 2);                  
        appendNode(list2, 1);
    }
    
    cout << areListsEqual(list1, list2) << endl;*/


    /*List* list = new List;
    list->first = nullptr;
    appendNode(list, 8);
    appendNode(list, 18);
    appendNode(list, 82);
    appendNode(list, -8);
    appendNode(list, 888);

    Node* x = list->first;
    while (x)
    {
        cout << x->data << " ";                 //uloha 8
        x = x->next;
    }
    cout << endl;
    List* listReturned;
    listReturned = copyListReverse(list);
    Node* p = listReturned->first;                           
    while (p)
    {
        cout << p->data << " ";
        p = p->next;
    }*/


    /*List* list = new List;
    list->first = nullptr;
    appendNode(list, 1);
    appendNode(list, 2);
    appendNode(list, 3);                    //uloha 9
    appendNode(list, 4);
    Node* tmp = list->first->next->next, * p;
    p = findPreviousNode(list, tmp);
    cout << p->data << endl;*/
    

    /*List* list = new List;
    list->first = nullptr;
    appendNode(list, -8);
    appendNode(list, 9);
    appendNode(list, -13);
    appendNode(list, 3);
    appendNode(list, -4);
    Node* x = list->first;
    while (x)
    {
        cout << x->data << " ";         
        x = x->next;
    }
    cout << endl;
    duplicatePositiveNodes(list);               //uloha 10
    x = list->first;
    while (x)
    {
        cout << x->data << " ";
        x = x->next;
    }
    cout << endl;*/

    
    return 0;
}