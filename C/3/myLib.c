#include "myLib.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define TXT 1024
#define WORD 30

// Get value of character ,  a = A = 1 , b = B = 2 , ... z = Z = 25 , everything else is 0
int get_value(char c)
{
	// Not in {a,b,c...z}
	if (c < 65 || c > 122 || ( ( c < 97 ) && ( c > 90 ))) return 0;
	// is uppercase
	if ( c < 91 ) return c - 'A' + 1;
	// is lowercase
	return c - 'a' + 1;
}
// Get pointer to array that has the word
char* get_word()
{
	// Make temporary array for input word,
	// then allocate memory to the actual length plus one more byte for \0
    char temp[WORD+1];
    scanf("%s", temp);

    char* word = malloc(strlen(temp)+1);
    if (word == NULL) return NULL;

    // Return pointer to word
    strcpy(word, temp);
    return word;
}
// Get pointer to array that has the whole text
char* get_text()
{
	char temp[TXT+1];
	// While input is not ~ or smaller than maximum size, continue to get input
	for (int i = 0; i < TXT; ++i)
	{
		char c;
		scanf("%c", &c);
		if (c == '~') 
		{
			temp[i] = '\0';
			break;
		}
		temp[i] = c;
	}
	temp[TXT] = '\0';
	int l = strlen(temp);
	char* text = malloc( l + 1);
	if (text == NULL) return NULL;

	strcpy(text, temp);
	text[l] = '\0';
       	return text;

}
int gematria(char* word)
{
	int ans = 0;
	int i = 0;
	while (*(word+i) != '\0')
	{
		ans = ans + get_value(*(word+i));
		i++;
	}
	return ans;
}
char* get_gematria(char* word, char* text)
{
	char temp[TXT+1];
	int temp_index = 0;

	int t = strlen(text); 
	int word_value = gematria(word);

	for (int i = 0; i < t ; ++i)
	{
		if (text[i] == '\0') break;
		// 
		// go to next loop if current letter is meaningless
		if (get_value(text[i]) == 0)
			continue;

		int z = 0;
		int current_value = 0;

		// For every character with meaning, sum current char and following characters until 
		// the sum is bigger \ equal to the input's sum.
		while (temp[i+z] != '\0' && i+z < t)
		{
			current_value = current_value + get_value(text[i+z]);
			// 
			// if current value is less than word value, nothing to be done
			//
			// if sum of sub text surpassed the desired sum, go to next character.
			if (current_value > word_value)
			{
				current_value = 0;
				break;
			}
		
			// If sub text has equal gematria value to desired value
			// --> start to copy sub text to temp array.
			if (current_value == word_value)
			{
				current_value = 0;
				for (int j = i; j <= i + z; ++j)
				{
					temp[temp_index] = text[j];
					temp_index++;
				}
				temp[temp_index] = '~';
				temp_index++;
				break;
			}
			z++;
		}
	}

	if (temp_index == 0)
		temp_index = 1;
	temp[temp_index-1] = '\0';
	
	char* answer = malloc(strlen(temp)+1);
	if (answer == NULL) return NULL;
	strcpy(answer, temp);
	return answer;

}
char abtash(char c)
{
	// Lowercase
	if ( c > 'Z' )
	{
		return 'z' - c + 'a';
	}
	return 'Z' - c + 'A';
}

char* get_abtrash(char* word, char* text)
{
	char temp[TXT + 1];
	int l = strlen(word);
	int t = strlen(text);
	int temp_index = 0;

	for (int i = 0; i < t; ++i)
	{
		if (text[i] == ' ')
			continue;

		int z = 0;
		int pos = 0;
		while ( i + z < t)
		{
			// Disregard empty spaces
			if ( text[i+z] == ' ')
			{
				z++;
				continue;
			}

			// Check if current letter is equal to abtash of current position
			// of given word or given word's reverse.
			if ( text[i+z] != abtash(word[pos]) && text[i+z] != abtash(word[ l - pos - 1]) )
				break;
			else
			{
				z++;
				pos++;
			}

			if (pos == l)
			{
				for (int j = i; j < i + z; ++j)
				{
					temp[temp_index] = text[j];
					temp_index++;
				}
				temp[temp_index] = '~';
				temp_index++;
				break;
			}
		}
	}

	if (temp_index == 0)
		temp_index = 1;

	temp[temp_index - 1] = '\0';
	char* ans = malloc(temp_index);
	if (ans == NULL) return NULL;

	strcpy(ans, temp);
   	 return ans;
}
char* get_anagram(char* word, char* text)
{
	char temp[TXT + 1];
	int l = strlen(word);
	int t = strlen(text);
	int temp_index = 0;

	int word_ascii[128];
	int temp_ascii[128];

	// Make two helper arrays to count the number of time each letter of the
	// given word appears.
	for (int i = 0; i < 128; i++)
	{
		word_ascii[i] = 0;
		temp_ascii[i] = 0;
	}

	// Count how many times each letter appears.
	for (int i = 0; i < l; i++)
	{
		char current = word[i];
		word_ascii[current]++;
	}

	// for each until i reaches text length minus word length because
	// a subtext can't be an anagram with less letters than the word.
	// but it can be with more (with empty spaces)
	for (int i = 0; i < t - l + 1; ++i)
	{
		if (text[i] == ' ')
			continue;

		int z = 0;
		int sub_text_l = 0;
		while ( i + z < t )
		{
			char current = text[i+z];
			if (current == ' ')
			{
				z++;
				continue;
			}
			temp_ascii[current]++;
			sub_text_l++;
			// Not a subtext - reset temp array for ascii values and 
			// go for next character.
			if (temp_ascii[current] > word_ascii[current]) 
			{
				for (int j = 0; j < 128; ++j)
				{
					temp_ascii[j] = 0;
				}
				break;
			}

			// Valid subtext of word
			// Copy subtext to temp array and then reset helper ascii array
			if (sub_text_l == l)
			{
				for (int j = i; j <= i + z; ++j)
				{
					temp[temp_index] = text[j];
					temp_index++;
				}
				temp[temp_index] = '~';
				temp_index++;
				for (int j = 0; j < 128; ++j)
				{
					temp_ascii[j] = 0;
				}
				break;
			}
			z++;
		}
	}
	if (temp_index == 0)
		temp_index = 1;
	temp[temp_index - 1] = '\0';
	char* ans = malloc(temp_index);
	if (ans == NULL) return NULL;

	strcpy(ans , temp);
	return ans;
}
