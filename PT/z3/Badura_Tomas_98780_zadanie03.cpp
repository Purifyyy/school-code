/*
Meno a priezvisko: Tomas Badura

POKYNY:
(1)  Subor premenujte na Priezvisko_Meno_ID_zadanie03.cpp (pouzite vase udaje bez diakritiky).
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

#include <iostream>
#include <cstring>

using namespace std;

//-------------------------------------------------------------------------------------------------
// DATOVE TYPY
//-------------------------------------------------------------------------------------------------

// Uzol zretazeneho zoznamu
struct Node {
    int data; // hodnota uzla
    Node* next; // smernik na dalsi uzol zoznamu
};

// Zretazeny zoznam
struct List {
    Node* first; // smernik na prvy uzol zoznamu
};

//------------------------------------------------------------------------------------------------
void merge(int* output, const int* input, const size_t low, const size_t middle, const size_t high);

void mergeSortRecursive(int* arrayA, int* arrayB, const size_t low, const size_t high)
{
    if (low + 1 >= high) {
        return;
    }
    const size_t middle = (high + low) / 2;

    mergeSortRecursive(arrayB, arrayA, low, middle);
    mergeSortRecursive(arrayB, arrayA, middle, high);

    merge(arrayA, arrayB, low, middle, high);
}

//-------------------------------------------------------------------------------------------------
// 1. ULOHA (0.8 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Usporiada pole 'data' od najvacsieho prvku po najmensi prvok.
    Pouzite algoritmus insertion sort.

    PARAMETRE:
        [in, out] data - pole, ktore funkcia usporiada
        [in] length    - pocet prvkov pola

    VSTUPNE PODMIENKY:
        'length' moze mat lubovolnu hodnotu
        'data' ukazuje na platne pole

    PRIKLADY:
        {1,3,2} -> {3, 2, 1}
        {1} -> {1}
        {} -> {}
*/

void insertionSort(int *data, const size_t length) {
    int tmp;
    int p;
    for (size_t i = 1; i < length; i++)
    {
        tmp = data[i];
        p = i - 1;
        while (p >= 0 && data[p] < tmp)
        {
            data[p + 1] = data[p];
            p--;
        }
        data[p + 1] = tmp;
    }
}

//-------------------------------------------------------------------------------------------------
// 2. ULOHA (0.8 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Usporiada textove retazce v poli 'data' od najvacsieho prvku po najmensi (lexikograficky).
    Preusporiadajte smerniky v poli.
    Pouzite algoritmus insertion sort.

    PARAMETRE:
        [in, out] data - pole, ktore funkcia usporiada.
                Pole obsahuje smerniky na textove retazce.
                Kazdy textovy retazec je ukonceny '\0'.
                Posledny smernik ma hodnotu 'nullptr'. Podla toho urcite pocet prvkov pola (pocet textovych retazcov).

    VSTUPNE PODMIENKY:
        'data' obsahuje minimalne jeden smernik.
        Posledny smernik ma hodnotu 'nullptr'.

    PRIKLADY:
        {"Juraj", "Peter", "Andrej", nullptr} -> {"Peter", "Juraj", "Andrej", nullptr}
        {"Andrej", nullptr} -> {"Andrej", nullptr}
        {nullptr} -> {nullptr}

    POZNAMKY:
        Pri testovani mozete jednoducho pole vytvorit nasledovnym sposobom:
        const char *mena[] = {"Juraj", "Peter", "Andrej", nullptr};

        Na porovnanie obsahu textovych retazcov vyuzite prislusnu funkciu zo standardnej kniznice.
*/
void insertionSort(const char *data[]) {
    size_t lenght = 0;
    int p;
    const char* tmp = data[lenght];
    while (tmp)
    {
        lenght++;
        tmp = data[lenght];
    }
    if (lenght < 2) return;
    for (size_t i = 1; i < lenght; i++)
    {
        tmp = data[i];
        p = i - 1;
        while (p >= 0 && (strcmp(tmp, data[p]) > 0))
        {
            data[p + 1] = data[p];
            p--;
        }
        data[p + 1] = tmp;
    }
}

