using System.Threading.Tasks;

namespace Evaluation.Domain
{
	public interface IEvalutationRepository
	{
		Task Save(IEvent @event);
	}
}