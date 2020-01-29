using System.Collections.Generic;
using System.Threading.Tasks;

namespace Evaluation.Features.Evaluation
{
	public interface IEvaluationRepository
	{
		Task Save(EvaluationPublished @event);

        IEnumerable<EvaluationPublished> GetEvents();
    }

	public class InMemoryEvaluationRepository : IEvaluationRepository
    {
        private readonly List<EvaluationPublished> _events;

        public InMemoryEvaluationRepository()
        {
            _events = new List<EvaluationPublished>();
        }

        public Task Save(EvaluationPublished @event)
        {
           _events.Add(@event);
           return Task.CompletedTask;
        }

        public IEnumerable<EvaluationPublished> GetEvents()
        {
            return _events.AsReadOnly();
        }
    }
}