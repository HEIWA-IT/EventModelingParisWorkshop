using System.Threading.Tasks;

namespace Evaluation.Domain
{
	public interface IHandles<T> where T: ICommand
	{
		Task Handle(T command);
	}
}