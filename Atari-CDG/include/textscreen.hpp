#ifndef TEXTSCREEN_HPP_
#define TEXTSCREEN_HPP_

#include <string>
#include <vector>
#include <SDL.h>
#include "colors.hpp"

class CTextScreen {
public:
    /**
     * Constructs a CTextScreen.
     */
    CTextScreen(SDL_Surface *target, Uint16 target_x, Uint16 target_y, Uint16 columns, Uint16 lines);
    /**
     * Destructor.
     */
    ~CTextScreen(void);
    void getCursorPos(Uint8 *x, Uint8 *y);
    void setCursorPos(Uint8 x, Uint8 y);
    void print(std::string text);
    void printJustified(std::string text);
    void toggleCursor();
    void update(void);

private:
    SDL_Surface *getSurface(void); /* TODO: let's see if we can remove it */
    SDL_Surface *target_surface;
    Uint16 target_x;
    Uint16 target_y;
    SDL_Surface *surface;
    Uint8 cursor_x;
    Uint8 cursor_y;
    Uint8 columns;
    Uint8 lines;
    void scroll(void);

    /**
     * This method converts the string to be used with print(std::string)
     * into lines, i.e. if a word does not fit onto the end of the line,
     * then spaces are added so that the word will later be printed in
     * the beginning of the following line.
     * Line feeds are not really taken into account here: this is handled
     * by the print(std::string) method itself.
     */
    std::string justifyLeft(std::string text);
    std::vector<std::string> tokenize(std::string str, std::string delimiters);
};

#endif /*TEXTSCREEN_HPP_*/
