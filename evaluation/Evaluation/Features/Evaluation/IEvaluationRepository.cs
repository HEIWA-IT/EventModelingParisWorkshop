using Evaluation.Common;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Evaluation.Features.Evaluation
{
	public interface IEvaluationRepository
	{
		Task Save(IEvent @event);

		IEnumerable<IEvent> GetEvents();
	}
}
