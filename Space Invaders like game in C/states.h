#ifndef STATES_H_INCLUDED
#define STATES_H_INCLUDED

void st_menu(Data_sdl *data_sdl, Player *player, Game *game);

void st_difficulty_select(Data_sdl *data_sdl, int bt1_x, int bt1_y, int bt_w, int bt_h, Game *game, Alien_swarm *swarm);

void st_game(Data_sdl *data_sdl, Player *player, Alien_swarm *swarm, Game *game);

    void swarm_move(Alien_swarm *swarm);

    void shoot_p_laser(Data_sdl *data_sdl, Player *player, Alien_swarm *swarm);

    void shoot_a_laserX(Alien_swarm *swarm, Game *game);

    void a_laserX(Data_sdl *data_sdl, Alien_swarm *swarm, Player *player, Game *game, int which);

void st_end_of_game(Data_sdl *data_sdl, Player *player, Alien_swarm *swarm, Game *game);

void st_scoreboard(Data_sdl *data_sdl, Game *game);

void st_inverted_game(Data_sdl *data_sdl, Player *player, Alien_swarm *swarm, Game *game);

    void shoot_p_laser_inv(Data_sdl *data_sdl, Player *player, Alien_swarm *swarm);

    void shoot_a_laserX_inv(Alien_swarm *swarm, Game *game);

    void a_laserX_inv(Data_sdl *data_sdl, Alien_swarm *swarm, Player *player, Game *game, int which);

void st_inverted_end_of_game(Data_sdl *data_sdl, Player *player, Alien_swarm *swarm, Game *game);


#endif // STATES_H_INCLUDED
