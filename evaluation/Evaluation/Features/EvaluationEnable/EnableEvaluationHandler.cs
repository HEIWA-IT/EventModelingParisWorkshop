using System.Threading.Tasks;
using Evaluation.Common;
using Evaluation.Features.Evaluation;

namespace Evaluation.Features.EvaluationEnable
{
    public class EnableEvaluationHandler : IHandle<EvaluationEnable>
    {
        private readonly IEvaluationRepository _repository;

        public EnableEvaluationHandler(IEvaluationRepository repository)
        {
            _repository = repository;
        }

        public async Task Handle(EvaluationEnable command)
        {
            var evaluationEnabled = new EvaluationEnabled(command.GuestId, command.BookingNumber);

            await _repository.Save(evaluationEnabled);
        }
    }
}