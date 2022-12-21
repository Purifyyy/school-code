#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include <unistd.h>
#define BUFSIZE 8
#define MAX 81


void u_vstup_prep(char riadok[],int dlzka)
{
    for (int i = 0; i < dlzka; i++)
    {
        riadok[i] = toupper(riadok[i]);
    }
}

void l_vstup_prep(char riadok[], int dlzka)
{
    for (int i = 0; i < dlzka; i++)
    {
        riadok[i] = tolower(riadok[i]);
    }
}

void a_vstup_prep(char riadok[], int dlzka)
{
    char CPYriadok[165];
    int pom = 0;
    for (int i = 0; i < dlzka; i++)
    {
        if (isalpha(riadok[i]) == 0)
        {
            if (i == 0 || CPYriadok[pom-1] != ' ')
            {
                CPYriadok[pom] = ' ';
                pom++;
            }
        }
        else
        {
            CPYriadok[pom] = riadok[i];
            pom++;
        }
    }
    CPYriadok[pom] = '\0';
    strcpy(riadok, CPYriadok);
}

void c_vstup_prep(char riadok[], int dlzka)
{
    char CPYriadok[MAX];
    int pom = 0;
    for (int i = 0; i < dlzka; i++)
    {
        if (isalpha(riadok[i]) != 0)
        {
            CPYriadok[pom] = riadok[i];
            pom++;
        }
    }
    CPYriadok[pom] = '\0';
    strcpy(riadok, CPYriadok);
}

void r_vstup_prep(char riadok[], char argument[])
{
    char* p;
    int dlzka_argument = strlen(argument);
    while((p = strstr(riadok, argument)))
    {
        for (int i = 0; i < dlzka_argument; i++)
        {
            *(p + i) = '*';
        }
    }
}

int cezar_posun(int c, int posun)
{
    char znak = c;
    if (posun > 0)
    {
        if (znak >= 'a' && znak <= 'z')
        {
            if (znak + posun > 'z')
            {
                int p = 'z' - znak;
                znak = 96 + (posun - p);
            }
            else
            {
                znak = znak + posun;
            }
        }
        else if (znak >= 'A' && znak <= 'Z')
        {
            if (znak + posun > 'Z')
            {
                int p = 'Z' - znak;
                znak = 64 + (posun - p);
            }
            else
            {
                znak = znak + posun;
            }
        }
    }
    else
    {
        if (znak >= 'a' && znak <= 'z')
        {
            if (znak + posun < 'a')
            {
                int p = znak - 'a';
                znak = 123 + (posun + p);
            }
            else
            {
                znak = znak + posun;
            }
        }
        else if (znak >= 'A' && znak <= 'Z')
        {
            if (znak + posun < 'A')
            {
                int p = znak - 'A';
                znak = 91 + (posun + p);
            }
            else
            {
                znak = znak + posun;
            }
        }
    }

    return znak;
}

void e_vstup_prep(char riadok[], char argument[])
{
    int k = atoi(argument);
    int dlzka_riadok = strlen(riadok);
    for (int i = 0; i < dlzka_riadok; i++)
    {
        if (isalpha(riadok[i]))
        {
            riadok[i] = cezar_posun(riadok[i], k);
        }
    }
}

void w_vystup_prep(char riadok[])
{
    int dlzka_riadok = strlen(riadok);
    char CPY_riadok[MAX+1];
    for (int i = 0; i < dlzka_riadok; i++)
    {
        if (!(isalnum(riadok[i])))
        {
            riadok[i] = '-';
        }
    }
    char* token = strtok(riadok, "-");
    int p = 0;
    while (token != NULL)
    {
        for (unsigned int i = 0; i < strlen(token); i++)
        {
            CPY_riadok[p] = token[i];
            p++;
        }
        CPY_riadok[p] = '\n';
        if ((token = strtok(NULL, "-")))
        {
            p++;
        }
    }
    CPY_riadok[p] = '\0';
    strcpy(riadok, CPY_riadok);
}

