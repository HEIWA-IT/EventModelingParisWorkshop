using System.Collections.Generic;
using System.Threading.Tasks;

namespace Evaluation.Domain.Tests
{
    public class InMemoryRepository : IEvalutationRepository
    {
        private readonly List<ReviewPublished> _events;

        public InMemoryRepository()
        {
            _events = new List<ReviewPublished>();
        }

        public Task Save(ReviewPublished @event)
        {
            _events.Add(@event);
            return Task.CompletedTask;
        }

        public IEnumerable<ReviewPublished> GetEvents()
        {
            return _events.AsReadOnly();
        }
    }
}