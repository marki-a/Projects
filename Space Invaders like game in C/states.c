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
#include "debugmalloc.h"
#include "states.h"

void st_menu(Data_sdl *data_sdl, Player *player, Game *game) {
    char unedited_name[11];
    game->have_final_score = false;
    game->score_compared = false;
    player->p_score = 0;
    draw_menu(data_sdl, player->p_name, game);

    SDL_WaitEvent(data_sdl->event);
    switch (data_sdl->event->type) {
        case SDL_MOUSEBUTTONDOWN:
            if (data_sdl->event->button.button == SDL_BUTTON_LEFT && 8 <= data_sdl->event->button.x && data_sdl->event->button.x <= 118 && 8 <= data_sdl->event->button.y && data_sdl->event->button.y <= 30) {
                SDL_Rect editbox_xywh = { 10, 10, 110, 22 };
                strcpy(unedited_name, player->p_name);
                input_text(player->p_name, 11, editbox_xywh, data_sdl);
                cut_spaces(player->p_name);
                if (player->p_name[0] != 0x0000) {
                    ;
                }
                else {
                    strcpy(player->p_name, unedited_name);
                }
            }
            if (data_sdl->event->button.button == SDL_BUTTON_LEFT && 90 <= data_sdl->event->button.x && data_sdl->event->button.x <= 250 && 150 <= data_sdl->event->button.y && data_sdl->event->button.y <= 198) {
                game->state = 2;
            }
            if (data_sdl->event->button.button == SDL_BUTTON_LEFT && 90 <= data_sdl->event->button.x && data_sdl->event->button.x <= 250 && 210 <= data_sdl->event->button.y && data_sdl->event->button.y <= 258) {
                game->state = 7;
            }
            break;
        case SDL_USEREVENT:
            if (strstr(player->p_name, "INVERTGAME") != NULL) {
                if (!game->turntables) {
                    game->turntables = true;
                }
                player->ship.y = 10;
                player->ship.w = SIZE_ALIEN;
                player->ship.h = SIZE_ALIEN;
                player->ship.x = WINDOW_X / 2 - SIZE_ALIEN / 2;
                player->p_laser.def_y = player->ship.y + SIZE_ALIEN + LASER_H;
            }
            else {
                if (game->turntables) {
                    game->turntables = false;
                }
                player->ship.y = 394;
                player->ship.w = SIZE_SHIP;
                player->ship.h = SIZE_SHIP;
                player->ship.x = player->def_ship_x;
                player->p_laser.def_y = player->ship.y - LASER_H;
            }
            break;
        case SDL_QUIT:
            game->state = 0;
            break;
    }
}

