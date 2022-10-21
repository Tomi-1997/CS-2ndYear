#include "Player.hpp"
#include <string>
#include <vector>
namespace coup
{
    class Captain : public Player
    {
        public:
        Captain(coup::Game& g, const std::string& n)
         : coup::Player(g, n)
        {
        }
        virtual void block(coup::Player& target)
        {

        }
        virtual void steal(coup::Player& target)
        {

        }
    };
};