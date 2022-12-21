#define _CRT_SECURE_NO_WARNINGS
#define _USE_MATH_DEFINES
#include <stdio.h>
#include <math.h>
#include <stdlib.h>

int j = 0, d, high, low, h, otoc = 0, o;

int nahoda(int minimum_n, int maximum_n)
{
	return ("%d", (rand() % (maximum_n + 1 - minimum_n) + minimum_n));
}

void obtiaznost()
{
	printf("                                                  ");
	printf("Vyber obtiaznosti\n");
	printf("                                          ");
	printf("Lahka = 0   Stredna = 1   Tazka = 2\n");
	printf("                                                        ");
	printf("Vyber: ");
	scanf("%d", &o);
	if (o != 0 && o != 1 && o != 2)
	{
		printf("\n                                            ");
		printf("Zadal si neplatnu obtiaznost!\n");
		exit(0);
	}
}

int tahH()
{
	int uhol;
	double g = 9.81, sila, d;
	printf("\n\nSI NA TAHU!\n\n");
	printf("Zadaj silu vystrelu (km/h): ");
	scanf("%lf", &sila);
	printf("Zadaj uhol vystrelu (stupne): ");
	scanf("%d", &uhol);
	sila = (sila * 1000) / 3600;
	d = ((sila*sila) / g)*sin(uhol*M_PI / 180);
	return d / 100;
	
}

void highP(int t1, int d)
{
	high = d-1;
}

void lowP(int t1, int d)
{
	low = d+1;
}

int prvaP(int t1, int t2)
{
	int q;
	if (o == 0)
	{
		q = 7;
	}
	else if (o == 1)
	{
		q = 4;
	}
	else if (o == 2)
	{
		q = 3;
	}
	if (otoc == 1)
	{
		high = (t1 + q);
		if ((t1 - t2) <= q)
		{
			low = (t2 + 1);
		}
		else
		{
			low = (t1 - q);
		}
	}
	else 
	{	
		if ((t1 + q) >= t2)
		{
			high = (t2 - 1);
		}
		else
		{
			high = (t1 + q);
		}
		low = (t1 - q);
	}
	return (rand() % (high + 1 - low) + low);
}

int tahP(int t1, int high, int low)
{
	d = rand() % (high + 1 - low) + low;
	return d;
}

void vykresli(int t1, int t2, int strela)
{
	for (int i = 0; i < 26; i++)
	{
		printf("\n");
	}
	for (int i = 0; i < 120; i++)
	{
		if (i == t1)
		{
			if (t1 == strela)
			{
				if (h == 1)
				{
					printf("+");
				}
				else 
				{
					printf("H");
				}
			}
			else
			{
				printf("H");
			}
		}
		else if (i == t2)
		{
			if (t2 == strela)
			{
				printf("+");
			}
			else
			{
				printf("P");
			}
		}
		else if (i == strela)
		{
			printf("+");
		}
		else
		{
			printf(" ");
		}
	}
	for (int i = 0; i < 120; i++)
	{
		printf("=");
	}
}

void hraj(int t1, int t2)
{
	int strela = -1, koniec = 0;
	vykresli(t1,t2,strela);
	while (1 == 1)
	{
		strela = tahH();
		h = 0;
		if (otoc == 1)
		{
			vykresli(t1, t2, (t1 - strela));
		}
		else
		{
			vykresli(t1, t2, (strela + t1));
		}
		h = 1;
		if (t2 == (strela + t1) && otoc == 0)
		{
			printf("\n\n");
			for (int i = 0; i < 55; i++)
			{
				printf(" ");
			}
			printf("GAME OVER!\n\n");
			for (int i = 0; i < 54; i++)
			{
				printf(" ");
			}
			printf("Vitaz: Hrac\n");
			break;
		}
		else if (t2 == (t1 - strela) && otoc == 1)
		{
			for (int i = 0; i < (t2 - 3); i++)
			{
				printf(" ");
			}
			printf("[DEAD]");
			printf("\n\n");
			for (int i = 0; i < 50; i++)
			{
				printf(" ");
			}
			printf("GAME OVER!\n\n");
			for (int i = 0; i < 50; i++)
			{
				printf(" ");
			}
			printf("Vitaz: Hrac\n");
			break;
		}
		if (((t1 - strela) > 120 || (t1 - strela) < 0) && otoc == 1)
		{
			printf("Vystrelil si mimo hraciu plochu\n");
		}
		if (((t1 + strela) > 120 || (t1 + strela) < 0) && otoc == 0)
		{
			printf("Vystrelil si mimo hraciu plochu\n");
		}
		printf("Netrafil si, je na rade pocitac, stlac klavesu ENTER pre pokracovanie!\n");
		getchar();
		getchar();
		if (j == 0)
		{
			d = prvaP(t1, t2);
			strela = d;
			vykresli(t1, t2, strela);
		}
		else if (j != 0)
		{
			if (d > t1)
			{
				highP(t1, d);
			}
			else if (d < t1)
			{
				lowP(t1, d);
			}
			d = tahP(t1, high, low);
			strela = d;
			vykresli(t1, t2, strela);
		}
		if (t1 == strela)
		{
			for (int i = 0; i < (t1 - 3); i++)
			{
				printf(" ");
			}
			printf("[DEAD]");
			printf("\n\n");
			for (int i = 0; i < 50; i++)
			{
				printf(" ");
			}
			printf("  GAME OVER!\n\n");
			for (int i = 0; i < 50; i++)
			{
				printf(" ");
			}
			printf("Vitaz: Pocitac\n");
			break;
		}
		j++;
		if ((strela < 0) && otoc == 0)
		{
			printf("Pocitac vystrelil si mimo hraciu plochu\n");
		}
		if ((t2 + (strela-t2)) > 119 && otoc == 1)
		{
			printf("Pocitac vystrelil si mimo hraciu plochu\n");
		}
		printf("Pocitac netrafil, si na rade, stlac klavesu ENTER pre pokracovanie!\n");
		getchar();
	}
}

int main()
{
	int t1, t2, minimum_n = 0, maximum_n = 120;
	srand(time(0));
	//t1 = nahoda(minimum_n, maximum_n);
	t1 = 119;
	t2 = nahoda(minimum_n, maximum_n);
	obtiaznost();
	if (t1 > t2)
	{
		otoc = 1;
	}
	hraj(t1,t2);
	return 0;
}