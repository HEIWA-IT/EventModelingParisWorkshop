using Evaluation.Common;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Evaluation.Features.Evaluation
{
	public class InMemoryEvaluationRepository : IEvaluationRepository
	{
		private readonly List<IEvent> _events;

		public InMemoryEvaluationRepository()
		{
			_events = new List<IEvent>();
		}

		public Task Save(IEvent @event)
		{
			_events.Add(@event);
			return Task.CompletedTask;
		}

		public IEnumerable<IEvent> GetEvents()
		{
			return _events.AsReadOnly();
		}
	}
}
