// Tema: Zretazeny zoznam
// Autor: Pavol Marak
// 29. 9. 2020

#include <iostream>

// Definicia potrebnych struktur

// Struktura reprezentujuca uzol
struct Node {
    int data;
    Node *next;
};

// Struktura reprezentujuca zoznam
struct List {
    Node *first; // smernik na prvy uzol zoznamu
};

// Funkcia na vytvorenie noveho uzla (dynamicky)
Node *createNode(const int value) {
    Node *tmp = new Node;
    tmp->data = value;
    tmp->next = nullptr;
    return tmp;
}

// Funkcia na pridanie uzla na zaciatok
void prependNode(List *list, const int value) {
    Node *newNode = createNode(value);
    newNode->next = list->first;
    list->first = newNode;
}

// Funkcia na pridanie uzla na koniec
void appendNode(List *list, const int value) {
    Node *newNode = createNode(value);
    if (!list->first) {
        list->first = newNode;
    } else {
        Node *tmp = list->first;
        while (tmp->next) {
            tmp = tmp->next;
        }
        tmp->next = newNode;
    }
}

// Funkcia na vypis zoznamu
void printList(const List *list) {
    Node *tmp = list->first;
    while (tmp) {
        std::cout << tmp->data << " ";
        tmp = tmp->next;
    }
    std::cout << std::endl;
}

// Funkcia na vymazanie prveho uzla
void removeFirstNode(List *list) {
    if (list->first) {
        Node *tmp = list->first->next;
        delete list->first;
        list->first = tmp;
    }
}

// Funkcia na najdenie predchodcu uzla s hodnotou 'value'
Node *findPredecessor(const List *list, const int value) {
    Node *tmp = list->first;
    Node *pred = nullptr;
    while (tmp) {
        if (tmp->data == value) {
            return pred;
        }
        pred = tmp;
        tmp = tmp->next;
    }
    return nullptr;
}

// Funkcia na najdenie uzla s hodnotou 'value'
Node *findNode(const List *list, const int value) {
    Node *tmp = list->first;
    while (tmp) {
        if (tmp->data == value) {
            return tmp;
        }
        tmp = tmp->next;
    }
    return nullptr;
}

// Funkcia na odstranenie uzla s hodnotou 'value'
// Poznamka: predpokladame, ze zoznam obsahuje len unikatne hodnoty
void removeNode(List *list, const int value) {
    Node *toDel = findNode(list, value);
    if (!toDel) {
        return;
    }
    Node *pred = findPredecessor(list, value);

    if (pred) {
        pred->next = pred->next->next;
    } else {
        toDel = list->first;
        list->first = list->first->next;
    }
    delete toDel;
}

// Funkcia, ktora vymaze (dealokuje) vsetky uzly zoznamu (zoznam bude existovat v pamati, ale bude prazdny)
void clearList(List *list) {
    Node *tmp = nullptr;
    while (list->first) {
        tmp = list->first->next;
        delete list->first;
        list->first = tmp;
    }
}

// Funkcia na vymazanie (dealokaciu) celeho zoznamu z pamate
void destroyList(List **list) {
    clearList(*list); // vymazanie vsetkych uzlov zoznamu, avsak pamat pre strukturu so zoznamom zostavana alokovana
    delete (*list); // dealokacia pamate pre strukturu so zoznamom
    (*list) = nullptr;
}

// Testovanie
int main() {

    // 1. uloha - vytvorit prazdny zoznam
    List *list = new List;
    list->first = nullptr;

    // 2. uloha - prepend
    for (int i = 0; i < 10; i++) {
        prependNode(list, 10 - i);
    }

    // 3. uloha - append
    for (int i = 11; i < 20; i++) {
        appendNode(list, i);
    }

    // 4. uloha - vypis
    printList(list);

    // 5. uloha - vymaz prvy uzol
    removeFirstNode(list);

    // 6. uloha - vymazanie konkretneho uzla
    removeNode(list, 5);

    // 7. uloha - vymazanie celeho zoznamu
    destroyList(&list);

    return 0;
}
