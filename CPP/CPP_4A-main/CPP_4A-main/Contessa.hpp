#include "Player.hpp"
#include <string>
#include <vector>
namespace coup
{
    class Contessa : public Player
    {
        public:
        Contessa(coup::Game& g, const std::string& n)
         : coup::Player(g, n)
        {
        }
        virtual void block(coup::Player& target)
        {

        }
    };
};