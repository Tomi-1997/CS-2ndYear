#include "Game.hpp"
namespace bball
{
    void Game::play(Team& home, Team& away)
    {
        // 0 to 45, plus 55 -> 55 to 100, plus talent bonus
        int homePoints = rand() % 46 + 55 + 10 * home.getTalent();
        homePoints = min(100, homePoints);
        // 0 to 50, plus 50 -> 50 to 100, plus talent bonus
        int awayPoints = rand() % 51 + 50 + 10 * away.getTalent();
        awayPoints = min(100, awayPoints);  

        // Init winner to be away, if home has more points, than home is the winner.
        // In a tie, away is the winner, as playing away is harder.
        if (homePoints > awayPoints)
        {
            home.win();
            away.lose();
            if (debug)
            {
                cout << "Home (" << home.getName() << ") team won, result: " << homePoints << "-" << awayPoints << endl;
            }
        }
        
        else
        {
            home.lose();
            away.win();
            if (debug)
            {
                cout << "Away (" << away.getName() << ") team won, result: " << awayPoints << "-" << homePoints << endl;
            }
        }
    }
}