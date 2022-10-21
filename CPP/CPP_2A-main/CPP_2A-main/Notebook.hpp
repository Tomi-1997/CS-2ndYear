#include "Direction.hpp"
#include <string>

namespace ariel
{
    class Notebook
    {
        public:
        void write(int page, int row, int col, Direction dir, const std::string &word);

	    std::string read(int page, int row, int col, Direction dir, int len);

        void erase(int page, int row, int col, Direction dir, int len);

        void show(int range);
    };
}
