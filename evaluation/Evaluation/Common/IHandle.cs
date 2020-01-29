using System.Threading.Tasks;

namespace Evaluation.Common
{
    public interface IHandle<in T> where T: ICommand
    {
        Task Handle(T command);
    }
}