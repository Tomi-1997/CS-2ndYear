

#include <iostream>
#include <fstream>
#include <sstream>
#include <stdexcept>
#include <string>

#include "Team.hpp"
#include "Game.hpp"
#include "League.hpp"
#include "Schedule.hpp"

using namespace bball;
using namespace std;

void demo_games()
{
  Team t1("shikagu bools", 1);
  Team t2("el ey laykers", 1);
  cout << t1.getName() << "   VS    " << t2.getName() << endl;
  
  for (int i = 0; i < 1000000; i = i + 2)
  {
    Game g1(&t1,&t2);
    Game g2(&t2,&t1);
  }
  cout << t1.getName() << " won " << t1.getWins() << " games." << endl;
  cout << t2.getName() << " won " << t2.getWins() << " games." << endl;
}

void demo_all()
{
  League l;
  Schedule s(&l);
  s.show();
  s.graph();
}

int main() 
{
  constexpr int N = 10;
  for (int i = 0; i < N; i++)
  {
    demo_all();
  }
  return 0;
}
