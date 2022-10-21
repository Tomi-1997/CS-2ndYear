#include "Player.hpp"
#include <string>
#include <vector>
namespace coup
{
    class Duke : public Player
    {
        public:
        Duke(coup::Game& g, const std::string& n)
         : coup::Player(g, n)
        {
        }
        virtual void block(coup::Player& target)
        {

        }
        virtual void tax()
        {
            
        }
    };
};