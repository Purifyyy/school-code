#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <math.h>
#include "gps.z4.h"
#define EARTH_R 6371000


int napln_strukturu(struct udaje* std, struct udaje* ptr, int n, char i_argument[])
{
    FILE* subor = fopen(i_argument, "r");
    while (1)
    {

        fscanf(subor, "%s", ptr->time);
        fscanf(subor, "%s", ptr->imei);
        fscanf(subor, "%s", ptr->gps_lat);
        fscanf(subor, "%s", ptr->gps_lon);
        n++;
        if (getc(subor) == EOF)
        {
            break;
        }
        ptr++;
    }
    fclose(subor);
    return n;
}

int comparator(const void* p, const void* q)
{
    return strcmp(((struct udaje*)q)->time, ((struct udaje*)p)->time);
}

void default_prep(struct udaje* std, int n, struct udaje *ptr, int o_f, char o_argument[])
{
    FILE *subor;
    if (o_f)
    {
    	subor = fopen(o_argument, "w");	
	}
	qsort(std, n, sizeof(struct udaje), comparator);

    for (int i = 0; i < n; i++)
    {
        if (o_f)
        {
        	fprintf(subor,"%s ", ptr->time);
        	fprintf(subor,"%s ", ptr->imei);
        	fprintf(subor,"%s ", ptr->gps_lat);
        	fprintf(subor,"%s\n", ptr->gps_lon);
		}
		else
		{
			printf("%s ", ptr->time);
        	printf("%s ", ptr->imei);
        	printf("%s ", ptr->gps_lat);
        	printf("%s\n", ptr->gps_lon);
    	}
        ptr++;
    }
    if(o_f)
    {
    	fclose(subor);
	}
}

void spracuj_argv(int *prep_u, int *prep_l, int *prep_t, int *prep_s, int *i_f, int *o_f, int argc, char *argv[], char i_argument[], char o_argument[], char u_imei[], char l_gps_lat[], char l_gps_lon[], char l_distance[], char t_start[], char t_stop[], char s_imei[], char s_time[], char s_distance[])
{
	FILE* file;
	int c,index;
	while ((c = getopt (argc, argv, "i:o:u:l:t:s:")) != -1)
		{
			switch(c)
			{
				case 'i':
					strcpy(i_argument,optarg);
					*i_f = 1;
					break;
				case 'o':
					strcpy(o_argument,optarg);
					*o_f = 1;
					break;
				case 'u':
					*prep_u = 1;
					break;
				case 'l':
					*prep_l = 1;
					break;
				case 't':
					*prep_t = 1;
					break;
				case 's':
					*prep_s = 1;
					break;
				default:
					exit(0);	
			}
		}
	if (!i_f)
	{
		printf("nieje prepinac i");
		exit(0);
	}
	if ((file = fopen(i_argument, "r")) == NULL)
    {
        printf("neplatny paramenter pre prepinac I");
        exit(0);
    }
    else
    {
    	//printf("PARAM I: %s\n",i_argument);
        fclose(file);
	}
	if (*prep_u)
	{
		int i = 0;
		for (index = optind-1; index < argc; index++)
		{
			if(i==0)
			{
				strcpy(u_imei,argv[index]);	
			}
			i++;
			if(i>1)
			{
				printf("prilis vela argumentov U");
				exit(0);
			}
		}	
	}
	else if (*prep_l)
	{
		int i = 0;
		for (index = optind-1; index < argc; index++)
		{
			if(i==0)
			{
				strcpy(l_gps_lat,argv[index]);	
			}
			else if(i==1)
			{
				strcpy(l_gps_lon,argv[index]);	
			}
			else if(i==2)
			{
				strcpy(l_distance,argv[index]);	
			}
			i++;
			if(i>3)
			{
				printf("prilis vela argumentov L");
				exit(0);
			}
		}
		if (l_gps_lon[0] == '\0')
		{
			printf("chybajuci/e L argument/y");
			exit(0);
		}
		else if (l_distance[0] == '\0')
		{
			printf("chybajuci/e L argument/y");
			exit(0);
		}	
	}
	else if (*prep_t)
	{
		int i = 0;
		for (index = optind-1; index < argc; index++)
		{
			if(i==0)
			{
				strcpy(t_start,argv[index]);	
			}
			else if(i==1)
			{
				strcpy(t_stop,argv[index]);	
			}
			i++;
			if(i>2)
			{
				exit(0);
			}
		}
		if (t_stop[0] == '\0')
		{
			exit(0);
		}	
	}
	else if (*prep_s)
	{
		int i = 0;
		for (index = optind-1; index < argc; index++)
		{
			if(i==0)
			{
				strcpy(s_imei,argv[index]);	
			}
			else if(i==1)
			{
				strcpy(s_time,argv[index]);	
			}
			else if(i==2)
			{
				strcpy(s_distance,argv[index]);	
			}
			i++;
			if(i>3)
			{
				printf("prilis vela argumentov S");
				exit(0);
			}
		}
		if (s_time[0] == '\0')
		{
			exit(0);
		}
		else if (s_distance[0] == '\0')
		{
			exit(0);
		}	
	}
}

