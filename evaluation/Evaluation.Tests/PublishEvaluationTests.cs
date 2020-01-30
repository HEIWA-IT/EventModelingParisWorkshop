using Evaluation.Common;
using Evaluation.Features.Evaluation;
using FluentAssertions;
using System;
using System.Linq;
using System.Threading.Tasks;
using Xunit;
using Xunit.Abstractions;

namespace Evaluation.Tests
{
	public class PublishEvaluationTests
	{
		[Fact]
		public async Task Publish_an_evaluation_create_an_review_published_eventAsync()
		{
			var command = new Evaluate
			{
				Comment = "Great Hotel",
				From = new DateTime(2020, 1, 29),
				To = new DateTime(2020, 2, 5),
				GuestId = Guid.NewGuid(),
				RoomNumber = 1,
				Ratings = new[]
				{
					new Rating {RateType = "Services", Rate = 4},
					new Rating {RateType = "Cleaness", Rate = 4 },
					new Rating {RateType = "Food", Rate = 4},
				}
			};
			var inMemoryRepository = new InMemoryEvaluationRepository();
			var handler = new EvaluateHandler(inMemoryRepository);

			await handler.Handle(command);

			var expectedEvent = new EvaluationPublished(command.GuestId, command.RoomNumber, command.From, command.To, command.Comment, command.Ratings);
			inMemoryRepository.GetEvents().Should().BeEquivalentTo(new[] { expectedEvent });
		}


	}

	public class EvaluationSpecification : Specification<EvaluationPublished, Evaluate>
	{
		private readonly ITestOutputHelper _output;
		public IEvaluationRepository EvaluationRepository { get; set; } = new InMemoryEvaluationRepository();

		public EvaluationSpecification(ITestOutputHelper output)
		{
			_output = output;
		}

		public override EvaluationPublished Given()
		{
			return null;
		}

		public override Evaluate When()
		{
			return new Evaluate();
		}

		[Fact]
		public void Publish_an_evaluation_create_an_review_published_event()
		{
			IHandle<Evaluate> handler = new EvaluateHandler(EvaluationRepository);
			Setup(handler);
			ProducedEvent = EvaluationRepository.GetEvents().ToList();

			Evaluate command = When();
			var expectedEvent = new EvaluationPublished(
				command.GuestId,
				command.RoomNumber,
				command.From,
				command.To,
				command.Comment,
				command.Ratings);

			ProducedEvent.Should().BeEquivalentTo(expectedEvent);
		}

		[Fact]
		public void PrintDocumentation()
		{
			//_output.WriteLine(Given()?.ToString());
			_output.WriteLine(When().ToString());
			Publish_an_evaluation_create_an_review_published_event();

			foreach (IEvent @event in ProducedEvent)
			{
				_output.WriteLine(@event.ToString());
			}
		}

		//public void DocumentSpecification(Specification s)
		//{
		////printf(for each given …)
		////printf when command
		////	Produced foreach printf

		////	Given a series of events
		////When a command
		////	Then a series of event or exception
		//}
	}

	//public void DocumentSpecification(Specification s)
	//{
	////printf(for each given …)
	////printf when command
	////	Produced foreach printf

	////	Given a series of events
	////When a command
	////	Then a series of event or exception
	//}

}
