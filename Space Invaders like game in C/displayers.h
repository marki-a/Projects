#ifndef DISPLAYERS_H_INCLUDED
#define DISPLAYERS_H_INCLUDED

void draw_piece(Data_sdl *data_sdl, Piece which, int x, int y, int sizex, int sizey);

void write_text(char *text, Data_sdl *data_sdl, int x, int y);

void draw_menu(Data_sdl *data_sdl, char *text, Game *Game);

void draw_difficulty_select(Data_sdl *data_sdl);

bool input_text(char *dest, size_t hossz, SDL_Rect rect, Data_sdl *data_sdl);

void cut_spaces(char *text);

#endif // DISPLAYERS_H_INCLUDED
