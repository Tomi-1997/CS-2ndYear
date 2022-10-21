#include "Matrix.hpp"



namespace zich
{
    Matrix::Matrix()
    {

    }

     // Friend functions
     
    std::ostream& operator<<(std::ostream& out, const Matrix& mat)
    {
    return out << "" ;
    }

    Matrix operator * (double num, const Matrix& mat)
    {
        return Matrix();
    }
    
    Matrix::Matrix(std::vector<double>& v, int n, int m)
    {

    }
    // Operators
    // Reference https://en.cppreference.com/w/cpp/language/operators

    // Output

    // Matrix& zich::Matrix::operator*(double num, /*const*/ Matrix& mat)
    // {
    // return *this;
    // }
    Matrix& Matrix::operator+( const Matrix& other)
    {
        return *this;
    }
    // zich::Matrix& operator+()
    // {
    //     
    // }
    zich::Matrix& Matrix::operator-( const Matrix& other)
    {
    return *this;
    }
    Matrix& Matrix::operator-()
    {
    return *this;
    }
    Matrix& Matrix::operator*(double num)
    {
    return *this;
    }
    Matrix& Matrix::operator*( const Matrix& other)
    {
        return *this;
    }
    // zich::Matrix& operator=(const Matrix& other)
    // {

    // }
    Matrix& Matrix::operator*=(const double num)
    {
        return *this;
    }
    // Matrix& operator*(const double num, const Matrix& other)
    // {

    // }
    // // Comparison
    bool Matrix::operator==(const Matrix& other)
    {
        return true;
    }
    bool Matrix::operator!=(const Matrix& other)
    {
        return true;
    }
    bool Matrix::operator>(const Matrix& other)
    {
        return true;
    }
    bool Matrix::operator>=(const Matrix& other)
    {
        return true;
    }
    bool Matrix::operator<(const Matrix& other)
    {
        return true;
    }
    bool Matrix::operator<=(const Matrix& other)
    {
        return true;
    }
}