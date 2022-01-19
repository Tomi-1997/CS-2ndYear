CC = gcc -Wall -g

all: stringProg

stringProg: main.o myLib.o
	gcc -o stringProg main.o myLib.o

main.o: 
	gcc -c main.c

myLib.o:
	gcc -c myLib.c

.PHONY: clean all
clean:
	rm stringProg *.o
