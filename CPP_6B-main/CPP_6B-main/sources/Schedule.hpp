#include "League.hpp"
#include "Game.hpp"
using namespace std;
namespace bball
{
    class Schedule
    {
        public:
        Schedule(League* l);
        ~Schedule();
        void show();
        void graph();
        
        private:
        bool sorted;
        void play();
        void update();
        void swap(size_t i, size_t j);
        League* t;
        vector<Team*> chart;
        int longestWS;
        int longestLS;
        int howManyPosDiff;
    };
}