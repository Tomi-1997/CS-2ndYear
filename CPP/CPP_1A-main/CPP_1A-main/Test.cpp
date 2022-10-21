/** *
 * AUTHORS: <Tom Inbar>
 * 
 * Date: 2021-02
 */

#include "doctest.h"
#include "mat.hpp"
using namespace ariel;

#include <string>
#include <algorithm>
using namespace std;

/**
 * Returns the input string without the whitespace characters: space, newline and tab.
 * Requires std=c++2a.
 */
string nospaces(string input) {
	std::erase(input, ' ');
	std::erase(input, '\t');
	std::erase(input, '\n');
	std::erase(input, '\r');
	return input;
}


TEST_CASE("Good input") {
	CHECK(nospaces(mat(1, 3, '!', '-')) == 
	nospaces ("!!!"));

	CHECK(nospaces(mat(9, 7, '@', '-')) == 
	nospaces("@@@@@@@@@"
		"@-------@"
		"@-@@@@@-@"
		"@-@---@-@"
		"@-@@@@@-@"
		"@-------@"
		"@@@@@@@@@"));

	CHECK(nospaces(mat(30, 30, 'A', 'B')) == 
	nospaces( "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
"ABBBBBBBBBBBBBBBBBBBBBBBBBBBBA"
"ABAAAAAAAAAAAAAAAAAAAAAAAAAABA"
"ABABBBBBBBBBBBBBBBBBBBBBBBBABA"
"ABABAAAAAAAAAAAAAAAAAAAAAABABA"
"ABABABBBBBBBBBBBBBBBBBBBBABABA"
"ABABABAAAAAAAAAAAAAAAAAABABABA"
"ABABABABBBBBBBBBBBBBBBBABABABA"
"ABABABABAAAAAAAAAAAAAABABABABA"
"ABABABABABBBBBBBBBBBBABABABABA"
"ABABABABABAAAAAAAAAABABABABABA"
"ABABABABABABBBBBBBBABABABABABA"
"ABABABABABABAAAAAABABABABABABA"
"ABABABABABABABBBBABABABABABABA"
"ABABABABABABABAABABABABABABABA"
"ABABABABABABABAABABABABABABABA"
"ABABABABABABABBBBABABABABABABA"
"ABABABABABABAAAAAABABABABABABA"
"ABABABABABABBBBBBBBABABABABABA"
"ABABABABABAAAAAAAAAABABABABABA"
"ABABABABABBBBBBBBBBBBABABABABA"
"ABABABABAAAAAAAAAAAAAABABABABA"
"ABABABABBBBBBBBBBBBBBBBABABABA"
"ABABABAAAAAAAAAAAAAAAAAABABABA"
"ABABABBBBBBBBBBBBBBBBBBBBABABA"
"ABABAAAAAAAAAAAAAAAAAAAAAABABA"
"ABABBBBBBBBBBBBBBBBBBBBBBBBABA"
"ABAAAAAAAAAAAAAAAAAAAAAAAAAABA"
"ABBBBBBBBBBBBBBBBBBBBBBBBBBBBA"
"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));

	CHECK(nospaces(mat(3, 1, 'A', 'B')) == 
	nospaces("AAA"));

	CHECK(nospaces(mat(5, 7, '|', '_')) == 
	nospaces("||||||___||_|_||_|_||_|_||___||||||"));

	CHECK(nospaces(mat(9, 7, '|', '_')) == 
	nospaces("||||||||||_______||_|||||_||_|___|_||_|||||_||"			"_______||||||||||"));

	CHECK(nospaces(mat(11, 7, '|', '_')) == 
	nospaces("||||||||||||_________||_|||||||_||_|_____|_||_"	"|||||||_||_________||||||||||||"));

	CHECK(nospaces(mat(13, 7, '|', '_')) == 
	nospaces("||||||||||||||___________||_|||||||||_||_|____"	"___|_||_|||||||||_||___________||||||||||||||"));

	CHECK(nospaces(mat(15, 7, '|', '_')) == 
	nospaces("||||||||||||||||_____________||_|||||||||||_||""_|_________|_||_|||||||||||_||_____________||||||||||||||||"));

	CHECK(nospaces(mat(7, 3, '~', '@')) == 
	nospaces("~~~~~~~~@@@@@~~~~~~~~"));

	CHECK(nospaces(mat(7, 7, '~', '@')) == 
	nospaces("~~~~~~~~@@@@@~~@~~~@~~@~@~@~~@~~~@~~@@@@@~~~~~"				"~~~"));

	CHECK(nospaces(mat(7, 11, '~', '@')) == 
	nospaces("~~~~~~~~@@@@@~~@~~~@~~@~@~@~~@~@~@~~@~@~@~~@~@"		"~@~~@~@~@~~@~~~@~~@@@@@~~~~~~~~"));

	CHECK(nospaces(mat(7, 15, '~', '@')) == 
	nospaces("~~~~~~~~@@@@@~~@~~~@~~@~@~@~~@~@~@~~@~@~@~~@~@""~@~~@~@~@~~@~@~@~~@~@~@~~@~@~@~~@~@~@~~@~~~@~~@@@@@~~~~~~~~"));

	CHECK(nospaces(mat(1, 1, '~', '@')) == 
	nospaces("~"));

	CHECK(nospaces(mat(1, 7, '1', '2')) == 
	nospaces("1111111"));

	CHECK(nospaces(mat(7, 1, '1', '2')) == 
	nospaces("1111111"));


}

// MARKED CASES DO NOT COMPILE
TEST_CASE("Bad input") {

    CHECK_THROWS(mat(4, 1, '$', '%'));
    CHECK_THROWS(mat(0,5, '%', '^'));

    CHECK_THROWS(mat(5, 0, '$', '%'));
    CHECK_THROWS(mat('A',5, '%', '^'));


    CHECK_THROWS(mat(-5, 5, '$', '%'));
  //  CHECK_THROWS(mat(5.2,5, '%', '^'));


  //  CHECK_THROWS(mat(0.002, 2, '$', '%'));
 //   CHECK_THROWS(mat("12abcd",5, '%', '^'));


//    CHECK_THROWS(mat(11, 5, '$', '%', extra_arg)); does not compile
    CHECK_THROWS(mat(12,'%', 5, '^'));

  //  CHECK_THROWS(mat(11, 1, '^')); does not compile
  //  CHECK_THROWS(mat(11, 5)); does not compile

}


TEST_CASE("Edge cases")
{
		CHECK(nospaces(mat(1, 1, '@', '-')) == 
	nospaces("@"));

		CHECK(nospaces(mat(1, 1, ' ', ' ')) == 
	nospaces(" "));

		CHECK(nospaces(mat(31, 1, '~', ' ')) == 
	nospaces("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"));

		CHECK(nospaces(mat(5, 3, '@', ' ')) == 
	nospaces  ("@@@@@"
			"@   @"
			"@@@@@"));




}