void st_difficulty_select(Data_sdl *data_sdl, int bt1_x, int bt1_y, int bt_w, int bt_h, Game *game, Alien_swarm *swarm) {
    draw_difficulty_select(data_sdl);
    SDL_WaitEvent(data_sdl->event);
    switch (data_sdl->event->type) {
        case SDL_MOUSEBUTTONDOWN:
            if (!game->turntables) {
                if (data_sdl->event->button.button == SDL_BUTTON_LEFT && bt1_x <= data_sdl->event->button.x && data_sdl->event->button.x <= bt1_x + bt_w && bt1_y <= data_sdl->event->button.y && data_sdl->event->button.y <= bt1_y + bt_h) {
                    game->state = 3;
                    game->v = V_EASY;
                    swarm->column_a = 5;
                    swarm->row_a = 4;
                }
                if (data_sdl->event->button.button == SDL_BUTTON_LEFT && bt1_x <= data_sdl->event->button.x && data_sdl->event->button.x <= bt1_x + bt_w && bt1_y + 60 <= data_sdl->event->button.y && data_sdl->event->button.y <= bt1_y + 60 + bt_h) {
                    game->state = 4;
                    game->v = V_NORMAL;
                    swarm->column_a = 6;
                    swarm->row_a = 5;
                }
                if (data_sdl->event->button.button == SDL_BUTTON_LEFT && bt1_x <= data_sdl->event->button.x && data_sdl->event->button.x <= bt1_x + bt_w && bt1_y + 120 <= data_sdl->event->button.y && data_sdl->event->button.y <= bt1_y + 120 + bt_h) {
                    game->state = 5;
                    game->v = V_HARD;
                    swarm->column_a = 8;
                    swarm->row_a = 6;
                }
                break;
            }
            else {
                if (data_sdl->event->button.button == SDL_BUTTON_LEFT && bt1_x <= data_sdl->event->button.x && data_sdl->event->button.x <= bt1_x + bt_w && bt1_y <= data_sdl->event->button.y && data_sdl->event->button.y <= bt1_y + bt_h) {
                    game->state = 8;
                    game->v = V_EASY;
                    swarm->column_a = 4;
                    swarm->row_a = 4;
                }
                if (data_sdl->event->button.button == SDL_BUTTON_LEFT && bt1_x <= data_sdl->event->button.x && data_sdl->event->button.x <= bt1_x + bt_w && bt1_y + 60 <= data_sdl->event->button.y && data_sdl->event->button.y <= bt1_y + 60 + bt_h) {
                    game->state = 8;
                    game->v = V_NORMAL;
                    swarm->column_a = 4;
                    swarm->row_a = 4;
                }
                if (data_sdl->event->button.button == SDL_BUTTON_LEFT && bt1_x <= data_sdl->event->button.x && data_sdl->event->button.x <= bt1_x + bt_w && bt1_y + 120 <= data_sdl->event->button.y && data_sdl->event->button.y <= bt1_y + 120 + bt_h) {
                    game->state = 8;
                    game->v = V_HARD;
                    swarm->column_a = 4;
                    swarm->row_a = 4;
                }
                break;
            }
        case SDL_QUIT:
            game->state = 0;
            break;
    }
}

