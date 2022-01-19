char* get_input();
char* get_word();
char* get_text();

/** Returns the gematric sum of the given word. a = A = 1, b = B = 2 ,... non-letter are 0  **/
int gematria(char* word);

/** For a given letter, returns the abtash letter:
 * a --> z
 * b -- > y
 * c -- > x
 */
char abtash(char c);


char* get_gematria(char* word, char* text);
char* get_abtrash(char* word, char* text);
char* get_anagram(char* word, char* text);
