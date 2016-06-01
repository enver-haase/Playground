/*
 * Atari - Computer des Grauens
 *
 * (c) 1987 - 2016 Hangman Cracking Crew
 *
 */

#include <stdlib.h>
#include <SDL.h>
#include <string>

#ifdef __EMSCRIPTEN__
#include <emscripten.h>
#endif

#include "logger.hpp"
#include "textscreen.hpp"
#include "state.hpp"
#include "parser.hpp"

#include "colors.hpp"

#define GAME_NAME ("Atari - Computer des Grauens")

SDL_Rect *imageRect;
SDL_Surface *screen;
CTextScreen *textScreen;
CTextScreen *inputScreen;

bool cursorOn = false;
Uint32 lastBlink = 0;

void updateScreen()
{

    SDL_FillRect(screen, NULL, SDL_MapRGB(screen->format, BLUE >> 24, BLUE >> 16, BLUE >> 8));
    CState::getInstance().updateImage(screen, imageRect);
    textScreen->update();
    inputScreen->update();
    Uint32 now = SDL_GetTicks() / 500;
    if (now != lastBlink) {
        lastBlink = now;
        inputScreen->toggleCursor();
    }
    SDL_UpdateRect(screen, 0, 0, 0, 0); /* event probably caused something to change on screen */

    /* show the room name */
    SDL_WM_SetCaption((std::string(GAME_NAME) + std::string(": ") + CState::getInstance().getRoomName()).c_str(), NULL);
}

void oneIteration()
{
    updateScreen();

    if (!CState::getInstance().quitGame()) {

        SDL_Event event;
        static std::string inputLine;
        int eventPending = SDL_PollEvent(&event);
        if (eventPending) {
            switch (event.type) {
            // If we
            // get an event telling us that the escape key has
            // been pressed the program will quit.
            case SDL_KEYDOWN: {
                switch (event.key.keysym.sym) {
                case SDLK_ESCAPE: {
                    CState::getInstance().quitGame(true);
                    break;
                }
                case SDLK_BACKSPACE: {
                    if (inputLine.length() > 0) {
                        inputLine = inputLine.substr(0, inputLine.length() - 1);
                    }
                    break;
                }
                case SDLK_RETURN: {
                    if (inputLine.length() > 0) {
                        textScreen->print("\n> " + inputLine + "\n\n");
                        CParser::getInstance().parse(inputLine, *textScreen);
                    }
                    inputLine.clear();
                    break;
                }
                default: {
                    const unsigned int maxLineLength = 77;
                    unsigned char c = event.key.keysym.sym;
                    if (c == 0xdf)/* sz-ligature */
                    {
                        std::string s = "SS";
                        if (inputLine.length() + s.length() <= maxLineLength) {
                            inputLine += s;
                        }
                        break;
                    }
                    // toUpperCase below:
                    if ((c >= 'a') && (c <= 'z')) {
                        c -= 32;
                    } else if (c == 0xe4)
                        c = 0x8e;
                    else if (c == 0xf6)
                        c = 0x99;
                    else if (c == 0xfc)
                        c = 0x9a;
                    else if (c != ' ')
                        // SPACE
                        break; /* not one of the keys we accept: leave. */

                    if (inputLine.length() + 1 <= maxLineLength) {
                        inputLine += c;
                    }
                    break;
                }
                }
                inputScreen->print("\n> " + inputLine);
                break;
            }
            case SDL_USEREVENT: {
                switch (event.user.code) {

                //case CODE_TEXTSCREEN_CURSOR_BLINK: {
                //CTextScreen* ts = static_cast<CTextScreen*>(event.user.data1);
                //ts->handleEvent(event);
                //break;
                //}

                default: {
                    break;
                }

                }
                break;
            }
                // The SDL_QUIT event is generated when you click
                // on the close button on a window. If we see that
                // event we should exit the program. So, we do.
            case SDL_QUIT: {
                CState::getInstance().quitGame(true);
                break;
            }
            }
        }
    }

    else {
#ifndef __EMSCRIPTEN__
        SDL_Delay(300);
#endif
    }
}

#ifdef __WIN32__
#include <windows.h>
int WINAPI WinMain(
        HINSTANCE hInstance,
        HINSTANCE hPrevInstance,
        LPSTR lpCmdLine,
        int nCmdShow
)
#endif
#ifdef __EMSCRIPTEN__
int main(int argc, char *argv[])
#endif
#ifdef __LINUX__
int main(int argc, char *argv[])
#endif
#if defined(__APPLE__) && defined(__MACH__)
int main(int argc, char *argv[])
#endif
#ifdef __FREEBSD__
int main(int argc, char *argv[])
#endif
{

    //#ifdef __linux__
    //    putenv(const_cast<char *>("SDL_VIDEODRIVER=x11")); /* dga, fbcon */
    //#endif

    if (SDL_Init(SDL_INIT_AUDIO | SDL_INIT_VIDEO | SDL_INIT_TIMER) < 0) {
        abort("Unable to init SDL: " + std::string(SDL_GetError()));
    } else {
        atexit(SDL_Quit);
    }

    static const Uint16 screen_width = 800;
    static const Uint16 screen_height = 600;
    screen = SDL_SetVideoMode(screen_width, screen_height, 0, SDL_ANYFORMAT | /*SDL_FULLSCREEN |*/SDL_HWSURFACE | SDL_DOUBLEBUF);
    //screen = SDL_SetVideoMode(screen_width, screen_height, 32, SDL_SWSURFACE | SDL_FULLSCREEN ); // For some crazy reason crashes when run from within Eclipse on OSX when not fullscreen
    if (screen == NULL) {
        fprintf(stderr, "Unable to set 640x480 video: %s\n", SDL_GetError());
        exit(1);
    }

    // Here used to be SDL_SetCaption but this is done every refresh anyway.

    imageRect = new SDL_Rect();
    imageRect->w = 600;
    imageRect->h = 400;
    imageRect->x = (screen_width - imageRect->w) / 2;
    imageRect->y = 3*8;

    textScreen = new CTextScreen(screen, 0, screen_height - 16 * 8, 100 /* cols */, 15);

    textScreen->print("\n\n\n");
    textScreen->printJustified("ATARI - Computer des Grauens.\nCopyright (c) 1987-2016 Enver Haase, Christian Bessert, Dieter Beck.\n");
    textScreen->print("\n\n\n\n\n");
    textScreen->print("Zum Start des Spiels START eingeben.\n\n");
    inputScreen = new CTextScreen(screen, 0, screen_height - 2 * 8, 100, 1);
    inputScreen->print("> ");

#ifdef __EMSCRIPTEN__
    emscripten_set_main_loop(oneIteration, 0, 1); /* 0 frames means requestAnimationFrame */
#else
    // Loop while reading all pending events.
    while (true) {
        if (!CState::getInstance().quitGame()) {
            oneIteration();
            SDL_Delay(20);
        } else {
            oneIteration();
            SDL_Delay(2000);
            goto exit;
        }
    }
exit:
#endif
    return 0;
}
