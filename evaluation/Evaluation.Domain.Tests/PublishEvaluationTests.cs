using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using FluentAssertions;
using Xunit;

namespace Evaluation.Domain.Tests
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
                Ratings = new List<Rating>()
                {
                    new Rating
                    {
                        RateType = "Services",
                        Rate = 4
                    },
                    new Rating
                    {
                        RateType = "Cleaness",
                        Rate = 4
                    },
                    new Rating
                    {
                        RateType = "Food",
                        Rate = 4
                    },
                }
            };

            var inMemoryRepository = new InMemoryRepository();
            var handler = new EvaluateHandler(inMemoryRepository);

            await handler.Handle(command);

            inMemoryRepository.GetEvents().Should().HaveCount(1);
        }
    }
}
