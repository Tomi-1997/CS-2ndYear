#include "doctest.h"
#include "Player.hpp"
#include "Duke.hpp"
#include "Assassin.hpp"
#include "Ambassador.hpp"
#include "Captain.hpp"
#include "Contessa.hpp"
#include "Game.hpp"

#include <iostream>
#include <stdexcept>
#include <vector>
#include <exception>
#include <string>
#include <algorithm>

using namespace coup;
using namespace std;

TEST_CASE("Valid gameplay with a few exceptions")
{

    // Test constructors
    Game myGame;
    CHECK_NOTHROW(Game myGame{});
	CHECK_NOTHROW( Assassin assassin(myGame, "Assassin guy") );
	CHECK_NOTHROW(Captain captain(myGame, "Captain buddy"));
	CHECK_NOTHROW(Duke duke(myGame, "Duke man"));
	CHECK_NOTHROW(Contessa contessa(myGame, "Contessa girl"));
	CHECK_NOTHROW(Ambassador ambassador(myGame, "Ambassador bro"));
	CHECK_NOTHROW(Contessa contessa(myGame, "Contessa chiquita"));

    // Initialize again as they are out of scope inside CHECKNOTHROW()
    Assassin assassin(myGame, "Assassin guy");
    Captain captain(myGame, "Captain buddy");
    Duke duke(myGame, "Duke man");
    Contessa contessa1(myGame, "Contessa girl");
    Ambassador ambassador(myGame, "Ambassador bro");
    Contessa contessa2(myGame, "Contessa chiquita");

    /* Check players() function of Game */
    string playersExpectation = "Assassin guyCaptain buddyDuke manContessa girlAmbassador broAmbassador broContessa chiquita";
    vector<string> playersFunc;
    CHECK_NOTHROW( playersFunc = myGame.players() );
    string combinedForCheck;
    for(string name : playersFunc)
    {
        combinedForCheck += name;
	}
    CHECK ( combinedForCheck == playersExpectation );

    /* Check turn() function of Game */
    string turnExpected = "Assassin guy";
    string turnFunc;
    CHECK_NOTHROW( myGame.turn() );
    CHECK( turnFunc == turnExpected );

    /* Play game by the order of the players, should not throw */
    CHECK_NOTHROW( assassin.coins() );
	CHECK_NOTHROW( captain.coins() );
	CHECK_NOTHROW( duke.coins() );
	CHECK_NOTHROW( contessa1.coins() );
	CHECK_NOTHROW( ambassador.coins() );
	CHECK_NOTHROW( contessa2.coins() );

    /* Should be back to assassin's turn */

    /* Try to perform actions while it is assassin's turn, should throw because of this. */
    CHECK_THROWS(captain.coins());
    CHECK_THROWS(duke.coins());
    CHECK_THROWS(contessa1.coins());
    CHECK_THROWS(contessa2.coins());
    CHECK_THROWS(ambassador.coins());

    /* Attempt to play the game by the rules 
    ---> All take coins by income()                                                   (round 1 over)

    ---> Assassin takes 2 coins from stack with foreign_aid()
    ---> Captain tries to steal 2 coins from assassin with steal()
    ---> Duke takes 3 coins from stack with tax() 
    ---> Contessa1 takes one coins from stack with income() 
    ---> Ambassador blocks captain from stealing() from assassin by using block()  
    ---> Contessa2 takes two coins from the stack with foreign_aid()                   (round 2 over)*/

    // R1
    CHECK_NOTHROW( assassin.income() );
	CHECK_NOTHROW( captain.income() );
	CHECK_NOTHROW( duke.income() );
	CHECK_NOTHROW( contessa1.income() );
	CHECK_NOTHROW( ambassador.income() );
	CHECK_NOTHROW( contessa2.income() );

    // R2
    /* Check turn() again for each player before action performed. */
    turnExpected = "Assassin guy";
    CHECK_NOTHROW( myGame.turn() );
    CHECK( turnFunc == turnExpected );
    CHECK_NOTHROW( assassin.foreign_aid() );

    /* Check turn() again for each player*/
    turnExpected = "Captain buddy";
    CHECK_NOTHROW( myGame.turn() );
    CHECK( turnFunc == turnExpected );
	CHECK_NOTHROW( captain.steal(assassin) );

    /* Check turn() again for each player*/
    turnExpected = "Duke man";
    CHECK_NOTHROW( myGame.turn() );
    CHECK( turnFunc == turnExpected );
	CHECK_NOTHROW( duke.tax() );

    /* Check turn() again for each player*/
    turnExpected = "Contessa girl";
    CHECK_NOTHROW( myGame.turn() );
    CHECK( turnFunc == turnExpected );
	CHECK_NOTHROW( contessa1.income() );

        /* Check turn() again for each player*/
    turnExpected = "Ambassador bro";
    CHECK_NOTHROW( myGame.turn() );
    CHECK( turnFunc == turnExpected );
	CHECK_NOTHROW( ambassador.block(captain) );

    /* Check turn() again for each player*/
    turnExpected = "Contessa chiquita";
    CHECK_NOTHROW( myGame.turn() );
    CHECK( turnFunc == turnExpected );
	CHECK_NOTHROW( contessa2.foreign_aid() );



}

