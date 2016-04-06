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
    var event = new CustomEvent('emscripten_done', { 'returnCode':  'false' });\
\
    document.dispatchEvent(event); \
    } catch (ex) { \
      alert(ex); \
    } \
    /*alert('Event dispatched.'); */ ");
    
#endif

    return 0;
}
