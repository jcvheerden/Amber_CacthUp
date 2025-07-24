using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace prjDoubleLinkedList
{
    public class Blob
    {
        public Node root;

        //checks if the node exists, whats previous and next nodes
        public void add(int number)
        {
            if (number == null)
            {
                return;
            }
            else if (root == null)
            {
                root = new Node(number);
            }
            else
            {
                Node n = new Node(number);
                Node current = root;

                while (current.next != null)
                {
                    current = current.next;
                }
                n.previous = current;
                current.next = n;
            }
        }
    }
}
