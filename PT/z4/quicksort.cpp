// Autor: Pavol Marak
// Tema: Quick Sort
// 19. 10. 2020

#include <iostream>
using namespace std;

// Funkcia na vymenu hodnot na danych adresach.
void swap(int *a1, int *a2) {
    int tmp = *a1;
    *a1 = *a2;
    *a2 = tmp;
}


// Funkcia na ziskanie indexu pivota v poli 'pole' v rozsahu od 'low' po 'high-1'.
// Pivot sa vyberie ako median laveho, stredneho a praveho prvku.
// Poznamka: 'high' je index za poslednym prvkom casti pola, s ktorou funkcia pracuje
size_t getPivot(const int *pole, const size_t low, const size_t high) {
    size_t middle_index = (low + high) / 2;
    int left = pole[low];
    int middle = pole[middle_index];
    int right = pole[high - 1];

    // left je median
    if (left <= middle && left >= right || left >= middle && left <= right) {
        return low;
    }
    // right je median
    if (right <= middle && right >= left || right >= middle && right <= left) {
        return high - 1;
    }
    // middle je median
    return middle_index;

}

// Funkcia na vykonanie operacie partition (styl Lomuto).
// Po vykonani operacie bude poradie prvkov nasledovne:
//   * mensie/rovnake prvky ako pivot
//   * pivot
//   * vacsie prvky ako pivot
// Funkcia vrati index pivota po vykonani partition.
size_t partition(int *pole, const size_t pivot_index, const size_t low, const size_t high) {
    // odlozim pivot na pravy okraj pola
    swap(&pole[pivot_index], &pole[high - 1]);
    size_t insert_index = low;
    size_t compare_index = low;
    while (compare_index < high) {
        if (pole[compare_index] <= pole[high - 1]) {
            swap(&pole[insert_index], &pole[compare_index]);
            insert_index++;
        }
        compare_index++;
    }
    return insert_index - 1;
}

// Quick sort algoritmus (rekurzivna funkcia).
void quickSort(int *pole, const size_t low, const size_t high) {
    // ak je pole menej ako 2-prvkove: return
    if (low + 1 >= high) {
        return;
    }
    // 1. vyber pivot
    size_t pivot_index = getPivot(pole, low, high);
    // 2. urob partition
    pivot_index = partition(pole, pivot_index, low, high);
    // 3. rekurzia do casti pola vlavo od pivota
    quickSort(pole, low, pivot_index);
    // 4. rekurzia do casti pola vpravo od pivota
    quickSort(pole, pivot_index + 1, high);
}

// Quick sort algoritmus (hlavna funkcia).
// Poznamka: zotriedi pole vzostupne.
void quickSort(int *pole, const size_t length) {
    quickSort(pole, 0, length);
}

// testovanie
int main() {
    int pole[] = {89, 15, 11, 105, 11, -3};
    int n = sizeof(pole) / sizeof(int);

    // quick sort - ascending
    quickSort(pole, n);

    return 0;
}
