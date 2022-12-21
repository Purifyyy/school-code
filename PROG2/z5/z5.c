#include <stdio.h>
#include <string.h>
#include <math.h>
#include <stdlib.h>
#define _USE_MATH_DEFINES 

void spracuj_vstup(int argc, char *argv[], int* v_arg, int* b_arg, int* riadky, int* stlpce, int* iteracie, char vstup_s[], char vystup_s[])
{
	FILE* subor;
	//printf("%d\n", argc);
	if (argc < 6 || argc > 8)
	{
		//printf("prilis malo alebo vela parametrov");
		exit(0);
	}
	if (argc == 7)
	{
		if ((strcmp(argv[1], "-v")) == 0)
		{
			*v_arg = 1;
		}
		else if ((strcmp(argv[1], "-b")) == 0)
		{
			*b_arg = 1;
		}
	}
	else if (argc == 8)
	{
		if ((strcmp(argv[1], "-v")) == 0 || (strcmp(argv[2], "-v")) == 0)
		{
			*v_arg = 1;
		}
		
		if ((strcmp(argv[1], "-b")) == 0 || (strcmp(argv[2], "-b")) == 0)
		{
			*b_arg = 1;
		}

		if (v_arg == 0 || b_arg == 0)
		{
			//printf("neplatny parameter");
			exit(0);
		}
	}
	int pocet = argc -1 ;
	if (*v_arg == 1)
	{
		pocet--;
	}
	if (*b_arg == 1)
	{
		pocet--;
	}
	int pocitadlo = 0;
	//printf("%d\n", argc);
	for (int i = argc - pocet; i < argc; i++)
	{
		if (pocitadlo == 0)
		{
			//printf("%d\n%s\n", i,argv[i]);
			if ((atoi(argv[i])) >= 1)
			{
				*riadky = atoi(argv[i]);
			}
			else
			{
				//printf("neplatny pocet riadkov");
				exit(0);
			}
		}
		else if (pocitadlo == 1)
		{
			if (*b_arg == 1)
			{
				int medzi = 0, podiel = 0;
				if ((atoi(argv[i])) >= 1)
				{
					medzi = atoi(argv[i]);
					if (medzi % 8 != 0)
					{
						podiel = medzi / 8;
						if (podiel == 0)
						{
							medzi = 8;
						}
						else
						{
							medzi = (podiel + 1) * 8;
						}
					}
					*stlpce = medzi;
				}
				else
				{
					//printf("neplatny pocet stlpcov");
					exit(0);
				}
			}
			else
			{
				if ((atoi(argv[i])) >= 1)
				{
					*stlpce = atoi(argv[i]);
				}
				else
				{
					//printf("neplatny pocet stlpcov");
					exit(0);
				}
			}
		}
		else if (pocitadlo == 2)
		{
			int dlzka = strlen(argv[i]);
			//printf("\n%d\n%s", dlzka, argv[i]);
			char pocet_iter[25];
			strcpy(pocet_iter, argv[i]);
			if (dlzka == 1 && (strcmp(pocet_iter,"0") == 0))
			{
				*iteracie = 0;
			}
			else
			{
				if (atoi(argv[i]) != 0)
				{
					*iteracie = atoi(argv[i]);
				}
				else
				{
					//printf("neplatny pocet iteracii");
					exit(0);
				}
			}
		}
		else if (pocitadlo == 3)
		{
			char subor_pole[25];
			strcpy(vstup_s, argv[i]);
			//printf("%s\n", vstup_s);
			int dlzka = strlen(vstup_s), x = 0;
			//printf("%d", dlzka);
			for (int k = dlzka - 4; k < dlzka; k++)
			{
				subor_pole[x] = vstup_s[k];
				x++;
			}
			subor_pole[x] = '\0';
			//printf("%s\n", subor_pole);
			if (*b_arg == 0)
			{
				if ((strcmp(subor_pole, ".txt")) != 0)
				{
					//printf("\nneplatny vstupny subor");
					exit(0);
				}
				if ((subor = fopen(vstup_s, "r")) == NULL)
				{
					//printf("neplatny vstup_subor");
					exit(0);
				}
				else
				{
					fclose(subor);
				}
			}
			else if (*b_arg == 1)
			{
				if ((strcmp(subor_pole, ".bin")) != 0)
				{
					//printf("\nneplatny vstupny subor .bin");
					exit(0);
				}
				if ((subor = fopen(vstup_s, "r")) == NULL)
				{
					//printf("neplatny vstup_subor neotvaram");
					exit(0);
				}
				else
				{
					fclose(subor);
				}
			}
			/*strcpy(vstup_s, argv[i]);
			if ((subor = fopen(vstup_s, "r")) == NULL)
			{
				printf("neplatny vstup_subor");
				exit(0);
			}
			else
			{
				fclose(subor);
			}*/
		}
		else if (pocitadlo == 4)
		{
			char subor_pole[25];
			strcpy(vystup_s, argv[i]);
			int dlzka = strlen(vystup_s), x = 0;
			for (int k = dlzka-4; k < dlzka; k++)
			{
				subor_pole[x] = vystup_s[k];
				x++;
			}
			subor_pole[x] = '\0';
			if (*b_arg == 0)
			{
				if ((strcmp(subor_pole, ".txt")) != 0)
				{
					//printf("\nneplatny nazov textoveho suboru pre vystup");
					exit(0);
				}
			}
			else if (*b_arg == 1)
			{
				if ((strcmp(subor_pole, ".bin")) != 0)
				{
					//printf("\nneplatny nazov textoveho suboru pre vystup");
					exit(0);
				}
			}
		}
		pocitadlo++;
	}
}

