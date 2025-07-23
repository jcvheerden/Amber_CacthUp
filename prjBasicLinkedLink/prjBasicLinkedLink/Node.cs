using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace prjBasicLinkedLink
{
    class Node
    {
        //stores current value and the next node in front of it
        //start from root node and search through other nodes, they each direct to the next node until the search result is found
        public int value;
        public Node next;

        public Node(int value)
        {
            this.value = value;
        }
    }
}