void prepinac_u(struct udaje* std, char u_imei[], int n, int o_f, char o_argument[])
{
	FILE *subor;
    if (o_f)
    {
    	subor = fopen(o_argument, "w");	
	}
	for (int i = 0; i < n; i++)
    {
        if (atoi(std[i].imei) == atoi(u_imei))
        {
            if (o_f)
            {
            	fprintf(subor, "%s %s %s %s\n", std[i].time, std[i].imei, std[i].gps_lon, std[i].gps_lat);
			}
			else
			{
				printf("%s %s %s %s\n", std[i].time, std[i].imei, std[i].gps_lon, std[i].gps_lat);	
			}
        }
    }
    if (o_f)
    {
    	fclose(subor);
	}
}

double vzdialenost_bodov(double nLat1, double nLon1, double nLat2, double nLon2)
{

    double nDLat = (M_PI / 180) * (nLat2 - nLat1);
    double nDLon = (M_PI / 180) * (nLon2 - nLon1);

    nLat1 = (M_PI / 180) * (nLat1);
    nLat2 = (M_PI / 180) * (nLat2);

    double nA = pow(sin(nDLat / 2), 2) + cos(nLat1) * cos(nLat2) * pow(sin(nDLon / 2), 2);

    double nC = 2 * atan2(sqrt(nA), sqrt(1 - nA));
    double vysledok = EARTH_R * nC;

    return vysledok;
}

void prepinac_l(struct udaje* std, char l_gps_lat[], char l_gps_lon[], char l_distance[], int n, int o_f, char o_argument[])
{
	FILE *subor;
    if (o_f)
    {
    	subor = fopen(o_argument, "w");	
	}
	double medzisucet = 0, nLat1 = atof(l_gps_lat), nLon1 = atof(l_gps_lon), nLat2 = 0, nLon2 = 0;
	for (int i=0;i<n;i++)
	{
		nLat2 = atof(std[i].gps_lat);
		nLon2 = atof(std[i].gps_lon);
		medzisucet = vzdialenost_bodov(nLat1,nLon1,nLat2,nLon2);
		if (medzisucet <= atof(l_distance))
		{
			if (o_f)
			{
				fprintf(subor, "%s %s %s %s\n",std[i].time,std[i].imei,std[i].gps_lat,std[i].gps_lon);
			}
			else
			{
				printf("%s %s %s %s\n",std[i].time,std[i].imei,std[i].gps_lat,std[i].gps_lon);
			}
		}
	}
	if (o_f)
    {
    	fclose(subor);
	}
}

void prepinac_t(struct udaje* std, char t_start[], char t_stop[], int n, int o_f, char o_argument[])
{
	FILE *subor;
    if (o_f)
    {
    	subor = fopen(o_argument, "w");	
	}
	char datum[11];
	for (int i = 0; i<n ;i++)
	{
		strcpy(datum,std[i].time);
		if (strcmp(datum, t_stop) <= 0)
    	{
        	if (strcmp(datum, t_start) >= 0)
        	{
            	if (o_f)
            	{
            		fprintf(subor, "%s %s %s %s\n",std[i].time,std[i].imei,std[i].gps_lat,std[i].gps_lon);
				}
				else
				{
					printf("%s %s %s %s\n",std[i].time,std[i].imei,std[i].gps_lat,std[i].gps_lon);
				}
    		}	
		}
	}
	if (o_f)
    {
    	fclose(subor);
	}
}

