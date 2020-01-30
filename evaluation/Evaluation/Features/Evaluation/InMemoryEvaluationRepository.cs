using Evaluation.Common;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Evaluation.Features.Evaluation
{
	public class InMemoryEvaluationRepository : IEvaluationRepository
	{
		private readonly List<EvaluationPublished> _events;

		public InMemoryEvaluationRepository()
		{
			_events = new List<EvaluationPublished>();
		}

		public Task Save(IEvent @event)
		{
			_events.Add(@event as EvaluationPublished);
			return Task.CompletedTask;
		}

		public IEnumerable<IEvent> GetEvents()
		{
			return _events.AsReadOnly();
		}
	}
}