void st_game(Data_sdl *data_sdl, Player *player, Alien_swarm *swarm, Game *game) {
    SDL_RenderClear(data_sdl->renderer);
    An_alien *moving;
    if (!(swarm->created)) {
        player->left_key_down = false;
        player->right_key_down = false;
        swarm->aliens_alive = swarm->column_a * swarm->row_a;
        swarm->dir = 1;
        SDL_Rect coords;
        coords.w = SIZE_ALIEN;
        coords.h = SIZE_ALIEN;
        for (int column = 0; column < swarm->column_a; ++column) {
            for (int row = 0; row < swarm->row_a; ++row) {
                coords.x = column * (SIZE_ALIEN + 10) + 10;
                coords.y = row * SIZE_ALIEN + 50;
                swarm->alien = set_alien(swarm->alien, coords.x, coords.y, coords.w, coords.h);
            }
        }
        draw_piece(data_sdl, Spaceship, player->ship.x, player->ship.y, player->ship.w, player->ship.h);
        moving = swarm->alien;
        while (moving != NULL) {
                draw_piece(data_sdl, Alien, moving->pos.x, moving->pos.y, SIZE_ALIEN, SIZE_ALIEN);
                moving = moving->next;
        }
        switch (game->state) {
            case 3:
                game->score_mult = MULT_EASY;
                break;
            case 4:
                game->score_mult = MULT_NORMAL;
                break;
            case 5:
                game->score_mult = MULT_HARD;
                break;
        }
        swarm->created = true;
    }
    if (swarm->back_n_forth == 2) {
        moving = swarm->alien;
        while (moving != NULL) {
            moving->pos.y += SIZE_ALIEN;
            moving = moving->next;
        }
        swarm->back_n_forth = 0;
    }
    draw_piece(data_sdl, Spaceship, player->ship.x, player->ship.y, player->ship.w, player->ship.h);
    moving = swarm->alien;
    while (moving != NULL) {
            draw_piece(data_sdl, Alien, moving->pos.x, moving->pos.y, SIZE_ALIEN, SIZE_ALIEN);
            moving = moving->next;
    }
    moving = swarm->alien;
    while (moving != NULL) {
        if (swarm->dir == 1 && moving->alive && WINDOW_X - (moving->pos.x + SIZE_ALIEN) <= 10) {
            swarm->dir = -1;
        }
        if (swarm->dir == -1 && moving->pos.x <= 10) {
            swarm->dir = 1;
            swarm->back_n_forth += 1;
        }
        if (WINDOW_Y - (10 + SIZE_SHIP) <= moving->pos.y + SIZE_ALIEN) {
            game->state = 6;
            swarm->a_win = true;
        }
        moving = moving->next;
    }
    swarm->v = swarm->dir * game->v;
    SDL_WaitEvent(data_sdl->event);
    switch (data_sdl->event->type) {
        case SDL_KEYDOWN:
            switch (data_sdl->event->key.keysym.sym) {
                case SDLK_LEFT:
                    player->left_key_down = true;
                    break;
                case SDLK_RIGHT:
                    player->right_key_down = true;
                    break;
                case SDLK_SPACE:
                    player->space_down = true;
                    break;
                case SDLK_z:                                            /* dev function */
                    game->state = 6;
                    swarm->alien = clear_aliens(swarm->alien);
                    player->p_laser.shot = false;
                    swarm->a_laser1.shot = false;
                    swarm->a_laser2.shot = false;
                    player->space_down = false;
                    swarm->aliens_move = 0;
                    swarm->aliens_shoot = 0;
                    swarm->back_n_forth = 0;
                    break;
            }
            break;
        case SDL_KEYUP:
            switch (data_sdl->event->key.keysym.sym) {
                case SDLK_LEFT:
                    player->left_key_down = false;
                    break;
                case SDLK_RIGHT:
                    player->right_key_down = false;
                    break;
                case SDLK_SPACE:
                    player->space_down = false;
                    break;
            }
            break;
        case SDL_USEREVENT:
            /* player movement */
            if (player->left_key_down && !player->right_key_down) {
                if (0 < player->ship.x) {
                    player->ship.x -= game->v;
                }
            }
            if (player->right_key_down && !player->left_key_down) {
                if (player->ship.x <= (WINDOW_X - SIZE_SHIP - game->v)) {
                    player->ship.x += game->v;
                }
            }
            swarm_move(swarm);
            shoot_p_laser(data_sdl, player, swarm);
            if (swarm->aliens_alive != 0) {
                shoot_a_laserX(swarm, game);
            }
            if (swarm->a_laser1.shot) {
                a_laserX(data_sdl, swarm, player, game, 1);
            }
            if (swarm->a_laser2.shot) {
                a_laserX(data_sdl, swarm, player, game, 2);
            }
            break;
        case SDL_QUIT:
            swarm->alien = clear_aliens(swarm->alien);
            game->state = 0;
            break;
    }
    SDL_RenderPresent(data_sdl->renderer);
    if (swarm->aliens_alive == 0) {
        player->space_down = false;
        player->p_laser.shot = false;
        swarm->a_laser1.shot = false;
        swarm->a_laser2.shot = false;
        game->state = 6;
        swarm->aliens_move = 0;
        swarm->aliens_shoot = 0;
        swarm->back_n_forth = 0;
    }
}

    /* alien movement */
    void swarm_move(Alien_swarm *swarm) {
        An_alien *moving = swarm->alien;
        if (swarm->aliens_move == 2) {
            while (moving != NULL) {
                moving->pos.x += swarm->v;
                moving = moving->next;
            }
            swarm->aliens_move = 0;
        }
        else {
            ++swarm->aliens_move;
        }
    }

    /* player's laser movement, hit detection and scoring */
    void shoot_p_laser(Data_sdl *data_sdl, Player *player, Alien_swarm *swarm) {
        An_alien *moving;
        if (player->space_down) {
            if (!player->p_laser.shot) {
                player->p_laser.shot = true;
                player->p_laser.pos.x = player->ship.x + (SIZE_SHIP / 2) - (LASER_W / 2);
                player->p_laser.pos.y = player->p_laser.def_y;
            }
        }
        if (player->p_laser.shot) {
            draw_piece(data_sdl, Laser, player->p_laser.pos.x, player->p_laser.pos.y, LASER_W, LASER_H);
            /* hit and score */
            moving = swarm->alien;
            while (moving != NULL) {
                if (moving->pos.x <= (player->p_laser.pos.x + LASER_W) && (moving->pos.x + SIZE_ALIEN) >= player->p_laser.pos.x && moving->pos.y <= (player->p_laser.pos.y + LASER_H) && (moving->pos.y + SIZE_ALIEN) >= player->p_laser.pos.y) {
                    moving->alive = false;
                    player->p_laser.shot = false;
                    swarm->aliens_alive -= 1;
                    player->p_score += 10;
                }
                moving = moving->next;
            }
            /* movement */
            player->p_laser.pos.y -= V_LASER;
            if (player->p_laser.pos.y < -LASER_H) {
                player->p_laser.shot = false;
            }
        }
    }

    /* aliens shooting */
    void shoot_a_laserX(Alien_swarm *swarm, Game *game) {
        An_alien *moving = swarm->alien;
        swarm->which_shoots = rand() % swarm->aliens_alive;
        if (swarm->aliens_shoot == 210 / game->v) {
            for (int i = 0; i < swarm->which_shoots; ++i) {
                moving = moving->next;
            }
            swarm->a_laser1.pos.x = (moving->pos.x + SIZE_ALIEN / 2) - LASER_W / 2;
            swarm->a_laser1.pos.y = moving->pos.y + SIZE_ALIEN;
            swarm->a_laser1.shot = true;
        }
        if (swarm->aliens_shoot == 420 / game->v) {
            swarm->aliens_shoot = 0;
            for (int i = 0; i < swarm->which_shoots; ++i) {
                moving = moving->next;
            }
            swarm->a_laser2.pos.x = (moving->pos.x + SIZE_ALIEN / 2) - LASER_W / 2;
            swarm->a_laser2.pos.y = moving->pos.y + SIZE_ALIEN;
            swarm->a_laser2.shot = true;
        }
        else {
            swarm->aliens_shoot += 1;
        }
    }

    /* aliens' lasers */
    void a_laserX(Data_sdl *data_sdl, Alien_swarm *swarm, Player *player, Game *game, int which) {
        /* a_laser1 */
        if (which == 1) {
            draw_piece(data_sdl, Laser, swarm->a_laser1.pos.x, swarm->a_laser1.pos.y, LASER_W, LASER_H);
            /* movement */
            swarm->a_laser1.pos.y += game->v;
            if (swarm->a_laser1.pos.y > WINDOW_Y + LASER_H) {
                swarm->a_laser1.shot = false;
            }
            /* hit */
            if (player->ship.x <= (swarm->a_laser1.pos.x + LASER_W) && (player->ship.x + SIZE_SHIP) >= swarm->a_laser1.pos.x && player->ship.y <= (swarm->a_laser1.pos.y + LASER_H) && (player->ship.y + SIZE_SHIP) >= swarm->a_laser1.pos.y) {
                game->state = 6;
                swarm->a_win = true;
            }
        }
        /* a_laser2 */
        if (which == 2) {
            draw_piece(data_sdl, Laser, swarm->a_laser2.pos.x, swarm->a_laser2.pos.y, LASER_W, LASER_H);
            /* movement */
            swarm->a_laser2.pos.y += game->v;
            if (swarm->a_laser2.pos.y > WINDOW_Y + LASER_H) {
                swarm->a_laser2.shot = false;
            }
            /* hit */
            if (player->ship.x <= (swarm->a_laser2.pos.x + LASER_W) && (player->ship.x + SIZE_SHIP) >= swarm->a_laser2.pos.x && player->ship.y <= (swarm->a_laser2.pos.y + LASER_H) && (player->ship.y + SIZE_SHIP) >= swarm->a_laser2.pos.y) {
                game->state = 6;
                swarm->a_win = true;
            }
        }
    }

