#include <vector>

#ifdef __EMSCRIPTEN__
#include <emscripten.h>
#endif

#include "parser.hpp"
#include "logger.hpp"
#include "state.hpp"

CParser::CParser(void)
{

}

CParser&
CParser::getInstance(void)
{
    static CParser instance;
    return instance;
}

void CParser::parse(std::string str, CTextScreen& textScreen)
{
    log("Parser: parsing '" + str + "'.");
    log(str.length());

    std::string delimiters = " ";
    // Skip delimiters at beginning.
    std::string::size_type lastPos = str.find_first_not_of(delimiters, 0);
    // Find first "non-delimiter".
    std::string::size_type pos = str.find_first_of(delimiters, lastPos);

    std::vector<std::string> tokens;

    while (std::string::npos != pos || std::string::npos != lastPos) {
        std::string token = str.substr(lastPos, pos - lastPos);
        token = token.substr(0, 4); // four-letters are enough to recognize the words

        // Found a token, add it to the vector.
        if ((token != "UND") && (token != "MIT") && (token != "AUF") && (token != "DEN") && (token != "DEM")) {
            tokens.push_back(token);
        }

        // Skip delimiters.  Note the "not_of"
        lastPos = str.find_first_not_of(delimiters, pos);
        // Find next "non-delimiter"
        pos = str.find_first_of(delimiters, lastPos);

    }
    for (unsigned int i = 0; i < tokens.size(); i++) {
        log(tokens[i]);
    }

    bool handledByState = CState::getInstance().accept(tokens);

    if (!handledByState) {
        if (tokens.size() > 0) {
            /* Default handling */
            if (tokens[0] == "HELP" || tokens[0] == "HILF") {
                textScreen.printJustified("Folgende Befehle sind in diesem Spiel erlaubt: HILFE N S O W RUNTER RAUF AUFGEBEN\n");
            } else if (tokens[0] == "AUFG") {
                textScreen.printJustified("Wirklich aufgeben (antworte WIRKLICH AUFGEBEN)?\n");
            } else if (tokens[0] == "WIRK" && tokens.size() > 1 && tokens[1] == "AUFG") {
                textScreen.printJustified("Und tschuess.\n");
                CState::getInstance().setRoom(26); // TODO: remove magic number.
                CState::getInstance().quitGame(true);
#ifdef __EMSCRIPTEN__
                EM_ASM("document.dispatchEvent(new CustomEvent('emscripten_done', { 'returnCode': 'false'  }));");
#endif
            } else if (tokens[0] == "STAR") {
                CState::getInstance().setRoom(2);
            } else {
                textScreen.printJustified("Das verstehe ich jetzt nicht.");
            }
        }
    }
}
