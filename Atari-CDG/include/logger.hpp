#ifndef LOGGER_HPP_
#define LOGGER_HPP_

#include <string>
#include <iostream>

void
log(int number);

void
log(std::string message);

void
abort(std::string message);

void
log_assert(bool condition, std::string problem);

#endif /*LOGGER_HPP_*/
