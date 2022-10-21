#include "Player.hpp"
#include "Assassin.hpp"
#include <string>
#include <vector>
#include <iostream>

namespace coup
{
    class Contessa : public Player
    {
        public:
        Contessa(coup::Game& g, const std::string& n) : coup::Player(g, n)
        {
        }
        virtual std::string role()
        {
            return "Contessa";
        }
        virtual void block(coup::Player& target)
        {
            if(Assassin* v = dynamic_cast<Assassin*>(&target)) 
            {
                if (v->lastTarget != nullptr)
                {
                Player* toRevive = v->lastTarget;
                this->myGame_ref.revivePlayer(toRevive);
                v->lastTarget = nullptr;
                return;
                }
            }
            throw std::invalid_argument("contessa block()");
        }
    };
};