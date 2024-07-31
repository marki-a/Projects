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

/* az infoc oldalrol vett fuggveny
 * kirajzol egy babut; a forras a betoltott png, a cel nevu kepre rajzol.
 * melyik babut, milyen koordinatakra */
void draw_piece(Data_sdl *data_sdl, Piece which, int x, int y, int w, int h) {
    SDL_Rect src = { (which % 3) * 161, (which / 3) * 50, w, h };
    SDL_Rect dest = { x, y, w, h };
    SDL_RenderCopy(data_sdl->renderer, data_sdl->texture, &src, &dest);
}

/* az infoc oldalrol vett fuggveny
 * kiir egy sztringet a megadott szinben a megadott fonttal az x es y koordinataju helyre */
void write_text(char *text, Data_sdl *data_sdl, int x, int y) {
    SDL_Surface *felirat;
    SDL_Texture *felirat_t;
    SDL_Rect where = { 0, 0, 0, 0 };

    felirat = TTF_RenderUTF8_Solid(data_sdl->font, text, data_sdl->color.green);
    felirat_t = SDL_CreateTextureFromSurface(data_sdl->renderer, felirat);
    where.x = x;
    where.y = y;
    where.w = felirat->w;
    where.h = felirat->h;
    SDL_RenderCopy(data_sdl->renderer, felirat_t, NULL, &where);
    SDL_FreeSurface(felirat);
    SDL_DestroyTexture(felirat_t);

}

/* kirajzolja a menu elemeit a kijelzore (jatekos neve, gombok) */
void draw_menu(Data_sdl *data_sdl, char *text, Game *game) {
        SDL_RenderClear(data_sdl->renderer);
        draw_piece(data_sdl, Newgame, 90, 150, BT_W, BT_H);
        draw_piece(data_sdl, Scoreboard, 90, 210, BT_W, BT_H);
        write_text(text, data_sdl, 10, 10);
        SDL_RenderPresent(data_sdl->renderer);
}

/* kirajzolja a nehezseg valaszto menu gombjait */
void draw_difficulty_select(Data_sdl *data_sdl) {
        SDL_RenderClear(data_sdl->renderer);
        draw_piece(data_sdl, Easy, 90, 120, BT_W, BT_H);
        draw_piece(data_sdl, Normal, 90, 180, BT_W, BT_H);
        draw_piece(data_sdl, Hard, 90, 240, BT_W, BT_H);
        SDL_RenderPresent(data_sdl->renderer);
}

/* Az infoc oldalarol vett fuggveny:
 * Beolvas egy szoveget a billentyuzetrol.
 * A rajzolashoz hasznalt font es a megjelenito az utolso parameterben.
 * Az elso a tomb, ahova a beolvasott szoveg kerul.
 * A masodik a maximális hossz, ami beolvasható.
 * A visszateresi erteke logikai igaz, ha sikerult a beolvasas. */
