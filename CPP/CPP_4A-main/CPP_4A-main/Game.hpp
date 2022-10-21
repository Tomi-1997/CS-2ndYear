#include <string>
#include <vector>

namespace coup
{
    class Player;
    class Game
    {
        std::vector< coup::Player* > myPlayers;
        std::string currentTurn;
        int myCoins;

        public:
        std::vector<std::string> players();
        void addPlayer(coup::Player* p);
        std::string turn();
        Game();
    };
}
