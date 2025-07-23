namespace prjBasicLinkedLink
{
    internal class Program
    {
        static void Main(string[] args)
        {
            Node n = new Node(6);
            Node na = new Node(7);
            Node nb = new Node(8);

            n.next = na;
            na.next = nb;

            Console.WriteLine(countNodes(n));
        }

        //count nodes from given node
        public static int countNodes(Node root)
        {
            int count = 1;
            Node current = root;
            while (current.next != null)
            {
                count++;
                current = current.next;
            }

            return count;
        }

        //HOMEWORK FOR THURSDAY
        //DO THE FIND NODE METHOD
        //how to find the wexisting nodes

        public static Node findNode(int value, Node root)
        {

        }
    }
}
