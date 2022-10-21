#include "Player.hpp"
#include "Captain.hpp"
#include <string>
#include <vector>
namespace coup
{
    class Ambassador : public Player
    {
        public:
        Ambassador(coup::Game& g, const std::string& n) : coup::Player(g, n)
        {
        }
        virtual std::string role()
        {
            return "Ambassador";
        }
        virtual void transfer(coup::Player& source, coup::Player& target)
        {
            if (source.coins() == 0)
            {
                throw std::invalid_argument("captain transfer()");
            }
            this->myGame_ref.validTurn(this);
            source.myCoins--;
            target.myCoins++;
        }
        virtual void block(coup::Player& target)
        {
            if(Captain* v = dynamic_cast<Captain*>(&target)) 
            {
                if (v->lastTarget != nullptr)
                {
                    v->lastTarget->myCoins += v->coinsTook;
                    v->myCoins -= v->coinsTook;
                    v->coinsTook = 0;
                    v->lastTarget = nullptr;
                    return;                    
                }
            }
            throw std::invalid_argument("ambassador block()");
        }
    };
};