//-------------------------------------------------------------------------------------------------
// 3. ULOHA (0.8 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Usporiada zretazeny zoznam 'list' od najvacsieho prvku po najmensi.
    Pouzite algoritmus insertion sort.

    PARAMETRE:
        [in, out] list - zretazeny zoznam, ktory funkcia usporiada

    VSTUPNE PODMIENKY:
        'list' obsahuje lubovolny pocet uzlov (moze byt prazdny)
        'list' nie je 'nullptr'

    PRIKLADY:
        vstup: 2->1->3, vystup: 3->2->1
        vstup: prazdny zoznam, vystup: prazdny zoznam
*/
void sortIt(List* list, Node* firstN, Node* previousN, Node* currentN, Node* nextN)
{
    Node* finder = firstN, * finderPrevious = firstN;
    while (true)
    {
        if (currentN->data > finder->data)
        {
            if (finder == previousN)
            {
                currentN->next = finder;
                finder->next = nextN;
                finderPrevious->next = currentN;
            }
           else
           {
                currentN->next = finder;
                previousN->next = nextN;
                if (finder == firstN)
                {
                    list->first = currentN;
                }
                else
                {
                    finderPrevious->next = currentN;
                }
           }
        break;
        }
        finderPrevious = finder;
        finder = finder->next;
    }
}

void insertionSort(List* list) {
    Node* tmpNode = list->first, * previous = list->first;
    if (tmpNode == nullptr || tmpNode->next == nullptr)
    {
        return;
    }
    tmpNode = tmpNode->next;
    while (tmpNode)
    {
        if (tmpNode->data > previous->data)
        {
            if (previous == list->first)
            {
                previous->next = tmpNode->next;
                tmpNode->next = previous;
                list->first = tmpNode;
            }
            else
            {
                sortIt(list, list->first, previous, tmpNode, tmpNode->next);
            }
        }
        previous = tmpNode;
        tmpNode = tmpNode->next;
    }
}


//-------------------------------------------------------------------------------------------------
// 4. ULOHA (0.8 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Vykona algoritmus merge (cast algoritmu merge sort), ktory ma linearnu vypoctovu zlozitost.
    Kombinuje dve susedne, usporiadane casti v poli 'input', do jednej usporiadanej casti v poli 'output'.
    Usporiadanie je od najvacsieho prvku po najmensi prvok!

    PARAMETRE:
        [out] output - vystupne pole, ktoreho cast output[low]...output[high-1] bude po vykonani funkcie usporiadana
        [in]  input  - vstupne pole, ktoreho casti input[low]...input[middle-1] a input[middle]...input[high-1]
                         musia byt pri volani funkcie usporiadane od najvacsieho prvku po najmensi
        [in]  low    - index 1. prvku lavej usporiadanej casti pola 'input'
        [in]  middle - index 1. prvku pravej usporiadanej casti pola 'input'
        [in]  high   - index za poslednym prvkom pravej usporiadanej casti pola 'input'

    VYSTUPNE PODMIENKY:
        Obsah 'input' sa nemeni.
        output[low] ... output[high-1] obsahuje usporiadane prvky z input[low] ... input[high-1], zvysne prvky v 'output' funkcia nemeni
        Prvky s indexami mensimi ako 'low' sa nemenia (ani v jednom poli)
        Prvky s indexami vacsimi alebo rovnymi ako 'high' sa nemenia (ani v jednom poli)

    PRIKLAD:
        low: 4
        middle: 8
        hight: 12
        input:                         {10, 10, 10, 10,  7,  5,  2,  0,  8,  4,  2,  1, 10, 10, 10, 10}
        output pred vykonanim funkcie: {20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20}
        output po vykonani funkcie:    {20, 20, 20, 20,  8,  7,  5,  4,  2,  2,  1,  0, 20, 20, 20, 20}
*/