void st_end_of_game(Data_sdl *data_sdl, Player *player, Alien_swarm *swarm, Game *game) {
    char sorok[5][16], compare[5], final_score[5];
    if (swarm->created) {
        player->ship.x = player->def_ship_x;
        FILE *scores_r = fopen("scores.txt", "r");
        for (int i = 0; i < 5; ++i) {
            fgets(sorok[i], 16, scores_r);
            for (int c = 0; c < 16; ++c) {
                if (sorok[i][c] == '\n') {
                    sorok[i][c] = '\0';
                }
            }
        }
        fclose(scores_r);
        swarm->created = false;
    }
    if (swarm->a_win) {
        swarm->alien = clear_aliens(swarm->alien);
        player->p_laser.shot = false;
        swarm->a_laser1.shot = false;
        swarm->a_laser2.shot = false;
        player->space_down = false;
        swarm->aliens_move = 0;
        swarm->aliens_shoot = 0;
        swarm->back_n_forth = 0;
        swarm->a_win = false;
    }
    SDL_RenderClear(data_sdl->renderer);
    draw_piece(data_sdl, Menu, 90, 390, BT_W, BT_H);
    write_text("Your score:", data_sdl, 90, 110);

    if (!(game->have_final_score)) {
        player->p_score *= game->score_mult;
        sprintf(player->final_score, "%d", player->p_score);
        game->have_final_score = true;
    }

    write_text(player->final_score, data_sdl, 90, 190);
    SDL_RenderPresent(data_sdl->renderer);

    if (!(game->score_compared)) {
        int c;
        int *all_scores = (int*) malloc(5 * sizeof(int));
        for (int i = 0; i < 5; ++i) {
            c = 0;
            while (sorok[i][c] != ' ' && c <= strlen(sorok[i])) {
                ++c;
            }
            for (int d = 0; d < 5; ++d) {
                compare[d] = sorok[i][c+1];
                ++c;
            }
            all_scores[i] = atoi(compare);
        }
        for (c = 4; all_scores[c] < player->p_score && 0 <= c; --c) {
            ;
        }
        c += 1;
        if (4 < c) {
            game->score_compared = true;
        }
        else {
            for (int temp = 4; c < temp; --temp) {
                strcpy(sorok[temp], sorok[temp-1]);
            }
            strcpy(sorok[c], player->p_name);
            strcat(sorok[c], " ");
            strcat(sorok[c], final_score);
        }
        FILE *scores_w = fopen("scores.txt", "w");
        for (int i = 0; i < 5; ++i) {
            fprintf(scores_w, "%s%s", sorok[i], "\n");
        }
        fclose(scores_w);
        free(all_scores);
        game->score_compared = true;
    }
    SDL_WaitEvent(data_sdl->event);
    switch (data_sdl->event->type) {
        case SDL_MOUSEBUTTONDOWN:
            if (data_sdl->event->button.button == SDL_BUTTON_LEFT && 90 <= data_sdl->event->button.x && data_sdl->event->button.x <= 250 &&
                390 <= data_sdl->event->button.y && data_sdl->event->button.y <= 438) {
                game->state = 1;
            }
            break;
        case SDL_QUIT:
            game->state = 0;
            break;
    }
}

