#include "Game.hpp"
#include <random>
#include <cmath>

namespace bball
{
    void Game::play(Team* home, Team* away)
    {

        std::random_device rd{};
        std::mt19937 gen{rd()};
    
        // values near the mean are the most likely
        // standard deviation affects the dispersion of generated values from the mean
        std::normal_distribution<> d{6,2};

        // 0 to 45, plus 55 -> 55 to 100, plus talent bonus
        int homePoints = rand() % 46 + 55 + std::round(d(gen)) * home->getTalent();
        homePoints = min(100, homePoints);
        // 0 to 50, plus 50 -> 50 to 100, plus talent bonus
        int awayPoints = rand() % 51 + 50 + std::round(d(gen)) * away->getTalent();
        awayPoints = min(100, awayPoints);  

        // Init winner to be away, if home has more points, than home is the winner.
        // In a tie, away is the winner, as playing away is harder.
        if (homePoints > awayPoints)
        {
            home->win();
            home->homeWins++;
            away->lose();
            if (debug)
            {
                cout << "Home (" << home->getName() << ") team won, result: " << homePoints << "-" << awayPoints << endl;
            }
        }
        
        else
        {
            home->lose();
            away->win();
            away->awayWins++;
            if (debug)
            {
                cout << "Away (" << away->getName() << ") team won, result: " << awayPoints << "-" << homePoints << endl;
            }
        }

        home->goalDiff += homePoints - awayPoints;
        away->goalDiff += awayPoints - homePoints;
    }
}