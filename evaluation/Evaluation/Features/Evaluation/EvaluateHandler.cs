using System.Linq;
using System.Threading.Tasks;
using Evaluation.Common;

namespace Evaluation.Features.Evaluation
{
    public class EvaluateHandler : IHandle<Evaluate>
    {
        private readonly IEvaluationRepository _repo;

        public EvaluateHandler(IEvaluationRepository repository)
        {
            _repo = repository;
        }

        public async Task Handle(Evaluate command)
        {
            var evt = new EvaluationPublished(command.GuestId, 
                command.RoomNumber, 
                command.From, 
                command.To, 
                command.Comment, 
                command.Ratings);

            await _repo.Save(evt);

           // var @event = _repo.GetEvents().ToList();
        }
    }
}