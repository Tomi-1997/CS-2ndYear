#pragma once

#include <string>
#include <vector>
#include <stdexcept>

namespace coup
{
    class Game;
    class Player
    {
        protected:
        coup::Game& myGame_ref;
        std::string myName;

        public:
        Player(coup::Game& g, const std::string& n)
         : myGame_ref(g), myName(n)
        {
        }
        std::string getName() const
        {
            return this->myName;
        }

        void coup(coup::Player& target);
        void income();
        void foreign_aid();
        int coins();        

        /*The below methods will throw unless implemented by 
        the inheriting class.*/
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
