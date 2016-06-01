#include "rooms.hpp"

static const Uint8 NUM_ROOMS = 27;
static room rooms[NUM_ROOMS];

void init_rooms(void)
{
    rooms[0].name = std::string("(c) 2016 Hangman Cracking Crew"); /* Title picture */
    rooms[0].image = SDL_LoadBMP("data/room00.bmp");

    rooms[1].name = std::string("Christians Zimmer");
    rooms[1].image = SDL_LoadBMP("data/room01.bmp");
    rooms[1].exits.east = 2;

    rooms[2].name = std::string("Weiss der Geier");
    rooms[2].image = SDL_LoadBMP("data/room02.bmp");
    rooms[2].exits.west = 1;
    rooms[2].exits.east = 3;

    rooms[3].name = std::string("An der Kebap-Bude");
    rooms[3].image = SDL_LoadBMP("data/room03.bmp");
    rooms[3].exits.west = 2;
    rooms[3].exits.east = 4;
    rooms[3].exits.south = 8;

    rooms[4].name = std::string("Auf'm Kotti");
    rooms[4].image = SDL_LoadBMP("data/room04.bmp");
    rooms[4].exits.west = 3;
    rooms[4].exits.east = 5;
    rooms[4].exits.south = 9;

    rooms[5].name = std::string("Zu Hause beim Atari-User");
    rooms[5].image = SDL_LoadBMP("data/room05.bmp");
    rooms[5].exits.west = 4;

    rooms[6].name = std::string("Spielhalle");
    rooms[6].image = SDL_LoadBMP("data/room06.bmp");
    rooms[6].exits.east = 7;
    rooms[6].exits.south = 11;

    rooms[7].name = std::string("Zu Hause vor der Tuer");
    rooms[7].image = SDL_LoadBMP("data/room07.bmp");
    rooms[7].exits.west = 6;
    rooms[7].exits.east = 8;
    rooms[7].exits.south = 12;

    rooms[8].name = std::string("Anti-Wumpus-Mahnwache");
    rooms[8].image = SDL_LoadBMP("data/room08.bmp");
    rooms[8].exits.north = 3;
    rooms[8].exits.west = 7;
    rooms[8].exits.east = 9;
    rooms[8].exits.south = 13;

    rooms[9].name = std::string("S-Bahnhof Feuerbachstrasse");
    rooms[9].image = SDL_LoadBMP("data/room09.bmp");
    rooms[9].exits.north = 4;
    rooms[9].exits.west = 8;
    rooms[9].exits.east = 10;
    /* use train to get to room 14 */

    rooms[10].name = std::string("Revier der Atari-Gang");
    rooms[10].image = SDL_LoadBMP("data/room10.bmp");
    /* No exit here */

    rooms[11].name = std::string("Motorradschrauber");
    rooms[11].image = SDL_LoadBMP("data/room11.bmp");
    rooms[11].exits.north = 6;
    rooms[11].exits.south = 16;

    rooms[12].name = std::string("Zu Hause im Zimmer");
    rooms[12].image = SDL_LoadBMP("data/room12.bmp");
    rooms[12].exits.north = 7;

    rooms[13].name = std::string("Wumpus-Platz");
    rooms[13].image = SDL_LoadBMP("data/room13.bmp");
    rooms[13].exits.north = 8;
    rooms[13].exits.south = 18;

    rooms[14].name = std::string("S-Bahnhof Anhalter Bahnhof");
    rooms[14].image = SDL_LoadBMP("data/room14.bmp");
    rooms[14].exits.south = 19;
    /* use train to get to room 9 */

    rooms[15].name = std::string("Berliner Mauer, das eine Stueck");
    rooms[15].image = SDL_LoadBMP("data/room15.bmp");
    rooms[15].exits.south = 20;

    rooms[16].name = std::string("Strasse mit Graffitti");
    rooms[16].image = SDL_LoadBMP("data/room16.bmp");
    rooms[16].exits.north = 11;
    rooms[16].exits.east = 17;
    rooms[16].exits.south = 21;

    rooms[17].name = std::string("Saarstrasse");
    rooms[17].image = SDL_LoadBMP("data/room17.bmp");
    rooms[17].exits.west = 16;
    rooms[17].exits.east = 18;
    rooms[17].exits.south = 22;

    rooms[18].name = std::string("Karstadt, Haupteingang");
    rooms[18].image = SDL_LoadBMP("data/room18.bmp");
    rooms[18].exits.north = 13;
    rooms[18].exits.west = 17;
    rooms[18].exits.up = 23;

    rooms[19].name = std::string("Elektronik von A-Z");
    rooms[19].image = SDL_LoadBMP("data/room19.bmp");
    rooms[19].exits.north = 14;
    rooms[19].exits.east = 20;

    rooms[20].name = std::string("Berliner Mauer, das andere Stueck");
    rooms[20].image = SDL_LoadBMP("data/room20.bmp");
    rooms[20].exits.west = 19;
    rooms[20].exits.north = 15;

    rooms[21].name = std::string("Jannes Drugstore");
    rooms[21].image = SDL_LoadBMP("data/room21.bmp");
    rooms[21].exits.north = 16;

    rooms[22].name = std::string("HCC Labs, Kopierschutzentfernung par excellence");
    rooms[22].image = SDL_LoadBMP("data/room22.bmp");
    rooms[22].exits.north = 17;

    rooms[23].name = std::string("Karstadt, Computerabteilung");
    rooms[23].image = SDL_LoadBMP("data/room23.bmp");
    rooms[23].exits.down = 18;

    rooms[24].name = std::string("Auf den Wolken");
    rooms[24].image = SDL_LoadBMP("data/room24.bmp");
    rooms[24].exits.east = 25;

    rooms[25].name = std::string("Im Farbenmeer");
    rooms[25].image = SDL_LoadBMP("data/room25.bmp");
    rooms[25].exits.west = 24;

    rooms[26].name = std::string("Arrivederci, mach's jut und tschuess.");
    rooms[26].image = SDL_LoadBMP("data/room26.bmp");
}

room get_room(Uint8 number)
{
    return rooms[number];
}
