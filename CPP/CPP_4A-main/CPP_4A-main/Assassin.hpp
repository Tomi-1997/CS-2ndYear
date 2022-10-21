#include "Player.hpp"
#include <string>
#include <vector>
namespace coup
{
    class Assassin : public Player
    {
        public:
        Assassin(coup::Game& g, const std::string& n)
         : coup::Player(g, n)
        {
        }
        virtual void transfer(coup::Player& source, coup::Player& target)
        {

        }
    };
};