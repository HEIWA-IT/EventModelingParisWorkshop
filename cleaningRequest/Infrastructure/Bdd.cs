using System.IO;
using System.Linq;
using System.Text.RegularExpressions;
using Xunit.Abstractions;

namespace Infrastructure
{
    public abstract class Bdd
    {
        protected readonly string name;
        protected readonly ITestOutputHelper log;
        protected IEvent[] given;
        protected ICommand when;
        protected IEvent[] then;

        public Bdd(string name, ITestOutputHelper log)
        {
            this.name = name;
            this.log = log;
        }

        public Bdd Given(params IEvent[] events)
        {
            given = events;
            return this;
        }

        public Bdd When(ICommand command)
        {
            when = command;
            return this;
        }

        public void Then(params IEvent[] events)
        {
            then = events;
            Assert();
            Print();
        }

        protected abstract void Assert();

        private void Print()
        {
            Clear();
            Log(ToLongName(name));
            Log("");
            Log("Given");
            if (given.Length == 0)
                Log("\tnothing special");
            else
            {
                foreach (var evt in given)
                {
                    PrintObject(evt);
                }
            }
            Log("When");
            PrintObject(when);
            Log("Then");
            if (then.Length == 0)
                Log("\tnothing expected");
            else
            {
                foreach (var evt in then)
                {
                    PrintObject(evt);
                }
            }
        }

        private void PrintObject(object evt)
        {
            var type = evt.GetType();
            Log($"\t{ToLongName(type.Name)}");
            foreach (var property in type.GetProperties())
            {
                Log($"\t\t{ToLongName(property.Name)} : {property.GetGetMethod().Invoke(evt, null)}");
            }
        }

        private void Log(string line)
        {
            log.WriteLine(line);
            File.AppendAllLines(GetFilePath(), new[] { line }); //note can be optimized
        }

        string ToLongName(string rawName)
        {
            var words = Regex.Split(rawName, @"(?<!^)(?=[A-Z])");
            return string.Join(" ", words.Select(x => x.ToLower()));
        }

        private void Clear()
        {
            var filePath = GetFilePath();
            var fileInfo = new FileInfo(filePath);
            if (fileInfo.Exists) fileInfo.Delete();
            if(!fileInfo.Directory.Exists) fileInfo.Directory.Create();
        }

        private string GetFilePath()
        {
            var appFolder = new FileInfo(typeof(Bdd).Assembly.Location).Directory.Parent.Parent.Parent.Parent;
            return Path.Combine(appFolder.FullName, "specs" , ToLongName(name) + ".specs.txt");
        }
    }
}