bool input_text(char *dest, size_t hossz, SDL_Rect rect, Data_sdl *data_sdl) {
    /* Ez tartalmazza az aktualis szerkesztest */
    char composition[SDL_TEXTEDITINGEVENT_TEXT_SIZE];
    composition[0] = '\0';
    /* Ezt a kirajzolas kozben hasznaljuk */
    char textandcomposition[hossz + SDL_TEXTEDITINGEVENT_TEXT_SIZE + 1];
    /* Max hasznalhato szelesseg */
    int maxw = rect.w - 5;
    int maxh = rect.h - 5;

    dest[0] = '\0';

    bool enter = false;
    bool kilep = false;

    SDL_StartTextInput();
    while (!kilep && !enter) {
        /* doboz kirajzolasa */
        boxRGBA(data_sdl->renderer, rect.x - 2, rect.y - 2, rect.x + rect.w - 1, rect.y + rect.h - 1, data_sdl->color.black.r, data_sdl->color.black.g, data_sdl->color.black.b, data_sdl->color.black.a);
        rectangleRGBA(data_sdl->renderer, rect.x - 2, rect.y - 2, rect.x + rect.w - 1, rect.y + rect.h - 1, data_sdl->color.green.r, data_sdl->color.green.g, data_sdl->color.green.b, data_sdl->color.green.a);
        /* szoveg kirajzolasa */
        int w;
        strcpy(textandcomposition, dest);
        strcat(textandcomposition, composition);
        if (textandcomposition[0] != '\0') {
            SDL_Surface *felirat = TTF_RenderUTF8_Blended(data_sdl->font, textandcomposition, data_sdl->color.green);
            SDL_Texture *felirat_t = SDL_CreateTextureFromSurface(data_sdl->renderer, felirat);
            SDL_Rect cel = { rect.x, rect.y, felirat->w < maxw ? felirat->w : maxw, felirat->h < maxh ? felirat->h : maxh };
            SDL_RenderCopy(data_sdl->renderer, felirat_t, NULL, &cel);
            SDL_FreeSurface(felirat);
            SDL_DestroyTexture(felirat_t);
            w = cel.w;
        } else {
            w = 0;
        }
        /* kurzor kirajzolasa */
        if (w < maxw) {
            vlineRGBA(data_sdl->renderer, rect.x + w + 1, rect.y + 1, rect.y + rect.h - 5, data_sdl->color.green.r, data_sdl->color.green.g, data_sdl->color.green.b, 192);
        }
        /* megjeleniti a képernyon az eddig rajzoltakat */
        SDL_RenderPresent(data_sdl->renderer);

        SDL_Event event;
        SDL_WaitEvent(&event);
        switch (event.type) {
            /* Kulonleges karakter */
            case SDL_KEYDOWN:
                if (event.key.keysym.sym == SDLK_BACKSPACE) {
                    int textlen = strlen(dest);
                    do {
                        if (textlen == 0) {
                            break;
                        }
                        if ((dest[textlen-1] & 0x80) == 0x00)   {
                            /* Egy bajt */
                            dest[textlen-1] = 0x00;
                            break;
                        }
                        if ((dest[textlen-1] & 0xC0) == 0x80) {
                            /* Bajt, egy tobb-bajtos szekvenciabol */
                            dest[textlen-1] = 0x00;
                            textlen--;
                        }
                        if ((dest[textlen-1] & 0xC0) == 0xC0) {
                            /* Egy tobb-bajtos szekvencia elso bajtja */
                            dest[textlen-1] = 0x00;
                            break;
                        }
                    } while(true);
                }
                if (event.key.keysym.sym == SDLK_RETURN) {
                    enter = true;
                }
                break;

            /* A feldolgozott szoveg bemenete */
            case SDL_TEXTINPUT:
                if (strlen(dest) + strlen(event.text.text) < hossz) {
                    strcat(dest, event.text.text);
                }

                /* Az eddigi szerkesztes torolheto */
                composition[0] = '\0';
                break;

            /* Szoveg szerkesztese */
            case SDL_TEXTEDITING:
                strcpy(composition, event.edit.text);
                break;

            case SDL_QUIT:
                /* visszatesszuk a sorba ezt az eventet, mert
                 * sok mindent nem tudunk vele kezdeni */
                SDL_PushEvent(&event);
                kilep = true;
                break;
        }
    }

    /* igaz jelzi a helyes beolvasast; = ha enter miatt allt meg a ciklus */
    SDL_StopTextInput();
    return enter;
}

/* kivagja a spaceket egy sztringbol
 * a text_input fv-hez kell, hogy a file-ba mentes megfelelo formatumu legyen */
void cut_spaces(char *text) {
    int to = 0;
    for (int from = 0; text[from] != '\0'; ++from) {
        if (text[from] != ' ') {
            text[to] = text[from];
            to++;
        }
    }
    text[to] = '\0';
}
