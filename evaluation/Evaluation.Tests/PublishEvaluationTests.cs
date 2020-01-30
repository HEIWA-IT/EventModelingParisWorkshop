using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Evaluation.Features.Evaluation;
using FluentAssertions;
using Xunit;

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
            inMemoryRepository.GetEvents().Should().BeEquivalentTo(new []{expectedEvent});
        }
    }
}
