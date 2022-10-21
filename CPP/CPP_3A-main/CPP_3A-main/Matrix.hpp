#include <vector> 
#include <iostream>

namespace zich
{
    class Matrix
    {
        public:
            // 2D Vector
            // Reference https://www.cplusplus.com/reference/vector/vector/vector/
            std::vector<std::vector<double>> myMat;
            Matrix();
            Matrix(std::vector<double>& v, int n, int m);

            // Operators
            // Reference https://en.cppreference.com/w/cpp/language/operators

            // Output
            friend std::ostream& operator << (std::ostream& out, const Matrix& mat);
            friend Matrix operator*(double num, const Matrix& mat);

            // Modifications, A and B are matrices, c is a scalar.
            Matrix& operator+(const Matrix& other); // A + B
            //Matrix& operator+();                          // A + 1


            // used https://www.tutorialspoint.com/cplusplus/unary_operators_overloading.htm
            // for - unary operation
            Matrix& operator-(const Matrix& other) ;       // A - B
            Matrix& operator-();
            Matrix& operator*(const double num);          // A * c
            Matrix& operator*(const Matrix& other);       // A * B
            Matrix& operator=(const Matrix& other);       // A = B
            Matrix& operator*=(const double num);         // A = A * c


            // Comparison
            bool operator==(const Matrix& other);
            bool operator!=(const Matrix& other);
            bool operator>(const Matrix& other);
            bool operator>=(const Matrix& other);
            bool operator<=(const Matrix& other);
            bool operator<(const Matrix& other);
            
        
    };
}