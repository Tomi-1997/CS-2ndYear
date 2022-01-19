LINK = sudo cp libclassloops.so /usr/lib
LINK_R = sudo cp libclassrec.so /usr/lib

BASIC = basicClassification.o
LOOP = advancedClassificationLoop.o
REC = advancedClassificationRec.o
CC = gcc -Wall

.PHONY: all
all: libclassloops.a libclassrec.a libclassrec.so libclassloops.so

loops: libclassloops.a

recursives: libclassrec.a

recursived: libclassrec.so

loopd: libclassloops.so

mains: libclassrec.a 
	${CC} -o mains main.c libclassrec.a

maindloop: libclassloops.so
	${CC} -o maindloop main.c -L. -l:libclassloops.so
	${LINK}

maindrec: libclassrec.so
	${CC} -o maindrec main.c -L. -l:libclassrec.so
	${LINK_R}

libclassloops.so: ${BASIC} ${LOOP}
	${CC} -shared -o libclassloops.so ${BASIC} ${LOOP} -fpic

libclassrec.so: ${BASIC} ${REC}
	${CC} -shared -o libclassrec.so ${BASIC} ${REC} -fpic

libclassrec.a: ${BASIC} ${REC}
	ar rcs libclassrec.a ${BASIC} ${REC}

libclassloops.a: ${BASIC} ${LOOP}
	ar rcs libclassloops.a ${BASIC} ${LOOP}

.PHONY : clean
clean:
	rm -f *.o *.a *.gch *.out *.so 
