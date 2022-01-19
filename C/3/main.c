#include "myLib.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define TXT 1024
#define WORD 30

int main()
{
 	char* word_input = NULL;
     	char* text_input = NULL;
    	char* gematria = NULL;
    	char* abtash = NULL;
       	char* anagram = NULL;

	word_input = get_word();
	if (word_input == NULL) return 1;

	text_input = get_text();
	if (text_input == NULL) return 1;

       	gematria = get_gematria(word_input , text_input);
	if (gematria == NULL) return 1;
	
	abtash = get_abtrash(word_input , text_input);
	if (abtash == NULL) return 1;

	anagram = get_anagram(word_input , text_input);
	if (anagram == NULL) return 1;

       	printf("Gematria Sequences: ");
	puts(gematria);
	
	printf("Atbash Sequences: ");
	puts(abtash);

	printf("Anagram Sequences: ");
	printf("%s", anagram);

	free(word_input);
	free(text_input);
	free(gematria);
	free(abtash);
	free(anagram);

    return 0;
}
