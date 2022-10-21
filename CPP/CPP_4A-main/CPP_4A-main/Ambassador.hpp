#include "Player.hpp"
#include <string>
#include <vector>
namespace coup
{
    class Ambassador : public Player
    {
        public:
        Ambassador(coup::Game& g, const std::string& n)
         : coup::Player(g, n)
        {
        }
        virtual void transfer(coup::Player& source, coup::Player& target)
        {

        }
        virtual void block(coup::Player& target)
        {

        }
    };
};