#pragma once

#include "Player.hpp"
#include <string>
#include <vector>
namespace coup
{
    class Assassin : public Player
    {
        public:
        Player* lastTarget = nullptr;
        Assassin(coup::Game& g, const std::string& n) : coup::Player(g, n)
        {
        }
        virtual std::string role()
        {
            return "Assassin";
        }
        virtual void transfer(coup::Player& source, coup::Player& target)
        {
            this->myGame_ref.validTurn(this);
        }
        virtual void coup(coup::Player& target)
        {
            if (!this->myGame_ref.isIn(&target) || this->myCoins < 3)
            {
                throw std::invalid_argument("assassin coup()");
            }
            this->myGame_ref.validTurnCoup(this);
            this->myGame_ref.ejectPlayer(&target);

            if (this->myCoins >= 7)
            {
                this->myCoins -= 7;
            }
            else
            {
                this->myCoins -= 3;            
                this->lastTarget = &target;
            }
        }
    };
};