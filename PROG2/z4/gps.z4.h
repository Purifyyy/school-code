int comparator(const void* p, const void* q);

struct udaje {
    char imei[20];
    char time[30];
    char gps_lat[20];
    char gps_lon[20];
};

int napln_strukturu(struct udaje* std, struct udaje* ptr, int n, char i_argument[]);

void default_prep(struct udaje* std, int n, struct udaje* ptr, int o_f, char o_argument[]);

void spracuj_argv(int *prep_u, int *prep_l, int *prep_t, int *prep_s, int *i_f, int *o_f, int argc, char *argv[], char i_argument[], char o_argument[], char u_imei[], char l_gps_lat[], char l_gps_lon[], char l_distance[], char t_start[], char t_stop[], char s_imei[], char s_time[], char s_distance[]);

void prepinac_u(struct udaje* std, char u_imei[], int n, int o_f, char o_argument[]);

void prepinac_l(struct udaje* std, char l_gps_lat[], char l_gps_lon[], char l_distance[], int n, int o_f, char o_argument[]);

void prepinac_t(struct udaje* std, char t_start[], char t_stop[], int n, int o_f, char o_argument[]);

void prepinac_s(struct udaje* std, char s_imei[], char s_time[], char s_distance[], int n, int o_f, char o_argument[]);
