#include "Player.hpp"
#include <cassert>

namespace coup
{
    void Player::income()
    {
        this->myGame_ref.validTurn(this);
        this->blockable = false;
        this->myCoins++;
    }
    void Player::foreign_aid()
    {
        this->myGame_ref.validTurn(this);
        this->blockable = true;
        this->myCoins += 2;
    }
}