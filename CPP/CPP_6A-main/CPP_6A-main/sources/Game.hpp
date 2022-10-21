#include <iostream>
#include <string>
#include <algorithm>

#include "Team.hpp"

#define debug 0
using namespace std;

namespace bball
{
    class Game
        {
            public:
            void play(Team& home, Team& away);
            Game(Team& home, Team& away)
            {
                play(home, away);
            }
        };
}
