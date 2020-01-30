using System;
using System.IO;
using System.Linq;
using System.Text.RegularExpressions;
using CleaningRequest.Logic;
using Infrastructure;
using NSubstitute;
using Xunit;
using Xunit.Abstractions;

namespace CleaningRequest.Tests
{
    public class RequestCleaningTest
    {
        private readonly ITestOutputHelper log;
        public RequestCleaningTest(ITestOutputHelper log) => this.log = log;

        [Fact]
        public void RequestCleaningShouldRaiseCleaningRequested()
        {
            var cleaningId = Guid.Parse("7c0534ed-ec70-40b7-acb2-52568eea6a9d");
            new Bdd("RequestCleaningShouldRaiseCleaningRequested", log).
            Given().
            When(new RequestCleaning(cleaningId, 12, new DateTime(2020, 1, 1))).
            Then(new CleaningRequested(cleaningId, 12, new DateTime(2020, 1, 1)));
        }
    }

    class Bdd
    {
        private readonly string name;
        private readonly ITestOutputHelper log;
        private IEvent[] given;
        private ICommand when;
        private IEvent[] then;

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

        private void Assert()
        {
            var eventBus = Substitute.For<IEventBus>();
            var commandBus = new CommandBus();
            var sut = new RequestCleaningHandler(eventBus);
            commandBus.Subscribe(sut);
            commandBus.Handle(when);
            foreach (var evt in then)
            {
                eventBus.Received().Publish(evt);
            }
        }

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
