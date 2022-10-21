#pragma once
#include "Player.hpp"
#include <string>
#include <vector>
namespace coup
{
    class Captain : public Player
    {
        public:
        Player* lastTarget = nullptr;
        int coinsTook;
        Captain(coup::Game& g, const std::string& n) : coup::Player(g, n), coinsTook(0)
        {
        }
        virtual std::string role()
        {
            return "Captain";
        }
        virtual void block(coup::Player& target)
        {
            if(Captain* v = dynamic_cast<Captain*>(&target)) 
            {
                if (v->lastTarget != nullptr)
                {
                    v->lastTarget->myCoins += coinsTook;
                    v->myCoins -= coinsTook;
                    v->coinsTook = 0;
                    v->lastTarget = nullptr;
                    return;                    
                }
            }
            throw std::invalid_argument("captain block()");
        }
        virtual void steal(coup::Player& target)
        {
            this->myGame_ref.validTurn(this);

            if (target.coins() < 2)
            {
                this->myCoins += target.coins();
                this->coinsTook = target.coins();
            }
            else
            {
                this->myCoins += 2;
                this->coinsTook = 2;
            }

            this->lastTarget = &target;
            target.stealCoins(2);
        }
    };
};