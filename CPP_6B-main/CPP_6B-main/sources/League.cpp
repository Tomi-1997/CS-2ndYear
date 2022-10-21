#include "League.hpp"
#include <algorithm>
#include <iostream>
#include <random>
#include <cmath>

using namespace std;
namespace bball
{
    Team* League::getTeam(int index)
    {
        if (index < 0 || index >= this->myTeams.size())
        {
            return nullptr;
        }
        size_t i = (size_t) index;
        return this->myTeams.at(i);
    }

    void League::printTeams()
    {
        for (Team* tp : myTeams)
        {
            cout << tp->getName() << ", Talent: " << tp->getTalent() << endl;
        }
    }

    League::League(League* ol)
    {
        constexpr int teams = 20;
        for (int i = 0; i < teams; i++)
        {
            Team* temp = ol->getTeam(i);
            myTeams.push_back(new Team(temp->getName(), temp->getTalent()));
        }
    }

    League::League(std::vector<Team*> teams)
    {
        for (Team* tp : teams)
        {
            if (myTeams.size() < 20)
            {
                myTeams.push_back(new Team(tp->getName(), tp->getTalent()));
            }
        }
        std::vector<std::string> type = {"FC ", "SC ", "AC "};
        std::vector<std::string> cities = {"Milan", "Juventus", "Napoli",
                                                "Barcelona", "Madrid", "Valencia",
                                                    "Lyon", "Monaco", "PSG",
                                                        "Chelsea", "Manchester", "Arsenal"};
        std::vector<std::string> team_names;
        for (std::string t : type)
        {
            for (std::string c: cities)
            {
                team_names.push_back(t + c);
            }
        }
        while(myTeams.size() < 20)
        {
            size_t index = (size_t) rand() % team_names.size();
            std::string name = team_names.at(index);
            team_names.erase(team_names.begin() + (int)index);

            std::random_device rd{};
            std::mt19937 gen{rd()};
            // values near the mean are the most likely
            // standard deviation affects the dispersion of generated values from the mean
            std::normal_distribution<> d{0.5,0.2};
            
            double t = (double) (rand() % 10) / 10;
            myTeams.push_back(new Team(name, d(gen)));
        }

    }
    League::League() : myTeams()
    {
        std::vector<std::string> type = {"FC ", "SC ", "AC "};
        std::vector<std::string> cities = {"Milan", "Juventus", "Napoli",
                                                "Barcelona", "Madrid", "Valencia",
                                                    "Lyon", "Monaco", "PSG",
                                                        "Chelsea", "Manchester", "Arsenal"};
        std::vector<std::string> team_names;
        for (std::string t : type)
        {
            for (std::string c: cities)
            {
                team_names.push_back(t + c);
            }
        }
        while(myTeams.size() < 20)
        {
            size_t index = (size_t) rand() % team_names.size();
            std::string name = team_names.at(index);
            team_names.erase(team_names.begin() + (int)index);

            std::random_device rd{};
            std::mt19937 gen{rd()};
            // values near the mean are the most likely
            // standard deviation affects the dispersion of generated values from the mean
            std::normal_distribution<> d{0.5,0.3};
            
            double t = (double) (rand() % 10) / 10;
            myTeams.push_back(new Team(name, d(gen)));
        }
    }

    League::~League()
    {
        for (Team* tp : myTeams)
        {
            delete tp;
        }
    }
}