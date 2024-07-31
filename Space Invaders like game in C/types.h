#ifndef TYPES_H_INCLUDED
#define TYPES_H_INCLUDED

typedef struct Projectile {
    SDL_Rect pos;
    int def_y;
    bool shot;
} Projectile;

typedef struct Player {
    char p_name[11];
    int p_score;
    bool left_key_down, right_key_down, space_down;
    int def_ship_x;
    SDL_Rect ship;
    bool alive;
    Projectile p_laser;
    char final_score[5];
} Player;

typedef struct An_alien {
    SDL_Rect pos;
    bool alive;
    struct An_alien *next;
} An_alien;

typedef struct Alien_swarm {
    An_alien *alien;
    int column_a;               /*                                          */
    int row_a;                  /*  mennyi alien kerul majd fel a palyara   */
    bool created;
    int aliens_alive;
    int aliens_move;
    int dir;
    int v;
    int back_n_forth;
    int aliens_shoot;
    int which_shoots;
    Projectile a_laser1;
    Projectile a_laser2;
    bool a_win;
} Alien_swarm;

typedef struct Game {
    int state;
    int v;
    int score_mult;
    bool have_final_score;
    bool score_compared;
    bool turntables;
} Game;

typedef enum Piece {
    Spaceship, Newgame, Easy, Alien, Scoreboard, Normal, Laser, Menu, Hard
} Piece;

enum { WINDOW_X = 340 };
enum { WINDOW_Y = 450 };

enum { SIZE_SHIP = 46 };
enum { SIZE_ALIEN = 22 };
enum { LASER_W = 4 };       enum { LASER_H = 10 };
enum { BT_W = 160 };        enum { BT_H = 48 };

enum { MULT_EASY = 1 };
enum { MULT_NORMAL = 2 };
enum { MULT_HARD = 3 };

enum { V_EASY = 2 };
enum { V_NORMAL = 3 };
enum { V_HARD = 5 };
enum { V_LASER = 8 };

An_alien *set_alien(An_alien *first, int x, int y, int w, int h);

An_alien *clear_aliens(An_alien *first);

An_alien *clear_dead_alien(An_alien *first);

#endif // TYPES_H_INCLUDED
