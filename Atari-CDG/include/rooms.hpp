#ifndef ROOMS_HPP_
#define ROOMS_HPP_

#include <string>
#include <vector>
#include <SDL.h>

#include "thing.hpp"

const unsigned int NO_EXIT = 0xff;

class Exits {
public:
    Uint8 north;
    Uint8 south;
    Uint8 west;
    Uint8 east;
    Uint8 down;
    Uint8 up;
    Exits()
    {
        north = south = west = east = down = up = NO_EXIT;
    }
};

typedef struct {
    std::string name;
    SDL_Surface *image;
    Exits exits;
    std::vector<CThing> things;
} room;

void init_rooms(void);
room get_room(Uint8 number);

#endif /*ROOMS_HPP_*/
