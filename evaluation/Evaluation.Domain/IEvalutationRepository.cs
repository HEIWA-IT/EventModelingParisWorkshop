using System.Collections.Generic;
using System.Threading.Tasks;

namespace Evaluation.Domain
{
	public interface IEvalutationRepository
	{
		Task Save(ReviewPublished @event);

        IEnumerable<ReviewPublished> GetEvents();
    }
}