void st_scoreboard(Data_sdl *data_sdl, Game *game) {
    int y = 60;
    char sor[16];
    SDL_RenderClear(data_sdl->renderer);
    FILE *scores = fopen("scores.txt", "r");
    if (scores == NULL) {
        perror("Couldn't open file");
    }
    draw_piece(data_sdl, Menu, 90, 390, BT_W, BT_H);
    write_text("Top scores:", data_sdl, 10, 30);
    for (int i = 0; i < 5; ++i) {
        fgets(sor, 16, scores);
        for (int c = 0; c < 16; ++c) {
            if (sor[c] == '\n') {
                sor[c] = '\0';
            }
        }
        write_text(sor, data_sdl, 50, y);
        y += 30;
    }
    SDL_RenderPresent(data_sdl->renderer);
    SDL_WaitEvent(data_sdl->event);
    switch (data_sdl->event->type) {
        case SDL_MOUSEBUTTONDOWN:
            if (data_sdl->event->button.button == SDL_BUTTON_LEFT && 90 <= data_sdl->event->button.x && data_sdl->event->button.x <= 250 &&
                390 <= data_sdl->event->button.y && data_sdl->event->button.y <= 438) {
                game->state = 1;
            }
            break;
        case SDL_QUIT:
            game->state = 0;
            break;
    }
    fclose(scores);
}

