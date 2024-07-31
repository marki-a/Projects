#include <SDL2/SDL.h>
#include <SDL2/SDL2_gfxPrimitives.h>
#include <SDL2/SDL_image.h>
#include <SDL2/SDL_ttf.h>
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include "sdl_init.h"
#include "types.h"

/* az infoc oldalrol vett fuggvenyek osszeallitva egybe
 * rendre inicializalja az SDL-t, letrehoz egy ablakot es egy renderert, valamint betolt egy fontot es egy forraskepet */
void sdl_init(int width, int height, Data_sdl *data_sdl) {
    if (SDL_Init(SDL_INIT_EVERYTHING) < 0) {
        SDL_Log("SDL failed to start: %s", SDL_GetError());
        exit(1);
    }
    SDL_Window *window = SDL_CreateWindow("NOT INVERTGAME", SDL_WINDOWPOS_CENTERED, SDL_WINDOWPOS_CENTERED, width, height, 0);
    if (window == NULL) {
        SDL_Log("Cannot create window: %s", SDL_GetError());
        exit(1);
    }
    SDL_Renderer *renderer = SDL_CreateRenderer(window, -1, SDL_RENDERER_SOFTWARE);
    if (renderer == NULL) {
        SDL_Log("Cannot create renderer: %s", SDL_GetError());
        exit(1);
    }
    TTF_Init();
    TTF_Font *font = TTF_OpenFont("retganon.ttf", 20);
    if (!font) {
        SDL_Log("Font failed to open: %s\n", TTF_GetError());
        exit(1);
    }
    SDL_Texture *texture = IMG_LoadTexture(renderer, "space_invaders_pieces.png");
    if (texture == NULL) {
        SDL_Log("Cannot open source picture: %s", IMG_GetError());
        exit(1);
    }

    SDL_RenderClear(renderer);

    data_sdl->window = window;
    data_sdl->renderer = renderer;
    data_sdl->font = font;
    data_sdl->texture = texture;
}

/* idozito fuggveny az infoc oldalrol */
Uint32 idozit(Uint32 ms, void *param) {
    SDL_Event ev;
    ev.type = SDL_USEREVENT;
    SDL_PushEvent(&ev);
    return ms;   /* ujabb varakozas */
}
