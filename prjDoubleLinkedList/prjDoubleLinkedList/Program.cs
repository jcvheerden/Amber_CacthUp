namespace prjDoubleLinkedList
{
    internal class Program
    {
        static void Main(string[] args)
        {
            Blob blob = new Blob();
            blob.add(6);
            blob.add(5);
            blob.add(4);
            blob.add(3);
            blob.add(2);
            blob.add(1);
            blob.add(0);

            Node root = blob.root;
            Node newNode = root.next.next.next.next; //separate single objectsm, can loop through them all
            Console.WriteLine("Head node value: " + root.value);
            Console.WriteLine("New node value: " + newNode.value);
            Console.WriteLine("Total nodes: " + countNodes(root));
        }

        public static int countNodes(Node root)
        {
            int count = 1;
            Node current = root;
            while (current.next != null)
            {
                current = current.next;
                count++;
            }

            return count;
        }

        /*HOMEWORK FOR TOMORROW*/
        public static Node getLast(Node root)
        {

        }

        public static Node getFirst(Node middle)
        {

        }

        //find inside the list and indicate that spot and show the full list of all nodes
        //find first spot of thTat node and print out the entire list that comes after
        public static void findNodePrintAll(Node current)
        {

        }
    }
}
