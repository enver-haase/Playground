
CPP := em++

INCDIR := include

EM_PRELOAD_FLAGS := --use-preload-plugins \
                    --preload-file data/room00.bmp \
                    --preload-file data/room01.bmp \
                    --preload-file data/room02.bmp \
                    --preload-file data/room03.bmp \
                    --preload-file data/room04.bmp \
                    --preload-file data/room05.bmp \
                    --preload-file data/room06.bmp \
                    --preload-file data/room07.bmp \
                    --preload-file data/room08.bmp \
                    --preload-file data/room09.bmp \
                    --preload-file data/room10.bmp \
                    --preload-file data/room11.bmp \
                    --preload-file data/room12.bmp \
                    --preload-file data/room13.bmp \
                    --preload-file data/room14.bmp \
                    --preload-file data/room15.bmp \
                    --preload-file data/room16.bmp \
                    --preload-file data/room17.bmp \
                    --preload-file data/room18.bmp \
                    --preload-file data/room19.bmp \
                    --preload-file data/room20.bmp \
                    --preload-file data/room21.bmp \
                    --preload-file data/room22.bmp \
                    --preload-file data/room23.bmp \
                    --preload-file data/room24.bmp \
                    --preload-file data/room25.bmp \
                    --preload-file data/room26.bmp

CPP_FILES := $(wildcard src/*.cpp)
OBJ_FILES := $(addprefix obj/,$(notdir $(CPP_FILES:.cpp=.o)))
LD_FLAGS  := $(EM_PRELOAD_FLAGS) -s ALLOW_MEMORY_GROWTH=1
CC_FLAGS  := -I$(INCDIR) -Wall -Werror

atari.html: $(OBJ_FILES)
	$(CPP) -o $@ $^ $(LD_FLAGS)
   
obj/%.o: src/%.cpp
	$(CPP) $(CC_FLAGS) -c -o $@ $<

clean:
	rm -rf obj/*.o *~ src/*~ include/*~ core atari *.html *.js *.data

clone-hpp:
	cp $(HOME)/svn/atari-adventure/atari-adventure/trunk/Atari-CDG/Atari-CDG/*hpp include/

clone-cpp:
	cp $(HOME)/svn/atari-adventure/atari-adventure/trunk/Atari-CDG/Atari-CDG/*cpp src/

clone-data:
	cp $(HOME)/svn/atari-adventure/atari-adventure/trunk/Atari-CDG/Atari-CDG/data/* data/

clone:	clone-hpp clone-cpp clone-data
