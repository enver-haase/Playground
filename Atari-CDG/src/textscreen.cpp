#include <SDL.h>
#include "textscreen.hpp"
#include "biosfont.hpp"
#include "logger.hpp"

void CTextScreen::toggleCursor()
{
    {
        SDL_Surface *surface = this->getSurface();

        SDL_LockSurface(surface);
        Uint32 *pixels = static_cast<Uint32 *>(surface->pixels);
        for (Uint8 charX = 0; charX < 8; charX++) {
            for (Uint8 charY = 0; charY < 8; charY++) {
                Uint32 *targetPixel = pixels + (cursor_y * columns * 8 * 8 + charY * columns * 8 + cursor_x * 8 + charX);
                *targetPixel = (~(*targetPixel) | 0xff);
            }
        }
        SDL_UnlockSurface(surface);

        update();
    }
}

SDL_Surface *
CTextScreen::getSurface(void)
{
    return surface;
}

CTextScreen::CTextScreen(SDL_Surface *target, Uint16 target_x, Uint16 target_y, Uint16 columns, Uint16 lines)
{
    /* Font is 8*8 pixels per character */
    surface = SDL_CreateRGBSurface(SDL_SWSURFACE, columns * 8, lines * 8, 32, 0xff << 24, 0xff << 16, 0xff << 8, 0xff);
    log_assert(surface != NULL, "Could not create surface.");
    SDL_LockSurface(this->surface);
    SDL_Rect rect;
    rect.x = 0;
    rect.y = 0;
    rect.w = 8 * columns;
    rect.h = 8 * lines;
    SDL_UnlockSurface(this->surface);
    SDL_FillRect(this->surface, &rect, SDL_MapRGB(this->surface->format, BLUE>>24, BLUE>>16, BLUE>>8));


    target_surface = target;
    this->target_x = target_x;
    this->target_y = target_y;

    this->columns = columns;
    this->lines = lines;
    setCursorPos(0, 0);
}

CTextScreen::~CTextScreen(void)
{
    SDL_FreeSurface(surface);
}

void CTextScreen::getCursorPos(Uint8 *x, Uint8 *y)
{
    if (x)
        *x = this->cursor_x;
    if (y)
        *y = this->cursor_y;
}

void CTextScreen::setCursorPos(Uint8 x, Uint8 y)
{
    if (x < this->columns)
        this->cursor_x = x;
    else
        this->cursor_x = columns - 1;

    if (y < this->lines)
        this->cursor_y = y;
    else
        this->cursor_y = lines - 1;
}

void CTextScreen::printJustified(std::string text)
{
    //log(text);
    text = justifyLeft(text);
    //log(text);
    print(text);
}

void CTextScreen::print(std::string text)
{
    Uint32 *pixels = static_cast<Uint32 *>(surface->pixels);
    const char *str = text.c_str();
    for (Uint32 i = 0; i < text.length(); i++) {
        unsigned char c = str[i];
        log_assert(c != 195, "WHOA - UTF8 encoding? Umlaut as Utf8?");

        /* Iso-8859-1 umlauts in the input, but graphics are in a CodePage437 BIOS font */
        if (c == 0xe4)      // a-umlaut
            c = 0x84;
        else if (c == 0xf6) // o-umlaut
            c = 0x94;
        else if (c == 0xfc) // u-umlaut
            c = 0x81;
        else if (c == 0xc4) // A-umlaut
            c = 0x8e;
        else if (c == 0xd6) // O-umlaut
            c = 0x99;
        else if (c == 0xdc) // U-umlaut
            c = 0x9a;
        else if (c == 0xdf) // sz-ligature
            c = 0xe1;

        if (c == '\n') {
            cursor_x = 0;
            if (cursor_y < lines - 1) {
                cursor_y++;
            } else {
                scroll();
            }
            continue;
        }

        SDL_LockSurface(this->surface);
        for (Uint8 charX = 0; charX < 8; charX++) {
            for (Uint8 charY = 0; charY < 8; charY++) {
                Uint32 *targetPixel = pixels + (cursor_y * columns * 8 * 8 + charY * columns * 8 + cursor_x * 8 + charX);
                if (BIOSfont::fontbit(c, charX, charY))
                {
                    *targetPixel = WHITE;
                }
                else
                {
                    *targetPixel = BLUE;
                }
            }
        }
        SDL_UnlockSurface(this->surface);

        if (++cursor_x == columns) {
            cursor_x = 0;
            if (++cursor_y == lines) {
                cursor_y = lines - 1;
                scroll();
            }
        }
    }

    update();
}

void CTextScreen::scroll(void)
{
    SDL_LockSurface(this->surface);
    Uint32 *pixels = static_cast<Uint32 *>(this->surface->pixels);
    for (int i = 0; i < columns * 8 * (lines - 1) * 8; i++) {
        pixels[i] = pixels[i + columns * 8 * 8];
    }
    SDL_UnlockSurface(this->surface);

    SDL_Rect rect;
    rect.x = 0;
    rect.y = (lines - 1) * 8;
    rect.w = 8 * columns;
    rect.h = 8;
    SDL_FillRect(this->surface, &rect, SDL_MapRGB(this->surface->format, BLUE>>24, BLUE>>16, BLUE>>8));
    update();
}

std::vector<std::string> CTextScreen::tokenize(std::string str, std::string delimiters)
{
    // Skip delimiters at beginning.
    std::string::size_type lastPos = str.find_first_not_of(delimiters, 0);
    // Find first "non-delimiter".
    std::string::size_type pos = str.find_first_of(delimiters, lastPos);

    std::vector<std::string> tokens;

    while (std::string::npos != pos || std::string::npos != lastPos) {
        // Found a token, add it to the vector.
        tokens.push_back(str.substr(lastPos, pos - lastPos));

        // Skip delimiters.  Note the "not_of"
        lastPos = str.find_first_not_of(delimiters, pos);
        // Find next "non-delimiter"
        pos = str.find_first_of(delimiters, lastPos);
    }

    return tokens;
}

std::string CTextScreen::justifyLeft(std::string str)
{
    Uint8 xpos = this->cursor_x;

    std::string out;
    std::vector<std::string> lines = tokenize(str, "\n");
    for (unsigned int i = 0; i < lines.size(); i++) {
        std::string line = lines[i];
        //log(line);
        std::vector<std::string> words = tokenize(line, " ");
        for (unsigned int j = 0; j < words.size(); j++) {
            std::string word = words[j];
            if (xpos + word.length() <= this->columns) // if the word fits on the current line
                    {
                xpos += word.length();
                out += word;
            } else {
                xpos = word.length();
                out += '\n' + word;
            }
            if ((xpos != 0) && (xpos != columns - 1) && (j != words.size() - 1)) // not beginning of real line, not end of real line, not last word in 'virtual' line:
                    {
                xpos++;
                out += " "; // add a space!
            }
        }
        out += "\n";
        xpos = 0;
    }

    return out;
}

void CTextScreen::update(void)
{
    SDL_Rect rect;
    rect.x = target_x;
    rect.y = target_y;
    SDL_BlitSurface(this->surface, NULL, this->target_surface, &rect);
}
