#pragma once

#include <string>
#include <vector>
#include <stdexcept>
#include "Game.hpp"

namespace coup
{
    class Game;
    class Player
    {
        protected:
        coup::Game& myGame_ref;
        std::string myName;
        bool alive;
        bool blockable;
        int turnID;

        public:
        int myCoins;
        Player(coup::Game& g, const std::string& n) : myGame_ref(g), myName(n), myCoins(0), turnID(0), alive(true), blockable(false)
        {
            myGame_ref.addPlayer(this);
        }

        bool isBlockable() const
        {
            return this->blockable;
        }
        std::string getName() const
        {
            return this->myName;
        }

        void setTurnID(int t)
        {
            this->turnID = t;
        }

        int getTurnID() const
        {
            return this->turnID;
        }

        bool isAlive() const
        {
            return this->alive;
        }

        int coins() const
        {
            return this->myCoins;
        }

        void stealCoins(int n)
        {
            if (n > this->myCoins)
            {
            this->myCoins = 0;
            }
            else
            {
            this->myCoins -= n;                
            }
        }

        void income();
        void foreign_aid();

        virtual void coup(coup::Player& target)
        {
            if (!this->myGame_ref.isIn(&target) || this->myCoins < 7)
            {
                throw std::invalid_argument("player coup()");
            }
            this->myGame_ref.validTurnCoup(this);
            this->myGame_ref.ejectPlayer(&target);
            this->myCoins -= 7;
        }

        /*The below methods will throw unless implemented by 
        the inheriting class.*/
        virtual std::string role()
        {
            throw std::invalid_argument("Unimplemented role()");
        }
        virtual void block(coup::Player& target)
        {
            throw std::invalid_argument("Unimplemented block()");
        }
        virtual void transfer(coup::Player& source, coup::Player& target)
        {
            throw std::invalid_argument("Unimplemented transfer()");
        }
        virtual void steal(coup::Player& target)
        {
            throw std::invalid_argument("Unimplemented steal()");
        }
        virtual void tax()
        {
            throw std::invalid_argument("Unimplemented tax()");
        }
    };
}
