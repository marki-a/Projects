#ifndef SDL_INIT_H_INCLUDED
#define SDL_INIT_H_INCLUDED

typedef struct Colors {
    SDL_Color green;
    SDL_Color black;
} Colors;

typedef struct Data_sdl {
    SDL_Window *window;
    SDL_Renderer *renderer;
    TTF_Font *font;
    SDL_Texture *texture;
    SDL_Event *event;
    Colors color;
    int r;
    int g;
    int b;
    int dir_rainb;
} Data_sdl;

void sdl_init(int width, int height, Data_sdl *data_sdl);

Uint32 idozit(Uint32 ms, void *param);

#endif // SDL_INIT_H_INCLUDED
