#pragma once
#include "Team.hpp"
#include <vector>
#include <string>

namespace bball
{
    class League
    {
        public:
            League();
            League(std::vector<Team*> teams);
            League(League* ol);
            ~League();
            void printTeams();
            Team* getTeam(int index);
        private:
        std::vector<Team*> myTeams;
    };
}