GCC = gcc -Wall -g

all: graph

call: clean graph

graph: myLib.a main.o
	$(CC) -o graph main.o myLib.a

myLib.a: graph.o
	ar -rcs myLib.a graph.o

main.o: main.c
	$(CC) -c main.c

graph.o: graph.c graph.h
	$(CC) -c graph.c

.PHONY : clean all call
clean:
	rm -f *.o *.a graph 
