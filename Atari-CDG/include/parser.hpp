#ifndef PARSER_HPP_
#define PARSER_HPP_

#include <string>
#include "textscreen.hpp"

class CParser {
private:
    CParser(void);
    CParser(const CParser&); /* make copy c'tor private */

public:
    static CParser& getInstance(void);
    void parse(std::string input, CTextScreen& textScreen);
};

#endif /*PARSER_HPP_*/
