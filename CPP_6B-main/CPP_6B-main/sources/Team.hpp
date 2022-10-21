#pragma once
#include <string>
using namespace std;

namespace bball
{
    class Team
        {
            string name;
            double talent;          

            public:
            int wins;
            int winStreak;
            int maxWinStreak;
            int loses;
            int loseStreak;
            int maxLoseStreak;
            int goalDiff; // Plus means the team scored more goals than was scored on, negative for the opposite.

            // Two extra data bits, is the team more comfortable at home than away?
            int homeWins;
            int awayWins;

            Team(const string& n, double t) : name(n), talent(t), wins(0), winStreak(0), maxWinStreak(0), loses(0), 
            loseStreak(0), maxLoseStreak(0), goalDiff(0), homeWins(0), awayWins(0)
            {
                //
            }
            string getName() const
            {
                return name;
            }
            double getTalent() const
            {
                return talent;
            }
            
            void win();
            void lose();

            int getWins() const
            {
                return wins;
            }
            int getWinStreak() const
            {
                return winStreak;
            }
            int getMaxWinStreak() const
            {
                return maxWinStreak;
            }
            int getLoses() const
            {
                return loses;
            }
            int getLoseStreak() const
            {
                return loseStreak;
            }
            int getMaxLoseStreak() const
            {
                return maxLoseStreak;
            }
            int getGoalDiff() const
            {
                return goalDiff;
            }
            int getHomeWins() const
            {
                return homeWins;
            }
            int getAwayWins() const
            {
                return awayWins;
            }
        };
}
