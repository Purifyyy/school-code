#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <math.h>
#include "gps.z4.h"
#define EARTH_R 6371000


int main(int argc, char *argv[])
{
	int n=0,prep_u=0,prep_l=0,prep_t=0,prep_s=0,i_f =0,o_f = 0;
	char i_argument[25] = {0}, o_argument[25] = {0}, u_imei[9] = {0}, l_gps_lat[9] = {0}, l_gps_lon[9] = {0}, l_distance[11] = {0}, t_start[11] = {0}, t_stop[11] = {0}, s_imei[9] = {0}, s_time[15] = {0}, s_distance[11] = {0};
	struct udaje* std= malloc(105000*sizeof(struct udaje));
    struct udaje* ptr = NULL;
    ptr = std;
	
	spracuj_argv(&prep_u, &prep_l, &prep_t, &prep_s, &i_f, &o_f, argc, argv, i_argument, o_argument, u_imei, l_gps_lat, l_gps_lon, l_distance, t_start, t_stop, s_imei, s_time, s_distance);

	n = napln_strukturu(std, ptr, n, i_argument);

	if (prep_u)
	{
		prepinac_u(std, u_imei, n, o_f, o_argument);
	}
	else if (prep_l)
	{
		prepinac_l(std, l_gps_lat, l_gps_lon, l_distance, n, o_f, o_argument);
	}
	else if (prep_t)
	{
		prepinac_t(std, t_start, t_stop, n, o_f, o_argument);
	}
	else if(prep_s)
	{
		prepinac_s(std, s_imei, s_time, s_distance, n, o_f, o_argument);
	}
	else
	{
		default_prep(std, n, ptr, o_f, o_argument);
	}
	free(std);

	return 0;
}

	


