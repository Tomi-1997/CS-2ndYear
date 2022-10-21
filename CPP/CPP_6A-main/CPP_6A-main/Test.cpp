#include "doctest.h"
#include "Team.hpp"
#include "Game.hpp"
#include "League.hpp"
#include "Schedule.hpp"

#include <iostream>
#include <stdexcept>
#include <vector>
#include <exception>
#include <string>
#include <algorithm>

using namespace bball;
using namespace std;

TEST_CASE("Test Team")
{
    Team t1("Napoli", 0.7);
    Team t2("Palermo", 0.3);
    Team t3("Parma", 0.1);
    Team t4("Lazio", 0);
    Team t5("Roma", 0.6);
    Team t6("Empoli", 0.2);
    Team t7("Sampdoria", 0.4);

    CHECK_NOTHROW(Team("Napoli", 0.7));
    CHECK_NOTHROW(Team("Palermo", 0.3));
    CHECK_NOTHROW(Team("Parma", 0.1));
    CHECK_NOTHROW(Team("Lazio", 0));
    CHECK_NOTHROW(Team("Roma", 0.6));
    CHECK_NOTHROW(Team("Empoli", 0.2));
    CHECK_NOTHROW(Team("Sampdoria", 0.4));


    // CHECK_THROWS(Team("", 0.4));
    // CHECK_THROWS(Team("throws", 1.4));
    // CHECK_THROWS(Team("throws", -0.2));

    CHECK(t1.getName() == "Napoli");
    CHECK(t2.getWins() == 0);
    CHECK(t3.getTalent() == 0.1);
    CHECK(t4.getLoses() == 0);
    CHECK(t5.getHomeWins() == 0);
    CHECK(t6.getGoalDiff() == 0);
    CHECK(t7.getAwayWins() == 0);
}

TEST_CASE("Test Game")
{
    Team t1("Napoli", 0.7);
    Team t2("Palermo", 0.3);
    Team t3("Parma", 0.1);
    Team t4("Lazio", 0);

    int N = 1000;
    for (int i = 0; i < N; i++)
    {
        CHECK_NOTHROW(Game(t1,t2));
        CHECK_NOTHROW(Game(t3,t4));
    }
}

TEST_CASE("Test League")
{
    // vector<Team*> myTeams;
    // myTeams.push_back(new Team("Golden State Warriors", 1));
    // myTeams.push_back(new Team("Boston Celtics", 0.4));
    // myTeams.push_back(new Team("Brooklyn Nets", 0.5));
    // myTeams.push_back(new Team("Denver Nuggets", 0.3));
    // myTeams.push_back(new Team("Philadelphia 76ers", 0.4));
    // myTeams.push_back(new Team("Minnesota Timberwolves", 0.5));
    // myTeams.push_back(new Team("Chicago Bulls", 0.3));
    // myTeams.push_back(new Team("Portland Trail Blazers", 0.2));
    // myTeams.push_back(new Team("LA Clippers", 0.5));
    // myTeams.push_back(new Team("Los Angeles Lakers", 1));
    // myTeams.push_back(new Team("Milwaukee Bucks", 0.7));
    // myTeams.push_back(new Team("Atlanta Hawks", 0.3));
    // myTeams.push_back(new Team("Charlotte Hornets", 0.2));
    // myTeams.push_back(new Team("Miami Heat", 0.6));
    // myTeams.push_back(new Team("Phoenix Suns", 0.8));
    // myTeams.push_back(new Team("Dallas Mavericks", 0.6));
    // myTeams.push_back(new Team("Memphis Grizzlies", 0.5));
    // myTeams.push_back(new Team("Utah Jazz", 0.3));
    // myTeams.push_back(new Team("Washington Wizards", 0));
    // myTeams.push_back(new Team("Oklahoma City Thunder", 0.1));
}

TEST_CASE("Test Schedule")
{
}