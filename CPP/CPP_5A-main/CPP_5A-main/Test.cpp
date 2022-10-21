#include "doctest.h"
#include "OrgChart.hpp"

#include <iostream>
#include <stdexcept>
#include <vector>
#include <exception>
#include <string>
#include <algorithm>

using namespace ariel;
using namespace std;

TEST_CASE("Test OrgChart")
{
    CHECK_NOTHROW(OrgChart organization);
    OrgChart organization; // Undefined unless initialized again.

    CHECK_NOTHROW(organization.add_root("Saba"));
    CHECK_NOTHROW(organization.add_sub("Saba", "Parent1"));
    CHECK_NOTHROW(organization.add_sub("Saba", "Parent2"));
    CHECK_NOTHROW(organization.add_sub("Saba", "Parent3"));

    CHECK_NOTHROW(cout << organization << endl);


    CHECK_NOTHROW(organization.add_sub("Parent1", "Child1"));
    CHECK_NOTHROW(organization.add_sub("Parent2", "Child2"));

    CHECK_NOTHROW(cout << organization << endl);

    CHECK_NOTHROW(organization.add_sub("Child1", "GrandChild1"));
    CHECK_NOTHROW(organization.add_sub("Child2", "GrandChild2"));

    CHECK_THROWS(organization.add_sub("Parent4", "ShouldThrow"));
    CHECK_THROWS(organization.add_sub("Parent5", "ShouldThrow"));

    CHECK_THROWS(organization.add_sub("Child3", "ShouldThrow"));
    CHECK_THROWS(organization.add_sub("Child4", "ShouldThrow"));
    

    CHECK_NOTHROW(cout << organization << endl); /*
        Saba
        |---------------|------------|
        Parent1         Parent2      Parent3
        |               |
        Child1          Child2
        |               |
        GrandChild1     GrandChild2
    */

   CHECK_NOTHROW(OrgChart firm);
    OrgChart firm; // Undefined unless initialized again.

    CHECK_NOTHROW(firm.add_root("CEO"));
    CHECK_NOTHROW(firm.add_sub("CEO", "Director_1"));
    CHECK_NOTHROW(firm.add_sub("CEO", "Director_2"));
    CHECK_NOTHROW(firm.add_sub("CEO", "Director_3"));

    CHECK_NOTHROW(firm.add_sub("Director_1", "Manager_1"));
    CHECK_NOTHROW(firm.add_sub("Director_2", "Manager_2"));
    CHECK_NOTHROW(firm.add_sub("Director_3", "Manager_3"));


    CHECK_NOTHROW(firm.add_sub("Manager_1", "Worker"));
    CHECK_NOTHROW(firm.add_sub("Manager_1", "Worker"));

    CHECK_NOTHROW(firm.add_sub("Manager_2", "Worker"));
    CHECK_NOTHROW(firm.add_sub("Manager_2", "Worker"));

    CHECK_NOTHROW(firm.add_sub("Manager_3", "Worker"));
    CHECK_NOTHROW(firm.add_sub("Manager_3", "Worker"));

    CHECK_NOTHROW(firm.add_root("New CEO"));

    CHECK_NOTHROW(cout << firm << endl); /*
        New CEO
        |------------------|------------------|
        Director_1         Director_2         Director_3
        |------------------|------------------|
        Manager_1          Manager_2          Manager_3
        |--------|---------|--------|---------|--------|
        Worker--Worker     Worker--Worker   Worker--Worker
    */
}

TEST_CASE("Test iterators")
{

    CHECK_NOTHROW(OrgChart abcTree);
    OrgChart abcTree; // Undefined unless initialized again.

    CHECK_NOTHROW(abcTree.add_root("A"));

    CHECK_NOTHROW(abcTree.add_sub("A", "B1"));
    CHECK_NOTHROW(abcTree.add_sub("A", "B2"));

    CHECK_NOTHROW(abcTree.add_sub("B1", "C1"));
    CHECK_NOTHROW(abcTree.add_sub("B1", "C2"));
    CHECK_NOTHROW(abcTree.add_sub("B2", "C3"));
    CHECK_NOTHROW(abcTree.add_sub("B2", "C4"));

    CHECK_NOTHROW(abcTree.add_sub("C1", "D1"));
    CHECK_NOTHROW(abcTree.add_sub("C1", "D2"));
    CHECK_NOTHROW(abcTree.add_sub("C2", "D3"));
    CHECK_NOTHROW(abcTree.add_sub("C2", "D4"));
    CHECK_NOTHROW(abcTree.add_sub("C3", "D5"));
    CHECK_NOTHROW(abcTree.add_sub("C3", "D6"));
    CHECK_NOTHROW(abcTree.add_sub("C4", "D7"));
    CHECK_NOTHROW(abcTree.add_sub("C4", "D8"));

    CHECK_NOTHROW(cout << abcTree << endl); /*
        A
        |---------------|
        B1              B2
        |-------|       |-------|
        C1      C2      C3      C4
        |---|   |---|   |---|   |---|
        D1  D2  D3  D4  D5  D6  D7  D8
    */

   auto temp_it = abcTree.begin_level_order();
   CHECK_NOTHROW(temp_it = abcTree.begin_level_order());
   CHECK_NOTHROW(*temp_it);
   CHECK_NOTHROW(temp_it->size());
   CHECK_NOTHROW(++temp_it);
   CHECK_NOTHROW(temp_it++);
   
   bool flag;
   CHECK_NOTHROW(flag = temp_it == temp_it);
   CHECK_NOTHROW(flag = temp_it != abcTree.end_level_order());
   CHECK(flag);
   CHECK(flag);

   std::string ans = "";
   std::string expected = "A B1 B2 C1 C2 C3 C4 D1 D2 D3 D4 D5 D6 D7 D8 ";
   for (auto it = abcTree.begin_level_order(); it != abcTree.end_level_order(); ++it)
    {
        CHECK_NOTHROW(ans += (*it) + " ");
        CHECK_NOTHROW(it->size());
    }
    CHECK( ans == expected );

    for (auto elem : abcTree)
    {
        CHECK_NOTHROW(std::cout << elem << std::endl);
    }

    ans = "";
    expected = "D1 D2 D4 D4 D5 D6 D7 D8 C1 C2 C3 C4 B1 B2 A";
    for (auto it = abcTree.begin_reverse_order(); it != abcTree.reverse_order(); ++it)
        {
            CHECK_NOTHROW(ans += (*it) + " ");
            CHECK_NOTHROW(it->size());
        }
        CHECK( ans == expected );

    ans = "";
    expected = "A B1 C1 D1 D2 C2 D3 D4 B2 C3 D5 D6 C4 D7 D8 ";
    for (auto it = abcTree.begin_preorder(); it != abcTree.end_preorder(); ++it)
        {
            CHECK_NOTHROW(ans += (*it) + " ");
            CHECK_NOTHROW(it->size());
        }
        CHECK( ans == expected );
}