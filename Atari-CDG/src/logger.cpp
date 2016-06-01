
#include "logger.hpp"

#include <stdlib.h>
#include <iostream>

void
log (int number)
{
    std::cout << number << std::endl;
}

void
log (std::string message)
{
    std::cout << message << std::endl;
}

void
abort (std::string message)
{
    std::cerr << message << std::endl;
    exit(1);
}

void
log_assert(bool condition, std::string problem)
{
    if (!condition)
        abort(problem);
}
