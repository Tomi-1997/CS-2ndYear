CC= gcc -Wall -g

all:  connections

connections: my_lib.a main.o
	$(CC) -o connections main.o my_lib.a -lm

my_lib.a: my_mat.o
	ar -rcs my_lib.a my_mat.o

my_mat.o: my_mat.c my_mat.h
	$(CC) -c my_mat.c

main.o: main.c my_mat.h
	$(CC) -c main.c

.PHONY : clean all
clean:
	rm -f *.o *.a *.out connections 
