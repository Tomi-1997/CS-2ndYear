
all: prog

call: clean all

prog: game_of_life.o
	gcc -Wall -o gol game_of_life.o

game_of_life.o: game_of_life.c
	gcc -Wall -c game_of_life.c

.PHONY: clean all call

clean:
	rm *.o gol
