#include "rooms.hpp"
#include "state.hpp"
#include "logger.hpp"

CState::CState(void)
{
    init_rooms();
    this->current_room = 0;
    this->quit = false;
}

void CState::setRoom(Uint8 room)
{
    this->current_room = room;
}

CState&
CState::getInstance(void)
{
    static CState instance;
    return instance;
}

std::string CState::getRoomName(void)
{
    std::string retVal = get_room(current_room).name;
    //log("Room is '"+retVal+"'.");
    return retVal;
}

void CState::updateImage(SDL_Surface *destSurface, SDL_Rect *destRect)
{
	// SDL 1.2 does not support stretching / shrinking / scaling during Blits.
    SDL_BlitSurface(get_room(current_room).image, NULL, destSurface, destRect);
}

bool CState::accept(std::vector<std::string> tokens)
{
    // TODO. Especially the movement.
    // N, O, S, W, HOCH, RUNT
    return false;
}

bool CState::quitGame(void)
{
    return this->quit;
}

void CState::quitGame(bool quit)
{
    this->quit = quit;
}