void prepinac_s(struct udaje* std, char s_imei[], char s_time[], char s_distance[], int n, int o_f, char o_argument[])
{
	FILE *subor;
	char datum[11];
    if (o_f)
    {
    	subor = fopen(o_argument, "w");	
	}
	struct udaje* nakazeny= malloc(5000*sizeof(struct udaje));
	int poz = 0;
	double dlzka_vypocet = 0;
	for (int i = 0; i<n ;i++)
	{
		if (atoi(s_imei) == atoi(std[i].imei))
		{ 
			strcpy(datum,std[i].time);
			if (strcmp(datum,s_time) >= 0)
			{
				strcpy(nakazeny[poz].imei,std[i].imei);
				strcpy(nakazeny[poz].time,std[i].time);
				strcpy(nakazeny[poz].gps_lat,std[i].gps_lat);
				strcpy(nakazeny[poz].gps_lon,std[i].gps_lon); 		
				poz++;
			}
		}	
	}
	qsort(nakazeny, poz, sizeof(struct udaje), comparator);
	for (int k = 0; k<n;k++)
	{
		if (atoi(std[k].imei) != atoi(s_imei))
		{
			for (int i = 0; i < poz; i++)
			{
				if ((strcmp(std[k].time,nakazeny[i].time)) == 0)
				{
					if (i == 0)
					{
						dlzka_vypocet = vzdialenost_bodov(atof(std[k].gps_lat), atof(std[k].gps_lon), atof(nakazeny[i].gps_lat), atof(nakazeny[i].gps_lon));
						if (dlzka_vypocet <= atoi(s_distance))
						{
							if (o_f)
							{
								fprintf(subor, "%s %s %s %s\n",std[k].time,std[k].imei,std[k].gps_lat,std[k].gps_lon);
							}
							else
							{
								printf("%s %s %s %s\n",std[k].time,std[k].imei,std[k].gps_lat,std[k].gps_lon);
							}		
						}
					}
					else if ((strcmp(std[k].time,nakazeny[i-1].time)) <= 0)
					{
						dlzka_vypocet = vzdialenost_bodov(atof(std[k].gps_lat), atof(std[k].gps_lon), atof(nakazeny[i].gps_lat), atof(nakazeny[i].gps_lon));
						if (dlzka_vypocet <= atoi(s_distance))
						{
							if (o_f)
							{
								fprintf(subor, "%s %s %s %s\n",std[k].time,std[k].imei,std[k].gps_lat,std[k].gps_lon);
							}
							else
							{
								printf("%s %s %s %s\n",std[k].time,std[k].imei,std[k].gps_lat,std[k].gps_lon);
							}	
						}	
					}	
				}
				else if ((strcmp(std[k].time,nakazeny[i].time)) > 0)
				{
					if (i == 0)
					{
						dlzka_vypocet = vzdialenost_bodov(atof(std[k].gps_lat), atof(std[k].gps_lon), atof(nakazeny[i].gps_lat), atof(nakazeny[i].gps_lon));
						if (dlzka_vypocet <= atoi(s_distance))
						{
							if (o_f)
							{
								fprintf(subor, "%s %s %s %s\n",std[k].time,std[k].imei,std[k].gps_lat,std[k].gps_lon);
							}
							else
							{
								printf("%s %s %s %s\n",std[k].time,std[k].imei,std[k].gps_lat,std[k].gps_lon);
							}		
						}
					}
					else if ((strcmp(std[k].time,nakazeny[i-1].time)) < 0)
					{
						dlzka_vypocet = vzdialenost_bodov(atof(std[k].gps_lat), atof(std[k].gps_lon), atof(nakazeny[i].gps_lat), atof(nakazeny[i].gps_lon));
						if (dlzka_vypocet <= atoi(s_distance))
						{
							if (o_f)
							{
								fprintf(subor, "%s %s %s %s\n",std[k].time,std[k].imei,std[k].gps_lat,std[k].gps_lon);
							}
							else
							{
								printf("%s %s %s %s\n",std[k].time,std[k].imei,std[k].gps_lat,std[k].gps_lon);
							}	
						}	
					}	
				} 
			}
		}
	}
	if (o_f)
    {
    	fclose(subor);
	}
}















