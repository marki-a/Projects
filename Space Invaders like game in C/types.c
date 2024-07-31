#include <SDL2/SDL.h>
#include <SDL2/SDL2_gfxPrimitives.h>
#include <SDL2/SDL_image.h>
#include <SDL2/SDL_ttf.h>
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include "types.h"
#include "debugmalloc.h"

An_alien *set_alien(An_alien *first, int x, int y, int w, int h) {
    An_alien *new_alien = (An_alien*) malloc(1*sizeof(An_alien));
    if (new_alien == NULL) {
        perror("Memory allocation unsuccessful");
    }
    new_alien->alive = true;
    new_alien->pos.x = x;
    new_alien->pos.y = y;
    new_alien->pos.w = w;
    new_alien->pos.h = h;
    new_alien->next = NULL;
    if (first == NULL) {
        return new_alien;
    }
    if (first->next == NULL) {
        first->next = new_alien;
        return first;
    }
    else {
        An_alien *moving = first;
        while (moving->next != NULL) {
            moving = moving->next;
        }
        moving->next = new_alien;
        return first;
    }
}

An_alien *clear_aliens(An_alien *first) {
    An_alien *moving = first;
    An_alien *next_element;
    if (first == NULL) {
        ;
    }
    else {
        while (moving != NULL) {
            next_element = moving->next;
            free(moving);
            moving = next_element;
        }
    }
    return NULL;
}

An_alien *clear_dead_alien(An_alien *first) {
    if (first == NULL) {
        return NULL;
    }
    An_alien *moving = first;
    An_alien *inching;
    while (moving != NULL && moving->alive) {
        inching = moving;
        moving = moving->next;
    }
    if (moving != NULL) {
        if (moving == first) {
            first = moving->next;
        }
        else {
            inching->next = moving->next;
        }
        free(moving);
    }
    return first;
}