void st_inverted_game(Data_sdl *data_sdl, Player *player, Alien_swarm *swarm, Game *game) {
    SDL_RenderClear(data_sdl->renderer);
    An_alien *moving;
    if (!(swarm->created)) {
        player->left_key_down = false;
        player->right_key_down = false;
        swarm->aliens_alive = swarm->column_a * swarm->row_a;
        swarm->dir = -1;
        SDL_Rect coords;
        coords.w = SIZE_ALIEN;
        coords.h = SIZE_ALIEN;
        for (int column = 0; column < swarm->column_a; ++column) {
            for (int row = 0; row < swarm->row_a; ++row) {
                coords.x = WINDOW_X - (column * (SIZE_SHIP + 10) + 10 + SIZE_SHIP);
                coords.y = WINDOW_Y - (row * (SIZE_SHIP + 10) + 50);
                swarm->alien = set_alien(swarm->alien, coords.x, coords.y, coords.w, coords.h);
            }
        }
        draw_piece(data_sdl, Alien, player->ship.x, player->ship.y, player->ship.w, player->ship.h);
        moving = swarm->alien;
        while (moving != NULL) {
                draw_piece(data_sdl, Spaceship, moving->pos.x, moving->pos.y, SIZE_SHIP, SIZE_SHIP);
                moving = moving->next;
        }
        swarm->created = true;
    }
    if (swarm->back_n_forth == 2) {
        moving = swarm->alien;
        while (moving != NULL) {
            moving->pos.y -= SIZE_SHIP;
            moving = moving->next;
        }
        swarm->back_n_forth = 0;
    }
    draw_piece(data_sdl, Alien, player->ship.x, player->ship.y, player->ship.w, player->ship.h);
    moving = swarm->alien;
    while (moving != NULL) {
            draw_piece(data_sdl, Spaceship, moving->pos.x, moving->pos.y, SIZE_SHIP, SIZE_SHIP);
            moving = moving->next;
    }
    moving = swarm->alien;
    while (moving != NULL) {
        if (swarm->dir == -1 && moving->alive && moving->pos.x <= 10) {
            swarm->dir = 1;
        }
        if (swarm->dir == 1 && moving->alive && WINDOW_X - (moving->pos.x + SIZE_SHIP) <= 10) {
            swarm->dir = -1;
            swarm->back_n_forth += 1;
        }
        if (moving->pos.y + SIZE_ALIEN <= (10 + SIZE_SHIP)) {
            game->state = 9;
            swarm->a_win = true;
        }
        moving = moving->next;
    }
    swarm->v = swarm->dir * game->v;
    SDL_WaitEvent(data_sdl->event);
    switch (data_sdl->event->type) {
        case SDL_KEYDOWN:
            switch (data_sdl->event->key.keysym.sym) {
                case SDLK_LEFT:
                    player->left_key_down = true;
                    break;
                case SDLK_RIGHT:
                    player->right_key_down = true;
                    break;
                case SDLK_SPACE:
                    player->space_down = true;
                    break;
                case SDLK_z:                                            /* dev function */
                    game->state = 9;
                    swarm->alien = clear_aliens(swarm->alien);
                    player->p_laser.shot = false;
                    swarm->a_laser1.shot = false;
                    swarm->a_laser2.shot = false;
                    player->space_down = false;
                    swarm->aliens_move = 0;
                    swarm->aliens_shoot = 0;
                    swarm->back_n_forth = 0;
                    break;
            }
            break;
        case SDL_KEYUP:
            switch (data_sdl->event->key.keysym.sym) {
                case SDLK_LEFT:
                    player->left_key_down = false;
                    break;
                case SDLK_RIGHT:
                    player->right_key_down = false;
                    break;
                case SDLK_SPACE:
                    player->space_down = false;
                    break;
            }
            break;
        case SDL_USEREVENT:
            /* player movement */
            if (player->left_key_down && !player->right_key_down) {
                if (0 < player->ship.x) {
                    player->ship.x -= game->v;
                }
            }
            if (player->right_key_down && !player->left_key_down) {
                if (player->ship.x <= (WINDOW_X - SIZE_SHIP - game->v)) {
                    player->ship.x += game->v;
                }
            }
            swarm_move(swarm);
            shoot_p_laser_inv(data_sdl, player, swarm);
            if (swarm->aliens_alive != 0) {
                shoot_a_laserX_inv(swarm, game);
            }
            if (swarm->a_laser1.shot) {
                a_laserX_inv(data_sdl, swarm, player, game, 1);
            }
            if (swarm->a_laser2.shot) {
                a_laserX_inv(data_sdl, swarm, player, game, 2);
            }
            break;
        case SDL_QUIT:
            swarm->alien = clear_aliens(swarm->alien);
            game->state = 0;
            break;
    }
    SDL_RenderPresent(data_sdl->renderer);
    if (swarm->aliens_alive == 0) {
        player->space_down = false;
        player->p_laser.shot = false;
        swarm->a_laser1.shot = false;
        swarm->a_laser2.shot = false;
        game->state = 9;
        swarm->aliens_move = 0;
        swarm->aliens_shoot = 0;
        swarm->back_n_forth = 0;
    }
}