int** malloc_torus(int riadky, int stlpce)
{
	int **pole;
	pole = malloc(sizeof(int*) * stlpce);
	for (int i = 0; i < riadky; i++)
	{
		pole[i] = malloc(sizeof(int) * riadky);
	}
	for (int i = 0; i < riadky; i++)
	{
		for (int j = 0; j < stlpce; j++)
		{
			pole[i][j] = 0;
		}
	}
	return pole;
}

void nacitaj_subor(int** torus, char vstup_s[], int riadky, int stlpce, int b_arg)
{
	FILE* subor;
	if (b_arg == 0)
	{
		subor = fopen(vstup_s, "r");
		char znak;
		int i = 0, j = 0, pocet_znakov = 0, x;
		while ((znak = fgetc(subor)) != EOF)
		{
			if (znak == '0' || znak == '1')
			{
				x = znak - '0';
				torus[i][j] = x;
				j++;
				if (j == stlpce)
				{
					j = 0;
					i++;
				}
				pocet_znakov++;
				if (pocet_znakov == (riadky * stlpce))
				{
					break;
				}
			}
		}
		fclose(subor);
	}
	else if (b_arg == 1)
	{
		unsigned char znak;

		int* vypln_torusu;

		int riadky = 6, pocitadlo = 0, pozB = 0, pozV = 0;
		vypln_torusu = (int*)calloc(riadky * 8, sizeof(int));
		int binar[8];

		subor = fopen(vstup_s, "rb");

		while ((znak = fgetc(subor)) != 255)
		{
			if (pocitadlo == riadky)
			{
				break;
			}
			for (int bit = 8; bit; bit--)
			{
				binar[pozB] = (int)(znak & (1 << (bit - 1)) ? '1' : '0') - 48;
				pozB++;
			}
			pozB = 0;
			for (int k = 0; k < 8; k++)
			{
				vypln_torusu[pozV] = binar[7 - k];
				pozV++;
			}
			pocitadlo++;
		}
		/*for (int x = 0; x < riadky * 8; x++)
		{
			printf("%d", vypln_torusu[x]);
			if (((x + 1) % 8) == 0 && x != 0)
			{
				printf("\n");
			}
		}*/
		pozV = 0;
		for (int i = 0; i < riadky; i++)
		{
			for (int j = 0; j < stlpce; j++)
			{
				torus[i][j] = vypln_torusu[pozV];
				pozV++;
			}
		}
		free(vypln_torusu);
		fclose(subor);
	}
}

int vypocet_susedov(int** torus, int** toruscpy, int i, int j, int riadky, int stlpce)
{
	int pocet = 0;
	for (int k = -1; k < 2; k++)
	{
		for (int l = -1; l < 2; l++)
		{
			if (torus[(i + k + riadky) % riadky][(j + l + stlpce) % stlpce] == 1)
			{
				pocet++;
			}
		}
	}
	pocet = pocet - torus[i][j];
	return pocet;
}

void novy_stav(int** torus, int** toruscpy, int riadky, int stlpce, int i, int j, int pocet)
{
	if (torus[i][j] == 1 && pocet < 2)
	{
		toruscpy[i][j] = 0;
	}
	else if (torus[i][j] == 1 && (pocet == 2 || pocet == 3))
	{
		toruscpy[i][j] = 1;
	}
	else if (torus[i][j] == 1 && pocet > 3)
	{
		toruscpy[i][j] = 0;
	}
	else if (torus[i][j] == 0 && pocet == 3)
	{
		toruscpy[i][j] = 1;
	}
	else
	{
		toruscpy[i][j] = torus[i][j];
	}
}

