/** *
 * AUTHORS: <Tom Inbar>
 * 
 */

#include "doctest.h"
#include "Matrix.hpp"


#include <string>
#include <algorithm>
using namespace std;
using namespace zich;

TEST_CASE("Valid")
{
    std::vector<double> zero_arr = {0, 0, 0, 0, 0, 0, 0, 0, 0};

    std::vector<double> a_arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    std::vector<double> a_arr_times_two_arr = {2, 4, 6, 8, 10, 12, 14, 16, 18};

    std::vector<double> b_arr = {9, 8, 7, 6, 5, 4, 3, 2, 1};
    std::vector<double> b_arr_times_three_arr = {27, 24, 21, 18, 15, 12, 9, 6, 3};

    std::vector<double> a_plus_b_arr = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10};
    std::vector<double> a_minus_b_arr = {-8, -6, -4, -2, 0, 2, 4, 6, 8};
    std::vector<double> b_minus_a_arr = {8, 6, 4, 2, 0, -2, -4, -6, -8};
    std::vector<double> a_times_b_arr = {30, 24, 18, 84, 69, 54, 138, 114, 90}; // https://matrix.reshish.com/multCalculation.php
    std::vector<double> b_times_a_arr = {90, 114, 138, 54, 69, 84, 18, 24, 30};

    Matrix a{a_arr, 3, 3};
    Matrix b{b_arr, 3, 3};
    Matrix zero{zero_arr, 3, 3};

    Matrix a_plus_b{a_plus_b_arr, 3, 3};
    Matrix a_minus_b{a_minus_b_arr, 3, 3};
    Matrix b_minus_a{b_minus_a_arr, 3, 3};

    Matrix a_times_two{a_arr_times_two_arr, 3, 3};
    Matrix b_times_three{b_arr_times_three_arr, 3, 3};

    Matrix a_times_b{a_times_b_arr, 3, 3};
    Matrix b_times_a{b_times_a_arr, 3, 3};

    /** Equal input **/
    bool ans = a + b == a_plus_b; 
    CHECK(ans);
    ans = b + a == a_plus_b;
    CHECK(ans);
    ans = a - b == a_minus_b;
    CHECK(ans);
    ans = b - a == b_minus_a;
    CHECK(ans);
    ans = a * b == a_times_b;
    CHECK(ans);
    ans = b * a == b_times_a;
    CHECK(ans);
    ans = a*2 == a_times_two;
    CHECK(ans);
    ans = b*3 == b_times_three;
    CHECK(ans);
    ans = (a - a) == zero;
    CHECK(ans);
    ans = b * 0 == zero;
    CHECK(ans);
    ans = 0 * a == zero;
    CHECK(ans);

    /** Unequal input **/
    ans = a + b != a;
    CHECK(ans);
    ans = b - a != b;
    CHECK(ans); 
    ans = a * 3 != a_times_two;
    CHECK(ans);
    ans = 2 * b != b_times_three;
    CHECK(ans);
    ans = a - a != a;
    CHECK(ans);
    ans = a * a != a_times_b;
    CHECK(ans);
    ans = a * b != b_times_a;
    CHECK(ans);

}

TEST_CASE("Invalid")
{
    // 3 by 2
    std::vector<double> a_arr = {1, 2, 3, 4, 5, 6};
    Matrix a{a_arr, 3, 2};

    // 4 by 2
    std::vector<double> b_arr = {1, 2, 3, 4, 5, 6, 7, 8};
    Matrix b{b_arr, 4, 2};

    // Matrix size mismatch between A and B
    CHECK_THROWS(a + b);
    CHECK_THROWS(a - b);
    CHECK_THROWS(bool ans = (a == b));
    CHECK_THROWS(bool ans = (a != b));

    CHECK_THROWS(b * a);
    CHECK_THROWS(a * b);
    CHECK_THROWS( (a+a) + b );

    CHECK_THROWS( Matrix(b_arr, 3, 2)); // Given matrix and size does not match
    CHECK_THROWS( Matrix(a_arr, 4, 2)); // Same

}