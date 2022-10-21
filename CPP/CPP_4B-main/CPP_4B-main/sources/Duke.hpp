#include "Player.hpp"
#include <string>
#include <vector>
namespace coup
{
    class Duke : public Player
    {
        public:
        Duke(coup::Game& g, const std::string& n): coup::Player(g, n)
        {
        }
        virtual std::string role()
        {
            return "Duke";
        }
        virtual void block(coup::Player& target)
        {
            if(target.coins() >= 2 && target.isBlockable()) 
            {
                target.myCoins -= 2;
                return;
            }
            throw std::invalid_argument("captain block()");
        }
        virtual void tax()
        {
            this->myGame_ref.validTurn(this);
            this->myCoins += 3;
        }   
    };
};