#include <stdexcept>
#include <algorithm>
#include <iostream>

#include "Game.hpp"
#include "Player.hpp"

constexpr int maxPlayers = 6;
constexpr int maxCoins = 10;
namespace coup
{
    Game::Game() : currentTurnID(0), gameOver(false), gameStarted(false)
    {
        this->myPlayers.reserve(maxPlayers);
    }

    bool Game::isIn(coup::Player* p)
    {
        return std::find(this->myPlayers.begin(), this->myPlayers.end(), p) != this->myPlayers.end();
    }
    
    void Game::addPlayer(coup::Player* p)
    {
        if (this->myPlayers.size() >= maxPlayers)
        {
            throw std::invalid_argument("Number of players can't exceed 6.");
        }
        if (isIn(p))
        {
            throw std::invalid_argument("Can't add same player twice.");
        }
        if (this->gameStarted)
        {
            throw std::invalid_argument("Game already started.");
        }

    p->setTurnID(this->myPlayers.size());
    this->myPlayers.push_back(p);
    }

    void Game::ejectPlayer(coup::Player* p)
    {
        this->myPlayers.erase (this->myPlayers.begin() + p->getTurnID());

        if (this->myPlayers.size() == 1)
        {
            this->gameOver = true;
            return;
        }

        for(size_t i = 0; i < this->myPlayers.size(); i++)
        {
            this->myPlayers[i]->setTurnID(i);
        }

        if (p->getTurnID() < this->currentTurnID)
        {
            this->currentTurnID--;
        }

        if (this->currentTurnID >= this->myPlayers.size())
        {
            this->currentTurnID = 0;
        }
    }

    void Game::revivePlayer(coup::Player* p)
    {
        auto itPos = this->myPlayers.begin() + p->getTurnID();
        this->myPlayers.insert(itPos, p);

        for(size_t i = 0; i < this->myPlayers.size(); i++)
        {
            this->myPlayers[i]->setTurnID(i);
        }

        if (p->getTurnID() < this->currentTurnID)
        {
            this->currentTurnID++;
        }
        
    }

    std::string Game::turn()
    {
        return this->myPlayers.at(this->currentTurnID)->getName();
    }

    std::string Game::winner()
    {
        if (!this->gameOver)
        {
            throw std::invalid_argument("Game not over!");
        }
        return this->myPlayers.at(0)->getName();
    }

    std::vector<std::string> Game::players()
    {
        std::string temp;
        std::vector<std::string> ans;
        for (unsigned i = 0; i < this->myPlayers.size(); i++)
        {
            ans.push_back(this->myPlayers[i]->getName());
            temp += this->myPlayers[i]->getName() + ",";
        }
        return ans;
    }

    bool Game::validTurn(coup::Player* p)
    {
        if (this->myPlayers.size() < 2 || this->currentTurnID != p->getTurnID() || this->gameOver)
        {
            throw std::invalid_argument("Not my turn!");
        }

        if (p->coins() >= maxCoins)
        {
            throw std::invalid_argument("Have to coup!");
        }

        if (!this->gameStarted)
        {
            this->gameStarted = true;
        }
        this->currentTurnID++;
        if (this->currentTurnID == this->myPlayers.size())
        {
            this->currentTurnID = 0;
        }
        return true;
    }

        bool Game::validTurnCoup(coup::Player* p)
    {
        if (this->myPlayers.size() < 2 || this->currentTurnID != p->getTurnID() || this->gameOver)
        {
            throw std::invalid_argument("Not my turn!");
        }

        this->currentTurnID++;
        if (this->currentTurnID == this->myPlayers.size())
        {
            this->currentTurnID = 0;
        }
        return true;
    }
}