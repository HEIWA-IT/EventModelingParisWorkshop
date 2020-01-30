using System;
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
            new BddRequestCleaningHandler("RequestCleaningShouldRaiseCleaningRequested", log).
            Given().
            When(new RequestCleaning(cleaningId, 12, new DateTime(2020, 1, 1))).
            Then(new CleaningRequested(cleaningId, 12, new DateTime(2020, 1, 1)));
        }
    }

    class BddRequestCleaningHandler : Bdd
    {
        public BddRequestCleaningHandler(string name, ITestOutputHelper log) : base(name, log)
        { }

        protected override void Assert()
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
    }
}
