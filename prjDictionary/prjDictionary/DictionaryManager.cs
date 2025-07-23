using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace prjDictionary
{
    public class DictionaryManager
    {
        private Dictionary<string, int> _dictionary;

        public DictionaryManager()
        {
            _dictionary = new Dictionary<string, int>();
        }

        public void AddEntry(string Name, int Mark)
        {
            if (!_dictionary.ContainsKey(Name))
            {
                _dictionary.Add(Name, Mark);
            }
            else
            {
                Console.WriteLine($"Student '{Name}' already exists with value {_dictionary[Name]}.");
            }
        }

        public int? GetMark(string Name)
        {
            if (_dictionary.TryGetValue(Name, out int mark))
            {
                return mark;

            }
            else
            {
                Console.WriteLine($"Student '{Name}' not found.");
                return null;
            }
        }

        public bool RemoveEntry(string Name)
        {
            if (_dictionary.Remove(Name))
            {
                Console.WriteLine($"Student '{Name}' removed successfully.");
                return true;
            }
            else
            {
                Console.WriteLine($"Student '{Name}' not found.");
                return false;
            }
        }

        public void DisplayEntries()
        {
            if (_dictionary.Count == 0)
            {
                Console.WriteLine("No entries in dictionary");
            }
            foreach (var entry in _dictionary)
            {
                Console.WriteLine($"Name: '{entry.Key}', Mark: {entry.Value}.");
            }
        }
    }
}

