using Evaluation.Common;

namespace Evaluation.Features.GuestEvaluations
{
	public interface IEventHandler<T> where T : IEvent
	{
		void Apply(T @event);
	}
}