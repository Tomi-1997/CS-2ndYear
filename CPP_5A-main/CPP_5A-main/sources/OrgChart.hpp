#include <string>
#include <stdexcept>
#include <iostream>

#define LEVEL 0
#define REVERSE 1
#define PRE 2 

#define DEBUG 0

/*
MISSING
♥ Iterators
♥ Destructor
♥ improve print operator
*/
namespace ariel
{
    class OrgChart
    {
        public:
        OrgChart() : first(nullptr)
        {

        }

        ~OrgChart()
        {

        }

        private:
            struct Node 
            {
                std::string value;
                Node* child;
                Node* rightSibling;
                Node(const std::string& val) 
                : value(val), child(nullptr), rightSibling(nullptr)
                {
                    if (DEBUG)
                    {
                        std::cout << "Node created:" << value << "," << this << "," << child << std::endl;
                    }
                }
            };
        
        Node* first;
        Node* find(const std::string& val, Node* curr)
        {
            if (curr != nullptr)
            {
                if (curr->value == val)
                {
                    return curr;
                }
                Node* a = find(val, curr->rightSibling);
                if (a != nullptr)
                {
                    return a;
                }
                Node* b = find(val, curr->child);
                if (b != nullptr)
                {
                    return b;
                }
            }
            return nullptr;
        }

        public:
        OrgChart& add_root(const std::string& r)
        {
            if (first)
            {
                first->value = r;
            }
            else
            {
                first = new Node(r);
            }
            return *this;
        }

        OrgChart& add_sub(const std::string& dad, const std::string& son)
        {
            /*Check dad is in chart*/
            Node* sub = find(dad, first);
            if (!sub)
            {
                if (DEBUG)
                {
                    std::cout << "Did not find node:"<< dad << std::endl;
                }
                throw std::invalid_argument("add_sub(): left node not in chart.");
            }

            Node** temp = &(sub->child);
            while (*temp)
            {
                temp = &(*temp)->rightSibling;
            }
            *temp = new Node(son);
            return *this;
        }

        void chartDisplay(const Node* n, std::string& ans) const
        {
            if (n != nullptr)
            {
                ans += n->value;
                if (n->rightSibling)
                {
                    ans += " --- ";
                    chartDisplay(n->rightSibling, ans);
                }
                if (n->child)
                {
                    ans += "\n";
                    chartDisplay(n->child, ans);
                }
            }
        }

        friend std::ostream& operator << (std::ostream& out, const OrgChart& oc)
        {
            std::string ans = "";
            oc.chartDisplay(oc.first, ans);
            return out << ans;
        }

        class iterator
        {
          private:
          Node* ptr_curr;
          int mode;
          
          public:
          iterator(Node* ptr = nullptr, int m = 0) 
          : ptr_curr(ptr), mode(m)
          {
          }  
          std::string& operator*()
          {
              return ptr_curr->value;
          }
          std::string* operator->()
          {
              return &(ptr_curr->value);
          }
          iterator& operator++()
          {
              ptr_curr = ptr_curr->child;
              return *this;
          }
          const iterator operator++(int)
          {
              iterator temp = *this;
              ptr_curr = ptr_curr->child;
              return temp;
          }
          bool operator==(const iterator& other) const
          {
              return ptr_curr == other.ptr_curr;
          }
          bool operator!=(const iterator& other) const
          {
              return ptr_curr != other.ptr_curr;
          }
        }; // ITERATOR END


        // Default iterator
        iterator begin()
        {
            return iterator{first, LEVEL};
        }
        iterator end()
        {
            return iterator{nullptr, LEVEL};
        }

        iterator begin_level_order()
        {
            return iterator{first, LEVEL};
        }
        iterator end_level_order()
        {
            return iterator{nullptr, LEVEL};
        }
        iterator begin_reverse_order()
        {
            return iterator{first, REVERSE};
        }
        iterator reverse_order()
        {
            return iterator{nullptr, REVERSE};
        }
        iterator begin_preorder()
        {
            return iterator{first, PRE};
        }
        iterator end_preorder()
        {
            return iterator{nullptr, PRE};
        }
    };
}
