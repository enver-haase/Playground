
#include "picscreen.hpp"

CPicScreen::CPicScreen(std::string name)
{
    /* Most pics are 768 x 512, let's shrink them using a factor of 2/3 -- yields 512*341 */
    surface = SDL_CreateRGBSurface(SDL_SWSURFACE, 512, 341, 32, 0xff<<24, 0xff<<16, 0xff<<8, 0xff);
}


CPicScreen::~CPicScreen(void)
{
    SDL_FreeSurface(surface);
}
