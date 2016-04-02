#include <stdio.h>
#include <unistd.h>

#ifdef __EMSCRIPTEN__
#include <emscripten.h>
#endif

int main(int argc, char* argv[])
{
    printf("Hello World!\n");

#ifdef __EMSCRIPTEN__

    printf("Emscripten version active\n");
    sleep(2);

//    emscripten_run_script("alert('hi')");

    EM_ASM(" \
    try {\
    var event = new CustomEvent('gamewon', { 'detail': 'The game was WON!' });\
\
    document.dispatchEvent(event); \
    } catch (ex) { \
      alert(ex); \
    } \
    alert('Event dispatched.'); ");
    
#endif

    return 0;
}
