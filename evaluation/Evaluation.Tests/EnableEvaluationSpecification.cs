using System;
using System.Linq;
using Evaluation.Common;
using Evaluation.Features.Evaluation;
using Evaluation.Features.EvaluationEnable;
using Xunit;
using FluentAssertions;
using Xunit.Abstractions;

namespace Evaluation.Tests
{
    public class EnableEvaluationSpecification : Specification<EvaluationEnabled, EvaluationEnable>
    {
        private readonly ITestOutputHelper _output;

        public IEvaluationRepository EvaluationRepository { get; set; } = new InMemoryEvaluationRepository();

        public EnableEvaluationSpecification(ITestOutputHelper output)
        {
            _output = output;
        }

        public override EvaluationEnabled Given()
        {
            return null;
        }

        public override EvaluationEnable When()
        {
            return new EvaluationEnable { BookingNumber = 1, GuestId = Guid.Empty };
        }

        [Fact]
        public void Evaluation_Is_Enable_For_A_User()
        {
            IHandle<EvaluationEnable> handler = new EnableEvaluationHandler(EvaluationRepository);
            Setup(handler);
            ProducedEvent = EvaluationRepository.GetEvents().ToList();

            var command = When();
            var expectedEvent = new EvaluationEnabled(
                command.GuestId
                ,command.BookingNumber);

            ProducedEvent.Should().BeEquivalentTo(expectedEvent);
        }

        [Fact]
        public void PrintDocumentation()
        {
            _output.WriteLine($"Given: {Given()}");
            _output.WriteLine($"When: {When()}");
            Evaluation_Is_Enable_For_A_User();

            _output.WriteLine("Expected:");
            foreach (IEvent @event in ProducedEvent)
            {
                _output.WriteLine($"\t{@event}");
            }
        }
    }
}
