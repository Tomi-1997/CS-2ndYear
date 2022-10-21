#include "Schedule.hpp"
#include <algorithm>

constexpr int teams = 20;
namespace bball
{
    Schedule::Schedule(League* l) : t(l), sorted(false)
    {
        play();
        update();
    }

    Schedule::~Schedule()
    {
        for (Team* tp: this->chart)
        {
            // delete tp;
        }
    }

    void Schedule::play()
    {
        for (int i = 0; i < teams; i++)
        {
            for (int j = 0; j < teams; j++)
            {
                if (i != j)
                {
                    Game(t->getTeam(i),t->getTeam(j));
                }
            }
        }
    }

    void Schedule::update()
    {
        for (int i = 0; i < teams; i++)
        {
            this->chart.push_back(this->t->getTeam(i));
        }

        // Sort
        for (size_t i = 0; i < teams; i++)
        {
            for (size_t j = i; j < teams; j++)
            {
                if (this->chart.at(i)->getWins() < this->chart.at(j)->getWins())
                {
                    swap(i, j);
                }
                if (this->chart.at(i)->getWins() == this->chart.at(j)->getWins())
                {
                    if (this->chart.at(i)->getGoalDiff() < this->chart.at(j)->getGoalDiff())
                    {
                        swap(i, j);
                    }
                }
            }
        }
        sorted = true;
    }

    void Schedule::show()
    {
        for (size_t i = 0; i < teams; i++)
        {
            Team* tp = this->chart.at(i);
            cout << i  << ": " << tp->getName() << ", Wins: " << tp->getWins() << " Loses: " << tp->getLoses() << " Diff: " << tp->getGoalDiff() << endl;
        }
    }

    void Schedule::swap(size_t i, size_t j)
    {
        Team* temp = this->chart.at(i);
        this->chart.at(i) = this->chart.at(j);
        this->chart.at(j) = temp;
    }

    void Schedule::graph()
    {
        if (!sorted)
        {
            update();
        }
        cout << "Showing graph by win amount and displaying talent level" << endl;
        for (Team* tp : this->chart)
        {
            for (int i = 0; i < tp->getWins(); i++)
            {
                cout << "_";
            }

            for (int i = tp->getWins(); i < 40; i++)
            {
                cout << " ";
            }

            for (double i = 0; i < tp->getTalent(); i += 0.1)
            {
                cout << "*";
            }
            cout << endl;
        }
    }
}