/* player's laser movement, hit detection and scoring in inverted mode */
    void shoot_p_laser_inv(Data_sdl *data_sdl, Player *player, Alien_swarm *swarm) {
        An_alien *moving;
        if (player->space_down) {
            if (!player->p_laser.shot) {
                player->p_laser.shot = true;
                player->p_laser.pos.x = player->ship.x + (SIZE_ALIEN / 2) - (LASER_W / 2);
                player->p_laser.pos.y = player->p_laser.def_y;
            }
        }
        if (player->p_laser.shot) {
            draw_piece(data_sdl, Laser, player->p_laser.pos.x, player->p_laser.pos.y, LASER_W, LASER_H);
            /* hit and score */
            moving = swarm->alien;
            while (moving != NULL) {
                if (moving->pos.x <= (player->p_laser.pos.x + LASER_W) && (moving->pos.x + SIZE_SHIP) >= player->p_laser.pos.x && moving->pos.y <= (player->p_laser.pos.y + LASER_H) && (moving->pos.y + SIZE_SHIP) >= player->p_laser.pos.y) {
                    moving->alive = false;
                    player->p_laser.shot = false;
                    swarm->aliens_alive -= 1;
                }
                moving = moving->next;
            }
            /* movement */
            player->p_laser.pos.y += V_LASER;
            if (WINDOW_Y + LASER_H < player->p_laser.pos.y) {
                player->p_laser.shot = false;
            }
        }
    }

    /* aliens shooting in inverted mode */
    void shoot_a_laserX_inv(Alien_swarm *swarm, Game *game) {
        An_alien *moving = swarm->alien;
        swarm->which_shoots = rand() % swarm->aliens_alive;
        if (swarm->aliens_shoot == 210 / game->v) {
            for (int i = 0; i < swarm->which_shoots; ++i) {
                moving = moving->next;
            }
            swarm->a_laser1.pos.x = (moving->pos.x + SIZE_SHIP / 2) - LASER_W / 2;
            swarm->a_laser1.pos.y = moving->pos.y + SIZE_SHIP;
            swarm->a_laser1.shot = true;
        }
        if (swarm->aliens_shoot == 420 / game->v) {
            swarm->aliens_shoot = 0;
            for (int i = 0; i < swarm->which_shoots; ++i) {
                moving = moving->next;
            }
            swarm->a_laser2.pos.x = (moving->pos.x + SIZE_SHIP / 2) - LASER_W / 2;
            swarm->a_laser2.pos.y = moving->pos.y + SIZE_SHIP;
            swarm->a_laser2.shot = true;
        }
        else {
            swarm->aliens_shoot += 1;
        }
    }

    /* aliens' lasers in inverted mode */
    void a_laserX_inv(Data_sdl *data_sdl, Alien_swarm *swarm, Player *player, Game *game, int which) {
        /* a_laser1 */
        if (which == 1) {
            draw_piece(data_sdl, Laser, swarm->a_laser1.pos.x, swarm->a_laser1.pos.y, LASER_W, LASER_H);
            /* movement */
            swarm->a_laser1.pos.y -= game->v;
            if (swarm->a_laser1.pos.y > WINDOW_Y + LASER_H) {
                swarm->a_laser1.shot = false;
            }
            /* hit */
            if (player->ship.x <= (swarm->a_laser1.pos.x + LASER_W) && (player->ship.x + SIZE_ALIEN) >= swarm->a_laser1.pos.x && player->ship.y <= (swarm->a_laser1.pos.y + LASER_H) && (player->ship.y + SIZE_ALIEN) >= swarm->a_laser1.pos.y) {
                game->state = 6;
                swarm->a_win = true;
            }
        }
        /* a_laser2 */
        if (which == 2) {
            draw_piece(data_sdl, Laser, swarm->a_laser2.pos.x, swarm->a_laser2.pos.y, LASER_W, LASER_H);
            /* movement */
            swarm->a_laser2.pos.y -= game->v;
            if (swarm->a_laser2.pos.y > WINDOW_Y + LASER_H) {
                swarm->a_laser2.shot = false;
            }
            /* hit */
            if (player->ship.x <= (swarm->a_laser2.pos.x + LASER_W) && (player->ship.x + SIZE_ALIEN) >= swarm->a_laser2.pos.x && player->ship.y <= (swarm->a_laser2.pos.y + LASER_H) && (player->ship.y + SIZE_ALIEN) >= swarm->a_laser2.pos.y) {
                game->state = 6;
                swarm->a_win = true;
            }
        }
    }

