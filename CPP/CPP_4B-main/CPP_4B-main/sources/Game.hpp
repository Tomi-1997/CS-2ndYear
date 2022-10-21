#pragma once
#include <string>
#include <vector>

namespace coup
{
    class Player;
    class Game
    {
        std::vector< coup::Player* > myPlayers;
        size_t currentTurnID;
        bool gameOver;
        bool gameStarted;

        public:
        Game();
        void addPlayer(coup::Player* p);
        std::vector<std::string> players();
        std::string turn();
        std::string winner();

        void ejectPlayer(coup::Player* p);
        void revivePlayer(coup::Player* p);

        bool validTurn(coup::Player* p);
        bool validTurnCoup(coup::Player* p);
        bool isIn(coup::Player* p);
    };
}
