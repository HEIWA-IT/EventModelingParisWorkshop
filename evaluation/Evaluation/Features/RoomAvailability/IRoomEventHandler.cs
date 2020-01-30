using Evaluation.Common;

namespace Evaluation.Features.Evaluation
{
	public interface IRoomEventHandler<T> where T : IEvent
	{
		void Apply(T @event);
	}
}