void st_inverted_end_of_game(Data_sdl *data_sdl, Player *player, Alien_swarm *swarm, Game *game) {
    if (data_sdl->r == 0x00 && data_sdl->g == 0x00 && data_sdl->b == 0x00) {
        data_sdl->dir_rainb = 1;
    }
    if (data_sdl->r == 0xFF && data_sdl->g == 0xFF && data_sdl->b == 0xFF) {
        data_sdl->dir_rainb = -1;
    }
    SDL_SetRenderDrawColor(data_sdl->renderer, data_sdl->r, data_sdl->g, data_sdl->b, 0xFF);
    if (swarm->created) {
        player->ship.x = player->def_ship_x;
        swarm->created = false;
    }
    if (swarm->a_win) {
        swarm->alien = clear_aliens(swarm->alien);
        player->p_laser.shot = false;
        swarm->a_laser1.shot = false;
        swarm->a_laser2.shot = false;
        player->space_down = false;
        swarm->aliens_move = 0;
        swarm->aliens_shoot = 0;
        swarm->back_n_forth = 0;
        swarm->a_win = false;
    }
    SDL_RenderClear(data_sdl->renderer);
    draw_piece(data_sdl, Menu, 90, 390, BT_W, BT_H);
    write_text("No scores, Just FUN", data_sdl, 90, 110);
    SDL_WaitEvent(data_sdl->event);
    switch (data_sdl->event->type) {
        case SDL_MOUSEBUTTONDOWN:
            if (data_sdl->event->button.button == SDL_BUTTON_LEFT && 90 <= data_sdl->event->button.x && data_sdl->event->button.x <= 250 &&
                390 <= data_sdl->event->button.y && data_sdl->event->button.y <= 438) {
                game->state = 1;
            }
            break;
        case SDL_USEREVENT:
            if (data_sdl->dir_rainb == 1) {
                if (data_sdl->r < 0xFF) {
                    data_sdl->r += 0x05;
                }
                if (data_sdl->r == 0xFF && data_sdl->g <0xFF) {
                    data_sdl->g += 0x05;
                }
                if (data_sdl->r == 0xFF && data_sdl->g == 0xFF && data_sdl->b < 0xFF) {
                    data_sdl->b += 0x05;
                }
            }
            if (data_sdl->dir_rainb == -1) {
                if (data_sdl->r > 0x00) {
                    data_sdl->r -= 0x05;
                }
                if (data_sdl->r == 0x00 && data_sdl->g > 0x00) {
                    data_sdl->g -= 0x05;
                }
                if (data_sdl->r == 0x00 && data_sdl->g == 0x00 && data_sdl->b > 0x00) {
                    data_sdl->b -= 0x05;
                }
            }
            break;
        case SDL_QUIT:
            game->state = 0;
            break;
    }
    SDL_RenderPresent(data_sdl->renderer);
    SDL_SetRenderDrawColor(data_sdl->renderer, 0x00, 0x00, 0x00, 0xFF);
}
