#define _CRT_SECURE_NO_WARNINGS
#define STATE_MAX_LEN 80 
#define RULES_MAX_LEN 10 

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>

int generujbit(int p)
{
    int x = rand() % (100 + 1 - 1) + 1;
    if (p == 0)
    {
        return 0;
    }
    else if (x <= p)
    {
        return 1;
    }
    else if (x > p)
    {
        return 0;
    }
}

void pociatok(int stav[], int max)
{
   for (int i = 0; i < max; i++)
   {
       stav[i] = generujbit(50);
   }    
}

int get_pravidla(int pravidla[],int x)
{
    int l;
    printf("Parny pocet pravidiel: ");
    scanf("%d", &l);
    if (l % 2 == 1 || l > 10 || l < 2)
    {
        printf("\nZadal si neplatny pocet pravidiel (neparny pocet, mimo rozsah), skus to znovu.\n");
        get_pravidla(pravidla, x);
    }
    else
    {
        for (int i = 0; i < l; i++)
        {
            int o, p = 0;
            while (p == 0)
            {
                printf("Zadaj pravdepodobnost pre pravidlo %d: ", i);
                scanf("%d", &o);
                if (o >= 0 && o <= 100)
                {
                    p = 1;
                    pravidla[i] = o;
                }
                else
                {
                    printf("\nPravdepodobnost ktoru si zadal je mimo interval <0,100>, skus to znovu.\n\n");
                }
            }
        }
    }
    printf("\nDlzka stavu: %d\n", STATE_MAX_LEN);
    printf("\nPRAVIDLA:\n");
    for (int k = 0; k < l; k++)
    {
        printf("Pravidlo [%d]: ... %d%%\n", k, pravidla[k]);
    }
    return l;
}

int sucetVriadku(int okno[], int max, int pravidla[])
{
    int sum = 0;
    for (int i = 0; i < max; i++)
    {
        if (okno[i] == 1)
        {
            sum++;
        }
    }
    return generujbit(pravidla[sum]);
}

void generujNovyStav(int stav[], int stavNovy[], int m, int pravidla[], int n)
{
    for (int i = 0; i < m; i++)
    {
        int okno[RULES_MAX_LEN];
        if (i < (n - 2) / 2)
        {
            for (int k = 0; k < (n / 2) + i; k++)
            {
                okno[k] = stav[k];
            }
            stavNovy[i] = sucetVriadku(okno, (n / 2) + i, pravidla);
        }
        else if (i > m - (n / 2))
        {
            int p = 0;
            for (int l = i - ((n - 2) / 2); l < m; l++)
            {
                okno[p] = stav[l];
                p++;
            }
            stavNovy[i] = sucetVriadku(okno, p, pravidla);
        }
        else
        {
            int p = i - ((n - 2) / 2);
            for (int j = 0; j < n - 1; j++)
            {
                okno[j] = stav[p];
                p++;
            }
            stavNovy[i] = sucetVriadku(okno, n-1, pravidla);
        }
    }
}

void vypisStavu(int stav[], int max)
{
    for (int i = 0; i < max; i++)
    {
        if (stav[i] == 1)
        {
            printf("*");
        }
        else
        {
            printf(" ");
        }
    }
}

void delay(float sekundy)
{
    int mili_sek = 1000 * sekundy;
    clock_t zaciatok_casu = clock();
    while (clock() < zaciatok_casu + mili_sek);
}

void automat(int num)
{
    int stav[STATE_MAX_LEN];
    int stavNovy[STATE_MAX_LEN];
    int pravidla[RULES_MAX_LEN];
    pociatok(stav, STATE_MAX_LEN);
    printf("\n");
    int n = get_pravidla(pravidla, RULES_MAX_LEN);
    printf("\nStlac lubovolnu klavesu pre pokracovanie...\n");
    getch();
    system("cls");
    printf("Vyvoj automatu:\n");
    printf("\nt=0 ");
    vypisStavu(stav, STATE_MAX_LEN);
    printf("\n");
    delay(0.3);
    for (int i = 0; i < num; i++)
    {
        generujNovyStav(stav, stavNovy, STATE_MAX_LEN, pravidla, n);
        printf("t%d= ", i+1);
        vypisStavu(stavNovy, STATE_MAX_LEN);
        printf("\n");
        delay(0.3);
        for (int x = 0; x < STATE_MAX_LEN; x++)
        {
            stav[x] = stavNovy[x];
        }
    }
}
    
int main()
{
    srand(time(0));
    int q;
    printf("\nStochasticky celularny automat\n\n------------------------------------------\n\n");
    printf("Pocet iteracii: ");
    scanf("%d", &q);
    automat(q);
    return 0;
}