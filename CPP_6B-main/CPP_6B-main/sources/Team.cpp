#include "Team.hpp"

namespace bball
{
    void Team::win()
    {
        wins++;
        winStreak++;
        if (winStreak > maxWinStreak)
        {
            maxWinStreak = winStreak;
        }
        loseStreak = 0;
    }
    void Team::lose()
    {
        loses++;
        loseStreak++;
        if (loseStreak > maxLoseStreak)
        {
            maxLoseStreak = loseStreak;
        }
        winStreak = 0;
    }
}