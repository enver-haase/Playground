#ifndef PICSCREEN_HPP_
#define PICSCREEN_HPP_

#include <string>
#include <SDL.h>

class CPicScreen{
    public:
        CPicScreen(std::string filename);
        ~CPicScreen(void);
    private:
        SDL_Surface    *surface; 
};

#endif /*PICSCREEN_HPP_*/
