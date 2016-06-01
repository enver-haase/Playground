#ifndef STATE_HPP_
#define STATE_HPP_

#include <SDL.h>
#include <vector>
#include <string>

class CState{
    private:
    CState(void);
    CState( const CState& ); /* make copy c'tor private */
    Uint8 current_room;
    bool quit;

    public:
    static CState& getInstance(void);
    std::string getRoomName(void);
    void setRoom(Uint8 room);
    void updateImage(SDL_Surface *destSurface, SDL_Rect *destRect);
    /* Try to handle textual input locally, in the context of a room
     * or more generally, the current state */
    bool accept(std::vector<std::string> tokens);
    bool quitGame(void);
    void quitGame(bool quit);
};

#endif /*STATE_HPP_*/
