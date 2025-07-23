namespace prjDictionary
{
    internal class Program
    {
        static void Main(string[] args)
        {
            DictionaryManager manager = new DictionaryManager();
            bool exit = false;

            while (!exit)
            {
                Console.WriteLine("\nMenu");
                Console.WriteLine("1. Add Entry");
                Console.WriteLine("2. Search for an entry");
                Console.WriteLine("3. Remove an entry");
                Console.WriteLine("4. Display all entries");
                Console.WriteLine("5. Exit");
                Console.Write("Choose an option 1-5: ");

                string choice = Console.ReadLine();
                switch (choice)
                {
                    case "1":
                        Console.Write("Enter student name: ");
                        string nameToAdd = Console.ReadLine();
                        Console.Write("Enter student mark: ");
                        if (int.TryParse(Console.ReadLine(), out int markToAdd))
                        {
                            manager.AddEntry(nameToAdd, markToAdd);
                        }
                        else
                        {
                            Console.WriteLine("Invalid mark; Please enter a valid integer.");
                        }
                        break;
                    case "2":
                        Console.Write("Enter student name to search: ");
                        string nameToSearch = Console.ReadLine();
                        int? mark = manager.GetMark(nameToSearch);
                        if (mark.HasValue)
                        {
                            Console.WriteLine($"Mark for {nameToSearch}: {mark.Value}");
                        }
                        break;
                    case "3":
                        Console.Write("Enter the name to remove");
                        string nameToRemove = Console.ReadLine();
                        manager.RemoveEntry(nameToRemove);
                        break;
                    case "4":
                        manager.DisplayEntries();
                        break;
                    case "5":
                        exit = true;    
                        Console.WriteLine("Exiting the program.");
                        break;
                    default:
                        Console.WriteLine("Invalid choice; Please select a valid option (1 - 5)");
                }
            }
        }
    }
}