void vyvoj_automatu(int** torus, int** toruscpy, char vystup_s[], int riadky, int stlpce, int iteracie, int v_arg)
{
	int zive_bunky = 0;
	if (v_arg == 1)
	{
		for (int i = 0; i < riadky; i++)
		{
			for (int j = 0; j < stlpce; j++)
			{
				if (torus[i][j] == 1)
				{
					printf("*");
				}
				else
				{
					printf(" ");
				}
				if (j == stlpce - 1)
				{
					printf("\n");
				}
			}
		}
		printf("\n");
	}
	for (int x = 0; x < iteracie; x++)
	{
		for (int i = 0; i < riadky; i++)
		{
			for (int j = 0; j < stlpce; j++)
			{
				zive_bunky = vypocet_susedov(torus, toruscpy, i, j, riadky, stlpce);
				novy_stav(torus, toruscpy, riadky, stlpce, i, j, zive_bunky);
			}
		}
		for (int i = 0; i < riadky; i++)
		{
			for (int j = 0; j < stlpce; j++)
			{
				torus[i][j] = toruscpy[i][j];
			}
		}
		if (v_arg == 1)
		{
			for (int i = 0; i < riadky; i++)
			{
				for (int j = 0; j < stlpce; j++)
				{
					if (torus[i][j] == 1)
					{
						printf("*");
					}
					else
					{
						printf(" ");
					}
					if (j == stlpce - 1)
					{
						printf("\n");
					}
				}
			}
			printf("\n");
		}
	}
}

void subor_output(int** torus, char vystup_s[], int riadky, int stlpce, int b_arg)
{
	FILE* subor;
	if (b_arg == 0)
	{
		subor = fopen(vystup_s, "w");
		for (int i = 0; i < riadky; i++)
		{
			for (int j = 0; j < stlpce; j++)
			{
				fprintf(subor, "%d", torus[i][j]);
				if (j == stlpce - 1)
				{
					fprintf(subor, "\n");
				}
			}
		}
		fclose(subor);
	}
	else if (b_arg == 1)
	{
		subor = fopen(vystup_s, "wb");
		int* riadok;
		int pocitadlo = 0, poz = 0;
		riadok = (int*)malloc(8 * sizeof(int));
		for (int i = 0; i < riadky; i++)
		{
			for (int j = 0; j < stlpce; j++)
			{
				riadok[poz] = torus[i][j];
				pocitadlo++;
				poz++;
				if (pocitadlo == 8)
				{
					int* start = &riadok[0];
					int total = 0;
					while (*start == 1 || *start == 0)
					{
						total *= 2;
						if (*start++ == 1) total += 1;
					}
					int *t = 0;
					*t = total;
					fwrite(&t, sizeof(t), 1, subor);
					pocitadlo = 0;
					poz = 0;
				}
			}
		}
		free(riadok);
		/*char* start = &riadok[0];
		int pocet = 0;
		while (*start)
		{
			pocet *= 2;
			if (*start++ == '1') pocet += 1;
		}
		int* t = pocet;
		fwrite(&t, sizeof(t), 1, subor);*/
	}
}

int main(int argc, char* argv[])
{
	//printf("zacinam");
	int v_arg = 1, b_arg = 0, riadky = 6, stlpce = 8, iteracie = 2, ** torus, ** torus_cpy;
	char vstup_s[25] = "toad0.txt", vystup_s[25] = "vysledok.txt";
	//spracuj_vstup(argc, argv, &v_arg, &b_arg, &riadky, &stlpce, &iteracie, vstup_s, vystup_s);
	//printf("v_arg = %d\nb_arg = %d\nriadky = %d\nstlpce = %d\niteracie = %d\nvstupny_subor = %s\nvystup_subor = %s\n", v_arg, b_arg, riadky, stlpce, iteracie, vstup_s, vystup_s);
	//printf("som pred mallocom\n");
	torus = malloc_torus(riadky, stlpce);
	torus_cpy = malloc_torus(riadky, stlpce);
	//printf("som pred nacitanim suboru\n");
	nacitaj_subor(torus, vstup_s, riadky, stlpce, b_arg);
	vyvoj_automatu(torus, torus_cpy, vystup_s, riadky, stlpce, iteracie, v_arg);
	subor_output(torus, vystup_s, riadky, stlpce, b_arg);

	
	
	//printf("som pred vypisom\n");
	/*for (int i = 0; i < riadky; i++) 
	{
		for (int j = 0; j < stlpce; j++) 
		{
			printf("%d ", torus[i][j]);
			if (j == stlpce-1) 
			{
				printf("\n");
			}
		}
	}*/
	//printf("koncim\n");
	return 0;
}