void g_vystup_prep(char riadok[], char argument[])
{
    int parameter = atoi(argument), dlzka_riadok = strlen(riadok), poz = 0, pocitadlo = 0;
    char CPYriadok[MAX];
    for (int i = 0; i < dlzka_riadok; i++)
    {
        CPYriadok[poz] = riadok[i];
        poz++;
        pocitadlo++;
        if (pocitadlo == parameter)
        {
            if (dlzka_riadok - i == 1)
            {
                CPYriadok[poz] = '\0';
            }
            else
            {
                CPYriadok[poz] = ' ';
                poz++;
                pocitadlo = 0;
            }
        }
        CPYriadok[poz] = '\0';
    }
    strcpy(riadok, CPYriadok);
}

void run_text_processor(int argc, char *argv[])
{
	int option,pocVstup = 0,pocVystup = 0, CGflag = 0;
    char rARG[BUFSIZE+1];
    char eARG[BUFSIZE+1];
    char gARG[BUFSIZE+1];
    char VstupPrep[BUFSIZE];
    char VystupPrep[BUFSIZE/2];
	while ((option = getopt(argc, argv,"ulacr:e:wg:")) != -1)
	{
		switch (option)
		{
			case 'u' :
                VstupPrep[pocVstup] = 'u';
                pocVstup++;
				break;
			case 'l' :
                VstupPrep[pocVstup] = 'l';
                pocVstup++;
				break;
			case 'a' :
                VstupPrep[pocVstup] = 'a';
                pocVstup++;
				break;
			case 'c' :
                VstupPrep[pocVstup] = 'c';
                pocVstup++;
				break;
            case 'r' :
                snprintf( rARG, BUFSIZE, "%s", optarg );
                VstupPrep[pocVstup] = 'r';
                pocVstup++;
				break;
            case 'e' :
                snprintf( eARG, BUFSIZE, "%s", optarg );
                VstupPrep[pocVstup] = 'e';
                pocVstup++;
                if (atoi(eARG) == 0)
                {
                    exit(0);
                }
                break;
            case 'w' :
                VystupPrep[pocVystup] = 'w';
                pocVystup++;
                break;
            case 'g' :
                snprintf( gARG, BUFSIZE, "%s", optarg );
                VystupPrep[pocVystup] = 'g';
                pocVystup++;
                if (atoi(gARG) < 1)
                {
                    exit(0);
                }
                break;
			default:
				exit(0);

		}
	}
    char riadok[240];
	while (fgets(riadok, MAX, stdin))
    {
        if (riadok[0] == '\n')
        {
            break;
        }
        int dlzka = strlen(riadok);
        if (riadok[dlzka - 1] == '\n')
        {
            riadok[dlzka - 1] = '\0';
            dlzka--;
        }
        for (int q = 0; q < pocVstup; q++)
        {
            if (VstupPrep[q] == 'u')
            {
                u_vstup_prep(riadok,dlzka);
            }
            else if (VstupPrep[q] == 'l')
            {
                l_vstup_prep(riadok, dlzka);
            }
            else if (VstupPrep[q] == 'a')
            {
                a_vstup_prep(riadok, dlzka);
            }
            else if (VstupPrep[q] == 'c')
            {
                c_vstup_prep(riadok, dlzka);
                CGflag++;
            }
            else if (VstupPrep[q] == 'r')
            {
                r_vstup_prep(riadok, rARG);
            }
            else if (VstupPrep[q] == 'e')
            {
                e_vstup_prep(riadok, eARG);
            }
        }
        for (int q = 0; q < pocVystup; q++)
        {
            if (VystupPrep[q] == 'w')
            {
                w_vystup_prep(riadok);
            }
            else if (VystupPrep[q] == 'g')
            {
                if (CGflag)
                {
                    g_vystup_prep(riadok,gARG);
                }
            }
        }
        printf("%s\n", riadok);
    }

}

int main(int argc, char *argv[])
{
    run_text_processor(argc, argv);
    return 0;
}
