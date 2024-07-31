#include <SDL2/SDL.h>
#include <SDL2/SDL2_gfxPrimitives.h>
#include <SDL2/SDL_image.h>
#include <SDL2/SDL_ttf.h>
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include "sdl_init.h"
#include "types.h"
#include "displayers.h"
#include "states.h"
#include "debugmalloc.h"
#include <time.h>

int main(int argc, char *argv[]) {
    Colors colors;
    colors.green.r = 0;
    colors.green.g = 168;
    colors.green.b = 0;
    colors.green.a = 255;
    colors.black.r = 0;
    colors.black.g = 0;
    colors.black.b = 0;
    colors.black.a = 255;

    Data_sdl data_sdl;
    data_sdl.color = colors;
    data_sdl.event = (SDL_Event*) malloc(sizeof(SDL_Event));

    sdl_init(WINDOW_X, WINDOW_Y, &data_sdl);
    SDL_SetRenderDrawColor(data_sdl.renderer, 0x00, 0x00, 0x00, 0xFF);
    data_sdl.r = 0x00;
    data_sdl.g = 0x00;
    data_sdl.b = 0x00;

    Player player;
    player.def_ship_x = WINDOW_X / 2 - SIZE_SHIP / 2;
    player.ship.x = player.def_ship_x;
    player.ship.y = 394;
    player.ship.w = SIZE_SHIP;
    player.ship.h = SIZE_SHIP;
    player.left_key_down = false;
    player.right_key_down = false;
    player.space_down = false;
    player.p_score = 0;
    player.p_laser.def_y = player.ship.y - LASER_H;
    strcpy(player.p_name, "Player1");

    Game game;
    game.have_final_score = false;
    game.score_compared = false;
    game.turntables = false;
    game.state = 1;
    /*  1 - menu
     *  2 - difficulty selection
     *  3 - easy game
     *  4 - normal game
     *  5 - hard game
     *  6 - end of game
     *  7 - scoreboard
     *  0 - quit */


    Alien_swarm swarm;
    swarm.alien = NULL;
    swarm.created = false;
    swarm.aliens_move = 0;
    swarm.dir = 1;
    swarm.back_n_forth = 0;
    swarm.aliens_shoot = 0;
    swarm.a_win = false;

    /* idozito hozzaadasa: 20 ms; 1000 ms / 20 ms -> 50 fps */
    SDL_TimerID fps = SDL_AddTimer(20, idozit, NULL);

    /* RNG, eszerint lonek az alienek */
    srand((int)time(NULL));

    FILE *scores_r = fopen("scores.txt", "r");
    if (scores_r == NULL) {
        fclose(scores_r);
        FILE *scores_w = fopen("scores.txt", "w");
        for (int i = 0; i < 5; ++i) {
            fprintf(scores_w, "%s", "- 0\n");
        }
        fclose(scores_w);
    }
    fclose(scores_r);

    while (game.state != 0) {
        switch (game.state) {
            /* menu */
            case 1:
                st_menu(&data_sdl, &player, &game);
                SDL_SetRenderDrawColor(data_sdl.renderer, 0x00, 0x00, 0x00, 0xFF);
                break;
            /* difficulty selection */
            case 2:
                st_difficulty_select(&data_sdl, 90, 120, BT_W, BT_H, &game, &swarm);
                break;
            /* easy game */
            case 3:
                st_game(&data_sdl, &player, &swarm, &game);
                swarm.alien = clear_dead_alien(swarm.alien);
                break;
            /* normal game */
            case 4:
                st_game(&data_sdl, &player, &swarm, &game);
                swarm.alien = clear_dead_alien(swarm.alien);
                break;
            /* hard game */
            case 5:
                st_game(&data_sdl, &player, &swarm, &game);
                swarm.alien = clear_dead_alien(swarm.alien);
                break;
            /* end of game */
            case 6:
                st_end_of_game(&data_sdl, &player, &swarm, &game);
                break;
            /* scoreboard */
            case 7:
                st_scoreboard(&data_sdl, &game);
                break;
            case 8:
                st_inverted_game(&data_sdl, &player, &swarm, &game);
                swarm.alien = clear_dead_alien(swarm.alien);
                break;
            case 9:
                st_inverted_end_of_game(&data_sdl, &player, &swarm, &game);
                swarm.alien = clear_dead_alien(swarm.alien);
                break;
        }
    }
    free(data_sdl.event);
    SDL_DestroyTexture(data_sdl.texture);
    TTF_CloseFont(data_sdl.font);
    SDL_RemoveTimer(fps);
    SDL_DestroyRenderer(data_sdl.renderer);
    SDL_DestroyWindow(data_sdl.window);
    SDL_Quit();
    return 0;
}