void merge(int* output, const int* input, const size_t low, const size_t middle, const size_t high)
{
    size_t first = low;     
    size_t second = middle; 
    size_t out = low;       
    while (first < middle && second < high) {
        if (input[first] >= input[second]) { 
            output[out] = input[first];
            ++first;
        }
        else {
            output[out] = input[second];
            ++second;
        }
        ++out;
    }
    while (first < middle) {
        output[out] = input[first];
        ++first;
        ++out;
    }
    while (second < high) {
        output[out] = input[second];
        ++second;
        ++out;
    }
}

//-------------------------------------------------------------------------------------------------
// 5. ULOHA (0.8 bodu)
//-------------------------------------------------------------------------------------------------
/*
    Usporiada prvky v poli 'data' od najvacsieho prvku po najmensi.
    Pouzite algoritmus merge sort.

    PARAMETRE:
        [in, out] data - pole, ktore funkcia usporiada
        [in] length    - pocet prvkov pola

    VSTUPNE PODMIENKY:
        'data' ukazuje na platne pole

    PRIKLADY:
        {1,3,2} -> {3, 2, 1}
        {1} -> {1}
        {} -> {}

    POZNAMKA:
        Pri implementacii top-down bude vhodne vytvorit a zavolat rekurzivnu funkciu.
*/
void mergeSort(int *data, const size_t length) {
    int* tmpArray = new int[length]; 
    memcpy(tmpArray, data, length * sizeof(int)); 
    mergeSortRecursive(data, tmpArray, 0, length);
    delete[] tmpArray;
}

//-------------------------------------------------------------------------------------------------
// TESTOVANIE
//-------------------------------------------------------------------------------------------------

Node* createNode(const int value) {
    Node* tmp = new Node;               
    tmp->data = value;
    tmp->next = nullptr;
    return tmp;
}

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

int main() {

    /*int data[] = { 11, 12, 13, 5, 6,46,89,5656,2,5,2,6,7,9,5,65,78,23 };
    int len = 18;
    insertionSort(data,len);
    for (int i = 0; i < len; i++)               //uloha 1
    {
        cout << data[i] << " ";
    }
    cout << endl;*/

    /*char meno1[] = "Peter";
    char meno2[] = "Juraj";
    cout << strcmp(meno1, meno2);*/
    /*const char* mena[] = { "Juraj", "Peter", "Andrej", "Xavier", nullptr };       //uloha 2
    insertionSort(mena);
    for (int g = 0; g < 4; g++)
    {
        cout << mena[g] << " ";
    }*/

    List* list = new List;
    list->first = nullptr;
    appendNode(list, 1);
    appendNode(list, 2);
    appendNode(list, 3);
    appendNode(list, 4);
    appendNode(list, 5);
    /*appendNode(list, 0);
    appendNode(list, 87);
    appendNode(list, 7);
    appendNode(list, 7);
    appendNode(list, 77);
    appendNode(list, 17);
    appendNode(list, 556);
    appendNode(list, 0);*/
    
    insertionSort(list);
    Node* x = list->first;          //uloha 3
    while (x)
    {
        cout << x->data << " ";
        x = x->next;
    }

    /*size_t low = 4, middle = 8, high = 12;
    int input[] = { 10, 10, 10, 10,  7,  5,  2,  0,  8,  4,  2,  1, 10, 10, 10, 10 };
    int output[16] = { 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20 };
    merge(output, input, low, middle, high);
    cout << endl << "Ukazkovy vystup : {20, 20, 20, 20, 8, 7, 5, 4, 2, 2, 1, 0, 20, 20, 20, 20}" << endl << "Actual vystup :   {";
    for (int i = 0; i < sizeof(output) / sizeof(output[0]); i++)
    {
        cout << output[i];
        if (i != (sizeof(output) / sizeof(output[0]) - 1))      //uloha 4
        {
            cout << ", ";
        }
    }
    cout << "}" << endl;*/

    /*int data[] = { 12,13,5,6,88,1,0,4,99,11,27,-2,-8 };
    mergeSort(data, 13);
    for (int i = 0; i < sizeof(data) / sizeof(data[0]); i++)        //uloha 5
    {
        cout << data[i] << " ";
    }
    cout << endl;*/
    return 0;
}
